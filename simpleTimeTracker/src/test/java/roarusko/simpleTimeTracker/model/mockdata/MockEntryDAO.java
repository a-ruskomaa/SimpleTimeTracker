package roarusko.simpleTimeTracker.model.mockdata;

import java.util.List;

import roarusko.simpleTimeTracker.model.data.EntryDAO;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.utility.IdGenerator;

/**
 * Merkintöjä käsitteleviä tiedonkäsittelyoperaatioita toteuttava luokka. Perii AbstractDAO:n geneeriset
 * toteutukset read(), update() sekä delete() -metodeista joita hyödyntää sellaisenaan. Tarjoaa
 * oman toteutuksen create-metodille sekä ylikirjoitetut toteutukset list-metodeille.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class MockEntryDAO extends MockAbstractDAO<Entry> implements EntryDAO{
    private IdGenerator idGenerator;
    
    /**
     * Testidataa käyttävä EntryDAO:n implementaatio
     */
    public MockEntryDAO() {
        this.data = SampleData.getEntries();
        this.idGenerator = new IdGenerator(data);
    }

    /**
     * Lisää pysyvään muistiin uuden merkinnän tiedot, jotka ottaa vastaan Entry-oliona
     * @param object Lisättävä merkintä
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    @Override
    public Entry create(Entry object) {        
        Entry entry = new Entry(idGenerator.getNewId(), object.getOwner(), object.getStartDateTime(), object.getEndDateTime());
        
        data.add(entry);
        
        return entry;
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
