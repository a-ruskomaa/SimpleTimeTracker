package fxTyoaika.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import fxTyoaika.controller.ModelAccess;
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
    ModelAccess modelAccess;
    private ObjectProperty<Entry> entry = new SimpleObjectProperty<Entry>();
    private ObjectProperty<LocalDateTime> timerStart = new SimpleObjectProperty<LocalDateTime>();
    private ObjectProperty<LocalDateTime> timerStop = new SimpleObjectProperty<LocalDateTime>();
    private BooleanProperty running = new SimpleBooleanProperty(false);
    private LongProperty elapsedTime = new SimpleLongProperty();
    
    
    public Timer(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
        this.entry.set(new Entry(this.modelAccess.getSelectedProject()));
    }
    
    public void start() {
        this.timerStart.set(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.running.set(true);
        this.entry.get().setStartDateTime(timerStart.get());
    }
    
    public void stop() {
        this.timerStop.set(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.running.set(false);
        this.entry.get().setEndDateTime(timerStop.get());
    }
    
    public void reset() {
        this.running.set(false);
        this.entry.set(new Entry(this.modelAccess.getSelectedProject()));
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
