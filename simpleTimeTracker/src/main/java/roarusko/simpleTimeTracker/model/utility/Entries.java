package roarusko.simpleTimeTracker.model.utility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Luokka sisältää staattisia apumetodeita merkintöjen, kellonaikojen ja päivämäärien käsittelyyn.
 * 
 * Sisältää tällä hetkellä runsaasti tarpeettomiakin metodeja. Näitä karsitaan ohjelman vaatimusten tarkentuessa.
 * @author aleks
 * @version 6 Feb 2020
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
    
    public static DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public static  DateTimeFormatter getTimeFormatter() {
        return timeFormatter;
    }
    
    // temporal -> string
    
    public static String getDateAsString(LocalDateTime t) {
        return t.toLocalDate().format(dateFormatter);
    }
    
    public static String getDateAsString(LocalDate t) {
        return t.format(dateFormatter);
    }
    
    public static String getTimeAsString(LocalDateTime t) {
        return t.toLocalTime().format(timeFormatter);
    }
    
    public static String getTimeAsString(LocalTime t) {
        return t.format(timeFormatter);
    }
    
    public static String getDateTimeAsString(LocalDateTime t) {
        return t.format(dateTimeFormatter);
    }
    
    /**
     * @param duration aikaväli
     * @return palauttaa aikavälin muotuiltuna merkkijonona muodossa "0h 00min"
     */
    public static String getDurationAsString(Duration duration) {
        Long seconds = duration.toSeconds();
        return String.format("%dh %02dmin", seconds / 3600,
                (seconds % 3600) / 60);
    }
    
    /**
     * @param seconds aikavälin pituus sekunneissa
     * @return palauttaa aikavälin muotuiltuna merkkijonona muodossa "0h 00min"
     */
    public static String getDurationAsString(Long seconds) {
        return String.format("%dh %02dmin", seconds / 3600,
                (seconds % 3600) / 60);
    }
    
    // string -> temporal
    
    public static LocalDate parseDateFromString(String s) {
        return LocalDate.parse(s, dateFormatter);
    }
    
    public static LocalTime parseTimeFromString(String s) {
        return LocalTime.parse(s, timeFormatter);
    }
    
    public static LocalDateTime parseDateTimeFromString(String s) {
        return LocalDateTime.parse(s, dateTimeFormatter);
    }
    
    public static LocalDateTime parseDateTimeFromString(String d, String t) {
        return LocalDateTime.of(LocalDate.parse(d, dateFormatter), LocalTime.parse(t, timeFormatter));
    }
    

    
}
