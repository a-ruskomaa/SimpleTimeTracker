package roarusko.simpleTimeTracker.model.data;

import java.util.List;

import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;

/**
 * Projekteja käsitteleviä tiedonkäsittelyoperaatioita toteuttavan luokan rajapinta. Perii DAO:n 
 * metodit read(), update() sekä delete(). Tarjoaa oman toteutuksen create-metodille sekä
 * ylikuormitetun toteutukset list-metodeille.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public interface ProjectDAO extends DAO<Integer, Project> {
    
    /**
     * Lisää pysyvään muistiin uuden projektin tiedot, jotka ottaa vastaan Project-oliona
     * @param object Lisättävä projekti
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    @Override
    public Project create(Project object);

    
    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut annetulle käyttäjälle kuuluvat merkinnät Project-olioina
     * @param user Käyttäjä, johon liittyviä projekteja halutaan listata
     * @return palauttaa Project-olioita listalla
     */
    public List<Project> list(User user);
}
