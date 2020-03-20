package roarusko.simpleTimeTracker.model.data.db;

import java.sql.ResultSet;
import java.util.List;

import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;

/**
 * Merkintöjä tiedostoon tallentava ja lukeva luokka. Tiedostojen käsittely toteutetaan abstraktin yläluokan
 * tarjoamilla metodeilla. Luokka sisältää tarvittavan logiikan, jolla merkkijonoista saadaan luotua Entry-tyyppisiä
 * olioita ja Entry-olioista merkkijonoja.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class EntryDAODb extends AbstractChildDAODb<Entry, Project> {
    private static final String tableName = "Entries";

    
    /**
     * Luo uuden EntryDAO:n annetulla tallennustiedostolla
     * @param connectionManager Olio, jolla luodaan tietokantayhteys
     * 
     */
    public EntryDAODb(ConnectionManager connectionManager) {
        super(connectionManager, tableName);
    }



    @Override
    public List<Entry> list(Project project) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Entry createObject(ResultSet rs) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    
    

}
