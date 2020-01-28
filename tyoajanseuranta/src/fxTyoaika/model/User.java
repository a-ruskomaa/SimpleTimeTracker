package fxTyoaika.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class User {
    
    //SimpleStringProperty name;
    private int id;
    private String name;
    private List<Project> projects;
    
    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.projects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public int getId() {
        return id;
    }
    
    public void addProject(Project project) {
        projects.add(project);
    }
    
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    //
}
