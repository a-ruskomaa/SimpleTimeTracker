package roarusko.simpleTimeTracker.model.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.ConnectionManager;
import roarusko.simpleTimeTracker.model.domain.User;

/**
 * Käyttäjiä tiedostoon tallentava ja lukeva luokka. Tiedostojen käsittely toteutetaan abstraktin yläluokan
 * tarjoamilla metodeilla. Luokka sisältää tarvittavan logiikan, jolla merkkijonoista saadaan luotua User-tyyppisiä
 * olioita ja User-olioista merkkijonoja.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class UserDAO extends AbstractDAO<User> {
    private static final String tableName = "Users";
    private static final String idName = "u_id";
     

     
     /**
      * Luo uuden UserDAO:n annetulla tallennustiedostolla
     * @param connectionManager Olio, jolla luodaan tietokantayhteys
      * 
      */
     public UserDAO(ConnectionManager connectionManager) {
         super(connectionManager, tableName, idName);
     }




    @Override
    public User create(User object) {
        String query = "INSERT INTO Users (name) "
                + "VALUES (?);";
        int rows = 0;
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            sql.setString(1, object.getName());
            rows = sql.executeUpdate();
            System.out.println("Inserted " + rows + " rows to " + tableName);
            if (rows == 0) {
                throw new SQLException("Creating the user failed.");
            }
            try (ResultSet generatedKeys = sql.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    object.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating the user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return object;
    }



    @Override
    public boolean update(User object) {
        String query = "UPDATE Users "
                + "SET name = ? "
                + "WHERE u_id = ?;";
        int rows = 0;
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(query)) {
            sql.setString(1, object.getName());
            sql.setInt(2, object.getId());
            rows = sql.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Updating the user failed.");
            }
            System.out.println("Inserted " + rows + " rows");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    
    
    @Override
    protected User resultAsObject(ResultSet rs) {
        User user = null;
        try {
            while (rs.next()) {
                int u_id = rs.getInt("u_id");
                String name = rs.getString("name");
                user = new User(u_id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    @Override
    protected List<User> resultAsList(ResultSet rs) {
        List<User> users = new ArrayList<>();
        try {
            while (rs.next()) {
                int u_id = rs.getInt("u_id");
                String name = rs.getString("name");
                users.add(new User(u_id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
