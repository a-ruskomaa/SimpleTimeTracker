package roarusko.simpleTimeTracker.model.data.file;

import roarusko.simpleTimeTracker.model.domain.User;

/**
 * Käyttäjiä tiedostoon tallentava ja lukeva luokka. Tiedostojen käsittely toteutetaan abstraktin yläluokan
 * tarjoamilla metodeilla. Luokka sisältää tarvittavan logiikan, jolla merkkijonoista saadaan luotua User-tyyppisiä
 * olioita ja User-olioista merkkijonoja.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class UserDAO_File extends AbstractDAO_File<User> {
    private static final String defaultPath = "data/users.dat";
     
    /**
     * Luo uuden UserDAO:n oletusarvoisella tallennustiedostolla
     */
     public UserDAO_File() {
        super(defaultPath);
    }
     
     
     /**
      * Luo uuden UserDAO:n annetulla tallennustiedostolla
      * @param pathToFile Merkkijonomuotoinen polku haluttuun tallennustiedostoon
      * 
      */
     public UserDAO_File(String pathToFile) {
         super(pathToFile);
     }

    
    @Override
    protected User parseObject(String row) {
        String[] parts = row.split(",");
        return new User(Integer.parseInt(parts[0]), parts[1]);
    }
    
    
    @Override
    protected String objectToStringForm(User user) {
        return String.format("%d,%s", user.getId(), user.getName());
    }

}
