package roarusko.simpleTimeTracker.model.data.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found");
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection("jdbc:sqlite:" + path);
        return con;
    }
    
    public void initDb() {
        File file = new File(path);
        if (file.delete()) {
            System.out.println("file deleted");
        }

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
