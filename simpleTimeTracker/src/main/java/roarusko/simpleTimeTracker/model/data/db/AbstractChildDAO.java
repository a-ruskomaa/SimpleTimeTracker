package roarusko.simpleTimeTracker.model.data.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import roarusko.simpleTimeTracker.model.data.ChildDAO;
import roarusko.simpleTimeTracker.model.data.ConnectionManager;
import roarusko.simpleTimeTracker.model.domain.ChildObject;
import roarusko.simpleTimeTracker.model.domain.ParentObject;

/**
 * 
 * @author aleks
 * @version 2 Mar 2020
 *
 * @param <T> ChildObject-rajapinnan toteuttava luokka
 * @param <P> ParentObject-rajapinnan toteuttava luokka
 */
public abstract class AbstractChildDAO<T extends ChildObject, P extends ParentObject> extends AbstractDAO<T> implements ChildDAO<Integer, T, P> {
    private final String parentid;


    /**
     * @param connectionManager Olio, jolla luodaan tietokantayhteys
     * @param tableName Tämän olion hallinnoiman tietokantataulun nimi
     * @param id Luokkaa edustavan olion id-sarakkeen nimi tietokantataulussa
     * @param parentid Luokan omistajaa edustavan olion id-sarakkeen nimi tietokantataulussa
     */
    public AbstractChildDAO(ConnectionManager connectionManager, String tableName, String id, String parentid) {
        super(connectionManager, tableName, id);
        this.parentid = parentid;
    }


    /**
     * Suodattaa annetusta listasta halutun omistajan oliot, eli esimerkiksi tietyn käyttäjän projektit
     * tai tietyn projektin merkinnät.
     * @param parent ParentObject-rajapinnan toteuttava olio (User tai Project), jolle kuuluvat domain-objektit halutaan listata
     * @return Palauttaa suodatetun listan T-tyyppisiä olioita
     */
    @Override
    public List<T> list(ParentObject parent) {
        String query = "SELECT * FROM " + table + " WHERE "  + parentid + " = ?;";
        List<T> objects = new ArrayList<T>();
        try (Connection con = connectionManager
                .getConnection();
                PreparedStatement sql = con.prepareStatement(query)) {
            sql.setInt(1, parent.getId());
            @SuppressWarnings("resource") //suljetaan automaattisesti
            ResultSet rs = sql.executeQuery();
            objects = resultAsList(rs);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return objects;
    }
}
