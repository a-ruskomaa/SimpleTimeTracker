package fxTyoaika.controller;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DataValidationException;

import fxTyoaika.model.Entry;
import fxTyoaika.model.Project;
import fxTyoaika.model.Timer;
import fxTyoaika.model.User;
import fxTyoaika.model.dataAccess.EntryDAO;
import fxTyoaika.model.dataAccess.ProjectDAO;
import fxTyoaika.model.dataAccess.UserDAO;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

/**
 * @author aleks
 * @version 28 Jan 2020
 *
 * Luokka, jonka avulla ohjelma ylläpitää tilaa. Ohjelmassa voi samanaikaisesti olla valittuna vain yksi käyttäjä,
 * yksi käyttäjälle kuuluva projekti sekä yksi projektiin kuuluva merkintä. Luokka sisältää metodit valitun käyttäjän, projektin
 * sekä merkinnän asettamiselle ja hakemiselle. Luokasta luodaan vain yksi instanssi ohjelman
 * käynnistyksen yhteydessä. Tällä hetkellä toimii ainoana rajapintana ohjelman käyttöliittymän sekä mallinnettavan datan välillä.
 */
public class ModelAccess {
    
    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final EntryDAO entryDAO;

    private final ListProperty<User> allUsers = new SimpleListProperty<User>();
    private final ObjectProperty<User> selectedUser = new SimpleObjectProperty<User>();
    private final ObjectProperty<Project> selectedProject = new SimpleObjectProperty<Project>();
    private final ObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<Entry>();
    private final ObjectProperty<Entry> editedEntry = new SimpleObjectProperty<Entry>();
    

//    private Entry currentlyEditedEntry = new Entry();
    
//    public Entry getCurrentlyEditedEntry() {
//        return currentlyEditedEntry;
//    }
//
//    public void setCurrentlyEditedEntry(Entry entry) {
//        this.currentlyEditedEntry = entry;
//    }
//    
//    public void resetCurrentlyEditedEntry() {
//        this.currentlyEditedEntry = new Entry();
//    }
    
    

    /**
     * Olion luomisen yhteydessä hakee tiedot käyttäjistä "tietokannasta". Myöhempi toteutustapa vielä auki.
     * Luo myös ajastimen.
     */
    public ModelAccess() {
        userDAO = new UserDAO();
        projectDAO = new ProjectDAO();
        entryDAO = new EntryDAO();
        
        System.out.println("modelAccess luotu!");
        
        
//        ChangeListener<Project> projectChangeListener = (observable, oldProject, newProject) -> {
//            System.out.println("Project changed");
//            ObservableList<Entry> entries = newProject.getEntries();
//            this.selectedEntry.set(entries.isEmpty() ? null : entries.get(0));
//        };
//        
//        selectedProject.addListener(projectChangeListener);
//        
//        ChangeListener<User> userChangeListener = (observable, oldUser, newUser) -> {
//            System.out.println("User changed");
//            selectedProject.removeListener(projectChangeListener);
//            ObservableList<Project> projects = newUser.getProjects();
//            this.selectedProject.set(projects.isEmpty() ? null : projects.get(0));
//            
//            selectedProject.addListener(projectChangeListener);
//        };
//        selectedUser.addListener(userChangeListener);
    };
    
    
    /**
     * Luo uuden kättäjän. UserDAO tallentaa käyttäjän tietokantaan, ja palauttaa käyttäjän jonka id vastaa
     * tietokantaan tallennettua id-numeroa. Lisää käyttäjän kaikki käyttäjät sisältävälle listalle.
     * @param name Nimi, joka käyttäjälle halutaan antaa.
     */
    public void addUser(String name) {
        User newUser = this.userDAO.create(new User(name));
        allUsers.add(newUser);
    }
    
    public void addProject(String name) {
        User _selectedUser = getSelectedUser();
        Project newProject = this.projectDAO.create(new Project(name, _selectedUser));
        _selectedUser.addProject(newProject);
    }
    
    public Entry getNewEntry() {
        Project _selectedProject = getSelectedProject();
        Entry newEntry = new Entry(_selectedProject);
        return newEntry;
    }
    
    // TODO mieti tarvitseeko?
    public void editEntry(Entry entry) {
        this.editedEntry.set(entry);
    }

    
    public void commitEntry() {
        Entry edited = editedEntry.get();
        Project _selectedProject = edited.getOwner();
        if (edited.getId() == -1) {
            Entry newEntry = this.entryDAO.create(edited);  
            _selectedProject.addEntry(newEntry);
            return;
        }
        Entry newEntry = this.entryDAO.update(edited);
        _selectedProject.updateEntry(newEntry);
    }

    
    public ObservableList<User> getUsers() {
        ObservableList<User> users = (ObservableList<User>) this.userDAO.list();
        this.allUsers.set(users);
        return allUsers;
    }
    
    public ObservableList<Project> getProjects() {
        User _selectedUser = getSelectedUser();
        ObservableList<Project> projects = (ObservableList<Project>) this.projectDAO.list(_selectedUser);
        _selectedUser.setProjects(projects);
        return projects;
    }
    
    public ObservableList<Entry> getEntries() {
        Project _selectedProject = getSelectedProject();
        ObservableList<Entry> entries = (ObservableList<Entry>) this.entryDAO.list(_selectedProject);
        _selectedProject.setEntries(entries);
        return entries;
    }
    


    /**
     * Valitun käyttäjän getteri
     * @return palauttaa valitun käyttäjän
     */
    private User getSelectedUser() {
        return selectedUser.get();
    }
    
    /**
     * Valitun projektin getteri
     * @return palauttaa ohjelmassa valitun projektin
     */
    private Project getSelectedProject() {
        return this.selectedProject.get();
    }

    /**
     * Valitun merkinnän getteri
     * @return palauttaa valitun merkinnän
     */
    private Entry getSelectedEntry() {
        return selectedEntry.get();
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
//    /**
//     * Asettaa parametrinaan saamansa merkinnän valituksi
//     * @param selectedEntry valittu merkintä
//     */
//    public void setSelectedEntry(Entry selectedEntry) {
//        this.selectedEntry.set(selectedEntry);
//    }
    
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


}
