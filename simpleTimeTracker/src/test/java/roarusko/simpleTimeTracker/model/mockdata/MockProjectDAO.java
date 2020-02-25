package roarusko.simpleTimeTracker.model.mockdata;

import java.util.List;

import roarusko.simpleTimeTracker.model.data.ProjectDAO;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

/**
 * Projekteja käsitteleviä tiedonkäsittelyoperaatioita toteuttava luokka. Perii AbstractDAO:n geneeriset
 * toteutukset read(), update() sekä delete() -metodeista joita hyödyntää sellaisenaan. Tarjoaa
 * oman toteutuksen create-metodille sekä ylikirjoitetut toteutukset list-metodeille.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class MockProjectDAO extends MockAbstractDAO<Project> implements ProjectDAO {
    private IdGenerator idGenerator;
    
    /**
     * Testidataa käyttävä ProjectDAO:n implementaatio
     */
    public MockProjectDAO() {
        this.data = SampleData.getProjects();
        this.idGenerator = new IdGenerator(data);
    }
    
    /**
     * Lisää pysyvään muistiin uuden projektin tiedot, jotka ottaa vastaan Project-oliona
     * @param object Lisättävä projekti
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    @Override
    public Project create(Project object) {        
        Project project = new Project(idGenerator.getNewId(), object.getName(), object.getOwner());
        
        data.add(project);
        
        return project;
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
