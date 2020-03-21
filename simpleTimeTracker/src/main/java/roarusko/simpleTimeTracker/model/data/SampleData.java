package roarusko.simpleTimeTracker.model.data;


import roarusko.simpleTimeTracker.model.data.db.ConnectionManager;
import roarusko.simpleTimeTracker.model.data.db.EntryDAO;
import roarusko.simpleTimeTracker.model.data.db.ProjectDAO;
import roarusko.simpleTimeTracker.model.data.db.UserDAO;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.Entries;

/**
 * Luo mallidataa ohjelman kehitystä ja testausta varten
 * @author aleks
 * @version 25 Feb 2020
 *
 */
public class SampleData {


    /**
     * Tallentaa mallidatan tietokantaan
     * @param cm connectionmanager
     */
    public static void createTestDb(ConnectionManager cm) {
        UserDAO ud = new UserDAO(cm);
        User janne = new User("Janne");
        User jonne = new User("Jonne");
        User jenni = new User("Jenni");
        User joonas = new User("Joonas");

        ud.create(janne);
        ud.create(jonne);
        ud.create(jenni);
        ud.create(joonas);
        
        ProjectDAO pd = new ProjectDAO(cm);
        Project janne_ohj2 = new Project(janne.getId(), "ohj2");
        Project janne_tika = new Project(janne.getId(), "tika");
        Project jonne_ohj1 = new Project(jonne.getId(), "ohj1");
        Project jonne_alg1 = new Project(jonne.getId(), "alg1");
        Project janne_alg1 = new Project(janne.getId(), "alg1");
        Project jonne_olio = new Project(jonne.getId(), "olio");
        Project jenni_wepa = new Project(jenni.getId(), "wepa");
        Project jenni_weka = new Project(jenni.getId(), "weka");
        Project jenni_fun1 = new Project(jenni.getId(), "fun1");

        pd.create(janne_ohj2);
        pd.create(janne_tika);
        pd.create(jonne_ohj1);
        pd.create(jonne_alg1);
        pd.create(janne_alg1);
        pd.create(jonne_olio);
        pd.create(jenni_wepa);
        pd.create(jenni_weka);
        pd.create(jenni_fun1);
        
        
        EntryDAO ed = new EntryDAO(cm);
        ed.create(new Entry(janne_ohj2.getId(),
                Entries.parseDateTimeFromString("11.06.2019 21:30"),
                Entries.parseDateTimeFromString("11.06.2019 22:45")));
        ed.create(new Entry(janne_ohj2.getId(),
                Entries.parseDateTimeFromString("12.06.2019 12:29"),
                Entries.parseDateTimeFromString("12.06.2019 21:12")));
        ed.create(new Entry(janne_ohj2.getId(),
                Entries.parseDateTimeFromString("14.06.2019 16:25"),
                Entries.parseDateTimeFromString("14.06.2019 17:32")));
        ed.create(new Entry(janne_ohj2.getId(),
                Entries.parseDateTimeFromString("14.06.2019 21:25"),
                Entries.parseDateTimeFromString("14.06.2019 23:32")));
        ed.create(new Entry(janne_ohj2.getId(),
                Entries.parseDateTimeFromString("15.06.2019 15:30"),
                Entries.parseDateTimeFromString("15.06.2019 20:25")));

        ed.create(new Entry(janne_tika.getId(),
                Entries.parseDateTimeFromString("13.06.2019 12:29"),
                Entries.parseDateTimeFromString("13.06.2019 21:12")));
        ed.create(new Entry(janne_tika.getId(),
                Entries.parseDateTimeFromString("15.06.2019 21:25"),
                Entries.parseDateTimeFromString("15.06.2019 22:21")));
        ed.create(new Entry(janne_tika.getId(),
                Entries.parseDateTimeFromString("16.06.2019 16:14"),
                Entries.parseDateTimeFromString("16.06.2019 22:25")));

        ed.create(new Entry(jonne_ohj1.getId(),
                Entries.parseDateTimeFromString("11.06.2019 21:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45")));
        ed.create(new Entry(jonne_ohj1.getId(),
                Entries.parseDateTimeFromString("12.06.2019 11:21"),
                Entries.parseDateTimeFromString("12.06.2019 21:12")));
        ed.create(new Entry(jonne_ohj1.getId(),
                Entries.parseDateTimeFromString("14.06.2019 21:25"),
                Entries.parseDateTimeFromString("14.06.2019 23:32")));

        ed.create(new Entry(jonne_alg1.getId(),
                Entries.parseDateTimeFromString("11.06.2019 21:30"),
                Entries.parseDateTimeFromString("11.06.2019 22:45")));
        ed.create(new Entry(jonne_alg1.getId(),
                Entries.parseDateTimeFromString("12.06.2019 12:29"),
                Entries.parseDateTimeFromString("12.06.2019 21:12")));
        ed.create(new Entry(jonne_alg1.getId(),
                Entries.parseDateTimeFromString("14.06.2019 21:25"),
                Entries.parseDateTimeFromString("14.06.2019 23:32")));
        ed.create(new Entry(jonne_alg1.getId(),
                Entries.parseDateTimeFromString("15.06.2019 15:30"),
                Entries.parseDateTimeFromString("15.06.2019 22:25")));

        ed.create(new Entry(janne_alg1.getId(),
                Entries.parseDateTimeFromString("11.06.2019 21:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45")));
        ed.create(new Entry(janne_alg1.getId(),
                Entries.parseDateTimeFromString("12.06.2019 11:21"),
                Entries.parseDateTimeFromString("12.06.2019 14:12")));
        ed.create(new Entry(janne_alg1.getId(),
                Entries.parseDateTimeFromString("12.06.2019 16:14"),
                Entries.parseDateTimeFromString("12.06.2019 22:25")));
        ed.create(new Entry(janne_alg1.getId(),
                Entries.parseDateTimeFromString("14.06.2019 21:25"),
                Entries.parseDateTimeFromString("14.06.2019 23:32")));
        ed.create(new Entry(janne_alg1.getId(),
                Entries.parseDateTimeFromString("15.06.2019 21:25"),
                Entries.parseDateTimeFromString("15.06.2019 22:21")));

        ed.create(new Entry(jenni_weka.getId(),
                Entries.parseDateTimeFromString("11.06.2019 21:22"),
                Entries.parseDateTimeFromString("11.06.2019 22:45")));
        ed.create(new Entry(jenni_weka.getId(),
                Entries.parseDateTimeFromString("12.06.2019 11:21"),
                Entries.parseDateTimeFromString("12.06.2019 14:12")));
        ed.create(new Entry(jenni_weka.getId(),
                Entries.parseDateTimeFromString("12.06.2019 16:14"),
                Entries.parseDateTimeFromString("16.06.2019 22:25")));
        ed.create(new Entry(jenni_weka.getId(),
                Entries.parseDateTimeFromString("14.06.2019 21:25"),
                Entries.parseDateTimeFromString("14.06.2019 23:32")));
        ed.create(new Entry(jenni_weka.getId(),
                Entries.parseDateTimeFromString("15.06.2019 21:25"),
                Entries.parseDateTimeFromString("15.06.2019 22:21")));

        ed.create(new Entry(jenni_wepa.getId(),
                Entries.parseDateTimeFromString("13.06.2019 12:29"),
                Entries.parseDateTimeFromString("13.06.2019 21:12")));
        ed.create(new Entry(jenni_wepa.getId(),
                Entries.parseDateTimeFromString("15.06.2019 21:25"),
                Entries.parseDateTimeFromString("15.06.2019 22:21")));
        
        System.out.println("Sample database created");
    }
}
