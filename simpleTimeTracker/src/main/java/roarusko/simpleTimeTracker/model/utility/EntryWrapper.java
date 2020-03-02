package roarusko.simpleTimeTracker.model.utility;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import roarusko.simpleTimeTracker.model.domain.Entry;


/**
 * Luokka, jonka avulla merkinnän sisältämät päivämäärät ja kellonajat voidaan sitoa suoraan
 * käyttöliittymäkomponentteihin muuttamatta Entry-luokan sisäistä toteutusta. Toistaiseksi
 * ei käytössä.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class EntryWrapper extends ObjectPropertyBase<Entry> {

    // private static final Object DEFAULT_BEAN = null;
    // private static final String DEFAULT_NAME = "";
    //
    // private final Object bean;
    // private final String name;

    // private final ObjectProperty<Entry> entry = new
    // SimpleObjectProperty<Entry>();
    private final ObjectProperty<LocalDateTime> starDateTime = new SimpleObjectProperty<LocalDateTime>();
    private final ObjectProperty<LocalDateTime> endDateTime = new SimpleObjectProperty<LocalDateTime>();
    private ChangeListener<LocalDateTime> startDateTimeListener;
    private ChangeListener<LocalDateTime> endDateTimeListener;

    
    /**
     * Luo uuden entrywrapperin. Lisää kuuntelijat reagoimaan merkinnän vaihtumiseen tai sen tietojen muokkaamiseen.
     */
    public EntryWrapper() {
        addListeners();
    }


    @Override
    public void set(Entry entry) {
//        this.starDateTime.removeListener(startDateTimeListener);
//        this.endDateTime.removeListener(endDateTimeListener);
        
        
//        Entry old = super.get();
//        
//        if (entry.equals(old)) {
//            if (!entry.getStartDateTime().isEqual(old.getStartDateTime()) ||
//                    !entry.getEndDateTime().isEqual(old.getEndDateTime())) {
//                fireValueChangedEvent();
//            }
//        }
        
        super.set(entry);
        
        this.starDateTime.set(entry.getStartDateTime());
        this.endDateTime.set(entry.getEndDateTime());
        
//        this.starDateTime.addListener(startDateTimeListener);
//        this.endDateTime.addListener(endDateTimeListener);
    }


    @Override
    public Entry get() {
        return super.get();
    }


    private void addListeners() {
        
        startDateTimeListener = ((obs, oldV, newV) -> {
            if (oldV != null && !oldV.isEqual(newV)) {
                fireValueChangedEvent();
            }
//            super.get().setStartDateTime(newV);
            System.out.println("start time changed!");
        });

        endDateTimeListener = ((obs, oldV, newV) -> {
            if (oldV != null && !oldV.isEqual(newV))
            fireValueChangedEvent();
//            super.get().setEndDateTime(newV);
            System.out.println("end time changed!");
        });
        
        this.starDateTime.addListener(startDateTimeListener);
        this.endDateTime.addListener(endDateTimeListener);
    }


    /**
     * Merkinnän alkuajankohdan property
     * @return Palauttaa merkinnän alkuajankohdan propertyn
     */
    public ObjectProperty<LocalDateTime> entryStartProperty() {
        return this.starDateTime;
    }

    /**
     * Merkinnän loppuajankohdan property
     * @return Palauttaa merkinnän loppuajankohdan propertyn
     */
    public ObjectProperty<LocalDateTime> entryEndProperty() {
        return this.endDateTime;
    }


    /**
     * Ei käytössä
     */
    @Override
    public Object getBean() {
        return null;
    }


    /**
     * Ei käytössä
     */
    @Override
    public String getName() {
        return null;
    }

}
