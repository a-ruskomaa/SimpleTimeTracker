package roarusko.simpleTimeTracker.model.data.db;

import java.sql.ResultSet;

import roarusko.simpleTimeTracker.model.domain.User;

/**
 * Käyttäjiä tiedostoon tallentava ja lukeva luokka. Tiedostojen käsittely toteutetaan abstraktin yläluokan
 * tarjoamilla metodeilla. Luokka sisältää tarvittavan logiikan, jolla merkkijonoista saadaan luotua User-tyyppisiä
 * olioita ja User-olioista merkkijonoja.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class UserDAODb extends AbstractDAODb<User> {
    private static final String tableName = "Users";
     

     
     /**
      * Luo uuden UserDAO:n annetulla tallennustiedostolla
     * @param connectionManager Olio, jolla luodaan tietokantayhteys
      * 
      */
     public UserDAODb(ConnectionManager connectionManager) {
         super(connectionManager, tableName);
     }


    @Override
    protected User createObject(ResultSet rs) {
        // TODO Auto-generated method stub
        return null;
    }

}
