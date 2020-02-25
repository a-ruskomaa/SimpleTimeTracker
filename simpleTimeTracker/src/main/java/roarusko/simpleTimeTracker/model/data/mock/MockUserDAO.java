package roarusko.simpleTimeTracker.model.data.mock;

import roarusko.simpleTimeTracker.model.data.UserDAO;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

/**
 * Käyttäjiä käsitteleviä tiedonkäsittelyoperaatioita toteuttava luokka. Perii AbstractDAO:n geneeriset
 * toteutukset read(), update() sekä delete() -metodeista joita hyödyntää sellaisenaan. Tarjoaa
 * oman toteutuksen create-metodille.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class MockUserDAO extends MockAbstractDAO<User> implements UserDAO {
    private IdGenerator idGenerator;
    
    
    /**
     * Testidataa käyttävä UserDAO:n implementaatio
     */
    public MockUserDAO() {
        this.data = SampleData.getUsers();
        this.idGenerator = new IdGenerator(data);
    }

    /**
     * Lisää pysyvään muistiin uuden käyttäjän tiedot, jotka ottaa vastaan User-oliona
     * @param object Lisättävä käyttäjä
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    @Override
    public User create(User object) {        
        User user = new User(idGenerator.getNewId(), object.getName());
        
        data.add(user);
        
        return user;
    }

}
