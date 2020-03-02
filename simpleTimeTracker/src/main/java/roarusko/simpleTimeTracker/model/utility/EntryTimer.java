package roarusko.simpleTimeTracker.model.utility;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

import roarusko.simpleTimeTracker.model.data.DataAccess;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Merkinnän reaaliaikaiseen luomiseen tarkoitettu ajastin. Käynnistää uudessa säikeessä ajastimen,
 * joka päivittää sekunnin välein elapsedTime-propertya. Ei osallistu uuden merkinnän luomiseen, mutta
 * tarjoaa propertyt joihin käyttöliittymäkomponentit voidaan sitoa ja joista luotavan merkinnän alku- 
 * ja loppuajankohta voidaan selvittää.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class EntryTimer {
    DataAccess dataAccess;

    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<LocalDateTime>();
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<LocalDateTime>();

    private BooleanProperty running = new SimpleBooleanProperty(false);
    private LongProperty elapsedTime = new SimpleLongProperty();

    private Timer timer;

    /**
     * 
     * @param dataAccess dataAccess
     */
    public EntryTimer(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }


    /**
     * Käynnistää ajastimen ja tallentaa alkuajankohdan startTime-propertyyn.
     * Luo uudessa säikeessä ajettavan Timer-olion, joka päivittää
     * sekunnin välein käynnistyksestä kuluneen ajan elapsedTime-propertyyn.
     */
    public void start() {
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                Platform.runLater(() -> elapsedTime.set(elapsedTime.get() + 1));
            }
        }, 1000, 1000);

        this.running.set(true);
        this.startTime.set(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }


    /**
     * Pysäyttää ajastimen. Tallentaa loppuajan endTime-propertyyn.
     */
    public void stop() {
        timer.cancel();
        this.running.set(false);
        this.endTime.set(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }


    /**
     * Nollaa ajastimen.
     */
    public void reset() {
        this.elapsedTime.set(0);
        this.running.set(false);
        this.startTime.set(null);
        this.endTime.set(null);
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
     * Palauttaa ajastimen käynnistyksestä kuluneen ajan LongPropertyna.
     * @return Palauttaa ajastimen käynnistyksestä kuluneen   LongPropertyna.
     */
    public LongProperty elapsedTimeProperty() {
        return this.elapsedTime;
    }
    
    
    /**
     * Palauttaa ajastimen käynnistyshetken LocalDateTime-objektin käärivänä propertyna
     * @return Palauttaa ajastimen käynnistyshetken LocalDateTime-objektin käärivänä propertyna
     */
    public ObjectProperty<LocalDateTime> startTimeProperty() {
        return this.startTime;
    }
    
    
    /**
     * Palauttaa ajastimen käynnistyshetken LocalDateTime-objektin käärivänä propertyna
     * @return Palauttaa ajastimen käynnistyshetken LocalDateTime-objektin käärivänä propertyna
     */
    public ObjectProperty<LocalDateTime> endTimeProperty() {
        return this.endTime;
    }

}
