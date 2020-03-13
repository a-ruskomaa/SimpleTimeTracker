package roarusko.simpleTimeTracker.model.data.db;

import java.sql.ResultSet;

import roarusko.simpleTimeTracker.model.data.UserDAO;
import roarusko.simpleTimeTracker.model.domain.User;

/**
 * Käyttäjiä tiedostoon tallentava ja lukeva luokka. Tiedostojen käsittely toteutetaan abstraktin yläluokan
 * tarjoamilla metodeilla. Luokka sisältää tarvittavan logiikan, jolla merkkijonoista saadaan luotua User-tyyppisiä
 * olioita ja User-olioista merkkijonoja.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class UserDAODb extends AbstractDAODb<User> implements UserDAO {
    private static final String defaultPath = "data/users.dat";
     
    /**
     * Luo uuden UserDAO:n oletusarvoisella tallennustiedostolla
     */
     public UserDAODb() {
        super(defaultPath);
    }
     
     
     /**
      * Luo uuden UserDAO:n annetulla tallennustiedostolla
      * @param pathToFile Merkkijonomuotoinen polku haluttuun tallennustiedostoon
      * 
      */
     public UserDAODb(String pathToFile) {
         super(pathToFile);
     }


    @Override
    protected User createObject(ResultSet rs) {
        // TODO Auto-generated method stub
        return null;
    }

}
