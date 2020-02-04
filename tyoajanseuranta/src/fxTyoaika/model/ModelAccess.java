package fxTyoaika.model;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    
    private ObjectProperty<User> selectedUser = new SimpleObjectProperty<User>();
    private ObjectProperty<Project> selectedProject = new SimpleObjectProperty<Project>();
    private ObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<Entry>();
    
    private Timer timer;
    private Entry tempEntry;
    
    /**
     * Olion luomisen yhteydessä hakee tiedot käyttäjistä "tietokannasta". Myöhempi toteutus vielä auki.
     * Luo myös uuden ajastimen.
     */
    public ModelAccess() {
        this.timer = new Timer();
        System.out.println("modelAccess luotu!");
    }

    /**
     * Lataa tallennetut käyttäjät. Toistaiseksi käytetään puhtaasti oliopohjaista ratkaisua datan ylläpitoon.
     * @return palauttaa käyttäjät listalla
     */
    public ObservableList<User> loadUserData() {
        return TempUsers.getUsers();
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
    
    //
}
