package fxTyoaika.model;

/**
 * DataObject-rajapinnan aliluokka. Käytetään luokille, jotka voivat loogisessa hierarkiassa "omistaa"
 * toisen luokan edustajia. Esim. käyttäjä omistaa projekteja ja projekti omistaa merkintöjä.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public interface ParentObject extends DataObject{

    /**
     * @return Palauttaa tietueen nimen
     */
    public String getName();
}
