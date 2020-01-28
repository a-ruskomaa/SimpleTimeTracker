package fxTyoaika.controller;

import java.util.ArrayList;
import java.util.List;

import fxTyoaika.model.Entry;
import fxTyoaika.model.Project;
import fxTyoaika.model.TempUsers;
import fxTyoaika.model.Timer;
import fxTyoaika.model.User;
import javafx.beans.property.SimpleStringProperty;

public class ModelAccess {
    
    private User selectedUser;
    private Project selectedProject;
    private Entry selectedEntry;
    
    private List<User> userList;
    
    private Timer timer;
    
    public ModelAccess() {
        this.userList = loadUsers();
        this.timer = new Timer();
//        this.selectedUser = userList.get(0);
//        this.selectedProject = selectedUser.getProjects().get(0);
    }
    
    // lataa käyttäjät tiedostosta / tietokannasta
    private List<User> loadUsers() {
        return TempUsers.getUsers();
    }
    
    public List<User> getUserList() {
        return this.userList;
    }
    
    public Project getSelectedProject() {
        return this.selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }
    
    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Entry getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(Entry selectedEntry) {
        this.selectedEntry = selectedEntry;
    }

    public Timer getTimer() {
        return this.timer;
    }
    
    //
}
