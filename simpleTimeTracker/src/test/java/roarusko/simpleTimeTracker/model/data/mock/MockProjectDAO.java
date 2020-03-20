package roarusko.simpleTimeTracker.model.data.mock;

import java.util.List;

import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

/**
 * Projekteja käsitteleviä tiedonkäsittelyoperaatioita toteuttava luokka. Perii AbstractDAO:n geneeriset
 * toteutukset CRUD-metodeista joita hyödyntää sellaisenaan.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class MockProjectDAO extends MockAbstractChildDAO<Project, User> {
    
    /**
     * Testidataa käyttävä ProjectDAO:n implementaatio
     */
    public MockProjectDAO() {
        super(SampleData.getProjects());
    }
    
    
    /**
     * Testidataa käyttävä ProjectDAO:n implementaatio
     * @param list lista joka sisältää testidatan
     */
    public MockProjectDAO(List<Project> list) {
        super(list);
    }
    
    /**
     * Testidataa käyttävä ProjectDAO:n implementaatio
     * @param list lista joka sisältää testidatan
     * @param idGen idGeneraattori jolla testidatan id:t luodaan
     */
    public MockProjectDAO(List<Project> list, IdGenerator idGen) {
        super(list, idGen);
    }


    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut annetulle käyttäjälle kuuluvat merkinnät Project-olioina
     * @param user Käyttäjä, johon liittyviä projekteja halutaan listata
     * @return palauttaa Project-olioita listalla
     */
    @Override
    public List<Project> list(User user) {
        return super.list(user);
    }
}
