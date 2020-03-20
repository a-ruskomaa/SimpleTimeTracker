package roarusko.simpleTimeTracker.model.data.file;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import roarusko.simpleTimeTracker.model.data.DAO;
import roarusko.simpleTimeTracker.model.domain.DataObject;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

/**
 * Abstrakti luokka, josta peritään tiedostoihin dataa tallentavat luokat. Tarjoaa geneeriset metodit
 * domain-objektien luomiselle, lukemiselle, muokkaamiselle ja poistamiselle.
 * @author aleks
 * @version 2 Mar 2020
 *
 * @param <T> DataObject-rajapinnan toteuttava luokka
 */
public abstract class AbstractDAOFile<T extends DataObject> implements DAO<Integer, T> {
    private final IdGenerator idGen;
    private final Path path;
    
    
    /**
     * DAO:n konstruktori. Tarvitsee parametrina polun käsiteltävälle tiedostolle. Luo tiedoston
     * jos sitä ei ole olemassa. Luo yksilöiviä tunnuksia antavan IdGeneraattorin, joka alustetaan
     * antamaan suurempia arvoja kuin tiedostoon tallennetuista Id-tunnuksista suurin.
     * @param pathToFile Polku käsiteltävälle tiedostolle.
     */
    protected AbstractDAOFile(String pathToFile) {
        this.path = createIfNotExists(Paths.get(pathToFile));
        this.idGen = new IdGenerator(getMaxId(path) + 1);
    }
    
    
    /**
     * Metodi, jonka avulla merkkijonosta luodaan halutun tyyppisiä olioita. Toteutus vaihtelee
     * luokkakohtaisesti.
     * @param row Tiedostosta luettu tekstirivi, joka sisältää olion luomiseen tarvittavan datan.
     * @return Palauttaa luodun olion
     */
    protected abstract T parseObject(String row);
    
    
    /**
     * Metodi, jonka avulla oliosta luodaan tiedostoon tallennettava merkkijono. Toteutus vaihtelee
     * luokkakohtaisesti.
     * @param object Olio, jonka sisältämä data halutaan tallentaa
     * @return Palauttaa muodostetun merkkijonon
     */
    protected abstract String objectToStringForm(T object);
    

    @Override
    public T create(T object) {
        // Luodaan FileWriter-luokan avulla tiedostoon uusi rivi
        try (FileWriter fw = new FileWriter(path.toFile(), true)) {
            // Asetetaan tallennettavalle oliolle yksilöivä id, tallennetaan tiedostoon
            object.setId(idGen.getNewId());
            fw.write(objectToStringForm(object) + "\n");
            fw.close();
            return object;
        } catch (IOException e ){
            // Mahdollinen tilanteessa, jossa käsiteltävä tiedosto on poistettu ohjelman käynnistyksen jälkeen
            e.printStackTrace();
            return object;
        }
    }
    
    
    @Override
    public T read(Integer key) {
        // TODO parempi poikkeuskäsittely
        T object;
        try {
            // Luetaan tiedostosta kaikki rivit, suodatetaan hakuavainta vastaavat rivit ja palautetaan näistä ensimmäinen (eli ainoa)
            String row = Files.lines(path)
                    .filter(e -> rowMatchesId(key, e))
                    .findFirst()
                    .get();
            object = parseObject(row);
        } catch (IOException e) {
            // Mahdollinen tilanteessa, jossa käsiteltävä tiedosto on poistettu ohjelman käynnistyksen jälkeen
            System.out.println("File not found! " + e.getMessage());
            return null;
        } catch (NullPointerException e) {
            // On mahdollista, mikäli tallennustiedoston sisältämä data ei vastaa ohjelman käytössä olevaa dataa
            System.out.println("Object not found! " + e.getMessage());
            return null;
        }
        return object;
    }
    

    @Override
    public boolean update(T object) {
        // TODO parempi poikkeuskäsittely, voisi heittää poikkeuksen jos muokkaus epäonnistuu
        boolean updated = false;
        try {
            // Luetaan tiedostosta kaikki rivit ja tallennetaan listalle.
            List<String> rows = Files.readAllLines(path);
            
            for (int i = 0; i < rows.size(); i++) {
                // Muokataan haluttua riviä
                if (rowMatchesId(object.getId(), rows.get(i))) {
                    rows.set(i, objectToStringForm(object));
                    updated = true;
                }
            }
            // Kirjoitetaan koko tiedosto uusiksi ja korvataan sillä aiempi
            Files.write(path, rows);
        } catch (IOException e) {
            // Mahdollinen tilanteessa, jossa käsiteltävä tiedosto on poistettu ohjelman käynnistyksen jälkeen
            e.printStackTrace();
        }
        return updated;
    }


    @Override
    public boolean delete(Integer key) {
        boolean deleted = false;
        try {
            // Luetaan tiedostosta kaikki rivit ja tallennetaan listalle.
            List<String> rows = Files.readAllLines(path);
            
            for (int i = 0; i < rows.size(); i++) {
                // Poistetaan haluttu rivi
                if (rowMatchesId(key, rows.get(i))) {
                    rows.remove(i);
                    deleted = true;
                }
            }

            // Kirjoitetaan jäljelle jääneet rivit tiedostoon, korvataan aiempi
            Files.write(path, rows);
        } catch (IOException e) {
            // Mahdollinen tilanteessa, jossa käsiteltävä tiedosto on poistettu ohjelman käynnistyksen jälkeen
            e.printStackTrace();
        }
        return deleted;
    }
    
    
    @Override
    public List<T> list() {
        try {
            // Luetaan tiedostosta kaikki rivit ja muodostetaan jokaiselta riviltä luetusta datasta olio
            List<T> objects = Files.lines(path)
                    .map(row -> parseObject(row))
                    .collect(Collectors.toList());
            return objects;
        } catch (IOException e) {
            // Mahdollinen tilanteessa, jossa käsiteltävä tiedosto on poistettu ohjelman käynnistyksen jälkeen
            System.out.println("File not found! " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("File not found! " + e.getMessage());
        }
        return new ArrayList<T>();
    }
    

    
    
    // Apumetodit
    
    /**
     * Etsii käsiteltävästä tiedostosta suurimman käytössä olevan Id-tunnuksen
     * @param pathToFile Polku käsiteltävään tiedostoon
     * @return Palauttaa kokonaislukuna suurimman löydetyn id-tunnuksen tai 0, mikäli tunnuksia ei ole
     */
    private final int getMaxId(Path pathToFile) {
        int max = 0;
        try {
            max = Files.lines(pathToFile)
                    .mapToInt(row -> parseId(row))
                    .max()
                    .orElse(0);
        } catch (IOException e) {
            // Ei pitäisi olla mahdollinen, sillä tiedoston olemassa olo on varmistettu aiemmassa vaiheessa
            System.out.println("File not found: " + e.getMessage());
        }
        return max;
    }
    
    
    /**
     * Tutkii vastaako annettu rivi annettua id-tunnusta
     */
    private final boolean rowMatchesId(Integer key, String row) {
        return parseId(row) == key;
    }
    
    
    /**
     * Etsii annetusta merkkijonosta id-tunnuksen ja palauttaa sen kokonaislukuna
     */
    private final int parseId(String row) {
        return Integer.parseUnsignedInt(row, 0, row.indexOf(','), 10);
    }

    
    /**
     * Tarkistaa onko parametrina annettu tiedosto olemassa. Luo tarvittaessa tiedoston
     * ja tarvittavan hakemistorakenteen.
     * @param pathToFile Polku haluttuun tiedostoon
     * @return Palauttaa polun
     */
    private Path createIfNotExists(Path pathToFile) {
        if (Files.exists(pathToFile)) return pathToFile;
        try {
            Files.createDirectories(pathToFile.getParent());
            return Files.createFile(pathToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
