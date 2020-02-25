package roarusko.simpleTimeTracker.model.mockdata;

import java.util.ArrayList;

import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.Entries;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

/**
 * Luo mallidataa ohjelman kehitystä ja testausta varten
 * @author aleks
 * @version 25 Feb 2020
 *
 */
public class SampleData {

    private static final IdGenerator userIdGen = new IdGenerator();
    private static final IdGenerator projectIdGen = new IdGenerator();
    private static final IdGenerator entryIdGen = new IdGenerator();

    private static final ArrayList<User> users;

    private static final ArrayList<Project> projects;

    private static final ArrayList<Entry> entries;

    static {
        users = new ArrayList<User>();
        User janne = new User(userIdGen.getNewId(), "Janne");
        User jonne = new User(userIdGen.getNewId(), "Jonne");
        User jenni = new User(userIdGen.getNewId(), "Jenni");
        User joonas = new User(userIdGen.getNewId(), "Joonas");

        users.add(janne);
        users.add(jonne);
        users.add(jenni);
        users.add(joonas);

        projects = new ArrayList<Project>();
        Project janne_ohj2 = new Project(projectIdGen.getNewId(), "ohj2",
                janne);
        Project janne_tika = new Project(projectIdGen.getNewId(), "tika",
                janne);
        Project jonne_ohj1 = new Project(projectIdGen.getNewId(), "ohj1",
                jonne);
        Project jonne_alg1 = new Project(projectIdGen.getNewId(), "alg1",
                jonne);
        Project janne_alg1 = new Project(projectIdGen.getNewId(), "alg1",
                janne);
        Project jonne_olio = new Project(projectIdGen.getNewId(), "olio",
                jonne);
        Project jenni_wepa = new Project(projectIdGen.getNewId(), "wepa",
                jenni);
        Project jenni_weka = new Project(projectIdGen.getNewId(), "weka",
                jenni);
        Project jenni_fun1 = new Project(projectIdGen.getNewId(), "fun1",
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
        entries.add(new Entry(entryIdGen.getNewId(), janne_ohj2,
                Entries.parseDateTimeFromString("11.06.2019 21:30:45"),
                Entries.parseDateTimeFromString("11.06.2019 22:45:12")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_ohj2,
                Entries.parseDateTimeFromString("12.06.2019 12:29:00"),
                Entries.parseDateTimeFromString("12.06.2019 21:12:05")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_ohj2,
                Entries.parseDateTimeFromString("14.06.2019 16:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 17:32:49")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_ohj2,
                Entries.parseDateTimeFromString("14.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 23:32:49")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_ohj2,
                Entries.parseDateTimeFromString("15.06.2019 15:30:45"),
                Entries.parseDateTimeFromString("15.06.2019 20:25:15")));

        entries.add(new Entry(entryIdGen.getNewId(), janne_tika,
                Entries.parseDateTimeFromString("13.06.2019 12:29:00"),
                Entries.parseDateTimeFromString("13.06.2019 21:12:05")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_tika,
                Entries.parseDateTimeFromString("15.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("15.06.2019 22:21:49")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_tika,
                Entries.parseDateTimeFromString("16.06.2019 16:14:45"),
                Entries.parseDateTimeFromString("16.06.2019 22:25:15")));

        entries.add(new Entry(entryIdGen.getNewId(), jonne_ohj1,
                Entries.parseDateTimeFromString("11.06.2019 21:22:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45:12")));
        entries.add(new Entry(entryIdGen.getNewId(), jonne_ohj1,
                Entries.parseDateTimeFromString("12.06.2019 11:21:09"),
                Entries.parseDateTimeFromString("12.06.2019 21:12:05")));
        entries.add(new Entry(entryIdGen.getNewId(), jonne_ohj1,
                Entries.parseDateTimeFromString("14.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 23:32:49")));

        entries.add(new Entry(entryIdGen.getNewId(), jonne_alg1,
                Entries.parseDateTimeFromString("11.06.2019 21:30:45"),
                Entries.parseDateTimeFromString("11.06.2019 22:45:12")));
        entries.add(new Entry(entryIdGen.getNewId(), jonne_alg1,
                Entries.parseDateTimeFromString("12.06.2019 12:29:00"),
                Entries.parseDateTimeFromString("12.06.2019 21:12:05")));
        entries.add(new Entry(entryIdGen.getNewId(), jonne_alg1,
                Entries.parseDateTimeFromString("14.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 23:32:49")));
        entries.add(new Entry(entryIdGen.getNewId(), jonne_alg1,
                Entries.parseDateTimeFromString("15.06.2019 15:30:45"),
                Entries.parseDateTimeFromString("15.06.2019 22:25:15")));

        entries.add(new Entry(entryIdGen.getNewId(), janne_alg1,
                Entries.parseDateTimeFromString("11.06.2019 21:22:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45:12")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_alg1,
                Entries.parseDateTimeFromString("12.06.2019 11:21:09"),
                Entries.parseDateTimeFromString("12.06.2019 14:12:05")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_alg1,
                Entries.parseDateTimeFromString("12.06.2019 16:14:45"),
                Entries.parseDateTimeFromString("12.06.2019 22:25:15")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_alg1,
                Entries.parseDateTimeFromString("14.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 23:32:49")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_alg1,
                Entries.parseDateTimeFromString("15.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("15.06.2019 22:21:49")));

        entries.add(new Entry(entryIdGen.getNewId(), jenni_weka,
                Entries.parseDateTimeFromString("11.06.2019 21:22:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45:12")));
        entries.add(new Entry(entryIdGen.getNewId(), jenni_weka,
                Entries.parseDateTimeFromString("12.06.2019 11:21:09"),
                Entries.parseDateTimeFromString("12.06.2019 14:12:05")));
        entries.add(new Entry(entryIdGen.getNewId(), jenni_weka,
                Entries.parseDateTimeFromString("12.06.2019 16:14:45"),
                Entries.parseDateTimeFromString("16.06.2019 22:25:15")));
        entries.add(new Entry(entryIdGen.getNewId(), jenni_weka,
                Entries.parseDateTimeFromString("14.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("14.06.2019 23:32:49")));
        entries.add(new Entry(entryIdGen.getNewId(), jenni_weka,
                Entries.parseDateTimeFromString("15.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("15.06.2019 22:21:49")));

        entries.add(new Entry(entryIdGen.getNewId(), jenni_wepa,
                Entries.parseDateTimeFromString("13.06.2019 12:29:00"),
                Entries.parseDateTimeFromString("13.06.2019 21:12:05")));
        entries.add(new Entry(entryIdGen.getNewId(), jenni_wepa,
                Entries.parseDateTimeFromString("15.06.2019 21:25:52"),
                Entries.parseDateTimeFromString("15.06.2019 22:21:49")));

    }



    /**
     * @return Palauttaa kaikki luodut käyttäjät
     */
    public static ArrayList<User> getUsers() {
        return users;
    }


    /**
     * @return Palauttaa kaikki luodut projektit
     */
    public static ArrayList<Project> getProjects() {
        return projects;
    }

    
    /**
     * @return Palauttaa kaikki luodut merkinnät
     */
    public static ArrayList<Entry> getEntries() {
        return entries;
    }
}
