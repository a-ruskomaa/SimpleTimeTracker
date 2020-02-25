package roarusko.simpleTimeTracker.model.utility;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import roarusko.simpleTimeTracker.model.domain.DataObject;

/**
 * Avustava luokka. Sisältää AtomicInteger-olion, joilla annetaan ulkoiseen tiedostoon
 * talletettaville tietueille yksiöivät tunnisteet juoksevalla numeroinnilla.
 * 
 * TODO lähtönumeron alustaminen automaattiseksi + konstruktoriin?
 * @author aleks
 * @version 17 Feb 2020
 *
 */
public class IdGenerator {
    private final AtomicInteger generator;
    
    
    
    /**
     * Luo oletusarvoisen IdGeneratorin, jonka aloitusarvo on 1000.
     */
    public IdGenerator() {
        generator = new AtomicInteger(1000);
    }
    
    
    /**
     * Luo IdGeneratorin, jonka ensimmäiseksi antama id vastaa annettua arvoa
     * @param value Ensimmäinen annettava id
     */
    public IdGenerator(int value) {
        generator = new AtomicInteger(value);
    }
    
    
    /**
     * Luo IdGeneratorin, joka asettaa aloitusarvokseen yhtä suuremman id:n kuin annetun
     * listan suurin id on
     * @param list Lista jonka alkioiden suurin id etsitään
     */
    public IdGenerator(List<? extends DataObject> list) {
        generator = new AtomicInteger(findLargestExistingId(list) + 1);
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
    
    
    /**
     * Etsii annetulta listalta suurimman käytössä olevan id-numeron
     * @param list Lista jolta etsitään
     * @return Palauttaa suurimman löydetyn id:n (tai 0 jos lista on tyhjä)
     */
    private int findLargestExistingId(List<? extends DataObject> list) {
        int largest = 0;
        
        for (DataObject object : list) {
            if (object.getId() > largest) largest = object.getId();
        }
        
        return largest;
    }

}
