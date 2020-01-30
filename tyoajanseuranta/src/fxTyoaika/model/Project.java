package fxTyoaika.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Project {
    //SimpleStringProperty name;
    private int id;
    private String name;
    private User user;
    private List<Entry> entries;
    
    public Project(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.entries = new LinkedList<>();
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addEntry(Entry entry) {
        //laitetaan aluksi listan loppuun
        entries.add(entry);
    }
    
    public void addEntry(String startTime, String endTime) {
        addEntry(new Entry(startTime, endTime));
    }
    
    public void removeEntry(Entry entry) {
        entries.remove(entry);
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
    
    
    @Override
    public String toString() {
        return this.name;
    }
    
    
    //
}
