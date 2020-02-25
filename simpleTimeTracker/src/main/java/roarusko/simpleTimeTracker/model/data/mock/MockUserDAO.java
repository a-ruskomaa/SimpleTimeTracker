package roarusko.simpleTimeTracker.model.data.mock;

import roarusko.simpleTimeTracker.model.data.UserDAO;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

/**
 * Käyttäjiä käsitteleviä tiedonkäsittelyoperaatioita toteuttava luokka. Perii AbstractDAO:n geneeriset
 * toteutukset CRUD-metodeista joita hyödyntää sellaisenaan.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class MockUserDAO extends MockAbstractDAO<User> implements UserDAO {
    
    /**
     * Testidataa käyttävä UserDAO:n implementaatio
     */
    public MockUserDAO() {
        super(SampleData.getUsers());
    }

}
