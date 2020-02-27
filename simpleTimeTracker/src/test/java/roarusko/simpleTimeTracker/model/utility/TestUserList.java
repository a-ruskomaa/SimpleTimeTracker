package roarusko.simpleTimeTracker.model.utility;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.UserList;

/**
 * Yksinkertainen testiluokka userListin testaamiseen
 * @version 2020.02.19 10:20:35
 *
 */
public class TestUserList {

    /**
    * Könttätesti, lisäillään ja poistellaan
    */
    @Test
    public void testUserList() {
        UserList lista = new UserList(5);
        assertEquals(0, lista.size(), "Incorrect size");
        assertEquals(true, lista.isEmpty(), "The list should be empty");
        User user1 = new User(1, "Janne");
        User user2 = new User(2, "Jonne");
        User user3 = new User(3, "Jenni");
        lista.add(user1);
        lista.add(user2);
        lista.add(user3); // [Janne, Jonne, Jenni]
        assertEquals(3, lista.size(), "Incorrect size");
        assertEquals(false, lista.isEmpty(), "The list should not be empty!");
        User user4 = new User(4, "Joonas");
        lista.add(0, user4); // [Joonas, Janne, Jonne, Jenni]
        assertEquals(0, lista.indexOf(user4));
        assertEquals(true, lista.contains(user4));
        assertEquals(true, lista.remove(user4)); // [Janne, Jonne, Jenni]
        lista.remove(1); // [Janne, Jenni]
        assertEquals(true, lista.get(1).equals(user3));
        User user5 = new User(5, "Jammu");
        User user6 = new User(6, "Kyösti");
        assertEquals(false, lista.contains(user5));
        assertEquals(-1, lista.indexOf(user6));
        lista.add(user5);
        lista.add(user6); // [Janne, Jenni, Jammu, Kyösti]
        assertEquals(4, lista.size());
        lista.add(user4); // [Janne, Jenni, Jammu, Kyösti, Joonas]
        lista.add(0, user2); // [Jonne, Janne, Jenni, Jammu, Kyösti, Joonas]
        assertEquals(6, lista.size());
        User user7 = new User(7, "Jorma");
        lista.set(4, user7); // [Jonne, Janne, Jenni, Jammu, Jorma, Joonas]
        assertEquals(false, lista.contains(user6));
        lista.get(1).equals(user1);
    }
}