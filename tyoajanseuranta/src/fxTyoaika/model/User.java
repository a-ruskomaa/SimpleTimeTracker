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
    
    
    /**
     * Luo uuden käyttäjän, jolle on annettu parametrina pelkkä nimi. Käytetään kun luodaan DAO:lla uutta
     * käyttäjää.
     * @param name Käyttäjän nimi
     */
    public User(String name) {
        this(-1, name, new ArrayList<Project>());
    }
    
    /**
     * Luo uuden käyttäjän, jolle ei ole vielä lisätty projekteja. DAO palauttaa tässä
     * muodossa olevan olion.
     * @param id Käyttäjän yksilöllinen id-numero
     * @param name Käyttäjän nimi
     */
    public User(int id, String name) {
        this(id, name, new ArrayList<Project>());
    }

    /**
     * Luo uuden käyttäjän, jolla on valmiiksi lisättynä projektit. DAO palauttaa tässä
     * muodossa olevan olion kun on luotu uusi
     * @param id Käyttäjän yksilöllinen id-numero
     * @param name Käyttäjän nimi
     * @param projects käyttäjälle liitettävät projektit
     */
    public User(int id, String name, List<Project> projects) {
        this.id = id;
        this.name = name;
        this.projects = projects;
    }

    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return this.projects;
    }
    
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }
    
    public void updateProject(Project project) {
        for (int i = 0; i < this.projects.size(); i++) {
            if (this.projects.get(i).getId() == project.getId()) {
                this.projects.set(i, project);
            }
        }
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    //
}
