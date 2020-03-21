package roarusko.simpleTimeTracker.model.data.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.DAO;
import roarusko.simpleTimeTracker.model.domain.DataObject;

/**
 * 
 * @author aleks
 * @version 2 Mar 2020
 *
 * @param <T> DataObject-rajapinnan toteuttava luokka
 */
public abstract class AbstractDAO<T extends DataObject> implements DAO<Integer, T> {
    protected final ConnectionManager connectionManager;
    protected final String table;
    protected final String id;

    
    
    /**
     * @param connectionManager Olio, jolla luodaan tietokantayhteys
     * @param tableName Tämän olion hallinnoiman tietokantataulun nimi
     * @param id Luokkaa edustavan olion id-sarakkeen nimi tietokantataulussa
     */
    public AbstractDAO(ConnectionManager connectionManager, String tableName, String id) {
        this.table = tableName;
        this.connectionManager = connectionManager;
        this.id = id;
    }


    @Override
    public T read(Integer key) {
        String query = "SELECT * FROM " + table + " WHERE " + id + " = ?;";
        T object = null;
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(query)) {
            sql.setInt(1, key);
            @SuppressWarnings("resource") //suljetaan automaattisesti
            ResultSet rs = sql.executeQuery();
            System.out.println("Database read");
            object = resultAsObject(rs);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return object;
    }



    @Override
    public boolean delete(Integer key) {
        String query = "DELETE FROM " + table + " WHERE " + id + " = ?;";
        int rows = 0;
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(query)) {
            sql.setInt(1, key);
            rows = sql.executeUpdate();
            System.out.println("Deleted " + rows + " rows");
            if (rows > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    
    @Override
    public List<T> list() {
        String query = "SELECT * FROM " + table + ";";
        List<T> objects = new ArrayList<T>();
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(query)) {
            @SuppressWarnings("resource") //suljetaan automaattisesti
            ResultSet rs = sql.executeQuery();
            objects = resultAsList(rs);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return objects;
    }



    /**
     * Metodi, jonka avulla tietokannan palauttamista tiedoista luodaan halutun tyyppisiä olioita. Toteutus vaihtelee
     * luokkakohtaisesti.
     * @param rs Tietokannasta luettu data
     * @return Palauttaa luodun olion
     */
    protected abstract T resultAsObject(ResultSet rs);
    
    
    /**
     * Metodi, jonka avulla tietokannan palauttamista tiedoista luodaan halutun tyyppisiä olioita. Toteutus vaihtelee
     * luokkakohtaisesti.
     * @param rs Tietokannasta luettu rivi, joka sisältää olion luomiseen tarvittavan datan.
     * @return Palauttaa luodun olion
     */
    protected abstract List<T> resultAsList(ResultSet rs);


    @SuppressWarnings("unused")
    private boolean makeUpdate(String pst, String[] arguments) {
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
    
    @SuppressWarnings("unused")
    private ResultSet makeQuery(String pst, int id) {
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
