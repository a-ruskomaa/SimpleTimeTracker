package roarusko.simpleTimeTracker.model.data.db;

import java.sql.ResultSet;
import java.time.format.DateTimeParseException;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.EntryDAO;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.utility.Entries;

/**
 * Merkintöjä tiedostoon tallentava ja lukeva luokka. Tiedostojen käsittely toteutetaan abstraktin yläluokan
 * tarjoamilla metodeilla. Luokka sisältää tarvittavan logiikan, jolla merkkijonoista saadaan luotua Entry-tyyppisiä
 * olioita ja Entry-olioista merkkijonoja.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class EntryDAODb extends AbstractDAODb<Entry> implements EntryDAO {
    private static final String defaultPath = "data/entries.dat";
    
    public EntryDAODb(String path) {
        super(path);
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
