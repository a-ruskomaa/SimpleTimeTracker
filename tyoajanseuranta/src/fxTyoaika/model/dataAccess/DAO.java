package fxTyoaika.model.dataAccess;

import java.util.List;

/**
 * 
 * Rajapinta lupaa sen toteuttaville DAO-luokille (Data Access Object) toiminnallisuudet
 * yksinkertaisiin CRUD (Create, Read, Update, Delete) -operaatioihin sekä kaikkien kyseistä
 * tietotyyppiä edustavien alkioiden listaamiseen
 * @author aleks
 * @version 17 Feb 2020
 *
 * @param <K> avainta edustava tietotyyppi, esim. Integer
 * @param <T> luokkaa edustava tietotyyppi
 */
public interface DAO <K, T> {
    
//    public List<T> getData();
    
    /**
     * Lisää pysyvään muistiin uuden T-olion
     * @param object Lisättävä olio
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    public T create(T object);
    
    
    /**
     * Lukee annettua avainta vastaavan alkion tiedot pysyvästä muistista
     * @param key Yksilöivä hakuavain, esim. id-luku
     * @return Palauttaa hakuavainta vastaavista tiedoista luodun olion
     */
    public T read(K key);
    
    
    /**
     * Päivittää annettua oliota vastaavan alkion tiedot pysyvään muistiin
     * @param object Olio, jonka sisältämät tiedot halutaan päivittää pysyvään muistiin
     * @return Palauttaa päivitetyn olion
     */
    public T update(T object);
    
    
    /**
     * Poistaa annettua avainta vastaavan alkion tiedot pysyvästä muistista
     * @param key Yksilöivä hakuavain, esim. id-luku
     * @return Palauttaa false mikäli poisto-operaatio epäonnistui
     */
    public boolean delete(K key);
    
    
    /**
     * Lukee kaikkien tietotyyppiä vastaavien alkioiden tiedot pysyvästä muistista
     * @return Palauttaa kaikki haetut alkiot listalla
     */
    public List<T> list();
}
