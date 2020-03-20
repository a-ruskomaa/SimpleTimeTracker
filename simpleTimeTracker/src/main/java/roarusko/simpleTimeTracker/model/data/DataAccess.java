package roarusko.simpleTimeTracker.model.data;

import java.time.LocalDateTime;

import javafx.collections.ObservableList;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;

/**
 * @author aleks
 * @version 02.03.2020
 *
 * Rajapinta, jonka avulla muu ohjelma voi lukea ja kirjoittaa pysyvään muistiin tallennettua dataa.
 */
public interface DataAccess {

    /**
     * Luo uuden kättäjän. Käyttäjä luodaan aluksi väliaikaisella id-numerolla -1. UserDAO:n create-metodi lisää
     * käyttäjän tietokantaan, hakee käyttäjälle yksilöivän id:n tietokannasta ja palauttaa käyttäjän jonka id vastaa
     * tietokantaan tallennettua id-numeroa.
     * @param name Nimi, joka käyttäjälle halutaan antaa.
     * @return Palauttaa luodun käyttäjän.
     */
    public User addUser(String name);


    /**
     * Luo uuden projektin. Projekti luodaan aluksi väliaikaisella id-numerolla -1. ProjectDAO:n create-metodi lisää
     * projektin tietokantaan, hakee projektille yksilöivän id:n tietokannasta ja palauttaa projektin jonka id vastaa
     * tietokantaan tallennettua id-numeroa.
     * @param name Nimi, joka projektille halutaan antaa.
     * @param user Käyttäjä, jolle projekti kuuluu
     * @return Palauttaa luodun projektin
     */
    public Project addProject(String name, User user);

    
    /**
     * Luo uuden aikamerkinnän oletusalustuksella (alku- sekä loppuaika asetettuna nykyhetkeen ja id: -1).
     * @param project Projekti jolle merkintä lisätään
     * @return Palauttaa luodun merkinnän.
     */
    public Entry newEntry(Project project);
    
    /**
     * Luo uuden aikamerkinnän tilapäisellä id:llä -1 alustettuna annettuun alku- ja loppuaikaan.
     * @param project Projekti jolle merkintä lisätään
     * @param start Merkinnän alkuaika
     * @param end Merkinnän loppuaika
     * @return Palauttaa luodun merkinnän.
     */
    public Entry newEntry(Project project, LocalDateTime start, LocalDateTime end);


    /**
     * Tallentaa annetun merkinnän tietokantaan. Mikäli kyseessä on uusi merkintä, lisätään
     * tietokantaan uusi merkintä ja tietokannan palauttama id tallennetaan luotuun merkintään. Aiemmin luodun
     * merkinnän tapauksessa etsitään tietokannasta muokattavan merkinnän id:tä vastaava
     * merkintä ja korvataan se muokatulla merkinnällä.
     * @param entry Merkintä joka tallennetaan
     * @return Palauttaa tallennetun merkinnän
     */
    public Entry commitEntry(Entry entry);


    
    /**
     * Poistaa annetun merkinnän tiedot tietokannasta. EntryDAO etsii merkinnän id:n perusteella poistettavan
     * merkinnän.
     * @param entry Merkintä joka halutaan poistaa
     * @return Palauttaa false jos poisto epäonnistui
     */
    public boolean deleteEntry(Entry entry);
   


    /**
     * Hakee kaikkien käyttäjien tiedot tietokannasta ja tallentaa ne listalle
     * @return palauttaa käyttäjät ObservableList:llä
     */
    public ObservableList<User> loadUsers();


    /**
     * Hakee annetun käyttäjän kaikkien projektien tiedot tietokannasta ja palauttaa ne listalla.
     * @param user Käyttäjä jonka tiedot haetaan
     * @return palauttaa projektit ObservableList:llä
     */
    public ObservableList<Project> loadProjects(User user);


    /**
     * Hakee annetun projektin kaikkien merkintöjen tiedot tietokannasta ja palauttaa ne listalla
     * @param project Projekti jonka merkinnät haetaan
     * @return palauttaa merkinnät ObservableList:llä
     */
    public ObservableList<Entry> loadEntries(Project project);


}
