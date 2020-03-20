package roarusko.simpleTimeTracker.model.data.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.DAO;
import roarusko.simpleTimeTracker.model.domain.DataObject;
import roarusko.simpleTimeTracker.model.domain.ParentObject;

/**
 * 
 * @author aleks
 * @version 2 Mar 2020
 *
 * @param <T> DataObject-rajapinnan toteuttava luokka
 */
public abstract class AbstractDAODb<T extends DataObject> implements DAO<Integer, T> {
    private final ConnectionManager connectionManager;
    private final String tableName;

    
    
    /**
     * @param connectionManager Olio, jolla luodaan tietokantayhteys
     * @param tableName Tämän olion hallinnoiman tietokantataulun nimi
     */
    public AbstractDAODb(ConnectionManager connectionManager, String tableName) {
        this.tableName = tableName;
        this.connectionManager = connectionManager;
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


    private boolean makeUpdate(String pst) {
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(pst)) {
            sql.executeUpdate();
            System.out.println("Database updated");
            return true;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }
    
    private ResultSet makeQuery(String pst) {
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(pst)) {
            ResultSet rs = sql.executeQuery();
            System.out.println("Database updated");
            return rs;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    
}
