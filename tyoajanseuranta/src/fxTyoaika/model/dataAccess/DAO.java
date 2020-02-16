package fxTyoaika.model.dataAccess;

import java.util.List;

import fxTyoaika.model.DataObject;

public interface DAO <K, T> {
    
//    public List<T> getData();
    
    public T create(T object);
    
    public T read(K key);
    
    public T update(T object);
    
    public boolean delete(K key);
    
    public List<T> list();
}
