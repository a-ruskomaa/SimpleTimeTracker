package fxTyoaika.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;


/**
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Timer {
    private ObjectProperty<Entry> entry = new SimpleObjectProperty<Entry>();
    private BooleanProperty running = new SimpleBooleanProperty(false);
    private LongProperty elapsedTime = new SimpleLongProperty();
    
    
    public Timer() {
    }
    
    public void start() {
        this.entry.get().setStartDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.running.set(true);
    }
    
    public void stop() {
        this.entry.get().setEndDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.running.set(false);
    }
    
    public void reset() {
        this.running.set(false);
        this.entry.set(new Entry());
    }
    
    public Entry getEntry() {
        return this.entry.get();
    }
    
    public ObjectProperty<Entry> entryProperty() {
        return this.entry;
    }
    
    public boolean isRunning() {
        return this.running.get();
    }
    
    public BooleanProperty runningProperty() {
        return this.running;
    }
    
    public long getTimeElapsed() {
        return Duration.between(entry.get().getStartTime(), LocalDateTime.now()).toSeconds();
    }
    
    

}
