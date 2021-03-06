package roarusko.simpleTimeTracker.model.data.mock;

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

    private static final IdGenerator userIdGen = new IdGenerator(1);
    private static final IdGenerator projectIdGen = new IdGenerator(1);
    private static final IdGenerator entryIdGen = new IdGenerator(1);

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
        Project janne_ohj2 = new Project(projectIdGen.getNewId(), janne.getId(), "ohj2");
        Project janne_tika = new Project(projectIdGen.getNewId(), janne.getId(), "tika");
        Project jonne_ohj1 = new Project(projectIdGen.getNewId(), jonne.getId(), "ohj1");
        Project jonne_alg1 = new Project(projectIdGen.getNewId(), jonne.getId(), "alg1");
        Project janne_alg1 = new Project(projectIdGen.getNewId(), janne.getId(), "alg1");
        Project jonne_olio = new Project(projectIdGen.getNewId(), jonne.getId(), "olio");
        Project jenni_wepa = new Project(projectIdGen.getNewId(), jenni.getId(), "wepa");
        Project jenni_weka = new Project(projectIdGen.getNewId(), jenni.getId(), "weka");
        Project jenni_fun1 = new Project(projectIdGen.getNewId(), jenni.getId(), "fun1");

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
        entries.add(new Entry(entryIdGen.getNewId(), janne_ohj2.getId(),
                Entries.parseDateTimeFromString("11.06.2019 21:30"),
                Entries.parseDateTimeFromString("11.06.2019 22:45")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_ohj2.getId(),
                Entries.parseDateTimeFromString("12.06.2019 12:29"),
                Entries.parseDateTimeFromString("12.06.2019 21:12")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_ohj2.getId(),
                Entries.parseDateTimeFromString("14.06.2019 16:25"),
                Entries.parseDateTimeFromString("14.06.2019 17:32")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_ohj2.getId(),
                Entries.parseDateTimeFromString("14.06.2019 21:25"),
                Entries.parseDateTimeFromString("14.06.2019 23:32")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_ohj2.getId(),
                Entries.parseDateTimeFromString("15.06.2019 15:30"),
                Entries.parseDateTimeFromString("15.06.2019 20:25")));

        entries.add(new Entry(entryIdGen.getNewId(), janne_tika.getId(),
                Entries.parseDateTimeFromString("13.06.2019 12:29"),
                Entries.parseDateTimeFromString("13.06.2019 21:12")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_tika.getId(),
                Entries.parseDateTimeFromString("15.06.2019 21:25"),
                Entries.parseDateTimeFromString("15.06.2019 22:21")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_tika.getId(),
                Entries.parseDateTimeFromString("16.06.2019 16:14"),
                Entries.parseDateTimeFromString("16.06.2019 22:25")));

        entries.add(new Entry(entryIdGen.getNewId(), jonne_ohj1.getId(),
                Entries.parseDateTimeFromString("11.06.2019 21:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45")));
        entries.add(new Entry(entryIdGen.getNewId(), jonne_ohj1.getId(),
                Entries.parseDateTimeFromString("12.06.2019 11:21"),
                Entries.parseDateTimeFromString("12.06.2019 21:12")));
        entries.add(new Entry(entryIdGen.getNewId(), jonne_ohj1.getId(),
                Entries.parseDateTimeFromString("14.06.2019 21:25"),
                Entries.parseDateTimeFromString("14.06.2019 23:32")));

        entries.add(new Entry(entryIdGen.getNewId(), jonne_alg1.getId(),
                Entries.parseDateTimeFromString("11.06.2019 21:30"),
                Entries.parseDateTimeFromString("11.06.2019 22:45")));
        entries.add(new Entry(entryIdGen.getNewId(), jonne_alg1.getId(),
                Entries.parseDateTimeFromString("12.06.2019 12:29"),
                Entries.parseDateTimeFromString("12.06.2019 21:12")));
        entries.add(new Entry(entryIdGen.getNewId(), jonne_alg1.getId(),
                Entries.parseDateTimeFromString("14.06.2019 21:25"),
                Entries.parseDateTimeFromString("14.06.2019 23:32")));
        entries.add(new Entry(entryIdGen.getNewId(), jonne_alg1.getId(),
                Entries.parseDateTimeFromString("15.06.2019 15:30"),
                Entries.parseDateTimeFromString("15.06.2019 22:25")));

        entries.add(new Entry(entryIdGen.getNewId(), janne_alg1.getId(),
                Entries.parseDateTimeFromString("11.06.2019 21:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_alg1.getId(),
                Entries.parseDateTimeFromString("12.06.2019 11:21"),
                Entries.parseDateTimeFromString("12.06.2019 14:12")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_alg1.getId(),
                Entries.parseDateTimeFromString("12.06.2019 16:14"),
                Entries.parseDateTimeFromString("12.06.2019 22:25")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_alg1.getId(),
                Entries.parseDateTimeFromString("14.06.2019 21:25"),
                Entries.parseDateTimeFromString("14.06.2019 23:32")));
        entries.add(new Entry(entryIdGen.getNewId(), janne_alg1.getId(),
                Entries.parseDateTimeFromString("15.06.2019 21:25"),
                Entries.parseDateTimeFromString("15.06.2019 22:21")));

        entries.add(new Entry(entryIdGen.getNewId(), jenni_weka.getId(),
                Entries.parseDateTimeFromString("11.06.2019 21:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45")));
        entries.add(new Entry(entryIdGen.getNewId(), jenni_weka.getId(),
                Entries.parseDateTimeFromString("12.06.2019 11:21"),
                Entries.parseDateTimeFromString("12.06.2019 14:12")));
        entries.add(new Entry(entryIdGen.getNewId(), jenni_weka.getId(),
                Entries.parseDateTimeFromString("12.06.2019 16:14"),
                Entries.parseDateTimeFromString("16.06.2019 22:25")));
        entries.add(new Entry(entryIdGen.getNewId(), jenni_weka.getId(),
                Entries.parseDateTimeFromString("14.06.2019 21:25"),
                Entries.parseDateTimeFromString("14.06.2019 23:32")));
        entries.add(new Entry(entryIdGen.getNewId(), jenni_weka.getId(),
                Entries.parseDateTimeFromString("15.06.2019 21:25"),
                Entries.parseDateTimeFromString("15.06.2019 22:21")));

        entries.add(new Entry(entryIdGen.getNewId(), jenni_wepa.getId(),
                Entries.parseDateTimeFromString("13.06.2019 12:29"),
                Entries.parseDateTimeFromString("13.06.2019 21:12")));
        entries.add(new Entry(entryIdGen.getNewId(), jenni_wepa.getId(),
                Entries.parseDateTimeFromString("15.06.2019 21:25"),
                Entries.parseDateTimeFromString("15.06.2019 22:21")));

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
