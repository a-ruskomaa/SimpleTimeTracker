package roarusko.simpleTimeTracker.model.data.db;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import roarusko.simpleTimeTracker.model.data.ConnectionManager;
import roarusko.simpleTimeTracker.model.data.ConnectionManagerImpl;
import roarusko.simpleTimeTracker.model.domain.User;


/**
 * UserDAO:n testit.
 * 
 * Jotta testit eivät olisi riippuvaisia käytetyn tietokannan skeemasta on testit kirjoitettu
 * ainoastaan UserDAO:n julkista API:a hyödyntäen.
 * 
 * Tämän vuoksi testit ovat riippuvaisia myös muiden kuin testattavan
 * metodin toiminnasta ja siksi testit on ajettava määrätyssä järjestyksessä:
 * 1. create, 2. read, 3. update, 4. delete, 5. list
 * @author aleks
 * @version 21 Mar 2020
 *
 */
public class TestUserDAO {
    private ConnectionManager cm;
    private Connection con;
    private UserDAO uDAO;

    
    /**
     * Luodaan testiasetelma. Testeissä käytetään vain muistissa toimivaa SQLite3-tietokantaa.
     * 
     * SQLite poistaa tietokannan muistista kun viimeinen yhteys siihen katkaistaan.
     * 
     * Koska testattavat metodit katkaisevat yhteyden automaattisesti jokaisen tietokantakutsun jälkeen, on
     * jokaisen testin aluksi luotava yksi ylimääräinen yhteys joka tietokantaa häviämästä testin aikana.
     * 
     * Yhteys tulee katkaista jokaisen testimetodin suorituksen jälkeen.
     */
    @BeforeEach
    public void createTestSetup() {
        this.cm = new ConnectionManagerImpl("file:memorydb.db?mode=memory&cache=shared");
        //luodaan yhteys, joka estää tietokantaa tyhjenemästä testin aikana
        this.con = cm.getConnection();
        cm.initDb();
        this.uDAO = new UserDAO(cm);
    }

    
    /**
     * Katkaistaan tietokantayhteys ja annetaan muistiin luodun tietokannan tuhoutua.
     */
    @AfterEach
    public void closeDb() {
        try {
            this.con.close();
        } catch (SQLException e) {
            System.out.println("Failed to close the database connection");
            e.printStackTrace();
        }
    }
    
    
    /**
     * Testataan uuden käyttäjän lisäämistä
     */
    @Test
    @Order(1)
    public void testCreateUser() {
        //luodaan uusi käyttäjä väliaikaisella id:llä
        User user1 = new User("User1");
        assertEquals(-1, user1.getId());
        
        //lisätään uusi käyttäjä tietokantaan, varmistetaan
        //että id päivittyi eivätkä muut tiedot vaihtuneet
        user1 = uDAO.create(user1);
        assertEquals(1, user1.getId());
        assertEquals("User1", user1.getName());
        
        //sama toisella käyttäjällä
        User user2 = new User("User2");
        assertEquals(-1, user2.getId());
        
        user2 = uDAO.create(user2);
        assertEquals(2, user2.getId());
        assertEquals("User2", user2.getName());
    }
    
    
    /**
     * Testataan käyttäjän lukemista tietokannasta
     */
    @Test
    @Order(2)
    public void testReadUser() {
        User user1 = uDAO.create(new User("User1"));
        User user2 = uDAO.create(new User("User2"));

        //verrataan luetusta datasta muodostettua oliota alkuperäiseen
        User read = uDAO.read(1);
        assertEquals(user1, read);
        
        read = uDAO.read(2);
        assertEquals(user2, read);
    }
    
    
    /**
     * Testataan käyttäjän päivittämistä tietokantaan
     */
    @Test
    @Order(3)
    public void testUpdateUser() {
        User user1 = uDAO.create(new User("User1"));
        User user2 = uDAO.create(new User("User2"));
        
        //vaihdetaan nimi ja päivitetään kantaa
        user1.setName("NewName");
        assertTrue(uDAO.update(user1));
        
        //verrataan luetusta datasta muodostettua oliota alkuperäiseen
        User read = uDAO.read(1);
        assertEquals(user1, read);
        
        //sama toiselle käyttäjälle
        user2.setName("NewName2");
        assertTrue(uDAO.update(user2));
        read = uDAO.read(2);
        assertEquals(user2, read);
        
        //varmistetaan että ensimmäinen käyttäjä pysyi ennallaan
        read = uDAO.read(1);
        assertEquals(user1, read);

        //päivitys ei onnistu käyttäjälle joka ei ole kannassa
        User user3 = new User(3,"User3");
        assertFalse(uDAO.update(user3));
    }
    
    
    /**
     * Testataan käyttäjän poistamista tietokannasta
     */
    @Test
    @Order(4)
    public void testDeleteUser() {
        uDAO.create(new User("User1"));
        User user2 = uDAO.create(new User("User2"));
        
        //poistetaan, varmistetaan että poistui
        assertTrue(uDAO.delete(1));
        assertNull(uDAO.read(1));
        
        //varmistetaan että toinen pysyi ennallaan
        User read = uDAO.read(2);
        assertEquals(user2, read);
        
        //poistetaan toinenkin
        assertTrue(uDAO.delete(2));
        assertNull(uDAO.read(2));
        
        assertFalse(uDAO.delete(3));
    }
    
    
    /**
     * Testataan käyttäjien listaamista
     */
    @Test
    @Order(5)
    public void testListUsers() {
        User user1 = uDAO.create(new User("User1"));
        User user2 = uDAO.create(new User("User2"));
        User user3 = uDAO.create(new User("User3"));
        
        //haetaan kannan sisältämät käyttäjät listalle
        ArrayList<User> users = new ArrayList<>();
        users.addAll(uDAO.list());
        
        //varmistetaan että listalla on kaikki käyttäjät mutta ei enempää
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));
        assertEquals(3, users.size());
    }
    
}
