package fxTyoaika.model;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Project implements ParentObject, ChildObject {
    private static final AtomicInteger idGenerator = new AtomicInteger(1000);
    
    private final int id;
    private String name;
    private User user;
    private List<Entry> entries;
    
    
    public Project(String name, User user) {
        this(idGenerator.getAndIncrement(), name, user);
    }
    
    public Project(int id, String name, User user) {
        this(id, name, user, new ArrayList<Entry>());
    }
    
    public Project(int id, String name, User user, List<Entry> entries) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.entries = entries;
    }
    
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public User getOwner() {
        return this.user;
    }
    
    public List<Entry> getEntries() {
        return this.entries;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    public void setUser(User user) {
        this.user = user;
    }


    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    
    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }
    
    
    public void removeEntry(Entry entry) {
        this.entries.remove(entry);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    
    //
}
