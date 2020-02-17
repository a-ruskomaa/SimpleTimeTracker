package fxTyoaika.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import java.time.Duration;

/**
 * Luokka projektimerkinnän tallentamiseksi. Sisältää tiedon merkinnän alku- ja loppuajasta. Tarjoaa metodit aikamerkintöjen muokkaamiseksi,
 * hakemiseksi sekä keston laskemiseksi. Tarjoaa metodit myös aikamerkintöjen muuttamiseksi muotoilluiksi merkkijonoiksi.
 * @author aleks
 * @version 28 Jan 2020
 *
 */
public class Entry implements ChildObject {

    private final int id;
    private Project project;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;


    /**
     * Luo "tyhjän" merkinnän. Tätä hyödynnetään mm. Timer-luokassa ja uusien merkintöjen luomisessa syötteestä.
     * @param project Projekti, johon merkintä liittyy
     */
    public Entry(Project project) {
        this(-1, project, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    
    /**
     * Luo uuden merkinnän annetulla alku- ja loppuajalla
     * Rajaa minuuttia tarkemmat aikamääreet pois tallennettavasta merkinnästä.
     * @param id Yksilöivä id-tunniste
     * @param project Projekti, johon merkintä liittyy
     * @param start Merkinnän alkuhetki LocalDateTime-muodossa
     * @param end Merkinnän loppuhetki LocalDateTime-muodossa
     */
    public Entry(int id, Project project, LocalDateTime start, LocalDateTime end) {
        this(id, project, start.toLocalDate(), start.toLocalTime(), end.toLocalDate(),
                end.toLocalTime());
    }

    
    /**
     * Luo uuden merkinnän annetulla alku- ja loppuajalla.
     * Rajaa minuuttia tarkemmat aikamääreet pois tallennettavasta merkinnästä.
     * @param id Yksilöivä id-tunniste
     * @param project Projekti, johon merkintä liittyy
     * @param startDate Merkinnän alkuhetken päivämäärä
     * @param startTime Merkinnän alkuhetken kellonaika
     * @param endDate Merkinnän loppuhetken päivämäärä
     * @param endTime Merkinnän loppuhetken kellonaika
     */
    public Entry(int id, Project project, LocalDate startDate, LocalTime startTime,
            LocalDate endDate, LocalTime endTime) {
        this.id = id;
        this.project = project;
        this.startDate = startDate;
        this.startTime = startTime.truncatedTo(ChronoUnit.MINUTES);
        this.endDate = endDate;
        this.endTime = endTime.truncatedTo(ChronoUnit.MINUTES);
    }

    // Getterit:


    @Override
    public int getId() {
        return this.id;
    }


    /**
     * @return Palauttaa projektin, johon merkintä liittyy
     */
    @Override
    public Project getOwner() {
        return project;
    }



    /**
     * @return Palauttaa merkinnän alkuhetken päivämäärän
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }


    /**
     * @return palauttaa merkinnän alkuhetken kellonajan
     */
    public LocalTime getStartTime() {
        return this.startTime;
    }


    /**
     * @return palauttaa merkinnän alkuajan
     */
    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(this.getStartDate(), this.getStartTime());
    }


    /**
     * @return palauttaa merkinnän loppuhetken päivämäärän
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }


    /**
     * @return palauttaa merkinnän loppuhetken kellonajan
     */
    public LocalTime getEndTime() {
        return this.endTime;
    }


    /**
     * @return palauttaa merkinnän loppuuajan
     */
    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(this.getEndDate(), this.getEndTime());
    }


    /**
     * @return palauttaa keston sekunneissa
     */
    public Long getDuration() {
        {
            try {
            LocalDateTime startDateTime = LocalDateTime.of(this.getStartDate(),
                    this.getStartTime());
            LocalDateTime endDateTime = LocalDateTime.of(this.getEndDate(),
                    this.getEndTime());
            return Duration.between(startDateTime, endDateTime).toSeconds();
            } catch (NullPointerException e) {
                return 0L;
            }
        }
    }

    // Setterit:


    /**
     * @param date asettaa merkinnän alkuajan
     */
    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }


    /**
     * @param time asettaa merkinnän alkuajan
     */
    public void setStartTime(LocalTime time) {
        this.startTime = time;
    }


    /**
     * @param time asettaa merkinnän alkuajan
     */
    public void setStartDateTime(LocalDateTime time) {
        this.startTime = time.toLocalTime();
        this.startDate = time.toLocalDate();
    }


    /**
     * @param date asettaa merkinnän loppuajan
     */
    public void setEndDate(LocalDate date) {
        this.endDate = date;
    }


    /**
     * @param time asettaa merkinnän loppuajan
     */
    public void setEndTime(LocalTime time) {
        this.endTime = time;
    }


    /**
     * @param time asettaa merkinnän alkuajan
     */
    public void setEndDateTime(LocalDateTime time) {
        this.endTime = time.toLocalTime();
        this.endDate = time.toLocalDate();
    }


    // Apumetodit:


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
        return startDate.format(Entries.getDateFormatter()) + " "
                + getDurationAsString();
    }


//    /**
//     * Luo kopion tästä merkinnästä samalla id:llä ja samalla alku- sekä loppuajalla kuin alkuperäinen merkintä.
//     * Luo uudet propertyt alku- ja loppuajalle jotta vältetään tilanne jossa kaksi erillistä Entryn instanssia viittaavat
//     * samoihin properteihin.
//     */
//    @Override
//    public Entry clone() throws CloneNotSupportedException {
//
//        Entry clonedEntry = new Entry(this.id,
//                LocalDate.from(this.getStartDate()),
//                LocalTime.from(this.getStartTime()),
//                LocalDate.from(this.getEndDate()),
//                LocalTime.from(this.getEndTime()));
//
//        return clonedEntry;
//    }

}
