package fxTyoaika.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class User {
    private static final AtomicInteger idGenerator = new AtomicInteger(1000);
    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final ListProperty<Project> projects = new SimpleListProperty<Project>();
            
    public User(int id, String name) {
        this.id.set(id);
        this.name.set(name);
        this.projects.set(FXCollections.observableArrayList());
    }
    
    public User(int id, String name, ObservableList<Project> projects) {
        this.id.set(id);
        this.name.set(name);
        this.projects.set(projects);
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
    
    public ObservableList<Project> getProjects() {
        return projects.get();
    }
    
    public void setProjects(ObservableList<Project> projects) {
        this.projects.set(projects);
    }

    public void addProject(Project project) {
        projects.add(project);
    }
    
    @Override
    public String toString() {
        return this.name.get();
    }
    //
}
