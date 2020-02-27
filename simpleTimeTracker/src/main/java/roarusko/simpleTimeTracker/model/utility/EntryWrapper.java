package roarusko.simpleTimeTracker.model.utility;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import roarusko.simpleTimeTracker.model.domain.Entry;

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


    public ObjectProperty<LocalDateTime> entryStartProperty() {
        return this.starDateTime;
    }


    public ObjectProperty<LocalDateTime> entryEndProperty() {
        return this.endDateTime;
    }


    /**
     * Ei käytössä
     */
    @Override
    public Object getBean() {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * Ei käytössä
     */
    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
