package roarusko.simpleTimeTracker.model.data;

import java.util.List;

import roarusko.simpleTimeTracker.model.domain.ParentObject;

/**
 * Merkintöjä käsitteleviä tiedonkäsittelyoperaatioita toteuttavan luokan rajapinta. Perii DAO:n 
 * metodit read(), update() sekä delete(). Tarjoaa oman toteutuksen create-metodille sekä
 * ylikuormitetun toteutukset list-metodeille.
 * @author aleks
 * @version 17 Feb 2020
 * @param <K> avainta edustava tietotyyppi, esim. Integer
 * @param <DataObject> luokkaa edustava tietotyyppi
 * @param <T> luokan "vanhempaa" edustava tietotyyppi
 *
 */
public interface ChildDAO<K, DataObject, T extends ParentObject> extends DAO<K, DataObject> {

    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut annettuun projektiin kuuluvat merkinnät Entry-olioina
     * @param object asd
     * @return palauttaa Entry-olioita listalla
     */
    public List<DataObject> list(T object);

}
