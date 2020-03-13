package roarusko.simpleTimeTracker.model.data.db;

import java.sql.ResultSet;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.ProjectDAO;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;

/**
 * Projekteja tiedostoon tallentava ja lukeva luokka. Tiedostojen käsittely toteutetaan abstraktin yläluokan
 * tarjoamilla metodeilla. Luokka sisältää tarvittavan logiikan, jolla merkkijonoista saadaan luotua Project-tyyppisiä
 * olioita ja Project-olioista merkkijonoja.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class ProjectDAODb extends AbstractDAODb<Project> implements ProjectDAO {
    private static final String defaultPath = "data/projects.dat";

    /**
     * Luo uuden ProjectDAO:n oletusarvoisella tallennustiedostolla
     */
    public ProjectDAODb() {
        super(defaultPath);
    }
    
    
    /**
     * Luo uuden ProjectDAO annetulla tallennustiedostolla
     * @param pathToFile Merkkijonomuotoinen polku haluttuun tallennustiedostoon
     * 
     */
    public ProjectDAODb(String pathToFile) {
        super(pathToFile);
    }

    
    @Override
    public List<Project> list(User user) {
        return super.list(user);
    }


    @Override
    protected Project createObject(ResultSet rs) {
        // TODO Auto-generated method stub
        return null;
    }

}
