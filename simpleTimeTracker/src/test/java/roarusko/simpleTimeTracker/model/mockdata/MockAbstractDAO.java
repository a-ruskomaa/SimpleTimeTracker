package roarusko.simpleTimeTracker.model.mockdata;

import java.util.List;
import java.util.stream.Collectors;

import roarusko.simpleTimeTracker.model.data.DAO;
import roarusko.simpleTimeTracker.model.domain.ChildObject;
import roarusko.simpleTimeTracker.model.domain.DataObject;
import roarusko.simpleTimeTracker.model.domain.ParentObject;

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
public abstract class MockAbstractDAO<T extends DataObject> implements DAO<Integer, T> {

    @SuppressWarnings("javadoc")
    protected List<T> data;

    
    /**
     * Lukee annettua avainta vastaavan alkion tiedot pysyvästä muistista
     * @param key Yksilöivä hakuavain, esim. id-luku
     * @return Palauttaa hakuavainta vastaavista tiedoista luodun olion
     */
    @Override
    public T read(Integer key) {
        
        for (T t : data) {
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
        int key = object.getId();
        
        for (int i = 0; i < data.size() ; i++) {
            if (data.get(i).getId() == key) {
                data.set(i, object);
                return data.get(i);
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
        for (int i = 0; i < data.size() ; i++) {
            if (data.get(i).getId() == key) {
                data.remove(i);
                return true;
            }
        }
        return false;
    }

    
    /**
     * Hakee kaikki annetun luokan edustajat listalle
     * @return palauttaa kaikki noudetut alkiot listalla
     */
    @Override
    public List<T> list() {
        return data;
    }
    
    /**
     * @param object haettujen alkioiden "omistaja", eli projektilla käyttäjä ja merkinnällä projekti
     * @return palauttaa kaikki tietylle omistajalle kuuluvat alkiot listalla.
     */
    protected List<T> list(ParentObject object) {
        return data.stream().filter(e -> ((ChildObject) e).getOwner().equals(object)).collect(Collectors.toList());
    }


    
}
