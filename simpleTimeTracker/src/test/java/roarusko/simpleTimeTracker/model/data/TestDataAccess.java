package roarusko.simpleTimeTracker.model.data;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import roarusko.simpleTimeTracker.model.data.mock.MockEntryDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockProjectDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockUserDAO;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

public class TestDataAccess {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Project> projects = new ArrayList<>();
    private ArrayList<Entry> entries = new ArrayList<>();

    private IdGenerator userIdGen;
    private IdGenerator projectIdGen;
    private IdGenerator entryIdGen;

    private UserDAO uDAO;
    private ProjectDAO pDAO;
    private EntryDAO eDAO;

    private DataAccess da;

    @BeforeEach
    public void createTestSetup() {
        users.clear();
        projects.clear();
        entries.clear();

        // TODO Nämä mockitolla kunhan osataan käyttää...

        userIdGen = new IdGenerator(1);
        projectIdGen = new IdGenerator(1);
        entryIdGen = new IdGenerator(1);

        uDAO = new MockUserDAO(users, userIdGen);
        pDAO = new MockProjectDAO(projects, projectIdGen);
        eDAO = new MockEntryDAO(entries, entryIdGen);

        da = new DataAccess(uDAO, pDAO, eDAO);
    }


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


    @Test
    public void testNewEntry() {

        Project project1 = new Project(101, 1, "Project 1");
        Entry entry1 = da.newEntry(project1);
        
        assertFalse(entries.contains(entry1));
        assertEquals(-1, entry1.getId());
        assertEquals(101, entry1.getOwnerId());
        assertEquals(entry1.getStartDateTime(), entry1.getEndDateTime());
        assertTrue(entry1.getStartDateTime().until(LocalDateTime.now(), ChronoUnit.MINUTES) <= 1L);
        
        LocalDateTime time1 = LocalDateTime.of(2000, 12, 30, 9, 30);
        LocalDateTime time2 = LocalDateTime.of(2000, 12, 30, 16, 15);
        Project project2 = new Project(102, 1, "Project 2");
        Entry entry2 = da.newEntry(project2, time1, time2);
        
        assertEquals(-1, entry2.getId());
        assertEquals(102, entry2.getOwnerId());
        assertEquals(time1, entry2.getStartDateTime());
        assertEquals(time2, entry2.getEndDateTime());
    }
    
    @Test
    public void testCommitNewEntry() {
        LocalDateTime time1 = LocalDateTime.of(2000, 12, 30, 9, 30);
        LocalDateTime time2 = LocalDateTime.of(2000, 12, 30, 16, 15);
        
        Entry entry1 = new Entry(-1, 101, time1, time2);
        
        Entry committed1 = da.commitEntry(entry1);
        
        // lisätty listalle?
        assertTrue(entries.contains(committed1));
        
        // uusi id?
        assertSame(entry1, committed1);
        assertNotEquals(-1, committed1.getId());
        
        // projektin id ja aikaleimat samat?
        assertEquals(101, committed1.getOwnerId());
        assertEquals(entry1.getStartDateTime(), committed1.getStartDateTime());
        assertEquals(entry1.getEndDateTime(), committed1.getEndDateTime());
        
        
        LocalDateTime time3 = LocalDateTime.of(2000, 12, 30, 17, 30);
        LocalDateTime time4 = LocalDateTime.of(2000, 12, 30, 19, 15);

        Entry entry2 = new Entry(-1, 101, time3, time4);

        Entry committed2 = da.commitEntry(entry2);
        
        // lisätty listalle?
        assertTrue(entries.contains(committed2));
        
        // uusi id?
        assertSame(entry2, committed2);
        assertNotEquals(-1, committed2.getId());
        
        // projektin id ja aikaleimat samat?
        assertEquals(101, committed2.getOwnerId());
        assertEquals(entry2.getStartDateTime(), committed2.getStartDateTime());
        assertEquals(entry2.getEndDateTime(), committed2.getEndDateTime());
    }
    
    
    @Test
    public void testCommitExistingEntry() {
        LocalDateTime time1 = LocalDateTime.of(2000, 12, 30, 9, 30);
        LocalDateTime time2 = LocalDateTime.of(2000, 12, 30, 16, 15);
        
        Entry entry1 = new Entry(-1, 101, time1, time2);
        
        Entry committed1 = da.commitEntry(entry1);
        
        // lisätty listalle?
        assertTrue(entries.contains(committed1));
        
        // uusi id?
        assertSame(entry1, committed1);
        assertNotEquals(-1, committed1.getId());
        
        // projektin id ja aikaleimat samat?
        assertEquals(101, committed1.getOwnerId());
        assertEquals(entry1.getStartDateTime(), committed1.getStartDateTime());
        assertEquals(entry1.getEndDateTime(), committed1.getEndDateTime());
        
        
        LocalDateTime time3 = LocalDateTime.of(2000, 12, 30, 17, 30);
        LocalDateTime time4 = LocalDateTime.of(2000, 12, 30, 19, 15);
        
        Entry entry2 = new Entry(-1, 101, time3, time4);
        
        Entry committed2 = da.commitEntry(entry2);
        
        // lisätty listalle?
        assertTrue(entries.contains(committed2));
        
        // uusi id?
        assertSame(entry2, committed2);
        assertNotEquals(-1, committed2.getId());
        
        // projektin id ja aikaleimat samat?
        assertEquals(101, committed2.getOwnerId());
        assertEquals(entry2.getStartDateTime(), committed2.getStartDateTime());
        assertEquals(entry2.getEndDateTime(), committed2.getEndDateTime());
    }
    //
}
