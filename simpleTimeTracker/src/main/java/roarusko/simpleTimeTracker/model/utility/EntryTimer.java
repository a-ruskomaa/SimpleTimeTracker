package roarusko.simpleTimeTracker.model.utility;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

import roarusko.simpleTimeTracker.model.ModelAccess;
import roarusko.simpleTimeTracker.model.domain.Entry;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;

/**
 * Merkinnän reaaliaikaiseen luomiseen tarkoitettu ajastin.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class EntryTimer {
    ModelAccess modelAccess;

//    private ObjectProperty<LocalDateTime> timerStart = new SimpleObjectProperty<LocalDateTime>();
//    private ObjectProperty<LocalDateTime> timerStop = new SimpleObjectProperty<LocalDateTime>();

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


    /**
     * Käynnistää ajastimen. Luo uudessa säikeessä ajettavan Timer-olion, joka päivittää
     * sekunnin välein käynnistyksestä kuluneen ajan elapsedTime-propertyyn. Luo uuden merkinnän,
     * jonka alkuajaksi asetetaan ajastimen käynnistyshetki.
     */
    public void start() {
        this.entry = modelAccess.newEntry();
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                Platform.runLater(() -> elapsedTime.set(elapsedTime.get() + 1));
            }
        }, 1000, 1000);

        this.running.set(true);
        this.entry.setStartDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }


    /**
     * Pysäyttää ajastimen. Tallentaa loppuajan luotuun merkintään.
     */
    public void stop() {
        timer.cancel();
        this.running.set(false);
        this.entry.setEndDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }


    /**
     * Nollaa ajastimen.
     */
    public void reset() {
        this.elapsedTime.set(0);
        this.running.set(false);
        this.entry = modelAccess.newEntry();
    }


    /**
     * Palauttaa ajastimeen liitetyn merkinnän
     * @return Palauttaa ajastimeen liitetyn merkinnän
     */
    public Entry getEntry() {
        return this.entry;
    }


    /**
     * Palauttaa onko ajastin käynnissä
     * @return Palauttaa onko ajastin käynnissä
     */
    public boolean isRunning() {
        return this.running.get();
    }


    /**
     * Palauttaa propertyn, joka sisältää tiedon onko ajastin käynnissä. Voidaan sitoa suoraan
     * käyttöliittymäkomponenttiin.
     * @return Palauttaa propertyn, joka sisältää tiedon onko ajastin käynnissä
     */
    public BooleanProperty runningProperty() {
        return this.running;
    }


    /**
     * Palauttaa ajastimen käynnistyksestä kuluneen   LongPropertyna.
     * @return Palauttaa ajastimen käynnistyksestä kuluneen   LongPropertyna.
     */
    public LongProperty elapsedTimeProperty() {
        return this.elapsedTime;
    }

}
