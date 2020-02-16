package fxTyoaika.model.dataAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fxTyoaika.SampleData;
import fxTyoaika.model.ChildObject;
import fxTyoaika.model.DataObject;
import fxTyoaika.model.Entry;
import fxTyoaika.model.ParentObject;

public abstract class AbstractDAO<T extends DataObject> implements DAO<Integer, T>{
    

    public abstract List<T> getData();
    
    @Override
    public T read(Integer key) {
        List<T> objects = getData();
        
        for (T t : objects) {
            if (t.getId() == key) return t;
        }
        
        return null;
    }

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
    protected List<T> list(Class<T> clazz, ParentObject object) {
        // TODO tätä pitää miettiä, mihin castaus?
        return (List<T>) SampleData.getData(clazz).stream().filter(e -> ((ChildObject) e).getOwner().equals(object)).collect(Collectors.toList());
    }



}
