package roarusko.simpleTimeTracker.model.data;

import java.util.List;

import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;

/**
 * Merkintöjä käsitteleviä tiedonkäsittelyoperaatioita toteuttavan luokan rajapinta. Perii DAO:n 
 * metodit read(), update() sekä delete(). Tarjoaa oman toteutuksen create-metodille sekä
 * ylikuormitetun toteutukset list-metodeille.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public interface EntryDAO extends DAO<Integer, Entry> {

    /**
     * Lisää pysyvään muistiin uuden merkinnän tiedot, jotka ottaa vastaan Entry-oliona
     * @param object Lisättävä merkintä
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    @Override
    public Entry create(Entry object);

    
    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut annettuun projektiin kuuluvat merkinnät Entry-olioina
     * @param project Projekti, johon liittyviä merkintöjä halutaan listata
     * @return palauttaa Entry-olioita listalla
     */
    public List<Entry> list(Project project);

}
