package fxTyoaika.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Formatter;

/**
 * Luokka sisältää staattisia apumetodeita merkintöjen, kellonaikojen ja päivämäärien käsittelyyn
 * @author aleks
 * @version 6 Feb 2020
 *
 */
public class Entries {
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static DateTimeFormatter dateTimeFormatterNoSeconds = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static DateTimeFormatter timeFormatterNoSeconds = DateTimeFormatter.ofPattern("HH:mm");
    
    private final String dateTimePattern = "([0][1-9]|[1-2][0-9]|[3][01]).(0[1-9]|1[012]).[0-9]{4}([01][0-9]|2[0-3]):[0-5][0-9]";
    private final String datePattern = "([0][1-9]|[1-2][0-9]|[3][01]).(0[1-9]|1[012]).[0-9]{4}";
    private final String timePatternSeconds = "([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
    private final String timePattern = "([01][0-9]|2[0-3]):[0-5][0-9]";
    
    public static DateTimeFormatter getDateFormatter() {
        return dateFormatter;
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public static  DateTimeFormatter getTimeFormatter() {
        return timeFormatter;
    }
    
    
    public static Entry parseEntryFromStrings(String start, String end) {
        LocalDateTime startTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(end, dateTimeFormatter);
        return new Entry(startTime, endTime);
    }
    
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
    
    public static String getTimeAsStringNoSeconds(LocalDateTime t) {
        return t.toLocalTime().format(timeFormatterNoSeconds);
    }
    
    public static String getTimeAsStringNoSeconds(LocalTime t) {
        return t.format(timeFormatterNoSeconds);
    }
    
    public static String getDateTimeAsStringNoSeconds(LocalDateTime t) {
        return t.format(dateTimeFormatterNoSeconds);
    }
    
    public static LocalDate parseDateFromString(String s) {
        return LocalDate.parse(s, dateFormatter);
    }
    
    public static LocalTime parseTimeFromString(String s) {
        return LocalTime.parse(s, timeFormatter);
    }
    
    public static LocalTime parseTimeFromStringNoSeconds(String s) {
        return LocalTime.parse(s, timeFormatterNoSeconds);
    }
    
    public static LocalDateTime parseDateTimeFromString(String s) {
        return LocalDateTime.parse(s, dateTimeFormatter);
    }
    
    public static LocalDateTime parseDateTimeFromString(String d, String t) {
        return LocalDateTime.of(LocalDate.parse(d, dateFormatter), LocalTime.parse(t, timeFormatter));
    }
    
    public static LocalDateTime parseDateTimeFromStringNoSeconds(String s) {
        return LocalDateTime.parse(s, dateTimeFormatterNoSeconds);
    }
    
    public static LocalDateTime parseDateTimeFromStringNoSeconds(String d, String t) {
        return LocalDateTime.of(LocalDate.parse(d, dateFormatter), LocalTime.parse(t, timeFormatterNoSeconds));
    }
}
