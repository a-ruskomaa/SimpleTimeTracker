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

    public int getId() {
        return id;
    }
    
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
