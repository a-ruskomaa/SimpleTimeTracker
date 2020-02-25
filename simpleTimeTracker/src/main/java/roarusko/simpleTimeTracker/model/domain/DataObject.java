package roarusko.simpleTimeTracker.model.domain;

/**
 * Rajapinta, jonka toteuttavat oliot mallintavat tietokantaan / ulkoiseen tiedostoon
 * tallennettua dataa.
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public interface DataObject {
    
/**
 * Palauttaa alkion yksilöivän id-tunnisteen
 * @return Palauttaa alkion yksilöivän id-tunnisteen
 */
public int getId();

/**
 * Asettaa alkiolle uuden yksilöivän tunnisteen. Toimii vain
 * jos edellinen id on -1.
 * @param id Uusi id
 */
public void setId(int id);
}

