package roarusko.simpleTimeTracker.model.data.file;

import java.util.List;
import java.util.stream.Collectors;

import roarusko.simpleTimeTracker.model.data.ChildDAO;
import roarusko.simpleTimeTracker.model.domain.ChildObject;
import roarusko.simpleTimeTracker.model.domain.ParentObject;

/**
 * Abstrakti luokka, josta peritään tiedostoihin dataa tallentavat luokat. Tarjoaa geneeriset metodit
 * domain-objektien luomiselle, lukemiselle, muokkaamiselle ja poistamiselle.
 * @author aleks
 * @version 2 Mar 2020
 *
 * @param <T> ChildObject-rajapinnan toteuttava luokka
 * @param <P> ParentObject-rajapinnan toteuttava luokka
 */
public abstract class AbstractChildDAO_File<T extends ChildObject, P extends ParentObject> extends AbstractDAO_File<T> implements ChildDAO<Integer, T, P> {
    
    
    /**
     * DAO:n konstruktori. Tarvitsee parametrina polun käsiteltävälle tiedostolle. Luo tiedoston
     * jos sitä ei ole olemassa. Luo yksilöiviä tunnuksia antavan IdGeneraattorin, joka alustetaan
     * antamaan suurempia arvoja kuin tiedostoon tallennetuista Id-tunnuksista suurin.
     * @param pathToFile Polku käsiteltävälle tiedostolle.
     */
    protected AbstractChildDAO_File(String pathToFile) {
        super(pathToFile);
    }
    
    /**
     * Suodattaa annetusta listasta halutun omistajan oliot, eli esimerkiksi tietyn käyttäjän projektit
     * tai tietyn projektin merkinnät.
     * @param object ParentObject-rajapinnan toteuttava olio (User tai Project), jolle kuuluvat domain-objektit halutaan listata
     * @return Palauttaa suodatetun listan T-tyyppisiä olioita
     */
    @Override
    public List<T> list(P object) {
        /*
         * Kaikista tiedostoon tallennetuista riveistä
         * muodostetaan ensin olio list()-metodin avulla, ja näistä suodataan attribuuttien
         * perusteella halutut oliot. Tämä kelvatkoon, sillä tiedostoon kirjoitus ja luku
         * on vain välivaihe ennen tietokantaan siirtymistä harjoitustyön myöhemmässä vaiheessa.
         */
        List<T> objects = list().stream()
                .filter(e -> ((ChildObject) e).getOwnerId() == object.getId())
                .collect(Collectors.toList());
            return objects;
    }
    
}
