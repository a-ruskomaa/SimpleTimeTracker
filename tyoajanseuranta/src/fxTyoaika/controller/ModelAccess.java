package fxTyoaika.controller;

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
 * yksi projekti sekä yksi projektiin kuuluva merkintä. Luokka Sisältää metodit valitun käyttäjän, projektin
 * sekä merkinnän asettamiselle ja kysymiselle. Luokasta luodaan vain yksi instanssi ohjelman
 * käynnistyksen yhteydessä. Tällä hetkellä toimii ainoana rajapintana ohjelman käyttöliittymän sekä datan välillä.
 */
public class ModelAccess {
    
    private UserDAO userDAO;
    private ProjectDAO projectDAO;
    private EntryDAO entryDAO;

    private final ObjectProperty<User> selectedUser = new SimpleObjectProperty<User>();
    private final ObjectProperty<Project> selectedProject = new SimpleObjectProperty<Project>();
    private final ObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<Entry>();
    
    private Timer timer;
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
     * Olion luomisen yhteydessä hakee tiedot käyttäjistä "tietokannasta". Myöhempi toteutus vielä auki.
     * Luo myös ajastimen.
     */
    public ModelAccess() {
        this.timer = new Timer(this);
        System.out.println("modelAccess luotu!");
        
        userDAO = new UserDAO();
        projectDAO = new ProjectDAO();
        entryDAO = new EntryDAO();
        
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
    
    
    
    
    public UserDAO getUserDAO() {
        return userDAO;
    }



    public ProjectDAO getProjectDAO() {
        return projectDAO;
    }




    public EntryDAO getEntryDAO() {
        return entryDAO;
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
     * Asettaa parametrinaan saamansa projektin valituksi
     * @param selectedProject valittu projekti
     */
    public void setSelectedProject(Project selectedProject) {
        this.selectedProject.set(selectedProject);
    }

    /**
     * Asettaa parametrinaan saamansa käyttäjän valituksi
     * @param selectedUser käyttäjä joka on valittuna
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser.set(selectedUser);
    }

    /**
     * Asettaa parametrinaan saamansa merkinnän valituksi
     * @param selectedEntry valittu merkintä
     */
    public void setSelectedEntry(Entry selectedEntry) {
        this.selectedEntry.set(selectedEntry);
    }
    
    public ObjectProperty<User> selectedUserProperty() {
        return this.selectedUser;
    }
    
    public ObjectProperty<Project> selectedProjectProperty() {
        return this.selectedProject;
    }
    
    public ObjectProperty<Entry> selectedEntryProperty() {
        return this.selectedEntry;
    }

    
    /**
     * Ajastimen getteri
     * @return palauttaa viitteen ohjelman ajastimeen
     */
    public Timer getTimer() {
        return this.timer;
    }

}
