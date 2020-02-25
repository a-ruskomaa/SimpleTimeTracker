package roarusko.simpleTimeTracker.model.mockdata;

import java.util.List;

import roarusko.simpleTimeTracker.model.data.ProjectDAO;
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
public class MockProjectDAO extends MockAbstractDAO<Project> implements ProjectDAO {
    
    /**
     * Testidataa käyttävä ProjectDAO:n implementaatio
     */
    public MockProjectDAO() {
        super(SampleData.getProjects());
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
