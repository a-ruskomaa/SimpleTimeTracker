package fxTyoaika.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.Duration;

/**
 * Luokka projektimerkinnän tallentamiseksi. Sisältää tiedon merkinnän alku- ja loppuajasta. Tarjoaa metodit aikamerkintöjen muokkaamiseksi,
 * hakemiseksi sekä keston laskemiseksi. Tarjoaa metodit myös aikamerkintöjen muuttamiseksi muotoilluiksi merkkijonoiksi.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Entry {
    private static final AtomicInteger idGenerator = new AtomicInteger(1000);
            
    private int id;
    private ObjectProperty<LocalDateTime> startDateTime = new SimpleObjectProperty<LocalDateTime>();
    private ObjectProperty<LocalDateTime> endDateTime = new SimpleObjectProperty<LocalDateTime>();
    private LongProperty duration = new SimpleLongProperty();
   
    
    /**
     * Luo "tyhjän" merkinnän. Tätä hyödynnetään mm. Timer-luokassa ja uusien merkintöjen luomisessa syötteestä.
     */
    public Entry() {
        this(-1, LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC), LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC));
    }

    
    /**
     * Luo uuden merkinnän annetulla alku- ja loppuajalla. Lisää merkinnälle seuraavan vapaan id:n.
     * @param start alkuaika LocalDateTime-muodossa
     * @param end loppuaika LocalDateTime-muodossa
     */
    public Entry(LocalDateTime start, LocalDateTime end) {
        this(idGenerator.getAndIncrement(), start, end);
    }
    
    
    /**
     * Luo uuden merkinnän annetulla alku- ja loppuajalla
     * @param id id
     * @param start alkuaika LocalDateTime-muodossa
     * @param end loppuaika LocalDateTime-muodossa
     */
    public Entry(int id, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.startDateTime.set(start);
        this.endDateTime.set(end);
        
        updateDuration();
        
        this.startDateTime.addListener((e) -> {
            updateDuration();
        });
        
        this.endDateTime.addListener((e) -> {
            updateDuration();
        });
    }
    
    // Getterit:

    public int getId() {
        return this.id;
    }
    /**
     * @return palauttaa merkinnän alkuajan
     */
    public LocalDateTime getStartTime() {
        return startDateTime.get();
    }
    
    /**
     * @return palauttaa merkinnän loppuajan
     */
    public LocalDateTime getEndTime() {
        return endDateTime.get();
    }
    
    /**
     * @return palauttaa keston sekunneissa
     */
    public Long getDuration() {
        return this.duration.get();
    }
    

    /**
     * @return palauttaa merkinnän alkuajan päivämäärän
     */
    public LocalDate getDate() {
        return startDateTime.get().toLocalDate();
    }


    // Setterit:
    
    /**
     * @param startTime asettaa merkinnän alkuajan
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startDateTime.set(startTime);
    }
    
    
    /**
     * @param endTime asettaa merkinnän loppuajan
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endDateTime.set(endTime);
    }
    
    
    // Propertyjen getterit:
    
    /**
     * @return palauttaa alkumerkinnän propertyna
     */
    public ObjectProperty<LocalDateTime> startTimeProperty() {
        return this.startDateTime;
    }
    
    /**
     * @return palauttaa loppumerkinnän propertyna
     */
    public ObjectProperty<LocalDateTime> endTimeProperty() {
        return this.endDateTime;
    }
    
    /**
     * @return palauttaa keston propertyna
     */
    public LongProperty durationProperty() {
        return this.duration;
    }

    // Apumetodit:
    
    /**
     * Laskee keston (jos mahdollista) ja päivittää kentän
     */
    private void updateDuration() {
        if (getStartTime() != null && getEndTime() != null) {
            Long d = calculateDuration();
            this.duration.set(d);
        } else this.duration.set(0);
    }
    
    /**
     * @return palauttaa merkinnän keston sekunneissa
     */
    public Long calculateDuration() {
        return Duration.between(startDateTime.get(), endDateTime.get()).toSeconds();
    }
    
    /**
     * @return palauttaa merkinnän keston muotuiltuna merkkijonona muodossa "0h 00min"
     */
    public String getDurationAsString() {
        Long seconds = getDuration();
        return String.format("%dh %02dmin", seconds / 3600, (seconds % 3600) / 60);
    }
    

    @Override
    public String toString() {
        return endDateTime.get().format(Entries.getDateFormatter()) + " " + getDurationAsString();
    }

}
