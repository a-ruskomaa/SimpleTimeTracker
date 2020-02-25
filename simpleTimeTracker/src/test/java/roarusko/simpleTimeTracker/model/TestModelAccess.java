package roarusko.simpleTimeTracker.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import roarusko.simpleTimeTracker.model.data.EntryDAO;
import roarusko.simpleTimeTracker.model.data.ProjectDAO;
import roarusko.simpleTimeTracker.model.data.UserDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockEntryDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockProjectDAO;
import roarusko.simpleTimeTracker.model.data.mock.MockUserDAO;
import roarusko.simpleTimeTracker.model.domain.User;

public class TestModelAccess {
    private UserDAO uDAO = new MockUserDAO();
    private ProjectDAO pDAO = new MockProjectDAO();
    private EntryDAO eDAO = new MockEntryDAO();
    
    private ModelAccess ma = new ModelAccess(uDAO, pDAO, eDAO);
    
    
    //
}
