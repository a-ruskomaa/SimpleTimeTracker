package roarusko.simpleTimeTracker.model.data;

import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;

/**
 * @author aleks
 * @version 02.03.2020
 *
 * Luokka, jonka avulla muu ohjelma osaa lukea ja kirjoittaa pysyvään muistiin tallennettua dataa. Tarjoaa rajapinnan,
 * jonka avulla muun ohjelman ei tarvitse välittää valitusta tallennustavasta.
 * 
 * Tällä hetkellä toimii lähinnä fasilitaattorina, ja kutsuu DAO-rajapinnan toteuttavien avustavien luokkien metodeja,
 * jolla varsinainen tiedostoihin kirjoitus ja luku tapahtuu. Jatkossa huolehtii enemmän myös poikkeuskäsittelystä ja
 * käsiteltävän datan oikeellisuudesta.
 * 
 * Metodien määrän kasvaessa siirryttäneen geneerisempään toteutustapaan.
 */
public class DataAccessImpl implements DataAccess {

    private final DAO<Integer, User> userDAO;
    private final ChildDAO<Integer, Project, User> projectDAO;
    private final ChildDAO<Integer, Entry, Project> entryDAO;

    
    /**
     * DataAccessin konstruktori. Saa parametreinaan käyttäjien, projektien ja merkintöjen
     * tietokantahakuja suorittavat DAO-rajapinnan toteuttavat oliot.
     * @param userDAO Olio, joka suorittaa käyttäjiin liittyvät tietokantahaut
     * @param projectDAO Olio, joka suorittaa projekteihin liittyvät tietokantahaut
     * @param entryDAO Olio, joka suorittaa merkintöihin liittyvät tietokantahaut
     */
    public DataAccessImpl(DAO<Integer, User> userDAO, ChildDAO<Integer, Project, User> projectDAO, ChildDAO<Integer, Entry, Project> entryDAO) {
        this.userDAO = userDAO;
        this.projectDAO = projectDAO;
        this.entryDAO = entryDAO;
    }


    /**
     * Luo uuden kättäjän. Käyttäjä luodaan aluksi väliaikaisella id-numerolla -1. UserDAO:n create-metodi lisää
     * käyttäjän tietokantaan, hakee käyttäjälle yksilöivän id:n tietokannasta ja palauttaa käyttäjän jonka id vastaa
     * tietokantaan tallennettua id-numeroa.
     * @param name Nimi, joka käyttäjälle halutaan antaa.
     * @return Palauttaa luodun käyttäjän.
     */
    @Override
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
    @Override
    public Project addProject(String name, User user) {
        return this.projectDAO.create(new Project(user.getId(), name));
    }

    
    /**
     * Luo uuden aikamerkinnän oletusalustuksella (alku- sekä loppuaika asetettuna nykyhetkeen ja id: -1).
     * @param project Projekti jolle merkintä lisätään
     * @return Palauttaa luodun merkinnän.
     */
    @Override
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
    @Override
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
    @Override
    public Entry commitEntry(Entry entry) {
        // TODO projektiin kenttä, johon tallentuu viimeisimmän luodun merkinnän aikaleima. Tähän kentän päivitys.
        if (entry.getOwnerId() == -1) {
            // TODO tälle poikkeus jonka avulla voidaan näyttää varoitusdialogi
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
    
    
    
    /**
     * Poistaa annetun merkinnän tiedot tietokannasta. EntryDAO etsii merkinnän id:n perusteella poistettavan
     * merkinnän.
     * @param entry Merkintä joka halutaan poistaa
     * @return Palauttaa false jos poisto epäonnistui
     */
    @Override
    public boolean deleteEntry(Entry entry) {
        return this.entryDAO.delete(entry.getId());
    }

   


    /**
     * Hakee kaikkien käyttäjien tiedot tietokannasta ja tallentaa ne listalle
     * @return palauttaa käyttäjät ObservableList:llä
     */
    @Override
    public ObservableList<User> loadUsers() {
        ObservableList<User> users = FXCollections
                .observableArrayList(this.userDAO.list());
        return users;
    }


    /**
     * Hakee annetun käyttäjän kaikkien projektien tiedot tietokannasta ja palauttaa ne listalla.
     * @param user Käyttäjä jonka tiedot haetaan
     * @return palauttaa projektit ObservableList:llä
     */
    @Override
    public ObservableList<Project> loadProjects(User user) {
        ObservableList<Project> projects = FXCollections
                .observableArrayList(this.projectDAO.list(user));
        return projects;
    }


    /**
     * Hakee annetun projektin kaikkien merkintöjen tiedot tietokannasta ja palauttaa ne listalla
     * @param project Projekti jonka merkinnät haetaan
     * @return palauttaa merkinnät ObservableList:llä
     */
    @Override
    public ObservableList<Entry> loadEntries(Project project) {
        ObservableList<Entry> entries = FXCollections
                .observableArrayList(this.entryDAO.list(project));
        return entries;
    }


}
