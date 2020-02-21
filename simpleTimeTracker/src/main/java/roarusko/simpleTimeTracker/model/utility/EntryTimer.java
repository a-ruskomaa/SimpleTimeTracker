package roarusko.simpleTimeTracker.model.utility;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

import roarusko.simpleTimeTracker.model.ModelAccess;
import roarusko.simpleTimeTracker.model.domainModel.Entry;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

/**
 * Merkinnän reaaliaikaiseen luomiseen tarkoitettu ajastin.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class EntryTimer {
    ModelAccess modelAccess;

    private ObjectProperty<LocalDateTime> timerStart = new SimpleObjectProperty<LocalDateTime>();
    private ObjectProperty<LocalDateTime> timerStop = new SimpleObjectProperty<LocalDateTime>();

    private BooleanProperty running = new SimpleBooleanProperty(false);
    private LongProperty elapsedTime = new SimpleLongProperty();
    private Entry entry;

    private Timer timer;

    /**
     * 
     * @param modelAccess modelAccess
     */
    public EntryTimer(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
    }


    public void start() {
        
        this.entry = modelAccess.newEntry();
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                Platform.runLater(() -> elapsedTime.set(elapsedTime.get() + 1));
            }
        }, 1000, 1000);

        this.timerStart
                .set(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.running.set(true);
        this.entry.setStartDateTime(timerStart.get());
    }


    public void stop() {
        timer.cancel();
        this.timerStop.set(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        this.running.set(false);
        this.entry.setEndDateTime(timerStop.get());
    }


    public void reset() {
        this.elapsedTime.set(0);
        this.running.set(false);
        this.entry = modelAccess.newEntry();
    }


    public Entry getEntry() {
        return this.entry;
    }


    public boolean isRunning() {
        return this.running.get();
    }


    public BooleanProperty runningProperty() {
        return this.running;
    }


    public LongProperty elapsedTimeProperty() {
        return this.elapsedTime;
    }


    public long getTimeElapsed() {
        return Duration.between(entry.getStartTime(), LocalDateTime.now())
                .toSeconds();
    }

}
