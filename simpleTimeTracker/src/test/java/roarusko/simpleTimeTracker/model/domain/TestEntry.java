package roarusko.simpleTimeTracker.model.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import roarusko.simpleTimeTracker.model.domain.Entry;

/**
 * Testaillaan Entry-luokkaa muutamilla testeillä. Luokka sisältää vain
 * gettereitä ja settereitä, joiden testaaminen on jokseenkin hyödytöntä.
 * @author aleks
 * @version 21 Mar 2020
 *
 */
public class TestEntry {
    
    /**
     * Testaillaan Entry-luokkaa
     */
    @Test
    public void TestEntry1() {
        Entry entry = new Entry(123,234
                ,LocalDateTime.of(2020, 05, 30, 14, 14)
                ,LocalDateTime.of(2020, 05, 31, 15, 15));
        
        assertEquals(123, entry.getId(), "Entry id should be equal to 123");
        assertEquals(234, entry.getOwnerId(), "Project id should be equal to 234");
        
        assertTrue(LocalDate.of(2020, 05, 30).isEqual(entry.getStartDate()), "Wrong start date");
        assertTrue(LocalDate.of(2020, 05, 31).isEqual(entry.getEndDate()), "Wrong end date");
        

        entry = new Entry(234
                ,LocalDateTime.of(2020, 05, 30, 14, 14)
                ,LocalDateTime.of(2020, 05, 31, 15, 15));
        assertEquals(-1, entry.getId(), "Entry id should be equal to -1");
        assertEquals(234, entry.getOwnerId(), "Project id should be equal to 234");
        
        assertTrue(LocalDateTime.of(2020, 05, 30, 14, 14).isEqual(entry.getStartDateTime()), "Wrong start date or time");
        assertTrue(LocalDateTime.of(2020, 05, 31, 15, 15).isEqual(entry.getEndDateTime()), "Wrong end date or time");
        
        entry.setId(999);
        entry.setStartDateTime(LocalDateTime.of(1973, 01, 20, 11, 11));
        entry.setEndDateTime(LocalDateTime.of(1973, 02, 21, 00, 00));
        assertEquals(999, entry.getId(), "Entry id should be equal to 999");
        assertEquals(234, entry.getOwnerId(), "Project id should be equal to 234");
        
        assertTrue(LocalDateTime.of(1973, 01, 20, 11, 11).isEqual(entry.getStartDateTime()), "Wrong start date");
        assertTrue(LocalDateTime.of(1973, 02, 21, 00, 00).isEqual(entry.getEndDateTime()), "Wrong end date");
    }
}
