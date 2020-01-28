package fxTyoaika.controller;

import java.util.ArrayList;
import java.util.List;

import fxTyoaika.model.Entry;
import fxTyoaika.model.Project;
import fxTyoaika.model.TempUsers;
import fxTyoaika.model.Timer;
import fxTyoaika.model.User;
import javafx.beans.property.SimpleStringProperty;

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
    
    private User selectedUser;
    private Project selectedProject;
    private Entry selectedEntry;
    
    private List<User> userList;
    
    private Timer timer;
    
    /**
     * Olion luomisen yhteydessä hakee tiedot käyttäjistä "tietokannasta". Myöhempi toteutus vielä auki.
     * Luo myös uuden ajastimen.
     */
    public ModelAccess() {
        this.userList = loadUsers();
        this.timer = new Timer();
    }

    /**
     * Lataa tallennetut käyttäjät. Toistaiseksi käytetään puhtaasti oliopohjaista ratkaisua datan ylläpitoon.
     * @return
     */
    private List<User> loadUsers() {
        return TempUsers.getUsers();
    }
    
    /**
     * Käyttäjälistan getteri
     * @return palauttaa listan käyttäjistä
     */
    public List<User> getUserList() {
        return this.userList;
    }
    
    /**
     * Valitun käyttäjän getteri
     * @return palauttaa valitun käyttäjän
     */
    public User getSelectedUser() {
        return selectedUser;
    }
    
    /**
     * Valitun projektin getteri
     * @return palauttaa ohjelmassa valitun projektin
     */
    public Project getSelectedProject() {
        return this.selectedProject;
    }

    /**
     * Valitun merkinnän getteri
     * @return palauttaa valitun merkinnän
     */
    public Entry getSelectedEntry() {
        return selectedEntry;
    }
    
    /**
     * Asettaa parametrinaan saamansa projektin valituksi
     * @param selectedProject valittu projekti
     */
    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    /**
     * Asettaa parametrinaan saamansa käyttäjän valituksi
     * @param selectedUser käyttäjä joka on valittuna
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }


    /**
     * Asettaa parametrinaan saamansa merkinnän valituksi
     * @param selectedEntry valittu merkintä
     */
    public void setSelectedEntry(Entry selectedEntry) {
        this.selectedEntry = selectedEntry;
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
