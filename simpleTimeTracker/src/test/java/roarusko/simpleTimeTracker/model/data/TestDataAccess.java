package roarusko.simpleTimeTracker.model.data;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import roarusko.simpleTimeTracker.model.data.mock.MockEntryDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockProjectDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockUserDAO;
import roarusko.simpleTimeTracker.model.domain.User;

public class TestDataAccess {
    private UserDAO uDAO = new MockUserDAO();
    private ProjectDAO pDAO = new MockProjectDAO();
    private EntryDAO eDAO = new MockEntryDAO();
    
    private DataAccess da = new DataAccess(uDAO, pDAO, eDAO);
    
    
    //
}
