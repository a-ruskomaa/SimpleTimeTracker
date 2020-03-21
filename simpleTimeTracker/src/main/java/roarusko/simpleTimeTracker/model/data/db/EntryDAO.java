package roarusko.simpleTimeTracker.model.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.ConnectionManager;
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
public class EntryDAO extends AbstractChildDAO<Entry, Project> {
    private static final String tableName = "Entries";
    private static final String idName = "e_id";
    private static final String parentIdName = "p_id";

    
    /**
     * Luo uuden EntryDAO:n annetulla tallennustiedostolla
     * @param connectionManager Olio, jolla luodaan tietokantayhteys
     * 
     */
    public EntryDAO(ConnectionManager connectionManager) {
        super(connectionManager, tableName, idName, parentIdName);
    }

    @Override
    public Entry create(Entry object) {
        String query = "INSERT INTO Entries (p_id, start, end) "
                + "VALUES (?, ?, ?);";
        int rows = 0;
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            sql.setInt(1, object.getOwnerId());
            sql.setString(2, Entries.getDateTimeAsString(object.getStartDateTime()));
            sql.setString(3, Entries.getDateTimeAsString(object.getEndDateTime()));
            rows = sql.executeUpdate();
            System.out.println("Inserted " + rows + " rows to " + tableName);
            if (rows == 0) {
                throw new SQLException("Creating the entry failed.");
            }
            try (ResultSet generatedKeys = sql.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    object.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating the entry failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return object;
    }
    
    @Override
    public boolean update(Entry object) {
        String query = "UPDATE Entries "
                + "SET start = ?, end = ? "
                + "WHERE e_id = ?;";
        int rows = 0;
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(query)) {
            sql.setString(1, Entries.getDateTimeAsString(object.getStartDateTime()));
            sql.setString(2, Entries.getDateTimeAsString(object.getEndDateTime()));
            sql.setInt(3, object.getId());
            rows = sql.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Updating the entry failed.");
            }
            System.out.println("Inserted " + rows + " rows");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }


    @Override
    protected Entry resultAsObject(ResultSet rs) {
        Entry entry = null;
        try {
            while (rs.next()) {
                int e_id = rs.getInt("e_id");
                int p_id = rs.getInt("p_id");
                LocalDateTime start = Entries.parseDateTimeFromString(rs.getString("start"));
                LocalDateTime end = Entries.parseDateTimeFromString(rs.getString("end"));
                entry = new Entry(e_id, p_id, start, end);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entry;
    }

    
    @Override
    protected List<Entry> resultAsList(ResultSet rs) {
        List<Entry> entries = new ArrayList<>();
        try {
            while (rs.next()) {
                int e_id = rs.getInt("e_id");
                int p_id = rs.getInt("p_id");
                LocalDateTime start = Entries.parseDateTimeFromString(rs.getString("start"));
                LocalDateTime end = Entries.parseDateTimeFromString(rs.getString("end"));
                entries.add(new Entry(e_id, p_id, start, end));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }
    
    
    
    

}
