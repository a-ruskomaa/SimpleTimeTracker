package roarusko.simpleTimeTracker.model.domain;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import roarusko.simpleTimeTracker.model.domain.User;

/**
 * Testaillaan User-luokkaa muutamilla testeillä. Luokka sisältää vain
 * gettereitä ja settereitä, joiden testaaminen on jokseenkin hyödytöntä.
 * @author aleks
 * @version 21 Mar 2020
 *
 */
public class TestUser {
    
    /**
     * Testaillaan User-luokkaa
     */
    @Test
    public void TestUser1() {
        User user = new User("User1");
        
        assertEquals(-1, user.getId());
        assertEquals("User1", user.getName());
        
        user = new User(123, "User2");
        
        assertEquals(123, user.getId());
        assertEquals("User2", user.getName());
        
        user = new User("User1");
        user.setId(444);
        user.setName("asdasd");
        
        assertEquals(444, user.getId());
        assertEquals("asdasd", user.getName());
        
        // ei pitäisi onnistua
        user.setId(123);
        assertEquals(444, user.getId());
    }
}
