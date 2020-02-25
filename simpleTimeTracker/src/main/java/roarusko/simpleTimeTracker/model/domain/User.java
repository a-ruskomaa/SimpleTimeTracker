package roarusko.simpleTimeTracker.model.domain;


/**
 * Luokka käyttäjän tietojen tallentamiseksi. Tarjoaa metodit nimen
 * kysymiselle ja muuttamiselle, sekä id:n kysymiselle.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class User implements ParentObject {
    
    private final int id;
    private String name;
    
    
    /**
     * Luo uuden käyttäjän, jolle on annettu parametrina pelkkä nimi. Antaa id:lle tilapäisen
     * arvon -1. Käytetään kun luodaan DAO:lla uutta käyttäjää.
     * @param name Käyttäjän nimi
     */
    public User(String name) {
        this(-1, name);
    }
    
    /**
     * Luo uuden käyttäjän annetulla id:llä. DAO palauttaa tässä
     * muodossa olevan olion.
     * @param id Käyttäjän yksilöllinen id-numero
     * @param name Käyttäjän nimi
     */
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }


    /**
     * Metodi käyttäjän id:n kysymiselle
     * @return Palauttaa käyttäjän yksilöivän id-numeron
     */
    @Override
    public int getId() {
        return this.id;
    }
    
    /**
     * Metodi käyttäjän nimen kysymiselle
     * @return Palauttaa käyttäjän nimen
     */
    @Override
    public String getName() {
        return this.name;
    }

    
    /**
     * Asettaa käyttäjälle nimen
     * @param name Nimi joka asetetaan
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
