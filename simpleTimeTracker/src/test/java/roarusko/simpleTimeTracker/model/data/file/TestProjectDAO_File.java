package roarusko.simpleTimeTracker.model.data.file;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;


/**
 * ProjectDAO_File:n testit.
 * 
 * Testit on kirjoitettu yksinkertaisuuden vuoksi
 * ainoastaan ProjectDAO_File:n julkista API:a hyödyntäen.
 * 
 * Tämän vuoksi testit ovat riippuvaisia myös muiden kuin testattavan
 * metodin toiminnasta ja siksi testit on ajettava määrätyssä järjestyksessä:
 * 1. create, 2. read, 3. update, 4. delete, 5. list
 * @author aleks
 * @version 21 Mar 2020
 *
 */
public class TestProjectDAO_File {
    private ProjectDAO_File pDAO;
    @SuppressWarnings("javadoc")
    @TempDir
    public Path tempdir;
    
    
    /**
     * Luodaan väliaikainen tallennustiedosto testejä varten
     */
    @BeforeEach
    public void createTestSetup() {
        Path tempfile;
        try {
            tempfile = Files.createFile(tempdir.resolve("test.dat"));
        } catch (IOException e) {
            System.out.println("Failed to create temporary file");
            e.printStackTrace();
            return;
        }
        this.pDAO = new ProjectDAO_File(tempfile.toString());
    }
    
    
    /**
     * Testataan uuden projektin lisäämistä
     */
    @Test
    @Order(1)
    public void testCreateProject() {
        //luodaan uusi projekti väliaikaisella id:llä
        Project project1 = new Project(1, "Project1");
        assertEquals(-1, project1.getId());

        //lisätään uusi merkintä tietokantaan, varmistetaan
        //että id päivittyi eivätkä muut tiedot vaihtuneet
        project1 = pDAO.create(project1);
        assertEquals(1, project1.getId());
        assertEquals(1, project1.getOwnerId());
        assertEquals("Project1", project1.getName());

        //sama toisella projektilla
        Project project2 = new Project(1, "Project2");
        assertEquals(-1, project2.getId());
        project2 = pDAO.create(project2);
        
        assertEquals(2, project2.getId());
        assertEquals(1, project2.getOwnerId());
        assertEquals("Project2", project2.getName());

        //sama kolmannella projektilla
        Project project3 = new Project(2, "Project1");
        assertEquals(-1, project3.getId());
        project3 = pDAO.create(project3);
        
        assertEquals(3, project3.getId());
        assertEquals(2, project3.getOwnerId());
        assertEquals("Project1", project3.getName());
    }
    
    
    /**
     * Testataan projektin lukemista tietokannasta
     */
    @Test
    @Order(2)
    public void testReadProject() {
        Project project1 = pDAO.create(new Project(1, "Project1"));
        Project project2 = pDAO.create(new Project(1, "Project2"));
        Project project3 = pDAO.create(new Project(2, "Project1"));

        //verrataan luetusta datasta muodostettua oliota alkuperäiseen
        Project read = pDAO.read(1);
        assertEquals(project1, read);
        
        read = pDAO.read(2);
        assertEquals(project2, read);
        
        read = pDAO.read(3);
        assertEquals(project3, read);
    }
    
    
    /**
     * Testataan projektin päivittämistä tietokantaan
     */
    @Test
    @Order(3)
    public void testUpdateProject() {
        Project project1 = pDAO.create(new Project(1, "Project1"));
        Project project2 = pDAO.create(new Project(1, "Project2"));
        Project project3 = pDAO.create(new Project(2, "Project1"));

        //vaihdetaan nimi ja päivitetään kantaa
        project1.setName("NewName");
        assertTrue(pDAO.update(project1));

        //verrataan luetusta datasta muodostettua oliota alkuperäiseen
        Project read = pDAO.read(1);
        assertEquals(project1, read);

        //sama toiselle projektille
        project2.setName("NewName2");
        assertTrue(pDAO.update(project2));
        
        read = pDAO.read(2);
        assertEquals(project2, read);
        
        //sama kolmannelle projektille
        project3.setName("NewName3");
        assertTrue(pDAO.update(project3));

        read = pDAO.read(3);
        assertEquals(project3, read);

        //varmistetaan että ensimmäinen projekti pysyi ennallaan
        read = pDAO.read(1);
        assertEquals(project1, read);

        //päivitys ei onnistu projektille jota ei ole tietokannassa
        Project project4 = new Project(1,"Project4");
        assertFalse(pDAO.update(project4));
    }
    
    
    /**
     * Testataan projektin poistamista tietokannasta
     */
    @Test
    @Order(4)
    public void testDeleteProject() {
        pDAO.create(new Project(1, "Project1"));
        Project project2 = pDAO.create(new Project(1, "Project2"));
        Project project3 = pDAO.create(new Project(2, "Project1"));

        //poistetaan, varmistetaan että poistui
        assertTrue(pDAO.delete(1));
        assertNull(pDAO.read(1));

        //varmistetaan että muut pysyivät ennallaan
        Project read = pDAO.read(2);
        assertEquals(project2, read);
        read = pDAO.read(3);
        assertEquals(project3, read);
        
        //poistetaan, varmistetaan että poistui
        assertTrue(pDAO.delete(2));
        assertNull(pDAO.read(2));
        
        //poistetaan, varmistetaan että poistui
        assertTrue(pDAO.delete(3));
        assertNull(pDAO.read(3));
        
        assertFalse(pDAO.delete(4));
    }
    
    
    /**
     * Testataan projektien listaamista
     */
    @Test
    @Order(5)
    public void testListProjects() {
        Project project1 = pDAO.create(new Project(1, "Project1"));
        Project project2 = pDAO.create(new Project(1, "Project2"));
        Project project3 = pDAO.create(new Project(2, "Project1"));

        //haetaan kannan sisältämät projektit listalle
        ArrayList<Project> projects = new ArrayList<>();
        projects.addAll(pDAO.list());

        //varmistetaan että listalla on kaikki projektit mutta ei muita
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));
        assertTrue(projects.contains(project3));
        assertEquals(3, projects.size());
    }
    
    
    /**
     * Testataan tietyn käyttäjän projektien listaamista
     */
    @Test
    @Order(6)
    public void testListUserProjects() {
        User user1 = new User(1, "User1");
        Project project1 = pDAO.create(new Project(1, "Project1"));
        Project project2 = pDAO.create(new Project(1, "Project2"));
        Project project3 = pDAO.create(new Project(2, "Project1"));

        //haetaan kannan sisältämät käyttäjän 1 projektit listalle
        ArrayList<Project> projects = new ArrayList<>();
        projects.addAll(pDAO.list(user1));

        //varmistetaan että listalla on kaikki käyttäjän projektit mutta ei muita
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));
        assertFalse(projects.contains(project3));
        assertEquals(2, projects.size());
    }
}