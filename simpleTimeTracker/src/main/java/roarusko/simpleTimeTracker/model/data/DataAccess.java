package roarusko.simpleTimeTracker.model.data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import roarusko.simpleTimeTracker.model.data.file.EntryDAOFile;
import roarusko.simpleTimeTracker.model.data.file.ProjectDAOFile;
import roarusko.simpleTimeTracker.model.data.file.UserDAOFile;
import roarusko.simpleTimeTracker.model.domain.DataObject;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.EntryTimer;

/**
 * @author aleks
 * @version 28 Jan 2020
 *
 * Luokka, jonka avulla ohjelman tilaa - kuten valittua projektia tai merkintää - ylläpidetään.
 * Toimii fasilitaattorina näkymää kontrolloivien luokkien ja dataa mallintavien luokkien välilä.
 * 
 * Samanaikaisesti voi olla valittuna vain yksi käyttäjä, yksi tälle käyttäjälle kuuluva projekti
 * sekä yksi projektiin kuuluva merkintä.
 * 
 * Luokka sisältää metodit tietyn käyttäjän, projektin sekä merkinnän valitsemiselle ja hakemiselle.
 * 
 * Luokasta luodaan vain yksi instanssi ohjelman käynnistyksen yhteydessä.
 * Toimii ainoana tiedonkulkuväylänä ohjelman kontrolleriluokkien, käyttöliittymän sekä
 * mallinnettavan datan välillä.
 * 
 * Viitteet attribuutteihin välitetään pääasiallisesti propertyina, jolloin käyttölittymäkomponentit
 * voi sitoa suoraan seuraamaan esim. valittuna olevan projektin merkintöjä.
 */
public class DataAccess {

    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final EntryDAO entryDAO;


    
    /**
     * DataAccessin konstruktori. Saa parametreinaan käyttäjien, projektien ja merkintöjen
     * tietokantahakuja toteuttavat DAO-rajapinnan toteuttavat oliot.
     * @param userDAO Olio, joka suorittaa käyttäjiin liittyvät tietokantahaut
     * @param projectDAO Olio, joka suorittaa projekteihin liittyvät tietokantahaut
     * @param entryDAO Olio, joka suorittaa merkintöihin liittyvät tietokantahaut
     */
    public DataAccess(UserDAO userDAO, ProjectDAO projectDAO, EntryDAO entryDAO) {
        this.userDAO = userDAO;
        this.projectDAO = projectDAO;
        this.entryDAO = entryDAO;

        System.out.println("dataAccess luotu!");
    }


    /**
     * Luo uuden kättäjän. Käyttäjä luodaan aluksi väliaikaisella id-numerolla -1. UserDAO:n create-metodi lisää
     * käyttäjän tietokantaan, hakee käyttäjälle yksilöivän id:n tietokannasta ja palauttaa käyttäjän jonka id vastaa
     * tietokantaan tallennettua id-numeroa.
     * @param name Nimi, joka käyttäjälle halutaan antaa.
     * @return Palauttaa luodun käyttäjän.
     */
    public User addUser(String name) {
        return this.userDAO.create(new User(name));
    }


    /**
     * Luo uuden projektin. Projekti luodaan aluksi väliaikaisella id-numerolla -1. ProjectDAO:n create-metodi lisää
     * projektin tietokantaan, hakee projektille yksilöivän id:n tietokannasta ja palauttaa projektin jonka id vastaa
     * tietokantaan tallennettua id-numeroa.
     * @param name Nimi, joka projektille halutaan antaa.
     * @param user Käyttäjä, jolle projekti kuuluu
     * @return Palauttaa luodun projektin
     */
    public Project addProject(String name, User user) {
        return this.projectDAO.create(new Project(user.getId(), name));
    }

    
    /**
     * Luo uuden aikamerkinnän oletusalustuksella (alku- sekä loppuaika asetettuna nykyhetkeen ja id: -1).
     * @param project Projekti jolle merkintä lisätään
     * @return Palauttaa luodun merkinnän.
     */
    public Entry newEntry(Project project) {
        return new Entry(project.getId());
    }
    
    /**
     * Luo uuden aikamerkinnän tilapäisellä id:llä -1 alustettuna annettuun alku- ja loppuaikaan.
     * @param project Projekti jolle merkintä lisätään
     * @param start Merkinnän alkuaika
     * @param end Merkinnän loppuaika
     * @return Palauttaa luodun merkinnän.
     */
    public Entry newEntry(Project project, LocalDateTime start, LocalDateTime end) {
        return new Entry(project.getId(), start, end);
    }


    /**
     * Tallentaa annetun merkinnän tietokantaan. Mikäli kyseessä on uusi merkintä, lisätään
     * tietokantaan uusi merkintä ja tietokannan palauttama id tallennetaan luotuun merkintään. Aiemmin luodun
     * merkinnän tapauksessa etsitään tietokannasta muokattavan merkinnän id:tä vastaava
     * merkintä ja korvataan se muokatulla merkinnällä.
     * @param entry Merkintä joka tallennetaan
     * @return Palauttaa tallennetun merkinnän
     */
    public Entry commitEntry(Entry entry) {
        if (entry.getOwnerId() == -1) {
            // TODO tälle poikkeus joka avaa dialogin
            System.out.println("Projektin id ei saa olla -1");
        }
        if (entry.getId() == -1) {
            return saveNewEntry(entry);
        }
        if (!saveExistingEntry(entry)) {
            // TODO poikkeus + dialogilla ilmoitus ohjelman käyttäjälle
            System.out.println("Merkinnän muokkaaminen epäonnistui!");
        }
        return entry;
    }


    /**
     * Tallentaa uuden merkinnän tietokantaan. EntryDAO lisää merkinnälle yksilöivän id:n tietokantaan
     * tallennuksen yhteydessä.
     * @param entry Merkintä joka halutaan tallentaa
     * @return Palauttaa lisätyn merkinnän
     */
    private Entry saveNewEntry(Entry entry) {
        return this.entryDAO.create(entry);
    }


    /**
     * Päivittää aiemmin luodun merkinnän tiedot tietokantaan. EntryDAO etsii merkinnän id:n perusteella
     * oikean merkinnän ja päivittää sen tietoja.
     * 
     * @param entry Merkintä joka halutaan tallentaa
     * @return Palauttaa false jos muokkaus epäonnistuu
     */
    private boolean saveExistingEntry(Entry entry) {
        return this.entryDAO.update(entry);
    }
    
    
    
    public boolean deleteEntry(Entry entry) {
        return this.entryDAO.delete(entry.getId());
    }

    /**
     * Hakee listalta valitun DataObjectin indeksin. Palauttaa -1 jos ei löycy.
     * @param object Objekti jonka indeksi listalla halutaan selvittää
     * @param list Lista jolta etsitään
     * @return Palauttaa indeksin tai -1 jos ei löydy
     */
    public int findIndexById(DataObject object,
            List<? extends DataObject> list) {
        ListIterator<? extends DataObject> iterator = list.listIterator();

        while (iterator.hasNext()) {
            int index = iterator.nextIndex();
            if (iterator.next().getId() == object.getId())
                return index;
        }

        return -1;
    }


    /**
     * Hakee kaikkien käyttäjien tiedot tietokannasta ja tallentaa ne listalle
     * @return palauttaa käyttäjät ObservableList:llä
     */
    public ObservableList<User> loadUsers() {
        System.out.println("ladataan käyttäjät");
        ObservableList<User> users = FXCollections
                .observableArrayList(this.userDAO.list());
        return users;
    }


    /**
     * Hakee annetun käyttäjän kaikkien projektien tiedot tietokannasta ja palauttaa ne listalla.
     * @param user Käyttäjä jonka tiedot haetaan
     * @return palauttaa projektit ObservableList:llä
     */
    public ObservableList<Project> loadProjects(User user) {
        System.out.println("ladataan projektit");
        ObservableList<Project> projects = FXCollections
                .observableArrayList(this.projectDAO.list(user));
        return projects;
    }


    /**
     * Hakee annetun projektin kaikkien merkintöjen tiedot tietokannasta ja palauttaa ne listalla
     * @param project Projekti jonka merkinnät haetaan
     * @return palauttaa merkinnät ObservableList:llä
     */
    public ObservableList<Entry> loadEntries(Project project) {
        System.out.println("ladataan merkinnät");
        ObservableList<Entry> entries = FXCollections
                .observableArrayList(this.entryDAO.list(project));
        return entries;
    }




}
