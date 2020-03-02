package roarusko.simpleTimeTracker.model.utility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Luokka sisältää staattisia apumetodeita merkintöjen, kellonaikojen ja päivämäärien käsittelyyn. Ei käsittele mahdollisia
 * poikkeuksia. Luokan metodien kutsujien vastuulla on varmistua siitä, että käsiteltävä data on vaaditussa formaatissa!
 * 
 * Sisältää tällä hetkellä tarpeettomiakin metodeja. Näitä karsitaan ohjelman vaatimusten tarkentuessa.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class Entries {
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
//    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
//    private final String dateTimePattern = "([0][1-9]|[1-2][0-9]|[3][01]).(0[1-9]|1[012]).[0-9]{4}([01][0-9]|2[0-3]):[0-5][0-9]";
//    private final String datePattern = "([0][1-9]|[1-2][0-9]|[3][01]).(0[1-9]|1[012]).[0-9]{4}";
//    private final String timePatternSeconds = "([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
//    private final String timePattern = "([01][0-9]|2[0-3]):[0-5][0-9]";
    
    /**
     * @return Palauttaa päivämäärän muotoiluehdon
     */
    public static DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }

    /**
     * @return Palauttaa päivämäärän ja kellonajan muotoiluehdon
     */
    public static DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    /**
     * @return Palauttaa kellonajan muotoiluehdon
     */
    public static  DateTimeFormatter getTimeFormatter() {
        return timeFormatter;
    }
    
    // temporal -> string
    
    /**
     * Palauttaa annetun LocalDateTime-olion sisältämän päivämäärän merkkijonona
     * @param datetime Merkkijonoksi muutettava päivämäärä
     * @return Palauttaa päivämäärän muunnettuna asetetun formaatin mukaiseksi merkkijonoksi
     */
    public static String getDateAsString(LocalDateTime datetime) {
        return datetime.toLocalDate().format(dateFormatter);
    }
    
    /**
     * Palauttaa annetun LocalDate-olion sisältämän päivämäärän merkkijonona
     * @param date Merkkijonoksi muutettava päivämäärä
     * @return Palauttaa päivämäärän muunnettuna asetetun formaatin mukaiseksi merkkijonoksi
     */
    public static String getDateAsString(LocalDate date) {
        return date.format(dateFormatter);
    }
    
    /**
     * Palauttaa annetun LocalDateTime-olion sisältämän kellonajan merkkijonona
     * @param datetime Merkkijonoksi muutettava kellonaika
     * @return Palauttaa kellonajan muunnettuna asetetun formaatin mukaiseksi merkkijonoksi
     */
    public static String getTimeAsString(LocalDateTime datetime) {
        return datetime.toLocalTime().format(timeFormatter);
    }
    
    /**
     * Palauttaa annetun LocalTime-olion sisältämän kellonajan merkkijonona
     * @param time Merkkijonoksi muutettava kellonaika
     * @return Palauttaa kellonajan muunnettuna asetetun formaatin mukaiseksi merkkijonoksi
     */
    public static String getTimeAsString(LocalTime time) {
        return time.format(timeFormatter);
    }
    
    /**
     * Palauttaa annetun LocalDateTime-olion sisältämän päivämäärän sekä kellonajan merkkijonona
     * @param datetime Merkkijonoksi muutettava päivämäärä sekä kellonaika
     * @return Palauttaa päivämäärän sekä kellonajan muunnettuna asetetun formaatin mukaiseksi merkkijonoksi
     */
    public static String getDateTimeAsString(LocalDateTime datetime) {
        return datetime.format(dateTimeFormatter);
    }
    
    /**
     * Laskee annetun aikavälin keston sekunneissa
     * @param startDate aikavälin alkupäivä
     * @param startTime aikavälin alkuaika
     * @param endDate aikavälin loppupäivä
     * @param endTime aikavälin loppuaika
     * @return palauttaa aikavälin keston sekunneissa
     */
    public static long getDurationAsLong(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return Duration.between(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime)).toSeconds();
    }
    
    
    /**
     * Laskee annetun aikavälin keston sekunneissa
     * @param start aikavälin alkuaika
     * @param end aikavälin loppuaika
     * @return palauttaa aikavälin keston sekunneissa
     */
    public static long getDurationAsLong(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toSeconds();
    }
    
    
    /**
     * Laskee annetun aikavälin keston ja muuntaa sen merkkijonoksi
     * @param startDate aikavälin alkupäivä
     * @param startTime aikavälin alkuaika
     * @param endDate aikavälin loppupäivä
     * @param endTime aikavälin loppuaika
     * @return palauttaa aikavälin muotuiltuna merkkijonona muodossa "0h 00min"
     */
    public static String getDurationAsString(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return getDurationAsString(Duration.between(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime)).toSeconds());
    }
    
    
    /**
     * Laskee annetun aikavälin keston ja muuntaa sen merkkijonoksi
     * @param start aikavälin alkuaika
     * @param end aikavälin loppuaika
     * @return palauttaa aikavälin muotuiltuna merkkijonona muodossa "0h 00min"
     */
    public static String getDurationAsString(LocalDateTime start, LocalDateTime end) {
        return getDurationAsString(Duration.between(start, end).toSeconds());
    }
    
    /**
     * Laskee annetun aikavälin keston ja muuntaa sen merkkijonoksi
     * @param duration aikaväli
     * @return palauttaa aikavälin muotuiltuna merkkijonona muodossa "0h 00min"
     */
    public static String getDurationAsString(Duration duration) {
        return getDurationAsString(duration.toSeconds());
    }
    
    /**
     * Muuntaa annetun aikavälin keston merkkijonoksi
     * @param seconds aikavälin pituus sekunneissa
     * @return palauttaa aikavälin muotuiltuna merkkijonona muodossa "0h 00min"
     */
    public static String getDurationAsString(Long seconds) {
        return String.format("%dh %02dmin", seconds / 3600,
                (seconds % 3600) / 60);
    }
    
    // string -> temporal
    
    /**
     * Parsii annetusta merkkijonosta päivämäärän, mikäli merkkijono vastaa asetettua formaattia.
     * @param date Päivämäärän sisältävä merkkijono muodossa "dd.MM.yyyy".
     * @return Palauttaa merkkijonosta luodun LocalDate-olion.
     */
    public static LocalDate parseDateFromString(String date) {
        return LocalDate.parse(date, dateFormatter);
    }
    
    /**
     * Parsii annetusta merkkijonosta kellonajan, mikäli merkkijono vastaa asetettua formaattia.
     * @param time Kellonajan sisältävä merkkijono muodossa "HH:mm".
     * @return Palauttaa merkkijonosta luodun LocalTime-olion.
     */
    public static LocalTime parseTimeFromString(String time) {
        return LocalTime.parse(time, timeFormatter);
    }
    
    /**
     * Parsii annetusta merkkijonosta päivämäärän ja kellonajan, mikäli merkkijono vastaa asetettua formaattia.
     * @param datetime Päivämäärän ja kellonajan sisältävä merkkijono muodossa "dd.MM.yyyy HH:mm".
     * @return Palauttaa merkkijonosta luodun LocalDateTime-olion.
     */
    public static LocalDateTime parseDateTimeFromString(String datetime) {
        return LocalDateTime.parse(datetime, dateTimeFormatter);
    }
    
    /**
     * Parsii annetuista merkkijonoista päivämäärän ja kellonajan, mikäli merkkijono vastaa asetettua formaattia.
     * @param date Päivämäärän sisältävä merkkijono muodossa "dd.MM.yyyy".
     * @param time Kellonajan sisältävä merkkijono muodossa "HH:mm".
     * @return Palauttaa merkkijonoista luodun LocalDateTime-olion.
     */
    public static LocalDateTime parseDateTimeFromString(String date, String time) {
        return LocalDateTime.of(LocalDate.parse(date, dateFormatter), LocalTime.parse(time, timeFormatter));
    }
    

    
}
