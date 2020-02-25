package roarusko.simpleTimeTracker.model;

import java.util.List;
import java.util.ListIterator;

import roarusko.simpleTimeTracker.model.data.EntryDAO;
import roarusko.simpleTimeTracker.model.data.ProjectDAO;
import roarusko.simpleTimeTracker.model.data.UserDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockEntryDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockProjectDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockUserDAO;
import roarusko.simpleTimeTracker.model.domain.DataObject;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.EntryTimer;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
public class ModelAccess {

    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final EntryDAO entryDAO;

    private final ListProperty<User> allUsers = new SimpleListProperty<User>();
    private final ObjectProperty<User> selectedUser = new SimpleObjectProperty<User>();

    private final ListProperty<Project> userProjects = new SimpleListProperty<Project>();
    private final ObjectProperty<Project> selectedProject = new SimpleObjectProperty<Project>();

    private final ListProperty<Entry> projectEntries = new SimpleListProperty<Entry>();
    private final ObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<Entry>();
    private final ObjectProperty<Entry> editedEntry = new SimpleObjectProperty<Entry>();

    private EntryTimer timer;

    
    /**
     * Luo Data Access -oliot ja lataa kaikkien käyttäjien tiedot tietokannasta.
     * 
     * Asettaa tapahtumankäsittelijät reagoimaan käyttäjän ja projektin vaihtamiselle.
     * 
     * Käyttäjän vaihtaminen lataa automaattisesti muistiin kaikki valitulle käyttäjälle kuuluvat projektit.
     * 
     * Projektin vaihtaminen lataa automaattisesti muistiin kaikki valittuun projektiin kuuluvat mnerkinnät.
     * 
     * TODO tämä poistetaan kunhan on opittu käyttämään Mockitoa testaamiseen!
     * 
     * @param userDAO userDAO
     * @param projectDAO projectDAO
     * @param entryDAO entryDAO
     */
    public ModelAccess(UserDAO userDAO, ProjectDAO projectDAO, EntryDAO entryDAO) {
        this.userDAO = userDAO;
        this.projectDAO = projectDAO;
        this.entryDAO = entryDAO;
        

        ChangeListener<User> userChangeListener = createUserChangeListener();
        selectedUser.addListener(userChangeListener);

        ChangeListener<Project> projectChangeListener = createProjectChangeListener();
        selectedProject.addListener(projectChangeListener);

        this.timer = new EntryTimer(this);

        loadUsers();

        selectedUser.set(allUsers.isEmpty() ? null : allUsers.get(0));

        System.out.println("modelAccess luotu!");
    }
    
    /**
     * ModelAccessin oletuskonstruktori. 
     */
    public ModelAccess() {
        this(new MockUserDAO(), new MockProjectDAO(), new MockEntryDAO());
    };


    /**
     * Luo tapahtumankäsittelijän kuuntelemaan valitun projektin vaihtumista.
     * Aina projektin vaihtuessa lataa tietokannasta valitulle projektille kuuluvat merkinnät
     * ja asettaa ne ListPropertyyn jota käyttöliittymä voi kuunnella. Valitsee ensimmäisenä listalla
     * olevan merkinnän jos lista ei ole tyhjä.
     * 
     * TODO listan järjestys aikajärjestykseen, uusimman merkinnän valinta oletukseksi
     * 
     * @return Palauttaa luodun tapahtumankäsittelijän
     */
    public ChangeListener<Project> createProjectChangeListener() {
        ChangeListener<Project> projectChangeListener = (observable, oldProject,
                newProject) -> {
            System.out.println("valittu projekti vaihtui: " + newProject);

            ObservableList<Entry> entries = loadEntries();

            this.projectEntries.set(entries);

            this.selectedEntry.set(entries.isEmpty() ? null : entries.get(0));
        };

        return projectChangeListener;
    }


    /**
     * Luo tapahtumankäsittelijän kuuntelemaan valitun käyttäjän vaihtumista.
     * Aina käyttäjän vaihtuessa lataa tietokannasta valitulle käyttäjälle kuuluvat projektit ja
     * asettaa ne ListPropertyyn jota käyttöliittymä voi kuunnella. Valitsee ensimmäisenä listalla
     * olevan projektin jos lista ei ole tyhjä.
     * 
     * TODO listan järjestys uusimman merkinnän mukaan?
     * 
     * @return Palauttaa luodun tapahtumankäsittelijän
     */
    public ChangeListener<User> createUserChangeListener() {
        ChangeListener<User> userChangeListener = (observable, oldUser,
                newUser) -> {
            System.out.println("valittu käyttäjä vaihtui: " + newUser);

            ObservableList<Project> projects = loadProjects();

            this.userProjects.set(projects);

            this.selectedProject
                    .set(projects.isEmpty() ? null : projects.get(0));

        };

        return userChangeListener;
    }


    /**
     * Luo uuden kättäjän. Käyttäjä luodaan aluksi väliaikaisella id-numerolla -1. UserDAO:n create-metodi lisää
     * käyttäjän tietokantaan, hakee käyttäjälle yksilöivän id:n tietokannasta ja palauttaa käyttäjän jonka id vastaa
     * tietokantaan tallennettua id-numeroa. Lisää käyttäjän kaikki ohjelman tiedossa olevat käyttäjät sisältävälle listalle.
     * @param name Nimi, joka käyttäjälle halutaan antaa.
     */
    public void addUser(String name) {
        User newUser = this.userDAO.create(new User(name));
        allUsers.add(newUser);
        selectedUser.set(newUser);
    }


    /**
     * Luo uuden projektin. Projekti luodaan aluksi väliaikaisella id-numerolla -1. ProjectDAO:n create-metodi lisää
     * projektin tietokantaan, hakee projektille yksilöivän id:n tietokannasta ja palauttaa projektin jonka id vastaa
     * tietokantaan tallennettua id-numeroa. Lisää projektin kaikki valitun käyttäjän projektit sisältävälle listalle.
     * @param name Nimi, joka projektille halutaan antaa.
     */
    public void addProject(String name) {
        User _selectedUser = getSelectedUser();
        Project newProject = this.projectDAO
                .create(new Project(name, _selectedUser));
        userProjects.add(newProject);
        selectedProject.set(newProject);
    }


    /**
     * Luo uuden aikamerkinnän oletusalustuksella (alku- sekä loppuaika asetettuna nykyhetkeen ja id: -1).
     * @return Palauttaa luodun merkinnän.
     */
    public Entry newEntry() {
        Project _selectedProject = getSelectedProject();
        Entry newEntry = new Entry(_selectedProject);
        return newEntry;
    }


    /**
     * Tallentaa muokattavaksi merkityn merkinnän tietokantaan. Mikäli kyseessä on uusi merkintä, lisätään
     * tietokantaan uusi merkintä ja tietokannan palauttama id tallennetaan luotuun merkintään. Aiemmin luodun
     * merkinnän tapauksessa etsitään merkinnät sisältävältä listalta muokattavan merkinnän id:tä vastaava
     * merkintä ja korvataan se muokatulla merkinnällä.
     * @return Palauttaa false jos merkinnän tallentaminen epäonnistui
     */
    public boolean commitEntry() {
        Entry edited = editedEntry.get();
        Project _selectedProject = edited.getOwner();

        if (!getSelectedProject().equals(_selectedProject)) {
            System.out.println(
                    "Muokattava projekti ei vastaa valittua projektia");
            return false;
        }

        if (edited.getId() == -1) {
            return saveNewEntry(edited);
        }

        return saveExistingEntry(edited);
    }


    /**
     * Tallentaa uuden merkinnän tietokantaan. EntryDAO lisää merkinnälle yksilöivän id:n tietokantaan
     * tallennuksen yhteydessä.
     * @param entry Merkintä joka halutaan tallentaa
     * @return Palauttaa false jos lisäys epäonnistuu
     */
    private boolean saveNewEntry(Entry entry) {
        Entry newEntry = this.entryDAO.create(entry);
        projectEntries.add(newEntry);
        return true;
    }


    /**
     * Päivittää aiemmin luodun merkinnän tiedot tietokantaan. EntryDAO etsii merkinnän id:n perusteella
     * oikean merkinnän ja päivittää sen tietoja. Metodi etsii myös projektin merkinnät sisältävältä
     * listalta id:n perusteella oikean merkinnän ja korvaa sen muokatulla merkinnällä.
     * 
     * @param entry Merkintä joka halutaan tallentaa
     * @return Palauttaa false jos muokkaus epäonnistuu
     */
    private boolean saveExistingEntry(Entry entry) {
        Entry newEntry = this.entryDAO.update(entry);

        int i = findIndexById(newEntry, projectEntries.get());

        if (i == -1) {
            System.out.println("merkintää ei löydy");
            return false;
        }

        projectEntries.set(i, newEntry);
        selectedEntry.set(newEntry);
        return true;
    }


    /**
     * Hakee listalta valitun DataObjectin indeksin. Palauttaa -1 jos ei löycy.
     * @param object Objekti jonka indeksi listalla halutaan selvittää
     * @param list Lista jolta etsitään
     * @return Palauttaa indeksin tai -1 jos ei löydy
     */
    private int findIndexById(DataObject object,
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
    private ObservableList<User> loadUsers() {
        System.out.println("ladataan käyttäjät");
        ObservableList<User> users = FXCollections
                .observableArrayList(this.userDAO.list());
        this.allUsers.set(users);
        return allUsers;
    }


    /**
     * Hakee valittuna olevan käyttäjän kaikkien projektien tiedot tietokannasta. Tallentaa projektit listalle ja asettaa
     * listan ListPropertyyn jota voidaan seurata suoraan käyttöliittymästä.
     * @return palauttaa projektit ObservableList:llä
     */
    private ObservableList<Project> loadProjects() {
        System.out.println("ladataan projektit");
        User _selectedUser = getSelectedUser();
        ObservableList<Project> projects = FXCollections
                .observableArrayList(this.projectDAO.list(_selectedUser));
        userProjects.set(projects);
        return projects;
    }


    /**
     * Hakee valittuna olevan projektin kaikkien merkintöjen tiedot tietokannasta. Tallentaa merkinnät listalle projektiolioon.
     * @return palauttaa merkinnät ObservableList:llä
     */
    private ObservableList<Entry> loadEntries() {
        System.out.println("ladataan merkinnät");
        Project _selectedProject = getSelectedProject();
        ObservableList<Entry> entries = FXCollections
                .observableArrayList(this.entryDAO.list(_selectedProject));
        projectEntries.set(entries);
        return entries;
    }

    
    // Getterit

    /**
     * Valitun käyttäjän getteri
     * @return palauttaa valitun käyttäjän
     */
    public User getSelectedUser() {
        return selectedUser.get();
    }


    /**
     * Valitun projektin getteri
     * @return palauttaa ohjelmassa valitun projektin
     */
    public Project getSelectedProject() {
        return this.selectedProject.get();
    }


    /**
     * Valitun merkinnän getteri
     * @return palauttaa valitun merkinnän
     */
    public Entry getSelectedEntry() {
        return selectedEntry.get();
    }
    

    /**
     * Ajastimen getteri
     * @return palauttaa viitteen ohjelman ajastimeen
     */
    public EntryTimer getTimer() {
        return this.timer;
    }


    // Setterit

    /**
     * Asettaa parametrinaan saamansa käyttäjän valituksi
     * @param selectedUser käyttäjä joka on valittuna
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser.set(selectedUser);
    }
    
    
    /**
     * Asettaa parametrinaan saamansa projektin valituksi
     * @param selectedProject valittu projekti
     */
    public void setSelectedProject(Project selectedProject) {
        this.selectedProject.set(selectedProject);
    }
    
    
    /**
     * Asettaa parametrinaan saamansa merkinnän valituksi
     * @param entry valittu merkintä
     */
    public void setSelectedEntry(Entry entry) {
        this.selectedEntry.set(entry);
    }


    /**
     * Asettaa parametrinaan saamansa merkinnän valituksi muokattavaksi
     * @param entry valittu merkintä
     */
    public void setEditedEntry(Entry entry) {
        this.editedEntry.set(entry);
    }


    // Propertyjen getterit

    /**
     * Palauttaa valitun käyttäjän sisältävän propertyn
     * @return Palauttaa valitun käyttäjän sisältävän propertyn
     */
    public ObjectProperty<User> selectedUserProperty() {
        return this.selectedUser;
    }


    /**
     * Palauttaa valitun projektin sisältävän propertyn
     * @return Palauttaa valitun projektin sisältävän propertyn
     */
    public ObjectProperty<Project> selectedProjectProperty() {
        return this.selectedProject;
    }


    /**
     * Palauttaa valitun merkinnän sisältävän propertyn
     * @return Palauttaa valitun merkinnän sisältävän propertyn
     */
    public ObjectProperty<Entry> selectedEntryProperty() {
        return this.selectedEntry;
    }


    /**
     * Palauttaa muokattavan merkinnän sisältävän propertyn
     * @return Palauttaa muokattavan merkinnän sisältävän propertyn
     */
    public ObjectProperty<Entry> editedEntryProperty() {
        return this.editedEntry;
    }


    /**
     * Palauttaa kaikki käyttäjät sisältävän propertyn
     * @return Palauttaa kaikki käyttäjät sisältävän propertyn
     */
    public ListProperty<User> allUsersProperty() {
        return this.allUsers;
    }


    /**
     * Palauttaa valitun käyttäjän kaikki projektit sisältävän propertyn
     * @return Palauttaa valitun käyttäjän kaikki projektit sisältävän propertyn
     */
    public ListProperty<Project> userProjectsProperty() {
        return this.userProjects;
    }


    /**
     * Palauttaa valitun projektin kaikki merkinnät sisältävän propertyn
     * @return Palauttaa valitun projektin kaikki merkinnät sisältävän propertyn
     */
    public ListProperty<Entry> projectEntriesProperty() {
        return this.projectEntries;
    }
    


}
