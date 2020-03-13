package roarusko.simpleTimeTracker.model.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteDB {

    public SqliteDB() {
        String tiedNimi = "kerhomalli.db";
        File ftied = new File(tiedNimi);
        ftied.delete();

        String luontilauseke =
                "CREATE TABLE Jasenet (\n" +
                "  jasenID INTEGER PRIMARY KEY AUTOINCREMENT,\n"+
                "  nimi VARCHAR(100) NOT NULL,\n"+
                "  hetu VARCHAR(11) DEFAULT '',\n"+
                "  katuosoite  VARCHAR(100) NOT NULL\n"+
                ")";

        try ( Connection con = DriverManager.getConnection("jdbc:sqlite:" + tiedNimi);
              PreparedStatement sql = con.prepareStatement(luontilauseke) ) {
            sql.executeUpdate();
            System.out.println("Luotiin tietokanta: " + tiedNimi);
        } catch (SQLException e) {
            System.err.println("Virhe: " + e.getMessage());
        }
    }

}
