package roarusko.simpleTimeTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import roarusko.simpleTimeTracker.model.domainModel.DataObject;
import roarusko.simpleTimeTracker.model.domainModel.Entry;
import roarusko.simpleTimeTracker.model.domainModel.Project;
import roarusko.simpleTimeTracker.model.domainModel.User;
import roarusko.simpleTimeTracker.model.utility.Entries;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import fxTyoaika.Model.*;

public class SampleData {
    private static ArrayList<User> users;

    private static ArrayList<Project> projects;

    private static ArrayList<Entry> entries;

    static {
        users = new ArrayList<User>();
        User janne = new User(IdGenerator.getNewUserId(), "Janne");
        User jonne = new User(IdGenerator.getNewUserId(), "Jonne");
        User jenni = new User(IdGenerator.getNewUserId(), "Jenni");
        User joonas = new User(IdGenerator.getNewUserId(), "Joonas");

        users.add(janne);
        users.add(jonne);
        users.add(jenni);
        users.add(joonas);

        projects = new ArrayList<Project>();
        Project janne_ohj2 = new Project(IdGenerator.getNewProjectId(), "ohj2",
                janne);
        Project janne_tika = new Project(IdGenerator.getNewProjectId(), "tika",
                janne);
        Project jonne_ohj1 = new Project(IdGenerator.getNewProjectId(), "ohj1",
                jonne);
        Project jonne_alg1 = new Project(IdGenerator.getNewProjectId(), "alg1",
                jonne);
        Project janne_alg1 = new Project(IdGenerator.getNewProjectId(), "alg1",
                janne);
        Project jonne_olio = new Project(IdGenerator.getNewProjectId(), "olio",
                jonne);
        Project jenni_wepa = new Project(IdGenerator.getNewProjectId(), "wepa",
                jenni);
        Project jenni_weka = new Project(IdGenerator.getNewProjectId(), "weka",
                jenni);
        Project jenni_fun1 = new Project(IdGenerator.getNewProjectId(), "fun1",
                jenni);
        projects.add(janne_ohj2);
        projects.add(janne_tika);
        projects.add(jonne_ohj1);
        projects.add(jonne_alg1);
        projects.add(janne_alg1);
        projects.add(jonne_olio);
        projects.add(jenni_wepa);
        projects.add(jenni_weka);
        projects.add(jenni_fun1);

        entries = new ArrayList<Entry>();
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_ohj2,
                Entries.parseDateTimeFromString("11.06.2019 21:30:45"),
                Entries.parseDateTimeFromString("11.06.2019 22:45:12")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_ohj2,
                Entries.parseDateTimeFromString("12.06.2019 12:29:00"),
                Entries.parseDateTimeFromString("12.06.2019 21:12:05")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_ohj2,
                Entries.parseDateTimeFromString("14.06.2019 16:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 17:32:49")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_ohj2,
                Entries.parseDateTimeFromString("14.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 23:32:49")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_ohj2,
                Entries.parseDateTimeFromString("15.06.2019 15:30:45"),
                Entries.parseDateTimeFromString("15.06.2019 20:25:15")));

        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_tika,
                Entries.parseDateTimeFromString("13.06.2019 12:29:00"),
                Entries.parseDateTimeFromString("13.06.2019 21:12:05")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_tika,
                Entries.parseDateTimeFromString("15.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("15.06.2019 22:21:49")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_tika,
                Entries.parseDateTimeFromString("16.06.2019 16:14:45"),
                Entries.parseDateTimeFromString("16.06.2019 22:25:15")));

        entries.add(new Entry(IdGenerator.getNewEntryId(), jonne_ohj1,
                Entries.parseDateTimeFromString("11.06.2019 21:22:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45:12")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), jonne_ohj1,
                Entries.parseDateTimeFromString("12.06.2019 11:21:09"),
                Entries.parseDateTimeFromString("12.06.2019 21:12:05")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), jonne_ohj1,
                Entries.parseDateTimeFromString("14.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 23:32:49")));

        entries.add(new Entry(IdGenerator.getNewEntryId(), jonne_alg1,
                Entries.parseDateTimeFromString("11.06.2019 21:30:45"),
                Entries.parseDateTimeFromString("11.06.2019 22:45:12")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), jonne_alg1,
                Entries.parseDateTimeFromString("12.06.2019 12:29:00"),
                Entries.parseDateTimeFromString("12.06.2019 21:12:05")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), jonne_alg1,
                Entries.parseDateTimeFromString("14.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 23:32:49")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), jonne_alg1,
                Entries.parseDateTimeFromString("15.06.2019 15:30:45"),
                Entries.parseDateTimeFromString("15.06.2019 22:25:15")));

        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_alg1,
                Entries.parseDateTimeFromString("11.06.2019 21:22:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45:12")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_alg1,
                Entries.parseDateTimeFromString("12.06.2019 11:21:09"),
                Entries.parseDateTimeFromString("12.06.2019 14:12:05")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_alg1,
                Entries.parseDateTimeFromString("12.06.2019 16:14:45"),
                Entries.parseDateTimeFromString("12.06.2019 22:25:15")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_alg1,
                Entries.parseDateTimeFromString("14.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 23:32:49")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), janne_alg1,
                Entries.parseDateTimeFromString("15.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("15.06.2019 22:21:49")));

        entries.add(new Entry(IdGenerator.getNewEntryId(), jenni_weka,
                Entries.parseDateTimeFromString("11.06.2019 21:22:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45:12")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), jenni_weka,
                Entries.parseDateTimeFromString("12.06.2019 11:21:09"),
                Entries.parseDateTimeFromString("12.06.2019 14:12:05")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), jenni_weka,
                Entries.parseDateTimeFromString("12.06.2019 16:14:45"),
                Entries.parseDateTimeFromString("16.06.2019 22:25:15")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), jenni_weka,
                Entries.parseDateTimeFromString("14.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 23:32:49")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), jenni_weka,
                Entries.parseDateTimeFromString("15.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("15.06.2019 22:21:49")));

        entries.add(new Entry(IdGenerator.getNewEntryId(), jenni_wepa,
                Entries.parseDateTimeFromString("13.06.2019 12:29:00"),
                Entries.parseDateTimeFromString("13.06.2019 21:12:05")));
        entries.add(new Entry(IdGenerator.getNewEntryId(), jenni_wepa,
                Entries.parseDateTimeFromString("15.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("15.06.2019 22:21:49")));

    }

    public static List<? extends DataObject> getData(
            Class<? extends DataObject> clazz) {
        if (clazz.equals(User.class))
            return users;
        if (clazz.equals(Project.class))
            return projects;
        if (clazz.equals(Entry.class))
            return entries;
        return null;
    }


//    public static List<Entry> getEntries() {
//        return entries;
//    }
//
//
//    public static List<User> getUsers() {
//        return users;
//    }
//
//
//    public static List<Project> getProjects() {
//        return projects;
//    }

}
