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
        //ArrayList<T> entries = (ArrayList<T>) SampleData.getData(T.class);
        ArrayList<T> entries = (ArrayList<T>) getData();
        
        
        for (int i = 0; i < entries.size() ; i++) {
            if (entries.get(i).getId() == key) {
                return entries.get(i);
            }
        }
        
        return null;
    }

    @Override
    public T update(T object) {
//        ArrayList<T> entries = (ArrayList<T>) SampleData.getData(T.class);
        ArrayList<T> entries = (ArrayList<T>) getData();
        
        int key = object.getId();
        
        for (int i = 0; i < entries.size() ; i++) {
            if (entries.get(i).getId() == key) {
                entries.set(i, object);
                return entries.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean delete(Integer key) {
//        ArrayList<T> entries = (ArrayList<T>) SampleData.getData(T.class);

        ArrayList<T> entries = (ArrayList<T>) getData();
        
        for (int i = 0; i < entries.size() ; i++) {
            if (entries.get(i).getId() == key) {
                entries.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<T> list(Class<T> clazz) {
        return (List<T>) SampleData.getData(clazz);
    }
    
    // TODO tätä pitää miettiä, mihin castaus?
    public List<T> list(Class<T> clazz, ParentObject object) {
        return (List<T>) SampleData.getData(clazz).stream().filter(e -> ((ChildObject) e).getOwner().equals(object)).collect(Collectors.toList());
    }



}
