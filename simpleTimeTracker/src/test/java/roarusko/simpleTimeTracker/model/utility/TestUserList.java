package roarusko.simpleTimeTracker.model.utility;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.UserList;

/**
 * Yksinkertainen testiluokka userListin testaamiseen.
 * Testit ajetaan könttänä määrätyssä järjestyksessä.
 * @version 2020.02.19 10:20:35
 *
 */
@TestMethodOrder(OrderAnnotation.class)
public class TestUserList {
    private UserList lista;
    
    
    @SuppressWarnings("javadoc")
    @BeforeEach
    public void Init() {
        lista = new UserList();
    }

    /**
    * Testataan listan luomista anetulla kapasiteetilla
    */
    @Test
    @Order(1)
    public void testNewListWithCapacity() {
        lista = new UserList(5);
        assertEquals(0, lista.size(), "Incorrect size");
        assertTrue(lista.isEmpty(), "The list should be empty");
    }

    /**
    * Testataan listan luomista ilman annettua kapasiteettia
    */
    @Test
    @Order(2)
    public void testNewListWithoutCapacity() {
        lista = new UserList();
        assertEquals(0, lista.size(), "Incorrect size");
        assertTrue(lista.isEmpty(), "The list should be empty");
    }

    /**
    * Testataan isEmpty-metodia
    */
    @Test
    @Order(3)
    public void testIsEmpty() {
        lista.add(new User("Janne"));
        assertEquals(1, lista.size(), "Incorrect size");
        assertFalse(lista.isEmpty(), "The list should be empty");
    }

    
    /**
    * Testataan alkioiden lisäämistä
    */
    @Test
    @Order(4)
    public void testAddMultiple() {
        lista.add(new User("Janne"));
        lista.add(new User("Jonne"));
        lista.add(new User("Jenni")); // [Janne, Jonne, Jenni]
        assertEquals(3, lista.size(), "Incorrect size");
    }
    
    
    /**
    * Testataan alkioiden lisäämistä määrättyyn indeksiin
    */
    @Test
    @Order(5)
    public void testAddToIndex() {
        User user1 = new User("Janne");
        User user2 = new User("Joonas");
        lista.add(user1);
        assertEquals(0, lista.indexOf(user1));
        lista.add(0, user2); // [Joonas, Janne]
        assertEquals(0, lista.indexOf(user2));
        assertEquals(1, lista.indexOf(user1));
        assertEquals(2, lista.size(), "Incorrect size");
    }
    
    /**
    * Testataan contains-metodia
    */
    @Test
    @Order(6)
    public void testContains() {
        User user1 = new User("Janne");
        User user2 = new User("Joonas");
        lista.add(user1);
        lista.add(user2); // [Joonas, Janne]
        assertTrue(lista.contains(user1));
        assertTrue(lista.contains(user2));
    }
    
    /**
    * Testataan remove-metodia
    */
    @Test
    @Order(7)
    public void testRemoveUser() {
        User user = new User("Pekko");
        lista.add(user);
        assertTrue(lista.contains(user));
        assertTrue(lista.remove(user));
        assertEquals(0, lista.size());
    }
    
    /**
    * Testataan että listan järjestys säilyy poiston jälkeen
    */
    @Test
    @Order(8)
    public void testRemoveUserOrderStays() {
        User user1 = new User("Janne");
        User user2 = new User("Pekko");
        User user3 = new User("Joonas");
        lista.add(user1);
        lista.add(user2);
        lista.add(user3); // [Janne, Pekko, Joonas]
        lista.remove(0);
        assertFalse(lista.contains(user1));
        assertEquals(0, lista.indexOf(user2));
        assertEquals(1, lista.indexOf(user3));
        assertEquals(2, lista.size());
    }
    
    /**
    * Testataan get-metodia
    */
    @Test
    @Order(9)
    public void testGetUser() {
        User user1 = new User("Janne");
        User user2 = new User("Pekko");
        User user3 = new User("Joonas");
        lista.add(user1);
        lista.add(user2);
        lista.add(user3); // [Janne, Pekko, Joonas]
        assertEquals(3, lista.size());
        User user = lista.get(1);
        assertEquals(3, lista.size());
        assertSame(user2, user);
    }
    
    /**
    * Testataan sekalaista sälää
    */
    @Test
    @Order(10)
    public void testUserNotFound() {
        User user1 = new User("Janne");
        User user2 = new User("Jammu");
        User user3 = new User("Kyösti");
        lista.add(user1);
        assertTrue(lista.contains(user1));
        assertFalse(lista.contains(user2));
        assertEquals(-1, lista.indexOf(user3));
    }
    
    /**
     * Testataan että listan koko kasvaa kun käyttäjien määrä ylittää kapasiteetin
     */
    @Test
    @Order(11)
    public void testListExpansion() {
        lista = new UserList(5);
        assertEquals(0, lista.size());
        
        for (int i = 0; i < 5; i++) {
            lista.add(new User("Jorma"));
        }
        
        assertEquals(5, lista.size());
        
        for (int i = 0; i < 10; i++) {
            lista.add(new User("Jorma"));
        }

        assertEquals(15, lista.size());
        
        for (int i = 0; i < 25; i++) {
            lista.add(new User("Jorma"));
        }
        
        assertEquals(40, lista.size());
    }
}