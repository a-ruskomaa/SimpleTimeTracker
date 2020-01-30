package fxTyoaika.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.Duration;

/**
 * Luokka projektimerkinnän tallentamiseksi. Sisältää tiedon merkinnän alku- ja loppuajasta. Tarjoaa metodit aikamerkintöjen muokkaamiseksi,
 * hakemiseksi sekä keston laskemiseksi. Tarjoaa metodit myös aikamerkintöjen muuttamiseksi muotoilluiksi merkkijonoiksi.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Entry {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
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
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    /**
     * Luo uuden merkinnän annetulla alku- ja loppuajalla. Kelpuuttaa parametreinaan merkkijonon muodossa "dd.MM.yyy HH:mm:ss".
     * @param startTime alkuaika merkkijonomuodossa
     * @param endTime loppuaika merkkijonomuodossa
     */
    public Entry(String startTime, String endTime) {
        this.startTime = LocalDateTime.parse(startTime, dateTimeFormatter);
        this.endTime = LocalDateTime.parse(endTime, dateTimeFormatter);
    }

    /**
     * @return palauttaa merkinnän alkuajan
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime asettaa merkinnän alkuajan
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return palauttaa merkinnän loppuajan
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    /**
     * @param endTime asettaa merkinnän loppuajan
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @return palauttaa merkinnän loppuajan päivämäärän
     */
    public LocalDate getEndDate() {
        return null;
    }

    /**
     * @return palauttaa merkinnän keston sekunneissa
     */
    public long getDurationInSeconds() {
        return Duration.between(startTime, endTime).toSeconds();
    }
    
    
    /**
     * @return palauttaa merkinnän keston muotuiltuna merkkijonona muodossa "0h 00min"
     */
    public String getDurationAsString() {
        Long seconds = getDurationInSeconds();
        return String.format("%dh %02dmin", seconds / 3600, (seconds % 3600) / 60);
    }

    /**
     * @return palauttaa alkuajan muotoiltuna merkkijonona muodossa "dd.MM.yyyy hh:mm:ss"
     */
    public String getStartTimeAsString() {
        return startTime.format(dateTimeFormatter);
    }
    
    /**
     * @return palauttaa loppuajan muotoiltuna merkkijonona muodossa "dd.MM.yyyy hh:mm:ss"
     */
    public String getEndTimeAsString() {
        return endTime.format(dateTimeFormatter);
    }
    
    @Override
    public String toString() {
        return endTime.format(dateFormatter) + " " + getDurationAsString();
    }

}
