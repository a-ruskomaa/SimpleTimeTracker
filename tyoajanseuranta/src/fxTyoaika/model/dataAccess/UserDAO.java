package fxTyoaika.model.dataAccess;

import java.util.ArrayList;
import java.util.List;

import fxTyoaika.SampleData;
import fxTyoaika.model.IdGenerator;
import fxTyoaika.model.Project;
import fxTyoaika.model.User;

/**
 * Käyttäjiä käsitteleviä tiedonkäsittelyoperaatioita toteuttava luokka. Perii AbstractDAO:n geneeriset
 * toteutukset read(), update() sekä delete() -metodeista joita hyödyntää sellaisenaan. Tarjoaa
 * oman toteutuksen create-metodille sekä ylikirjoitetut toteutukset list-metodeille.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class UserDAO extends AbstractDAO<User> {

    /**
     * Lisää pysyvään muistiin uuden käyttäjän tiedot, jotka ottaa vastaan User-oliona
     * @param object Lisättävä käyttäjä
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    @Override
    public User create(User object) {
        @SuppressWarnings("unchecked")
        // TODO tämä poistetaan myöhemmin
        ArrayList<User> users = (ArrayList<User>) SampleData.getData(User.class);
        
        User user = new User(IdGenerator.getNewUserId(), object.getName());
        
        users.add(user);
        
        return user;
    }

    /**
     * TODO tämä poistetaan myöhemmin
     * Tilapäinen metodi, jota hyödynnetään haettaessa testidataa muun ohjelman käyttöön
     * @return palauttaa tyypin Project olioita listalla
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<User> getData() {
        return (ArrayList<User>) SampleData.getData(User.class);
    }
    
    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut käyttäjät User-olioina
     * @return palauttaa User-olioita listalla
     */
    @Override
    public List<User> list() {
        return list(User.class);
    }

}
