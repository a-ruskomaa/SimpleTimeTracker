package roarusko.simpleTimeTracker.model.domain;


/**
 * Luokka käyttäjän tietojen tallentamiseksi. Tarjoaa metodit nimen
 * kysymiselle ja muuttamiselle, sekä id:n kysymiselle.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class User implements ParentObject {
    
    private int id;
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
     * Luo uuden käyttäjän annetulla id:llä. Muut tiedot kopioidaan parametrina
     * annetusta oliosta.
     * @param id Käyttäjän yksilöllinen id-numero
     * @param user Käyttäjä, jonka tiedot halutaan kopioida tähän olioon.
     */
    public User(int id, User user) {
        this.id = id;
        this.name = user.getName();
    }


    /**
     * Metodi käyttäjän id:n kysymiselle
     * @return Palauttaa käyttäjän yksilöivän id-numeron
     */
    @Override
    public int getId() {
        return this.id;
    }
    
    
    @Override
    public void setId(int id) {
        if (this.id == -1) this.id = id;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
    
    
}
