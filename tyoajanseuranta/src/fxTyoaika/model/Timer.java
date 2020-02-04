package fxTyoaika.model;

import java.time.Duration;
import java.time.LocalDateTime;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;


/**
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Timer {
    private ObjectProperty<Entry> entry = new SimpleObjectProperty<Entry>();
    private BooleanProperty running = new SimpleBooleanProperty(false);
    
    
    public Timer() {
    }
    
    public void start() {
        this.entry.get().setStartTime(LocalDateTime.now());
        this.running.set(true);
    }
    
    public void stop() {
        this.entry.get().setEndTime(LocalDateTime.now());
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
