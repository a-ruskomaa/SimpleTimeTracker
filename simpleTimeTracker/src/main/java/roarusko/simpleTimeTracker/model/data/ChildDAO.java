package roarusko.simpleTimeTracker.model.data;

import java.util.List;


/**
 * Merkintöjä käsitteleviä tiedonkäsittelyoperaatioita toteuttavan luokan rajapinta. Perii DAO:n 
 * metodit read(), update() sekä delete(). Tarjoaa oman toteutuksen create-metodille sekä
 * ylikuormitetun toteutukset list-metodeille.
 * @author aleks
 * @version 17 Feb 2020
 * @param <K> avainta edustava tietotyyppi, esim. Integer
 * @param <T> luokkaa edustava tietotyyppi
 * @param <P> luokan "vanhempaa" edustava tietotyyppi
 *
 */
public interface ChildDAO<K, T, P> extends DAO<K, T> {

    /**
     * Palauttaa kaikki pysyvään muistiin tallennetut annettuun projektiin kuuluvat merkinnät Entry-olioina
     * @param object asd
     * @return palauttaa Entry-olioita listalla
     */
    public List<T> list(P object);

}
