package roarusko.simpleTimeTracker.model.utility;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Id-tunnusten luomiseen käytettävä avustava luokka.
 * 
 * Sisältää AtomicInteger-olion, jolla luodaan juoksevaan numerointiin
 * perustuvat yksiöivät tunnisteet.
 * 
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class IdGenerator {
    private final AtomicInteger generator;
    
    
    
//    /**
//     * Luo oletusarvoisen IdGeneratorin, jonka aloitusarvo on 1000.
//     */
//    public IdGenerator() {
//        generator = new AtomicInteger(1000);
//    }
    
    
    /**
     * Luo IdGeneratorin, jonka ensimmäiseksi antama id vastaa annettua arvoa
     * @param value Ensimmäinen annettava id
     */
    public IdGenerator(int value) {
        generator = new AtomicInteger(value);
    }
    
    
    
    /**
     * Palauttaa seuraavan vapaana olevan id-tunnisteen. Ennen ensimmäistä kutsua on huolehdittava siitä,
     * että ensimmäinen tarjottava luku on suurempi kuin suurin tiedostoon tallennettu luku
     * @return Palauttaa seuraavan vapaan id-tunnisteen
     */
    public int getNewId() {
        return generator.getAndIncrement();
    }
    
    
    
    /**
     * Asettaa juoksevan numeroinnin lähtöarvon. Asetettavan arvon on oltava suurempi kuin suurin tämänhetkinen käytössä oleva numero.
     * @param newValue Uusi lähtöarvo
     */
    public void setIdStartValue(int newValue) {
        generator.set(newValue);
    }
    
    


}
