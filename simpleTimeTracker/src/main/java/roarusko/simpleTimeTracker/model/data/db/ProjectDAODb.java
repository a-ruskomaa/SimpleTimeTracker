package roarusko.simpleTimeTracker.model.data.db;

import java.sql.ResultSet;
import java.util.List;

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
public class ProjectDAODb extends AbstractChildDAODb<Project, User>  {
    private static final String tableName = "Projects";


    /**
     * Luo uuden ProjectDAO annetulla tallennustiedostolla
     * @param connectionManager Olio, jolla luodaan tietokantayhteys
     * 
     */
    public ProjectDAODb(ConnectionManager connectionManager) {
        super(connectionManager, tableName);
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
