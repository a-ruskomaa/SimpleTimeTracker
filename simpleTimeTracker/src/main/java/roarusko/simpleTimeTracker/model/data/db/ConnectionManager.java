package roarusko.simpleTimeTracker.model.data.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String defaultPath = "data/data.db";
    private final String path;

    public ConnectionManager(String path) {
        this.path = path;
    }


    public ConnectionManager() {
        this.path = defaultPath;
    }


    public Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:sqlite:" + path);
        return con;
    }
    
    public void initDb() {
//        File file = new File(path);
//        file.delete();

        String pst = "CREATE TABLE IF NOT EXISTS Users (\n" + 
                "    u_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" + 
                "    name VARCHAR(50) NOT NULL );\n" + 
                "\n" + 
                "CREATE TABLE IF NOT EXISTS UserProject (\n" + 
                "    p_id INTEGER,\n" + 
                "    u_id INTEGER,\n" + 
                "    PRIMARY KEY (p_id, u_id),\n" + 
                "    FOREIGN KEY (p_id) REFERENCES Projects (p_id),\n" + 
                "    FOREIGN KEY (u_id) REFERENCES Users (u_id) );\n" + 
                "\n" + 
                "CREATE TABLE IF NOT EXISTS Projects (\n" + 
                "    p_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" + 
                "    name VARCHAR(50) NOT NULL );\n" + 
                "\n" + 
                "CREATE TABLE IF NOT EXISTS Entries (\n" + 
                "    e_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" + 
                "    p_id INTEGER NOT NULL,\n" + 
                "    start_time DATETIME NOT NULL,\n" + 
                "    end_time DATETIME NOT NULL,\n" + 
                "    FOREIGN KEY (p_id) REFERENCES Projects (p_id) );";

        try (Connection con = this.getConnection();
                PreparedStatement sql = con.prepareStatement(pst)) {
            sql.executeUpdate();
            System.out.println("Database created: " + path);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
