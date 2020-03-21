package roarusko.simpleTimeTracker.model.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Luokka tietokantayhteyden luomiseen
 * @author aleks
 * @version 21 Mar 2020
 *
 */
public class ConnectionManagerImpl implements ConnectionManager {
    private static final String defaultPath = "data/data.db";
    private final String path;

    /**
     * ConnectionManagerImpl:n konstruktori
     * @param path Polku tietokannan sijaintiin
     */
    public ConnectionManagerImpl(String path) {
        this.path = path;
    }

    /**
     * ConnectionManagerImpl:n konstruktori. Määrittää tietokannan sijainniksi
     * oletussijainnin.
     */
    public ConnectionManagerImpl() {
        this.path = defaultPath;
    }


    @Override
    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:" + path);
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to get a database connection");
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void initDb() {
        String query1 = "CREATE TABLE IF NOT EXISTS Users (\n" + 
                "    u_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" + 
                "    name VARCHAR(50) NOT NULL );\n" + 
                "\n";
        String query2 = "CREATE TABLE IF NOT EXISTS Projects (\n" + 
                "    p_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" + 
                "    name VARCHAR(50) NOT NULL );\n" + 
                "\n";
        String query3 = "CREATE TABLE IF NOT EXISTS UserProject (\n" + 
                "    u_id INTEGER,\n" + 
                "    p_id INTEGER,\n" + 
                "    PRIMARY KEY (u_id, p_id),\n" + 
                "    FOREIGN KEY (u_id) REFERENCES Users (u_id),\n" + 
                "    FOREIGN KEY (p_id) REFERENCES Projects (p_id) );\n" + 
                "\n";
        String query4 = "CREATE TABLE IF NOT EXISTS Entries (\n" + 
                "    e_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" + 
                "    p_id INTEGER NOT NULL,\n" + 
                "    start DATETIME NOT NULL,\n" + 
                "    end DATETIME NOT NULL,\n" + 
                "    FOREIGN KEY (p_id) REFERENCES Projects (p_id) );";

        try (Connection con = this.getConnection();
                Statement sql = con.createStatement()) {
            sql.addBatch(query1);
            sql.addBatch(query2);
            sql.addBatch(query3);
            sql.addBatch(query4);
            sql.executeBatch();
            System.out.println("Database created: " + path);
        } catch (SQLException e) {
            System.err.println("Database creation failed: " + e.getMessage());
        }
    }

}
