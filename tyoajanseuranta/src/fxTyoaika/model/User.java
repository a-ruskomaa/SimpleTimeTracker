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
public class User implements ParentObject {
    
    private final int id;
    private String name;
    private List<Project> projects;
    
    public User(String name) {
        this(-1, name, new ArrayList<Project>());
    }
    
    public User(int id, String name) {
        this(id, name, new ArrayList<Project>());
    }

    public User(int id, String name, List<Project> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
    }

    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }
    
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        projects.add(project);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    //
}
