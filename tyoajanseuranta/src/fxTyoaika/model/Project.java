package fxTyoaika.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.embed.swt.FXCanvas;

/**
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Project {
    private static final AtomicInteger idGenerator = new AtomicInteger(1000);
    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<User> user = new SimpleObjectProperty<User>();
    private final ListProperty<Entry> entries = new SimpleListProperty<Entry>();
    
    public Project(int id, String name, User user) {
        this.id.set(id);
        this.name.set(name);
        this.user.set(user);
        this.entries.set(FXCollections.observableArrayList());
        // TODO luo extractor
//        this.entries.set(FXCollections.observableArrayList((p) -> )());
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
    
    public StringProperty nameProperty() {
        return this.name;
    }
    
    public User getUser() {
        return user.get();
    }
    
    public void setUser(User user) {
        this.user.set(user);
    }
    
    public ObjectProperty<User> userProperty() {
        return this.user;
    }
    
    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }

    
    public void removeEntry(Entry entry) {
        this.entries.remove(entry);
    }

    public ObservableList<Entry> getEntries() {
        return this.entries;
    }

    public void setEntries(ObservableList<Entry> entries) {
        this.entries.set(entries);
    }
    
    public ListProperty<Entry> entriesProperty() {
        return this.entries;
    }
    
    
    @Override
    public String toString() {
        return this.name.get();
    }
    
    
    //
}
