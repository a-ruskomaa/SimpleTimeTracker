package roarusko.simpleTimeTracker.model.domain;




/**
 * Luokka projektin tallentamiseksi. Sisältää tiedon merkinnän alku- ja loppuajasta.
 * Tarjoaa metodit nimen hakemiseen ja muokkaamiseen sekä projektin id-tunnisteen sekä omistajan hakemiseen.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Project implements ParentObject, ChildObject {

    private int projectId;
    private String name;
    private int userId;

    
    
    /**
     * Luo annetulle käyttäjälle uuden projektin annetulla nimellä. Id:ksi asetetaan tilapäisesti -1.
     * Tämä olio korvataan DAO-luokassa vastaavansisältöisellä oliolla, jolla id-numero on aidosti yksilöivä.
     * @param userId Käyttäjä, jolle projekti kuuluu
     * @param name Projektin nimi
     */
    public Project(int userId, String name) {
        this(-1, userId, name);
    }

    /**
     * Luo annetulle käyttäjälle uuden projektin annetulla nimellä sekä yksilöivällä id-tunnisteella.
     * @param id Yksilöivä id-numero
     * @param userId Käyttäjä, jolle projekti kuuluu
     * @param name Projektin nimi
     */
    public Project(int id, int userId, String name) {
        this.projectId = id;
        this.name = name;
        this.userId = userId;
    }


    /**
     * Metodi projektin id:n kysymiselle
     * @return Palauttaa projektin yksilöivän id-numeron
     */
    @Override
    public int getId() {
        return this.projectId;
    }
    
    
    @Override
    public void setId(int id) {
        if (this.projectId == -1) this.projectId = id;
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
    public int getOwnerId() {
        return this.userId;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + projectId;
        result = prime * result + userId;
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
        Project other = (Project) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (projectId != other.projectId)
            return false;
        if (userId != other.userId)
            return false;
        return true;
    }

    
}
