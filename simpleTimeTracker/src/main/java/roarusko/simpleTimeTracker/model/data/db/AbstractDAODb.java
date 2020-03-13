package roarusko.simpleTimeTracker.model.data.db;


import java.sql.ResultSet;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.DAO;
import roarusko.simpleTimeTracker.model.domain.DataObject;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.ParentObject;

/**
 * 
 * @author aleks
 * @version 2 Mar 2020
 *
 * @param <T> DataObject-rajapinnan toteuttava luokka
 */
public abstract class AbstractDAODb<T extends DataObject> implements DAO<Integer, T> {
    private final String path;

    
    
    public AbstractDAODb(String path) {
        this.path = path;
    }


    @Override
    public T read(Integer key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update(T object) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(Integer key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<T> list() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T create(T object) {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * Metodi, jonka avulla tietokannan palauttamista tiedoista luodaan halutun tyyppisiä olioita. Toteutus vaihtelee
     * luokkakohtaisesti.
     * @param rs Tietokannasta luettu rivi, joka sisältää olion luomiseen tarvittavan datan.
     * @return Palauttaa luodun olion
     */
    protected abstract T createObject(ResultSet rs);
    
    
    
    /**
     * Suodattaa annetusta listasta halutun omistajan oliot, eli esimerkiksi tietyn käyttäjän projektit
     * tai tietyn projektin merkinnät.
     * @param object ParentObject-rajapinnan toteuttava olio (User tai Project), jolle kuuluvat domain-objektit halutaan listata
     * @return Palauttaa suodatetun listan T-tyyppisiä olioita
     */
    protected List<T> list(ParentObject object) {
        return null;
    }

}
