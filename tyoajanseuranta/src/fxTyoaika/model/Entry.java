package fxTyoaika.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.Duration;

/**
 * Luokka projektimerkinnän tallentamiseksi. Sisältää tiedon merkinnän alku- ja loppuajasta. Tarjoaa metodit aikamerkintöjen muokkaamiseksi,
 * hakemiseksi sekä keston laskemiseksi. Tarjoaa metodit myös aikamerkintöjen muuttamiseksi muotoilluiksi merkkijonoiksi.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Entry {
    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<LocalDateTime>(); 
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<LocalDateTime>(); 
    private LongProperty duration = new SimpleLongProperty();
//    private LocalDateTime startTime;
//    private LocalDateTime endTime;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    
    /**
     * Luo "tyhjän" merkinnän. Tätä hyödynnetään Timer-luokassa.
     */
    public Entry() {
    }
    
    /**
     * Luo uuden merkinnän annetulla alku- ja loppuajalla
     * @param startTime alkuaika LocalDateTime-muodossa
     * @param endTime loppuaika LocalDateTime-muodossa
     */
    public Entry(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime.set(startTime);
        this.endTime.set(endTime);
        this.duration.set(calculateDuration());
    }
    
    /**
     * Luo uuden merkinnän annetulla alku- ja loppuajalla. Kelpuuttaa parametreinaan merkkijonon muodossa "dd.MM.yyy HH:mm:ss".
     * @param startTime alkuaika merkkijonomuodossa
     * @param endTime loppuaika merkkijonomuodossa
     */
    public Entry(String startTime, String endTime) {
        this.startTime.set(LocalDateTime.parse(startTime, dateTimeFormatter));
        this.endTime.set(LocalDateTime.parse(endTime, dateTimeFormatter));
        this.duration.set(calculateDuration());
    }

    /**
     * @return palauttaa merkinnän alkuajan
     */
    public LocalDateTime getStartTime() {
        return startTime.get();
    }

    /**
     * @param startTime asettaa merkinnän alkuajan
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime.set(startTime);
    }
    
    /**
     * @return palauttaa merkinnän propertyna
     */
    public ObjectProperty<LocalDateTime> startTimeProperty() {
        return this.startTime;
    }

    /**
     * @return palauttaa merkinnän loppuajan
     */
    public LocalDateTime getEndTime() {
        return endTime.get();
    }
    
    /**
     * @param endTime asettaa merkinnän loppuajan
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime.set(endTime);
    }
    
    /**
     * @return palauttaa merkinnän propertyna
     */
    public ObjectProperty<LocalDateTime> endTimeProperty() {
        return this.endTime;
    }
    
    /**
     * @return palauttaa keston sekunneissa
     */
    public Long getDuration() {
        return this.duration.get();
    }

    /**
     * @param duration asetettava kesto
     */
    public void setDuration(Long duration) {
        this.duration.set(duration);
    }
    
    /**
     * @return palauttaa keston propertyna
     */
    public LongProperty durationProperty() {
        return this.duration;
    }

    /**
     * @return palauttaa merkinnän alkuajan päivämäärän
     */
    public LocalDate getDate() {
        return startTime.get().toLocalDate();
    }

    /**
     * @return palauttaa merkinnän keston sekunneissa
     */
    public Long calculateDuration() {
        return Duration.between(startTime.get(), endTime.get()).toSeconds();
    }
    
    
    /**
     * @return palauttaa merkinnän keston muotuiltuna merkkijonona muodossa "0h 00min"
     */
    public String getDurationAsString() {
        Long seconds = getDuration();
        return String.format("%dh %02dmin", seconds / 3600, (seconds % 3600) / 60);
    }

    /**
     * @return palauttaa alkuajan muotoiltuna merkkijonona muodossa "dd.MM.yyyy hh:mm:ss"
     */
    public String getStartTimeAsString() {
        return startTime.get().format(dateTimeFormatter);
    }
    
    /**
     * @return palauttaa loppuajan muotoiltuna merkkijonona muodossa "dd.MM.yyyy hh:mm:ss"
     */
    public String getEndTimeAsString() {
        return endTime.get().format(dateTimeFormatter);
    }
    
    @Override
    public String toString() {
        return endTime.get().format(dateFormatter) + " " + getDurationAsString();
    }

}
