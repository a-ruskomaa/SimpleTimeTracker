package fxTyoaika.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Avustava luokka. Sisältää staattiset AtomicInteger-oliot, joilla annetaan ulkoiseen tiedostoon
 * talletettaville tietueille yksiöivät tunnisteet juoksevalla numeroinnilla.
 * 
 * TODO lähtönumeron alustaminen automaattiseksi + konstruktoriin?
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class IdGenerator {
    private static final AtomicInteger userIdGenerator = new AtomicInteger(1000);
    private static final AtomicInteger projectIdGenerator = new AtomicInteger(1000);
    private static final AtomicInteger entryIdGenerator = new AtomicInteger(1000);
    
    private IdGenerator() {
    }
    
    
    /**
     * Palauttaa seuraavan vapaana olevan id-tunnisteen uudelle käyttäjälle. Ennen ensimmäistä kutsua on huolehdittava siitä,
     * että ensimmäinen tarjottava luku on suurempi kuin suurin tiedostoon tallennettu luku
     * @return Palauttaa seuraavan vapaan id-tunnisteen
     */
    public static int getNewUserId() {
        return userIdGenerator.getAndIncrement();
    }
    
    
    /**
     * Palauttaa seuraavan vapaana olevan id-tunnisteen uudelle projektille. Ennen ensimmäistä kutsua on huolehdittava siitä,
     * että ensimmäinen tarjottava luku on suurempi kuin suurin tiedostoon tallennettu luku
     * @return Palauttaa seuraavan vapaan id-tunnisteen
     */
    public static int getNewProjectId() {
        return projectIdGenerator.getAndIncrement();
    }
    
    
    /**
     * Palauttaa seuraavan vapaana olevan id-tunnisteen uudelle merkinnälle. Ennen ensimmäistä kutsua on huolehdittava siitä,
     * että ensimmäinen tarjottava luku on suurempi kuin suurin tiedostoon tallennettu luku
     * @return Palauttaa seuraavan vapaan id-tunnisteen
     */
    public static int getNewEntryId() {
        return entryIdGenerator.getAndIncrement();
    }
    
    
    /**
     * Asettaa juoksevan numeroinnin lähtöarvon. Asetettavan arvon on oltava suurempi kuin suurin tämänhetkinen käytössä oleva numero.
     * @param newValue Uusi lähtöarvo
     */
    public static void setUserIdStartValue(int newValue) {
        userIdGenerator.set(newValue);
    }
    
    
    /**
     * Asettaa juoksevan numeroinnin lähtöarvon. Asetettavan arvon on oltava suurempi kuin suurin tämänhetkinen käytössä oleva numero.
     * @param newValue Uusi lähtöarvo
     */
    public static void setProjectIdStartValue(int newValue) {
        userIdGenerator.set(newValue);
    }
    
    
    /**
     * Asettaa juoksevan numeroinnin lähtöarvon. Asetettavan arvon on oltava suurempi kuin suurin tämänhetkinen käytössä oleva numero.
     * @param newValue Uusi lähtöarvo
     */
    public static void setEntryIdStartValue(int newValue) {
        userIdGenerator.set(newValue);
    }

}
