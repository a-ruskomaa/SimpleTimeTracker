package fxTyoaika.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Project {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty<User> user = new SimpleObjectProperty<User>();
    private ListProperty<Entry> entries = new SimpleListProperty<Entry>();
    
    public Project(int id, String name, User user) {
        this.id.set(id);
        this.name.set(name);
        this.user.set(user);
    }
    
    public Project(int id, String name, User user, ObservableList<Entry> entries) {
        this.id.set(id);
        this.name.set(name);
        this.user.set(user);
        this.entries.set(entries);
    }
    
    public int getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
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

    public void setEntries(ObservableList<Entry> entries) {
        this.entries.set(entries);
    }
    
    
    @Override
    public String toString() {
        return this.name.get();
    }
    
    
    //
}
