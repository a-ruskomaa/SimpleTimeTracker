package roarusko.simpleTimeTracker.model.dataAccess;

import java.util.List;
import java.util.stream.Collectors;

import roarusko.simpleTimeTracker.SampleData;
import roarusko.simpleTimeTracker.model.domainModel.ChildObject;
import roarusko.simpleTimeTracker.model.domainModel.DataObject;
import roarusko.simpleTimeTracker.model.domainModel.ParentObject;

/**
 * 
 * Käyttäjien, projektien ja merkintöjen tietokantahakuihin tarkoitettu abstrakti luokka.
 * Hyväksyy geneerisenä tyyppiparametrinaan DataObject-rajapinnan toteuttavat luokat. Tarjoaa
 * geneerisenä toteutuksena kaikki muut DAO-rajapinnan metodit, paitsi create.
 * @author aleks
 * @version 17 Feb 2020
 *
 * @param <T> Luokan tyyppiparametri, oltava DataObject-rajapinnan toteuttava luokka, kuten User, Project tai Entry
 */
public abstract class AbstractDAO<T extends DataObject> implements DAO<Integer, T>{
    

    /**
     * Tilapäinen metodi, jota hyödynnetään haettaessa testidataa muun ohjelman käyttöön
     * @return palauttaa tyypin T olioita listalla
     */
    protected abstract List<T> getData();
    
    
    /**
     * Lukee annettua avainta vastaavan alkion tiedot pysyvästä muistista
     * @param key Yksilöivä hakuavain, esim. id-luku
     * @return Palauttaa hakuavainta vastaavista tiedoista luodun olion
     */
    @Override
    public T read(Integer key) {
        List<T> objects = getData();
        
        for (T t : objects) {
            if (t.getId() == key) return t;
        }
        
        return null;
    }

    /**
     * Päivittää annettua oliota vastaavan alkion tiedot pysyvään muistiin
     * @param object Olio, jonka sisältämät tiedot halutaan päivittää pysyvään muistiin
     * @return Palauttaa päivitetyn olion
     */
    @Override
    public T update(T object) {
        List<T> objects = getData();
        
        int key = object.getId();
        
        for (int i = 0; i < objects.size() ; i++) {
            if (objects.get(i).getId() == key) {
                objects.set(i, object);
                return objects.get(i);
            }
        }
        return null;
    }

    /**
     * Poistaa annettua avainta vastaavan alkion tiedot pysyvästä muistista
     * @param key Yksilöivä hakuavain, esim. id-luku
     * @return Palauttaa false mikäli poisto-operaatio epäonnistui
     */
    @Override
    public boolean delete(Integer key) {

        List<T> objects = getData();
        
        for (int i = 0; i < objects.size() ; i++) {
            if (objects.get(i).getId() == key) {
                objects.remove(i);
                return true;
            }
        }
        return false;
    }

    
    /**
     * Hakee kaikki annetun luokan edustajat listalle
     * @param clazz luokka, jonka edustajat halutaan noutaa
     * @return palauttaa kaikki noudetut alkiot listalla
     */
    @SuppressWarnings("unchecked")
    protected List<T> list(Class<T> clazz) {
        return (List<T>) SampleData.getData(clazz);
    }
    
    /**
     * @param clazz luokka, jonka edustajat halutaan noutaa listalle
     * @param object haettujen alkioiden "omistaja", eli projektilla käyttäjä ja merkinnällä projekti
     * @return palauttaa kaikki tietylle omistajalle kuuluvat alkiot listalla.
     */
    @SuppressWarnings("unchecked")
    protected List<T> list(Class<T> clazz, ParentObject object) {
        // TODO tätä pitää miettiä, mihin castaus?
        return (List<T>) SampleData.getData(clazz).stream().filter(e -> ((ChildObject) e).getOwner().equals(object)).collect(Collectors.toList());
    }



}
