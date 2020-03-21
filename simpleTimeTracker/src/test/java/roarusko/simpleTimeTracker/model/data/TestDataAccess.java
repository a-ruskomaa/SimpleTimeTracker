package roarusko.simpleTimeTracker.model.data;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import roarusko.simpleTimeTracker.model.data.mock.MockEntryDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockProjectDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockUserDAO;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

/**
 * Testataan DataAcces-luokan lisäys, poisto, muokkaus ja lukuoperaatioita.
 * 
 * Testeissä hyödynnetään mock-olioita jotka tarjoavat samat toiminnallisuudet
 * kuin oikeat DAO-oliot, mutta tallentavat käsittelemänsä datan ainoastaan listalle.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class TestDataAccess {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();
    private ArrayList<Entry> entries = new ArrayList<>();

    private IdGenerator userIdGen;
    private IdGenerator projectIdGen;
    private IdGenerator entryIdGen;

    private DAO<Integer, User>uDAO;
    private ChildDAO<Integer, Project, User> pDAO;
    private ChildDAO<Integer, Entry, Project> eDAO;

    private DataAccess da;

    /**
     * Alustaa testauksessa käytettävät DAO:t
     */
    @BeforeEach
    public void createTestSetup() {
        users.clear();
        projects.clear();
        entries.clear();

        // TODO Nämä mockitolla kunhan osataan käyttää...

        // TODO nyt monien testien toiminta on riippuvainen myös IdGeneratorin sisäisestä toteutuksesta
        userIdGen = new IdGenerator(1);
        projectIdGen = new IdGenerator(1);
        entryIdGen = new IdGenerator(1);

        uDAO = new MockUserDAO(users, userIdGen);
        pDAO = new MockProjectDAO(projects, projectIdGen);
        eDAO = new MockEntryDAO(entries, entryIdGen);

        da = new DataAccessImpl(uDAO, pDAO, eDAO);
    }


    /**
     * Testataan uuden käyttäjän luomista:
     * Pyydetään DataAccessia luomaan uusi käyttäjä. Tarkistetaan
     * että listalle päätynyt käyttäjä on saanut yksilöivän id:n ja vastaa
     * nimeltää tallennettavaksi määriteltyä käyttäjää
     */
    @Test
    public void testAddUser() {

        User user1 = da.addUser("User 1");
        assertTrue(users.contains(user1));
        assertEquals("User 1", user1.getName());
        assertEquals(1, user1.getId());

        User user2 = da.addUser("User 2");
        assertTrue(users.contains(user1));
        assertEquals("User 2", user2.getName());
        assertEquals(2, user2.getId());

        /*
         * TODO Tyhjien merkkijonojen syöttäminen nimeksi on rajattu
         * kontrollerissa, mutta voisihan tuolle luoda poikkeuksen dataAccessiin
         * ja testata sitäkin..?
         */
    }


    
    /**
     * Testataan uuden projektin luomista:
     * Luodaan mallikäyttäjä, ja pyydetään DataAccessia luomaan käyttäjälle
     * uusi projekti. Tarkistetaan että listalle päätynyt projekti on saanut
     * yksilöivän id:n ja vastaa nimeltään sekä omistajan id:ltään tallennettavaksi
     * määriteltyä projektia
     */
    @Test
    public void testAddProject() {
        User user1 = new User(100, "User 1");
        User user2 = new User(101, "User 2");

        Project project1_a = da.addProject("Project 1", user1);
        assertTrue(projects.contains(project1_a));
        assertEquals("Project 1", project1_a.getName());
        assertEquals(1, project1_a.getId());
        assertEquals(100, project1_a.getOwnerId());

        Project project1_b = da.addProject("Project 1", user2);
        assertTrue(projects.contains(project1_b));
        assertEquals("Project 1", project1_b.getName(), "Wrong name!");
        assertEquals(2, project1_b.getId());
        assertEquals(101, project1_b.getOwnerId());

        Project project2 = da.addProject("Project 2", user1);
        assertTrue(projects.contains(project2));
        assertEquals("Project 2", project2.getName(), "Wrong name!");
        assertEquals(3, project2.getId());
        assertEquals(100, project2.getOwnerId());

        Project project3 = da.addProject("Project 3", user2);
        assertTrue(projects.contains(project3));
        assertEquals("Project 3", project3.getName());
        assertEquals(4, project3.getId());
        assertEquals(101, project3.getOwnerId());
    }


    /**
     * Testataan uusien merkintöjen luomista:
     * Luodaan uusi projekti, ja pyydetään DataAccessia luomaan projektille
     * uusi merkintä. Tarkistetaan että merkintä luodaan väliaikaisella id:llä (-1)
     * ja että luodun merkinnän tiedot vastaavat annettuja tietoja.
     */
    @Test
    public void testNewEntry() {

        Project project1 = new Project(101, 1, "Project 1");
        
        // Uusi merkintä ilman annettua alku- ja loppuaikaa saa arvokseen nykyhetken
        Entry entry1 = da.newEntry(project1);
        
        assertTrue(entries.isEmpty());
        assertEquals(-1, entry1.getId());
        assertEquals(101, entry1.getOwnerId());
        assertTrue(entry1.getStartDateTime().isEqual(entry1.getEndDateTime()));
        
        // Varmistetaan että merkinnän alku- ja loppuaika vastaa nykyhetkeä. Huomioidaan mahdollisuus
        // kellonajan vaihtumiseen testin aikana
        assertTrue(entry1.getStartDateTime().until(LocalDateTime.now(), ChronoUnit.MINUTES) <= 1L);
        
        LocalDateTime time1 = LocalDateTime.of(2000, 12, 30, 9, 30);
        LocalDateTime time2 = LocalDateTime.of(2000, 12, 30, 16, 15);
        Project project2 = new Project(102, 1, "Project 2");
        Entry entry2 = da.newEntry(project2, time1, time2);

        assertTrue(entries.isEmpty());
        assertEquals(-1, entry2.getId());
        assertEquals(102, entry2.getOwnerId());
        assertTrue(time1.isEqual(entry2.getStartDateTime()));
        assertTrue(time2.isEqual(entry2.getEndDateTime()));
    }
    
    /**
     * Testataan uuden merkinnän tallentamista:
     * Pyydetään DataAccessia tallentamaan annettu merkintä. Varmistetaan että
     * listalle päätynyt merkintä on saanut yksilöivän id:n eikä sen sisältämä
     * data ole muuttunut.
     */
    @Test
    public void testCommitNewEntry() {
        LocalDateTime starttime1 = LocalDateTime.of(2000, 12, 30, 9, 30);
        LocalDateTime endtime1 = LocalDateTime.of(2000, 12, 30, 16, 15);
        
        Entry entry1 = new Entry(-1, 101, starttime1, endtime1);
        
        Entry committed1 = da.commitEntry(entry1);
        
        // lisätty listalle?
        assertEquals(entries.get(0).getId(), committed1.getId());
        assertEquals(1, entries.size());
        
        // uusi id?
        assertNotEquals(-1, committed1.getId());
        
        // projektin id ja aikaleimat samat?
        assertEquals(entries.get(0).getOwnerId(), committed1.getOwnerId());
        assertEquals(101, committed1.getOwnerId());
        
        // vastaako palautetun merkinnän aika alkuperäistä?
        assertTrue(entry1.getStartDateTime().isEqual(committed1.getStartDateTime()));
        assertTrue(entry1.getEndDateTime().isEqual(committed1.getEndDateTime()));
        
        // vastaako listalle lisätun merkinnän aika palautettua?
        assertTrue(committed1.getStartDateTime().isEqual(entries.get(0).getStartDateTime()));
        assertTrue(committed1.getEndDateTime().isEqual(entries.get(0).getEndDateTime()));
        
        // vastaako alkuperäinen aika listalle lisättyä?
        assertTrue(entries.get(0).getStartDateTime().isEqual(starttime1));
        assertTrue(entries.get(0).getEndDateTime().isEqual(endtime1));
        
        
        LocalDateTime starttime2 = LocalDateTime.of(2000, 12, 30, 17, 30);
        LocalDateTime endtime2 = LocalDateTime.of(2000, 12, 30, 19, 15);

        Entry entry2 = new Entry(-1, 102, starttime2, endtime2);

        Entry committed2 = da.commitEntry(entry2);
        
        // lisätty listalle?
        assertEquals(entries.get(1).getId(), committed2.getId());
        assertEquals(2, entries.size());
        
        // uusi id?
        assertNotEquals(-1, committed2.getId());
        
        // projektin id ja aikaleimat samat?
        assertEquals(entries.get(1).getOwnerId(), committed2.getOwnerId());
        assertEquals(102, committed2.getOwnerId());
        
        // vastaako palautetun merkinnän aika alkuperäistä?
        assertTrue(entry2.getStartDateTime().isEqual(committed2.getStartDateTime()));
        assertTrue(entry2.getEndDateTime().isEqual(committed2.getEndDateTime()));
        
        // vastaako listalle lisätun merkinnän aika palautettua?
        assertTrue(committed2.getStartDateTime().isEqual(entries.get(1).getStartDateTime()));
        assertTrue(committed2.getEndDateTime().isEqual(entries.get(1).getEndDateTime()));
        
        // vastaako alkuperäinen aika listalle lisättyä?
        assertTrue(entries.get(1).getStartDateTime().isEqual(starttime2));
        assertTrue(entries.get(1).getEndDateTime().isEqual(endtime2));
    }
    
    
    /**
     * Testataan olemassa olevan merkinnän muokkaamista:
     * Luodaan uusia merkintöjä, jotka lisätään suoraan DAO-olioiden käsittelemälle
     * listalle. Muutetaan merkintöjen aikoja, ja tallennetaan ne DataAccess:n avulla.
     * 
     * Verrataan listalla olevia merkintöjä sekä DataAccessin palauttamia merkintöjä.
     * Varmistetaan että merkinnät vastaavat toisiaan kaiken muun, paitsi aikaleimojen
     * osalta.
     */
    @Test
    public void testCommitExistingEntry() {
        LocalDateTime starttime1 = LocalDateTime.of(2000, 12, 30, 9, 30);
        LocalDateTime endtime1 = LocalDateTime.of(2000, 12, 30, 16, 15);
        
        Entry orig_entry1 = new Entry(1, 101, starttime1, endtime1);
        entries.add(orig_entry1);

        LocalDateTime mod_starttime1 = LocalDateTime.of(2000, 12, 31, 11, 35);
        LocalDateTime mod_endtime1 = LocalDateTime.of(2000, 12, 31, 17, 20);
        Entry mod_entry1 = new Entry(1, 101, mod_starttime1, mod_endtime1);
        Entry committed1 = da.commitEntry(mod_entry1);
        
        // edelleen ainoa merkintä listalla?
        assertEquals(1, entries.size());
        
        // samat idt?
        assertEquals(1, committed1.getId());
        assertEquals(entries.get(0).getId(), committed1.getId());
        assertEquals(101, committed1.getOwnerId());
        assertEquals(entries.get(0).getOwnerId(), committed1.getOwnerId());
        
        // eri aikaleimat?
        assertFalse(orig_entry1.getStartDateTime().isEqual(committed1.getStartDateTime()));
        assertFalse(orig_entry1.getEndDateTime().isEqual(committed1.getEndDateTime()));
        
        // aikaleima vastaa muokkausta
        assertTrue(committed1.getStartDateTime().isEqual(mod_starttime1));
        assertTrue(committed1.getEndDateTime().isEqual(mod_endtime1));
        
        
        LocalDateTime starttime2 = LocalDateTime.of(2000, 12, 30, 17, 30);
        LocalDateTime endtime2 = LocalDateTime.of(2000, 12, 30, 19, 15);
        
        Entry orig_entry2 = new Entry(2, 102, starttime2, endtime2);
        entries.add(orig_entry2);

        LocalDateTime mod_starttime2 = LocalDateTime.of(2000, 12, 30, 11, 35);
        LocalDateTime mod_endtime2 = LocalDateTime.of(2000, 12, 30, 17, 20);
        Entry mod_entry2 = new Entry(2, 102, mod_starttime2, mod_endtime2);
        Entry committed2 = da.commitEntry(mod_entry2);
        
        // edelleen kaksi merkintää listalla?
        assertEquals(2, entries.size());
        
        // samat idt?
        assertEquals(2, committed2.getId());
        assertEquals(entries.get(1).getId(), committed2.getId());
        assertEquals(102, committed2.getOwnerId());
        assertEquals(entries.get(1).getOwnerId(), committed2.getOwnerId());
        
        // eri aikaleimat?
        assertFalse(orig_entry2.getStartDateTime().isEqual(committed2.getStartDateTime()));
        assertFalse(orig_entry2.getEndDateTime().isEqual(committed2.getEndDateTime()));
        
        // aikaleima vastaa muokkausta
        assertTrue(committed2.getStartDateTime().isEqual(mod_starttime2));
        assertTrue(committed2.getEndDateTime().isEqual(mod_endtime2));
    }
    
    
    /**
     * Testataan merkintöjen poistamista:
     * 
     * Listätään suoraan DAO:n käsittelemälle listalle merkintöjä.
     * Poistetaan merkintöjä DataAccess:n kautta. Varmistetaan
     * että vain haluttu merkintä poistuu, eikä poistaminen vaikuta
     * jäljelle jääneiden merkintöjen sisältöön.
     */
    @Test
    public void testDeleteEntry() {
        LocalDateTime starttime1 = LocalDateTime.of(2000, 12, 30, 9, 30);
        LocalDateTime endtime1 = LocalDateTime.of(2000, 12, 30, 16, 15);
        
        Entry orig_entry1 = new Entry(1, 101, starttime1, endtime1);
        entries.add(orig_entry1);
        
        LocalDateTime starttime2 = LocalDateTime.of(2000, 12, 31, 17, 30);
        LocalDateTime endtime2 = LocalDateTime.of(2000, 12, 31, 19, 15);
        
        Entry orig_entry2 = new Entry(2, 102, starttime2, endtime2);
        entries.add(orig_entry2);
        
        LocalDateTime starttime3 = LocalDateTime.of(2000, 11, 30, 11, 12);
        LocalDateTime endtime3 = LocalDateTime.of(2000, 11, 30, 16, 13);
        
        Entry orig_entry3 = new Entry(3, 102, starttime3, endtime3);
        entries.add(orig_entry3);
        
        assertEquals(3, entries.size());
        assertEquals(1, entries.get(0).getId());
        assertEquals(2, entries.get(1).getId());
        assertEquals(3, entries.get(2).getId());
        
        boolean removed = da.deleteEntry(orig_entry2);
        
        // onnistuiko poisto?
        assertTrue(removed);

        // listalle jääneet merkinnät ovat oikeat?
        assertEquals(2, entries.size());
        assertEquals(1, entries.get(0).getId());
        assertEquals(3, entries.get(1).getId());
        
        // listalle jääneiden merkintöjen tiedot eivät muuttuneet?
        assertTrue(entries.get(0).getStartDateTime().isEqual(starttime1));
        assertTrue(entries.get(0).getEndDateTime().isEqual(endtime1));
        
        assertTrue(entries.get(1).getStartDateTime().isEqual(starttime3));
        assertTrue(entries.get(1).getEndDateTime().isEqual(endtime3));
        
        // koitetaan poistaa merkintää joka ei ole listalla
        removed = da.deleteEntry(orig_entry2);
        assertFalse(removed);
        

        removed = da.deleteEntry(orig_entry1);
        
        // onnistuiko poisto?
        assertTrue(removed);
        
        // listalle jääneet merkinnät ovat oikeat?
        assertEquals(1, entries.size());
        assertEquals(3, entries.get(0).getId());
        
        // listalle jääneiden merkintöjen tiedot eivät muuttuneet?
        assertTrue(entries.get(0).getStartDateTime().isEqual(starttime3));
        assertTrue(entries.get(0).getEndDateTime().isEqual(endtime3));
        
        removed = da.deleteEntry(orig_entry3);
        
        // onnistuiko poisto?
        assertTrue(removed);
        
        // lista on tyhjä?
        assertTrue(entries.isEmpty());
    }
    
    
    /**
     * Testataan kaikkien käyttäjien lataamista:
     * 
     * Tallennetaan suoraan DAO:n käsittelemälle listalle käyttäjiä.
     * Ladataan käyttäjälistaus DataAccess:n kautta ja verrataan listoja
     * keskenään.
     * 
     * Tässä oikaistaan hieman ja verrataan listojen sisältämiä olioita equals()-metodia
     * hyödyntäen, sillä MockDAO palauttaa tietokantaa esittävän listansa sisällön
     * sellaisenaan. Varmempaa olisi verrata listan sisältöä kenttien tasolla.
     * 
     * Koska tässä testataan, että DataAccess ei muuta välittämänsä listan sisältöä
     * testausmenetelmän voi todeta olevan riittävä.
     */
    @Test
    public void testLoadUsers() {
        User user1 = new User(101, "User 1");
        User user2 = new User(102, "User 2");
        User user3 = new User(103, "User 3");
        User user4 = new User(104, "User 4");
        
        users.addAll(Arrays.asList(new User[] {user1, user2, user3, user4}));
        
        List<User> loadedUsers = da.loadUsers();
        assertTrue(loadedUsers.containsAll(users));
        assertEquals(users.size(), loadedUsers.size());
    }
    
    
    /**
     * Testataan kaikkien projektien lataamista:
     * 
     * Tallennetaan suoraan DAO:n käsittelemälle listalle kahdelle käyttäjälle
     * kuuluvia projekteja.
     * 
     * Ladataan projektilistaus DataAccess:n kautta ja verrataan listoja
     * keskenään.
     * 
     * Tässä oikaistaan hieman ja verrataan listojen sisältämiä olioita equals()-metodia
     * hyödyntäen, sillä MockDAO palauttaa tietokantaa esittävän listansa sisällön
     * sellaisenaan. Varmempaa olisi verrata listan sisältöä kenttien tasolla.
     * 
     * Koska tässä testataan, että DataAccess ei muuta välittämänsä listan sisältöä
     * testausmenetelmän voi todeta olevan riittävä.
     */
    @Test
    public void testLoadProjects() {
        User user1 = new User(1, "User 1");
        User user2 = new User(2, "User 2");
        
        Project u1_Project1 = new Project(101,1,"U1_Project1");
        Project u1_Project2 = new Project(102,1,"U1_Project2");
        Project u2_Project1 = new Project(103,2,"U2_Project1");
        Project u1_Project3 = new Project(104,1,"U1_Project3");
        
        projects.addAll(Arrays.asList(new Project[] {u1_Project1, u1_Project2, u2_Project1, u1_Project3}));
        
        List<Project> user1Projects = Arrays.asList(new Project[] {u1_Project1, u1_Project2, u1_Project3});
        
        List<Project> loadedProjects = da.loadProjects(user1);
        
        assertTrue(loadedProjects.containsAll(user1Projects));
        assertEquals(3, loadedProjects.size());
        
        loadedProjects = da.loadProjects(user2);
        
        assertTrue(loadedProjects.contains(u2_Project1));
        assertEquals(1, loadedProjects.size());
    }
    
    
    /**
     * Testataan kaikkien merkintöjen lataamista:
     * 
     * Tallennetaan suoraan DAO:n käsittelemälle listalle merkintöjä.
     * Ladataan merkintälistaus DataAccess:n kautta ja verrataan listoja
     * keskenään.
     * 
     * Tässä oikaistaan hieman ja verrataan listojen sisältämiä olioita equals()-metodia
     * hyödyntäen, sillä MockDAO palauttaa tietokantaa esittävän listansa sisällön
     * sellaisenaan. Varmempaa olisi verrata listan sisältöä kenttien tasolla.
     * 
     * Tässä kuitenkin testataan, että DataAccess ei muuta välittämänsä listan sisältöä
     * joten testausmenetelmän katsotaan olevan riittävä.
     */
    @Test
    public void testLoadEntries() {
        Project project1 = new Project(101,1,"Project1");
        Project project2 = new Project(102,1,"Project2");
        
        Entry p1_entry1 = new Entry(1, 101, LocalDateTime.of(2000, 11, 30, 9, 30), LocalDateTime.of(2000, 11, 30, 16, 15));
        
        Entry p1_entry2 = new Entry(2, 101, LocalDateTime.of(2000, 11, 30, 17, 30), LocalDateTime.of(2000, 11, 30, 19, 15));
        
        Entry p2_entry1 = new Entry(3, 102, LocalDateTime.of(2000, 12, 01, 11, 35), LocalDateTime.of(2000, 12, 01, 17, 20));
 
        Entry p1_entry3 = new Entry(4, 101, LocalDateTime.of(2000, 12, 01, 11, 12), LocalDateTime.of(2000, 12, 01, 16, 13)); 
        
        Entry p2_entry2 = new Entry(5, 102, LocalDateTime.of(2000, 12, 30, 11, 35), LocalDateTime.of(2000, 12, 30, 17, 20));
        
        List<Entry> project1_entries = Arrays.asList(new Entry[] {p1_entry1, p1_entry2, p1_entry3});
        List<Entry> project2_entries = Arrays.asList(new Entry[] {p2_entry1, p2_entry2});
        
        entries.addAll(Arrays.asList(new Entry[] {p1_entry1, p1_entry2, p2_entry1, p1_entry3, p2_entry2}));
        
        List<Entry> loadedEntries = da.loadEntries(project1);
        assertTrue(loadedEntries.containsAll(project1_entries));
        assertEquals(3, loadedEntries.size());
        
        loadedEntries = da.loadEntries(project2);
        assertTrue(loadedEntries.containsAll(project2_entries));
        assertEquals(2, loadedEntries.size());
    }
}
