package roarusko.simpleTimeTracker.model;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import roarusko.simpleTimeTracker.model.dataAccess.EntryDAO;
import roarusko.simpleTimeTracker.model.dataAccess.ProjectDAO;
import roarusko.simpleTimeTracker.model.dataAccess.UserDAO;
import roarusko.simpleTimeTracker.model.domainModel.DataObject;
import roarusko.simpleTimeTracker.model.domainModel.Entry;
import roarusko.simpleTimeTracker.model.domainModel.Project;
import roarusko.simpleTimeTracker.model.domainModel.User;
import roarusko.simpleTimeTracker.model.utility.Timer;
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
 * Luokka, jonka avulla ohjelman tilaa, kuten valittua projektia tai merkintää, ylläpidetään. Toimii fasilitaattorina
 * näkymää kontrolloivien luokkien ja dataa mallintavien luokkien välilä. Samanaikaisesti olla valittuna vain yksi käyttäjä,
 * yksi tällekäyttäjälle kuuluva projekti sekä yksi projektiin kuuluva merkintä. Luokka sisältää metodit valitun käyttäjän, projektin
 * sekä merkinnän asettamiselle ja hakemiselle. Luokasta luodaan vain yksi instanssi ohjelman
 * käynnistyksen yhteydessä. Toimii ainoana tiedonkulkuväylänä ohjelman kontrolleriluokkien, käyttöliittymän sekä mallinnettavan datan välillä.
 */
public class ModelAccess {
    
    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final EntryDAO entryDAO;

    private final ListProperty<User> allUsers = new SimpleListProperty<User>();
    private final ObjectProperty<User> selectedUser = new SimpleObjectProperty<User>();
    
    private final ListProperty<Project> selectedUserProjects = new SimpleListProperty<Project>();
    private final ObjectProperty<Project> selectedProject = new SimpleObjectProperty<Project>();
    
    private final ListProperty<Entry> selectedProjectEntries = new SimpleListProperty<Entry>();
    private final ObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<Entry>();
    private final ObjectProperty<Entry> editedEntry = new SimpleObjectProperty<Entry>();

    private Timer timer;

    /**
     * ModelAccessin oletuskonstruktori. Luo Data Access -oliot ja lataa kaikkien käyttäjien tiedot tietokannasta.
     * Asettaa tapahtumankäsittelijät reagoimaan käyttäjän ja projektin vaihtamiselle. Käyttäjän vaihtaminen lataa
     * automaattisesti muistiin kaikki valitulle käyttäjälle kuuluvat projektit. Projektin vaihtaminen lataa
     * automaattisesti muistiin kaikki valittuun projektiin kuuluvat mnerkinnät.
     */
    public ModelAccess() {
        userDAO = new UserDAO();
        projectDAO = new ProjectDAO();
        entryDAO = new EntryDAO();
        
        
        ChangeListener<Project> projectChangeListener = (observable, oldProject, newProject) -> {
            System.out.println("valittu projekti vaihtui: " + newProject);
            ObservableList<Entry> entries = loadEntries();
            this.selectedProjectEntries.set(entries);
            this.selectedEntry.set(entries.isEmpty() ? null : entries.get(0));
        };
        
        
        selectedProject.addListener(projectChangeListener);
        
        ChangeListener<User> userChangeListener = (observable, oldUser, newUser) -> {
            System.out.println("valittu käyttäjä vaihtui: " + newUser);
            selectedProject.removeListener(projectChangeListener);
            
            ObservableList<Project> projects = loadProjects();
            
            this.selectedUserProjects.set(projects);
            
            selectedProject.addListener(projectChangeListener);
            
            this.selectedProject.set(projects.isEmpty() ? null : projects.get(0));
            
        };
        selectedUser.addListener(userChangeListener);
        
        this.timer = new Timer(this);
        
        loadUsers();
        
        selectedUser.set(allUsers.isEmpty() ? null : allUsers.get(0));
        
        System.out.println("modelAccess luotu!");
    };
    
    
    /**
     * Luo uuden kättäjän. UserDAO tallentaa käyttäjän tietokantaan, ja palauttaa käyttäjän jonka id vastaa
     * tietokantaan tallennettua id-numeroa. Lisää käyttäjän kaikki ohjelman käyttäjät sisältävälle listalle.
     * @param name Nimi, joka käyttäjälle halutaan antaa.
     */
    public void addUser(String name) {
        User newUser = this.userDAO.create(new User(name));
        allUsers.add(newUser);
        selectedUser.set(newUser);
    }
    
    
    /**
     * Luo uuden projektin. ProjectDAO tallentaa projektin tietokantaan, ja palauttaa projektin jonka id vastaa
     * tietokantaan tallennettua id-numeroa. Lisää projektin kaikki valitun käyttäjän projektit sisältävälle listalle.
     * @param name Nimi, joka projektille halutaan antaa.
     */
    public void addProject(String name) {
        User _selectedUser = getSelectedUser();
        Project newProject = this.projectDAO.create(new Project(name, _selectedUser));
        selectedUserProjects.add(newProject);
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
     * Tallentaa muokattavaksi merkityn merkinnän tietokantaan. Mikäli kyseessä on uusi merkintä, luo
     * uuden merkinnän ja lisää sen valitun projektin listalle. Mikälo kyseessä
     */
    public void commitEditedEntry() {
        Entry edited = editedEntry.get();
        Project _selectedProject = edited.getOwner();
        if (!getSelectedProject().equals(_selectedProject)) {
            System.out.println("Muokattava projekti ei vastaa valittua projektia");
            return;
        }
        if (edited.getId() == -1) {
            Entry newEntry = this.entryDAO.create(edited);
            selectedProjectEntries.add(newEntry);
            System.out.println("hip");
            return;
        }
        Entry newEntry = this.entryDAO.update(edited);
        int i = findIndexById(newEntry, selectedProjectEntries.get());
        if (i == -1) {
            System.out.println("merkintää ei löydy");
            return;
        }
        selectedProjectEntries.set(i, newEntry);
        selectedEntry.set(newEntry);
        System.out.println("hep");
    }

    
    /**
     * Hakee kaikkien käyttäjien tiedot tietokannasta ja tallentaa ne listalle
     * @return palauttaa käyttäjät ObservableList:llä
     */
    public ObservableList<User> loadUsers() {
        System.out.println("ladataan käyttäjät");
        ObservableList<User> users = FXCollections.observableArrayList(this.userDAO.list());
        this.allUsers.set(users);
        return allUsers;
    }
    
    /**
     * Hakee valittuna olevan käyttäjän kaikkien projektien tiedot tietokannasta. Tallentaa projektit listalle käyttäjäolioon.
     * @return palauttaa projektit ObservableList:llä
     */
    public ObservableList<Project> loadProjects() {
        System.out.println("ladataan projektit");
        User _selectedUser = getSelectedUser();
        ObservableList<Project> projects = FXCollections.observableArrayList(this.projectDAO.list(_selectedUser));
        selectedUserProjects.set(projects);
        return projects;
    }
    
    /**
     * Hakee valittuna olevan projektin kaikkien merkintöjen tiedot tietokannasta. Tallentaa merkinnät listalle projektiolioon.
     * @return palauttaa merkinnät ObservableList:llä
     */
    public ObservableList<Entry> loadEntries() {
        System.out.println("ladataan merkinnät");
        Project _selectedProject = getSelectedProject();
        ObservableList<Entry> entries = FXCollections.observableArrayList(this.entryDAO.list(_selectedProject));
        selectedProjectEntries.set(entries);
        return entries;
    }
    


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
    
    private int findIndexById(DataObject o, List<? extends DataObject> list) {
        ListIterator<? extends DataObject> iterator = list.listIterator();
        
        while (iterator.hasNext()) {
            int index = iterator.nextIndex();
            if (iterator.next().getId() == o.getId()) return index;
        }
        
        return -1;
    }

    
//    /**
//     * Asettaa parametrinaan saamansa projektin valituksi
//     * @param selectedProject valittu projekti
//     */
//    public void setSelectedProject(Project selectedProject) {
//        this.selectedProject.set(selectedProject);
//    }
//
//    /**
//     * Asettaa parametrinaan saamansa käyttäjän valituksi
//     * @param selectedUser käyttäjä joka on valittuna
//     */
//    public void setSelectedUser(User selectedUser) {
//        this.selectedUser.set(selectedUser);
//    }
//

    public ObjectProperty<User> selectedUserProperty() {
        return this.selectedUser;
    }
    
    public ObjectProperty<Project> selectedProjectProperty() {
        return this.selectedProject;
    }
    
    public ObjectProperty<Entry> selectedEntryProperty() {
        return this.selectedEntry;
    }
    
    public ObjectProperty<Entry> editedEntryProperty() {
        return this.editedEntry;
    }
    
    public ListProperty<User> allUsersProperty() {
        return this.allUsers;
    }
    
    public ListProperty<Project> selectedUserProjectsProperty() {
        return this.selectedUserProjects;
    }
    
    public ListProperty<Entry> selectedProjectEntriesProperty() {
        return this.selectedProjectEntries;
    }
    
    /**
     * Ajastimen getteri
     * @return palauttaa viitteen ohjelman ajastimeen
     */
    public Timer getTimer() {
        return this.timer;
    }


}
