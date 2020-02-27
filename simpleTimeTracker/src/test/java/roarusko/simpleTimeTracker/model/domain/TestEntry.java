package roarusko.simpleTimeTracker.model.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import roarusko.simpleTimeTracker.model.domain.Entry;

public class TestEntry {
    
    @Test
    public void TestEntry1() {
        Entry entry = new Entry(123,234
                ,LocalDateTime.of(2020, 05, 30, 14, 14)
                ,LocalDateTime.of(2020, 05, 31, 15, 15));
        
        assertEquals(123, entry.getId(), "Entry id should be equal to 123");
        assertEquals(234, entry.getOwnerId(), "Project id should be equal to 234");
        
        assertTrue(LocalDate.of(2020, 05, 30).isEqual(entry.getStartDate()), "Wrong start date");
        assertTrue(LocalDate.of(2020, 05, 31).isEqual(entry.getEndDate()), "Wrong end date");
    }
    //
}
