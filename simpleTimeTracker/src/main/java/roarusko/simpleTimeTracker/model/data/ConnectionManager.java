package roarusko.simpleTimeTracker.model.data;

import java.sql.Connection;

/**
 * Rajapinta tietokantayhteyden luomiseen. Sisältää toistaiseksi myös tietokannan luontiskeeman
 * ja metodin tietokannan alustamiseen.
 * @author aleks
 * @version 21 Mar 2020
 *
 */
public interface ConnectionManager {

    /**
     * Luo uuden tietokantayhteyden
     * @return Palauttaa luodun tietokantayhteyden
     */
    public Connection getConnection();
    
    /**
     * Alustaa tietokannan. Luo tarvittaessa skeeman mukaiset taulut.
     */
    public void initDb();

}
