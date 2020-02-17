package fxTyoaika.model.dataAccess;

import java.util.ArrayList;
import java.util.List;

import fxTyoaika.SampleData;
import fxTyoaika.model.IdGenerator;
import fxTyoaika.model.Project;
import fxTyoaika.model.User;

/**
 * Projekteja käsitteleviä tiedonkäsittelyoperaatioita toteuttava luokka. Perii AbstractDAO:n geneeriset
 * toteutukset read(), update() sekä delete() -metodeista joita hyödyntää sellaisenaan. Tarjoaa
 * oman toteutuksen create-metodille sekä ylikirjoitetut toteutukset list-metodeille.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class ProjectDAO extends AbstractDAO<Project> {
    
    /**
     * Lisää pysyvään muistiin uuden projektin tiedot, jotka ottaa vastaan Project-oliona
     * @param object Lisättävä projekti
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    @Override
    public Project create(Project object) {

        // TODO tämä poistetaan myöhemmin
        @SuppressWarnings("unchecked")
        ArrayList<Project> projects = (ArrayList<Project>) SampleData.getData(Project.class);
        
        Project project = new Project(IdGenerator.getNewProjectId(), object.getName(), object.getOwner());
        
        projects.add(project);
        
        return project;
    }

    /**
     * TODO tämä poistetaan myöhemmin
     * Tilapäinen metodi, jota hyödynnetään haettaessa testidataa muun ohjelman käyttöön
     * @return palauttaa tyypin Project olioita listalla
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Project> getData() {
        return (ArrayList<Project>) SampleData.getData(Project.class);
    }
    
    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut projektit Project-olioina
     * @return palauttaa Project-olioita listalla
     */
    @Override
    public List<Project> list() {
        return list(Project.class);
    }
    
    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut annetulle käyttäjälle kuuluvat merkinnät Project-olioina
     * @param user Käyttäjä, johon liittyviä projekteja halutaan listata
     * @return palauttaa Project-olioita listalla
     */
    public List<Project> list(User user) {
        return list(Project.class, user);
    }
}
