package roarusko.simpleTimeTracker.model.data.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.ConnectionManager;
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
public class ProjectDAO extends AbstractChildDAO<Project, User>  {
    private static final String tableName = "Projects";
    private static final String idName = "p_id";
    private static final String parentIdName = "u_id";


    /**
     * Luo uuden ProjectDAO annetulla tallennustiedostolla
     * @param connectionManager Olio, jolla luodaan tietokantayhteys
     * 
     */
    public ProjectDAO(ConnectionManager connectionManager) {
        super(connectionManager, tableName, idName, parentIdName);
    }

    @Override
    public Project create(Project object) {
        String query1 = "INSERT INTO Projects (name) "
                + "VALUES (?);";
        String query2 = "INSERT INTO UserProject (u_id, p_id) "
                + "VALUES (?, last_insert_rowid());";
        int rows1 = 0;
        int rows2 = 0;
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql1 = con.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement sql2 = con.prepareStatement(query2)) {
            con.setAutoCommit(false);
            sql1.setString(1, object.getName());
            sql2.setInt(1, object.getOwnerId());
            rows1 = sql1.executeUpdate();
            rows2 = sql2.executeUpdate();
            con.commit();
            System.out.println("Inserted " + rows1 + " + " + rows2 + " rows to " + tableName);
            if (rows1 + rows2 <= 1) {
                throw new SQLException("Creating the project failed.");
            }
            try (ResultSet generatedKeys = sql1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    object.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating the project failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return object;
    }
    
    // TODO joinit
    @Override
    public Project read(Integer key) {
        String query = "SELECT * FROM Projects "
                + "JOIN UserProject ON Projects.p_id = UserProject.p_id "
                + "WHERE Projects.p_id = ?;";
        Project object = null;
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

    
    // TODO joinit
    @Override
    public boolean update(Project object) {
        String query = "UPDATE Projects "
                + "SET name = ? "
                + "WHERE p_id = ?;";
        // TODO tähän toteutus jos projektiin on lisätty uusia käyttäjiä
        int rows = 0;
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(query)) {
            sql.setString(1, object.getName());
            sql.setInt(2, object.getId());
            rows = sql.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Updating the project failed.");
            }
            System.out.println("Inserted " + rows + " rows");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }


    // TODO joinit
    @Override
    public boolean delete(Integer key) {
        String query1 = "DELETE FROM Projects WHERE p_id = ?;";
        String query2 = "DELETE FROM UserProject WHERE p_id = ?;";
        int rows1 = 0;
        int rows2 = 0;
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql1 = con.prepareStatement(query1);
            PreparedStatement sql2 = con.prepareStatement(query2)) {
            con.setAutoCommit(false);
            sql1.setInt(1, key);
            sql2.setInt(1, key);
            rows1 = sql1.executeUpdate();
            rows2 = sql2.executeUpdate();
            con.commit();
            System.out.println("Deleted " + rows1 + " + " + rows2 + " rows from Projects");
            if (rows1 + rows2 > 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }


    // TODO joinit
    @Override
    public List<Project> list() {
        String query = "SELECT * FROM Projects "
                + "JOIN UserProject ON Projects.p_id = UserProject.p_id;";
        List<Project> objects = new ArrayList<>();
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
    
    @Override
    public List<Project> list(User user) {
        String query = "SELECT * FROM Projects "
                + "JOIN UserProject ON Projects.p_id = UserProject.p_id "
                + "WHERE UserProject.u_id = ?;";
        List<Project> objects = new ArrayList<>();
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(query)) {
            sql.setInt(1, user.getId());
            @SuppressWarnings("resource") //suljetaan automaattisesti
            ResultSet rs = sql.executeQuery();
            objects = resultAsList(rs);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return objects;
    }
    


    @Override
    protected Project resultAsObject(ResultSet rs) {
        Project project = null;
        try {
            while (rs.next()) {
                int p_id = rs.getInt("p_id");
                int u_id = rs.getInt("u_id");
                String name = rs.getString("name");
                //TODO tämä kuntoon!
                project = new Project(p_id, u_id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    protected List<Project> resultAsList(ResultSet rs) {
        List<Project> projects = new ArrayList<>();
        try {
            while (rs.next()) {
                int p_id = rs.getInt("p_id");
                int u_id = rs.getInt("u_id");
                String name = rs.getString("name");
                projects.add(new Project(p_id, u_id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
    

}
