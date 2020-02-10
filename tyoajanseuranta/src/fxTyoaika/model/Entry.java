package fxTyoaika.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
 * 
 * TODO mieti kannattaisiko luoda EntryBeanWrapper, joka toimisi wrapperina entryn ympärillä. Säästäisi paljon resursseja kun joka entrystä ei tarvitsisi luoda propertyja, tapahtumankäsittelijöitä, jne
 *
 */
public class Entry implements Cloneable {
    private static final AtomicInteger idGenerator = new AtomicInteger(1000);

    private final int id;
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<LocalDate>();
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<LocalTime>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<LocalDate>();
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<LocalTime>();
    private final LongProperty duration = new SimpleLongProperty();

    /**
     * Luo "tyhjän" merkinnän. Tätä hyödynnetään mm. Timer-luokassa ja uusien merkintöjen luomisessa syötteestä.
     */
    public Entry() {
        this(-1, LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC),
                LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC));
    }


    /**
     * Luo uuden merkinnän annetulla alku- ja loppuajalla. Lisää merkinnälle seuraavan vapaan id:n.
     * @param start alkuaika LocalDateTime-muodossa
     * @param end loppuaika LocalDateTime-muodossa
     */
    public Entry(LocalDateTime start, LocalDateTime end) {
        this(idGenerator.getAndIncrement(), start.toLocalDate(), start.toLocalTime(), end.toLocalDate(),
                end.toLocalTime());
    }


    /**
     * Luo uuden merkinnän annetulla alku- ja loppuajalla
     * @param id id
     * @param start alkuaika LocalDateTime-muodossa
     * @param end loppuaika LocalDateTime-muodossa
     */
    public Entry(int id, LocalDateTime start, LocalDateTime end) {
        this(id, start.toLocalDate(), start.toLocalTime(), end.toLocalDate(),
                end.toLocalTime());
    }
    
    public Entry(LocalDate startDate, LocalTime startTime,
            LocalDate endDate, LocalTime endTime) {
        this(idGenerator.getAndIncrement(), startDate, startTime, endDate, endTime);
    }


    public Entry(int id, LocalDate startDate, LocalTime startTime,
            LocalDate endDate, LocalTime endTime) {
        this.id = id;
        this.startDate.set(startDate);
        this.startTime.set(startTime);
        this.endDate.set(endDate);
        this.endTime.set(endTime);
        
        // TODO muuta bindaukseksi

        updateDuration();

        this.startDate.addListener((o, oldValue, newValue) -> {
            updateDuration();
            System.out.println(
                    "StartDate changed: " + oldValue + " -> " + newValue);
        });

        this.startTime.addListener((o, oldValue, newValue) -> {
            updateDuration();
            System.out.println(
                    "startTime changed: " + oldValue + " -> " + newValue);
        });

        this.endDate.addListener((o, oldValue, newValue) -> {
            updateDuration();
            System.out.println(
                    "endDate changed: " + oldValue + " -> " + newValue);
        });

        this.endTime.addListener((o, oldValue, newValue) -> {
            updateDuration();
            System.out.println(
                    "endTime changed: " + oldValue + " -> " + newValue);
        });
    }

    // Getterit:


    public int getId() {
        return this.id;
    }


    /**
     * @return palauttaa merkinnän alkuajan
     */
    public LocalDate getStartDate() {
        return startDate.get();
    }


    /**
     * @return palauttaa merkinnän alkuajan
     */
    public LocalTime getStartTime() {
        return startTime.get();
    }


    /**
     * @return palauttaa merkinnän alkuajan
     */
    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(this.getStartDate(), this.getStartTime());
    }


    /**
     * @return palauttaa merkinnän loppuajan
     */
    public LocalDate getEndDate() {
        return endDate.get();
    }


    /**
     * @return palauttaa merkinnän loppuajan
     */
    public LocalTime getEndTime() {
        return endTime.get();
    }


    /**
     * @return palauttaa merkinnän alkuajan
     */
    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(this.getEndDate(), this.getEndTime());
    }


    /**
     * @return palauttaa keston sekunneissa
     */
    public Long getDuration() {
        return this.duration.get();
    }

    // Setterit:


    /**
     * @param date asettaa merkinnän alkuajan
     */
    public void setStartDate(LocalDate date) {
        this.startDate.set(date);
    }


    /**
     * @param time asettaa merkinnän alkuajan
     */
    public void setStartTime(LocalTime time) {
        this.startTime.set(time);
    }


    /**
     * @param time asettaa merkinnän alkuajan
     */
    public void setStartDateTime(LocalDateTime time) {
        this.startTime.set(time.toLocalTime());
        this.startDate.set(time.toLocalDate());
    }


    /**
     * @param date asettaa merkinnän loppuajan
     */
    public void setEndDate(LocalDate date) {
        this.endDate.set(date);
    }


    /**
     * @param time asettaa merkinnän loppuajan
     */
    public void setEndTime(LocalTime time) {
        this.endTime.set(time);
    }


    /**
     * @param time asettaa merkinnän alkuajan
     */
    public void setEndDateTime(LocalDateTime time) {
        this.endTime.set(time.toLocalTime());
        this.endDate.set(time.toLocalDate());
    }

    // Propertyjen getterit:


    /**
     * @return palauttaa alkumerkinnän propertyna
     */
    public ObjectProperty<LocalDate> startDateProperty() {
        return this.startDate;
    }


    /**
     * @return palauttaa alkumerkinnän propertyna
     */
    public ObjectProperty<LocalTime> startTimeProperty() {
        return this.startTime;
    }


    /**
     * @return palauttaa loppumerkinnän propertyna
     */
    public ObjectProperty<LocalDate> endDateProperty() {
        return this.endDate;
    }


    /**
     * @return palauttaa loppumerkinnän propertyna
     */
    public ObjectProperty<LocalTime> endTimeProperty() {
        return this.endTime;
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
        } else
            this.duration.set(0);
    }


    /**
     * @return palauttaa merkinnän keston sekunneissa
     */
    private Long calculateDuration() {
        LocalDateTime startDateTime = LocalDateTime.of(this.getStartDate(),
                this.getStartTime());
        LocalDateTime endDateTime = LocalDateTime.of(this.getEndDate(),
                this.getEndTime());
        return Duration.between(startDateTime, endDateTime).toSeconds();
    }


    /**
     * @return palauttaa merkinnän keston muotuiltuna merkkijonona muodossa "0h 00min"
     */
    public String getDurationAsString() {
        Long seconds = getDuration();
        return String.format("%dh %02dmin", seconds / 3600,
                (seconds % 3600) / 60);
    }


    @Override
    public String toString() {
        return startDate.get().format(Entries.getDateFormatter()) + " "
                + getDurationAsString();
    }


    /**
     * Luo kopion tästä merkinnästä samalla id:llä ja samalla alku- sekä loppuajalla kuin alkuperäinen merkintä.
     * Luo uudet propertyt alku- ja loppuajalle jotta vältetään tilanne jossa kaksi erillistä Entryn instanssia viittaavat
     * samoihin properteihin.
     */
    @Override
    public Entry clone() throws CloneNotSupportedException {

        Entry clonedEntry = new Entry(this.id,
                LocalDate.from(this.getStartDate()),
                LocalTime.from(this.getStartTime()),
                LocalDate.from(this.getEndDate()),
                LocalTime.from(this.getEndTime()));

        return clonedEntry;
    }

}
