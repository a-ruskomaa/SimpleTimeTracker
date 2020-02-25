package roarusko.simpleTimeTracker.model.domain;

/**
 * DataObject-rajapinnan aliluokka. Käytetään luokille, jotka voivat loogisessa hierarkiassa "kuulua"
 * toiselle luokalle. Esim. Projekti kuuluu käyttäjälle ja merkintä kuuluu projektiin.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public interface ChildObject extends DataObject {
    
    /**
     * Metodilla haetaan kyseisen olion "omistaja"
     * @return Palauttaa omistajan
     */
    public DataObject getOwner();
}
