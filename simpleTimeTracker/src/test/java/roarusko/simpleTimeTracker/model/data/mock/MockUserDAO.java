package roarusko.simpleTimeTracker.model.data.mock;

import java.util.List;

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
    
    /**
     * Testidataa käyttävä UserDAO:n implementaatio
     * @param list lista joka sisältää testidatan
     */
    public MockUserDAO(List<User> list) {
        super(list);
    }
    
    /**
     * Testidataa käyttävä UserDAO:n implementaatio
     * @param list lista joka sisältää testidatan
     * @param idGen idGeneraattori jolla testidatan id:t luodaan
     */
    public MockUserDAO(List<User> list, IdGenerator idGen) {
        super(list, idGen);
    }
    

}
