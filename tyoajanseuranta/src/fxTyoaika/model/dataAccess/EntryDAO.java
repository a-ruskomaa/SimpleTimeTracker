package fxTyoaika.model.dataAccess;

import java.util.ArrayList;
import java.util.List;

import fxTyoaika.SampleData;
import fxTyoaika.model.Entry;
import fxTyoaika.model.IdGenerator;
import fxTyoaika.model.Project;

/**
 * Merkintöjä käsitteleviä tiedonkäsittelyoperaatioita toteuttava luokka. Perii AbstractDAO:n geneeriset
 * toteutukset read(), update() sekä delete() -metodeista joita hyödyntää sellaisenaan. Tarjoaa
 * oman toteutuksen create-metodille sekä ylikirjoitetut toteutukset list-metodeille.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class EntryDAO extends AbstractDAO<Entry> {

    /**
     * Lisää pysyvään muistiin uuden merkinnän tiedot, jotka ottaa vastaan Entry-oliona
     * @param object Lisättävä merkintä
     * @return Palauttaa lisäämänsä olion, jotta esim. päivitetty id-arvo saadaan ohjelman käyttöön
     */
    @Override
    public Entry create(Entry object) {
        // TODO tämä poistetaan myöhemmin
        @SuppressWarnings("unchecked")
        ArrayList<Entry> entries = (ArrayList<Entry>) SampleData.getData(Entry.class);
        
        Entry entry = new Entry(IdGenerator.getNewEntryId(), object.getOwner(), object.getStartDateTime(), object.getEndDateTime());
        
        entries.add(entry);
        
        return entry;
    }

    
    /**
     * Tilapäinen metodi, jota hyödynnetään haettaessa testidataa muun ohjelman käyttöön
     * @return palauttaa tyypin Entry olioita listalla
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Entry> getData() {
        // TODO tämä poistetaan myöhemmin
        return (ArrayList<Entry>) SampleData.getData(Entry.class);
    }
    
    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut merkinnät Entry-olioina
     * @return palauttaa Entry-olioita listalla
     */
    @Override
    public List<Entry> list() {
        return list(Entry.class);
    }
    
    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut annettuun projektiin kuuluvat merkinnät Entry-olioina
     * @param project Projekti, johon liittyviä merkintöjä halutaan listata
     * @return palauttaa Entry-olioita listalla
     */
    public List<Entry> list(Project project) {
        return list(Entry.class, project);
    }

}
