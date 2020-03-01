package roarusko.simpleTimeTracker.model.data.mock;

import java.util.List;
import java.util.stream.Collectors;

import roarusko.simpleTimeTracker.model.data.DAO;
import roarusko.simpleTimeTracker.model.domain.ChildObject;
import roarusko.simpleTimeTracker.model.domain.DataObject;
import roarusko.simpleTimeTracker.model.domain.ParentObject;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

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
    private IdGenerator idGen;
    protected List<T> data;

    public MockAbstractDAO(List<T> data) {
        this.data = data;
        this.idGen = new IdGenerator(findMaxId(data) + 1);
    }
    
    public MockAbstractDAO(List<T> data, IdGenerator idGen) {
        this.data = data;
        this.idGen = idGen;
    }
    
    public void setIdGenerator(IdGenerator idGen) {
        this.idGen = idGen;
    }
    
    /**
     * Lisää pysyvään muistiin uuden alkion tiedot, jotka ottaa vastaan oliona
     * @param object Lisättävä alkio
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    @Override
    public T create(T object) {        
        object.setId(idGen.getNewId());
        
        data.add(object);
        
        return object;
    }
    
    
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
     * @return Palauttaa false mikäli päivitysoperaatio epäonnistui
     */
    @Override
    public boolean update(T object) {
        int key = object.getId();
        
        for (int i = 0; i < data.size() ; i++) {
            if (data.get(i).getId() == key) {
                data.set(i, object);
                return true;
            }
        }
        return false;
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
        return data.stream().filter(e -> ((ChildObject) e).getOwnerId() == object.getId()).collect(Collectors.toList());
    }
    
    
    /**
     * Etsii annetulta listalta suurimman käytössä olevan id-numeron
     * @param list Lista jolta etsitään
     * @return Palauttaa suurimman löydetyn id:n (tai 0 jos lista on tyhjä)
     */
    private int findMaxId(List<? extends DataObject> list) {
        int largest = 0;
        
        for (DataObject object : list) {
            if (object.getId() > largest) largest = object.getId();
        }
        
        return largest;
    }



}
