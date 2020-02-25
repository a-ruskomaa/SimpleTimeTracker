package roarusko.simpleTimeTracker.model.mockdata;

import java.util.List;

import roarusko.simpleTimeTracker.model.data.EntryDAO;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

/**
 * Merkintöjä käsitteleviä tiedonkäsittelyoperaatioita toteuttava luokka. Perii AbstractDAO:n geneeriset
 * toteutukset CRUD-metodeista joita hyödyntää sellaisenaan.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class MockEntryDAO extends MockAbstractDAO<Entry> implements EntryDAO{
    
    /**
     * Testidataa käyttävä EntryDAO:n implementaatio
     */
    public MockEntryDAO() {
        super(SampleData.getEntries());
    }


    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut annettuun projektiin kuuluvat merkinnät Entry-olioina
     * @param project Projekti, johon liittyviä merkintöjä halutaan listata
     * @return palauttaa Entry-olioita listalla
     */
    @Override
    public List<Entry> list(Project project) {
        return super.list(project);
    }

}
