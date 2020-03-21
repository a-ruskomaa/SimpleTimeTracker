package roarusko.simpleTimeTracker.model.data.file;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;

import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;

/**
 * EntryDAO_File:n testit.
 * 
 * Testit on kirjoitettu yksinkertaisuuden vuoksi
 * ainoastaan EntryDAO_File:n julkista API:a hyödyntäen.
 * 
 * Tämän vuoksi testit ovat riippuvaisia myös muiden kuin testattavan
 * metodin toiminnasta ja siksi testit on ajettava määrätyssä järjestyksessä:
 * 1. create, 2. read, 3. update, 4. delete, 5. list
 * @author aleks
 * @version 21 Mar 2020
 *
 */
public class TestEntryDAO_File {
    private EntryDAO_File eDAO;
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
        this.eDAO = new EntryDAO_File(tempfile.toString());
    }
    
    
    /**
     * Testataan uuden merkinnän lisäämistä
     */
    @Test
    @Order(1)
    public void testCreateEntry() {
        //luodaan uusi merkintä väliaikaisella id:llä
        Entry entry1 = new Entry(1, LocalDateTime.of(2000, 12, 30, 9, 30), LocalDateTime.of(2000, 12, 30, 16, 15));
        assertEquals(-1, entry1.getId());

        //lisätään uusi merkintä tietokantaan, varmistetaan
        //että id päivittyi eivätkä muut tiedot vaihtuneet
        entry1 = eDAO.create(entry1);
        assertEquals(1, entry1.getId());
        assertEquals(1, entry1.getOwnerId());
        assertTrue(entry1.getStartDateTime().isEqual(LocalDateTime.of(2000, 12, 30, 9, 30)));
        assertTrue(entry1.getEndDateTime().isEqual(LocalDateTime.of(2000, 12, 30, 16, 15)));

        //sama toisella merkinnällä
        Entry entry2 = new Entry(1,LocalDateTime.of(2000, 12, 30, 17, 30), LocalDateTime.of(2000, 12, 30, 19, 15));
        assertEquals(-1, entry2.getId());
        
        entry2 = eDAO.create(entry2);
        assertEquals(2, entry2.getId());
        assertEquals(1, entry2.getOwnerId());
        assertTrue(entry2.getStartDateTime().isEqual(LocalDateTime.of(2000, 12, 30, 17, 30)));
        assertTrue(entry2.getEndDateTime().isEqual(LocalDateTime.of(2000, 12, 30, 19, 15)));

        //sama kolmannella merkinnällä
        Entry entry3 = new Entry(2,LocalDateTime.of(2000, 12, 31, 11, 35), LocalDateTime.of(2000, 12, 31, 17, 20));
        assertEquals(-1, entry3.getId());
        
        entry3 = eDAO.create(entry3);
        assertEquals(3, entry3.getId());
        assertEquals(2, entry3.getOwnerId());
        assertTrue(entry3.getStartDateTime().isEqual(LocalDateTime.of(2000, 12, 31, 11, 35)));
        assertTrue(entry3.getEndDateTime().isEqual(LocalDateTime.of(2000, 12, 31, 17, 20)));
    }
    
    
    /**
     * Testataan merkinnän lukemista tietokannasta
     */
    @Test
    @Order(2)
    public void testReadEntry() {
        Entry entry1 = eDAO.create(new Entry(1, LocalDateTime.of(2000, 12, 30, 9, 30), LocalDateTime.of(2000, 12, 30, 16, 15)));
        Entry entry2 = eDAO.create(new Entry(1,LocalDateTime.of(2000, 12, 30, 17, 30), LocalDateTime.of(2000, 12, 30, 19, 15)));
        Entry entry3 = eDAO.create(new Entry(2,LocalDateTime.of(2000, 12, 31, 11, 35), LocalDateTime.of(2000, 12, 31, 17, 20)));

        //verrataan luetusta datasta muodostettua oliota alkuperäiseen
        Entry read = eDAO.read(1);
        assertEquals(entry1, read);
        
        read = eDAO.read(2);
        assertEquals(entry2, read);
        
        read = eDAO.read(3);
        assertEquals(entry3, read);
    }
    
    
    /**
     * Testataan merkinnän päivittämistä tietokantaan
     */
    @Test
    @Order(3)
    public void testUpdateEntry() {
        Entry entry1 = eDAO.create(new Entry(1, LocalDateTime.of(2000, 12, 30, 9, 30), LocalDateTime.of(2000, 12, 30, 16, 15)));
        Entry entry2 = eDAO.create(new Entry(1,LocalDateTime.of(2000, 12, 30, 17, 30), LocalDateTime.of(2000, 12, 30, 19, 15)));
        Entry entry3 = eDAO.create(new Entry(2,LocalDateTime.of(2000, 12, 31, 11, 35), LocalDateTime.of(2000, 12, 31, 17, 20)));

        //vaihdetaan alkuaika ja päivitetään kantaa
        entry1.setStartDateTime(LocalDateTime.of(2000, 12, 30, 8, 45));
        assertTrue(eDAO.update(entry1));

        //verrataan luetusta datasta muodostettua oliota alkuperäiseen
        Entry read = eDAO.read(1);
        assertEquals(entry1, read);

        //sama toiselle merkinnälle
        entry2.setStartDateTime(LocalDateTime.of(2000, 12, 30, 16, 45));
        entry2.setEndDateTime(LocalDateTime.of(2000, 12, 30, 19, 30));
        assertTrue(eDAO.update(entry2));
        
        read = eDAO.read(2);
        assertEquals(entry2, read);

        //sama kolmannelle merkinnälle
        entry3.setStartDateTime(LocalDateTime.of(2000, 12, 30, 16, 45));
        entry3.setEndDateTime(LocalDateTime.of(2000, 12, 30, 19, 30));
        assertTrue(eDAO.update(entry3));
        
        read = eDAO.read(3);
        assertEquals(entry3, read);
        
        //varmistetaan että ensimmäinen merkintä pysyi ennallaan
        read = eDAO.read(1);
        assertEquals(entry1, read);

        //päivitys ei onnistu merkinnälle jota ei ole tietokannassa
        Entry entry4 = new Entry(2,LocalDateTime.of(2000, 12, 31, 11, 35), LocalDateTime.of(2000, 12, 31, 17, 20));
        assertFalse(eDAO.update(entry4));
    }
    
    
    /**
     * Testataan merkinnän poistamista tietokannasta
     */
    @Test
    @Order(4)
    public void testDeleteEntry() {
        eDAO.create(new Entry(1, LocalDateTime.of(2000, 12, 30, 9, 30), LocalDateTime.of(2000, 12, 30, 16, 15)));
        Entry entry2 = eDAO.create(new Entry(1,LocalDateTime.of(2000, 12, 30, 17, 30), LocalDateTime.of(2000, 12, 30, 19, 15)));
        Entry entry3 = eDAO.create(new Entry(2,LocalDateTime.of(2000, 12, 31, 11, 35), LocalDateTime.of(2000, 12, 31, 17, 20)));

        //poistetaan, varmistetaan että poistui
        assertTrue(eDAO.delete(1));
        assertNull(eDAO.read(1));

        //varmistetaan että muut pysyivät ennallaan
        Entry read = eDAO.read(2);
        assertEquals(entry2, read);
        read = eDAO.read(3);
        assertEquals(entry3, read);
        
        //poistetaan, varmistetaan että poistui
        assertTrue(eDAO.delete(2));
        assertNull(eDAO.read(2));
        
        //poistetaan, varmistetaan että poistui
        assertTrue(eDAO.delete(3));
        assertNull(eDAO.read(3));
        
        assertFalse(eDAO.delete(4));
    }
    
    
    /**
     * Testataan merkintöjen listaamista
     */
    @Test
    @Order(5)
    public void testListEntries() {
        Entry entry1 = eDAO.create(new Entry(1, LocalDateTime.of(2000, 12, 30, 9, 30), LocalDateTime.of(2000, 12, 30, 16, 15)));
        Entry entry2 = eDAO.create(new Entry(1,LocalDateTime.of(2000, 12, 30, 17, 30), LocalDateTime.of(2000, 12, 30, 19, 15)));
        Entry entry3 = eDAO.create(new Entry(2,LocalDateTime.of(2000, 12, 31, 11, 35), LocalDateTime.of(2000, 12, 31, 17, 20)));

        //haetaan kannan sisältämät merkinnät listalle
        ArrayList<Entry> entrys = new ArrayList<>();
        entrys.addAll(eDAO.list());

        //varmistetaan että listalla on kaikki merkinnät mutta ei muita
        assertTrue(entrys.contains(entry1));
        assertTrue(entrys.contains(entry2));
        assertTrue(entrys.contains(entry3));
    }
    
    
    /**
     * Testataan tietyn projektin merkintöjen listaamista
     */
    @Test
    @Order(6)
    public void testListProjectEntries() {
        Project project = new Project(1, 2, "Project");
        Entry entry1 = eDAO.create(new Entry(1, LocalDateTime.of(2000, 12, 30, 9, 30), LocalDateTime.of(2000, 12, 30, 16, 15)));
        Entry entry2 = eDAO.create(new Entry(1,LocalDateTime.of(2000, 12, 30, 17, 30), LocalDateTime.of(2000, 12, 30, 19, 15)));
        Entry entry3 = eDAO.create(new Entry(2,LocalDateTime.of(2000, 12, 31, 11, 35), LocalDateTime.of(2000, 12, 31, 17, 20)));

        //haetaan kannan sisältämät projektin 1 merkinnät listalle
        ArrayList<Entry> entries = new ArrayList<>();
        entries.addAll(eDAO.list(project));

        //varmistetaan että listalla on kaikki projektin merkinnät mutta ei muita
        assertTrue(entries.contains(entry1));
        assertTrue(entries.contains(entry2));
        assertFalse(entries.contains(entry3));
    }
    
}
