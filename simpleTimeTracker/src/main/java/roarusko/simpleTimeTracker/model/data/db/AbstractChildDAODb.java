package roarusko.simpleTimeTracker.model.data.db;


import java.sql.ResultSet;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.ChildDAO;
import roarusko.simpleTimeTracker.model.domain.ChildObject;
import roarusko.simpleTimeTracker.model.domain.ParentObject;

/**
 * 
 * @author aleks
 * @version 2 Mar 2020
 *
 * @param <T> ChildObject-rajapinnan toteuttava luokka
 * @param <P> ParentObject-rajapinnan toteuttava luokka
 */
public abstract class AbstractChildDAODb<T extends ChildObject, P extends ParentObject> extends AbstractDAODb<T> implements ChildDAO<Integer, T, P> {


    public AbstractChildDAODb(ConnectionManager connectionManager, String tableName) {
        super(connectionManager, tableName);
    }


    /**
     * Suodattaa annetusta listasta halutun omistajan oliot, eli esimerkiksi tietyn käyttäjän projektit
     * tai tietyn projektin merkinnät.
     * @param object ParentObject-rajapinnan toteuttava olio (User tai Project), jolle kuuluvat domain-objektit halutaan listata
     * @return Palauttaa suodatetun listan T-tyyppisiä olioita
     */
    @Override
    public List<T> list(ParentObject object) {
        return null;
    }
}
