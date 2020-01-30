package fxTyoaika.model;

import java.time.Duration;
import java.time.LocalDateTime;


/**
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Timer {
    private Entry entry;
    private boolean running;
    
    
    public Timer() {
        // TODO Auto-generated constructor stub
        this.entry = new Entry();
    }
    
    public void start() {
        this.entry.setStartTime(LocalDateTime.now());
        this.running= true;
    }
    
    public void stop() {
        this.entry.setEndTime(LocalDateTime.now());
        this.running = false;
    }
    
    public Entry getEntry() {
        return this.entry;
    }
    
    public boolean isRunning() {
        return this.running;
    }
    
    public long getDurationInSeconds() {
        return Duration.between(entry.getStartTime(), LocalDateTime.now()).toSeconds();
    }
    
    public void reset() {
        this.running = false;
        this.entry = new Entry();
    }
    
    

}
