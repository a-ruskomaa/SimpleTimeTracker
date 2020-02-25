package roarusko.simpleTimeTracker.model.domain;




/**
 * Luokka projektin tallentamiseksi. Sisältää tiedon merkinnän alku- ja loppuajasta.
 * Tarjoaa metodit nimen hakemiseen ja muokkaamiseen sekä projektin id-tunnisteen sekä omistajan hakemiseen.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Project implements ParentObject, ChildObject {

    private final int id;
    private String name;
    private User user;

    
    
    /**
     * Luo annetulle käyttäjälle uuden projektin annetulla nimellä. Id:ksi asetetaan tilapäisesti -1.
     * Tämä olio korvataan DAO-luokassa vastaavansisältöisellä oliolla, jolla id-numero on aidosti yksilöivä.
     * @param name Projektin nimi
     * @param user Käyttäjä, jolle projekti kuuluu
     */
    public Project(String name, User user) {
        this(-1, name, user);
    }

    /**
     * Luo annetulle käyttäjälle uuden projektin annetulla nimellä sekä yksilöivällä id-tunnisteella.
     * @param id Yksilöivä id-numero
     * @param name Projektin nimi
     * @param user Käyttäjä, jolle projekti kuuluu
     */
    public Project(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }


    /**
     * Metodi projektin id:n kysymiselle
     * @return Palauttaa projektin yksilöivän id-numeron
     */
    @Override
    public int getId() {
        return this.id;
    }


    /**
     * Metodi projektin nimen kysymiselle
     * @return Palauttaa projektin nimen
     */
    @Override
    public String getName() {
        return this.name;
    }


    /**
     * @return Palauttaa käyttäjän, jolle projekti kuuluu
     */
    @Override
    public User getOwner() {
        return this.user;
    }


    /**
     * Asettaa projektille uuden nimen
     * @param name Asetettava nimi
     */
    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return this.name;
    }

    //
}
