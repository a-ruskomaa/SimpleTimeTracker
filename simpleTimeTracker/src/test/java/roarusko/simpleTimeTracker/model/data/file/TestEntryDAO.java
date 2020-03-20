package roarusko.simpleTimeTracker.model.data.file;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.rules.TemporaryFolder;
import org.junit.Rule;

import roarusko.simpleTimeTracker.model.domain.Entry;

public class TestEntryDAO {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private final String testFileName = "test_users.dat";
    File tempfile;
    
    
    @BeforeEach
    public void createTempFile() {
        try {
            tempfile = folder.newFile(testFileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testCreateFile() {
        //
    }
    
}
