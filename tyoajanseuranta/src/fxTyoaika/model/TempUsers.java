package fxTyoaika.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import fxTyoaika.Model.*;

public class TempUsers {

    public static List<User> getUsers() {
        ArrayList<User> list = new ArrayList<>();
        
        User janne = new User(1, "Janne");
        User jonne = new User(2, "Jonne");
        
        ArrayList<Project> projectList1 = new ArrayList<>(Arrays.asList(new Project[] { new Project(1, "ohj2", janne), new Project(2, "tika", janne)}));
        
        ArrayList<Project> projectList2 = new ArrayList<>(Arrays.asList(new Project[] { new Project(3, "ohj1", jonne), new Project(4, "alg1", jonne)}));
        
        janne.setProjects(projectList1);
        jonne.setProjects(projectList2);
        
        janne.getProjects().get(0).addEntry("11.06.2019 21:30:45", "11.06.2019 22:45:12");
        janne.getProjects().get(0).addEntry("12.06.2019 12:29:00", "12.06.2019 21:12:05");
        janne.getProjects().get(0).addEntry("14.06.2019 21:25:52", "14.06.2019 23:32:49");
        janne.getProjects().get(0).addEntry("15.06.2019 15:30:45", "15.06.2019 20:25:15");

        janne.getProjects().get(1).addEntry("13.06.2019 12:29:00", "13.06.2019 21:12:05");
        janne.getProjects().get(1).addEntry("15.06.2019 21:25:52", "15.06.2019 22:21:49");
        janne.getProjects().get(1).addEntry("16.06.2019 16:14:45", "16.06.2019 22:25:15");
        
        jonne.getProjects().get(0).addEntry("11.06.2019 21:22:22", "11.06.2019 22:45:12");
        jonne.getProjects().get(0).addEntry("12.06.2019 11:21:09", "12.06.2019 21:12:05");
        jonne.getProjects().get(0).addEntry("14.06.2019 21:25:52", "14.06.2019 23:32:49");
        
        jonne.getProjects().get(1).addEntry("11.06.2019 21:30:45", "11.06.2019 22:45:12");
        jonne.getProjects().get(1).addEntry("12.06.2019 12:29:00", "12.06.2019 21:12:05");
        jonne.getProjects().get(1).addEntry("14.06.2019 21:25:52", "14.06.2019 23:32:49");
        jonne.getProjects().get(1).addEntry("15.06.2019 15:30:45", "15.06.2019 22:25:15");
        
        list.add(janne);
        list.add(jonne);
        
        return list;
    }
}