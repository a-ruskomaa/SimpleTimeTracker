package fxTyoaika.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.Duration;

public class Entry {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    
    public Entry() {
        
    }
    
    public Entry(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public Entry(String startTime, String endTime) {
        this.startTime = LocalDateTime.parse(startTime, dateTimeFormatter);
        this.endTime = LocalDateTime.parse(endTime, dateTimeFormatter);
    }

    public LocalDate getEndDate() {
        return null;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public long getDurationInSeconds() {
        return Duration.between(startTime, endTime).toSeconds();
    }
    
    public String getDurationAsString() {
        Long seconds = getDurationInSeconds();
        return String.format("%dh %02dmin", seconds / 3600, (seconds % 3600) / 60);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public String toString() {
        return endTime.format(dateFormatter) + " " + getDurationAsString();
    }
    
    public String getStartTimeAsString() {
        return startTime.format(dateTimeFormatter);
    }
    
    public String getEndTimeAsString() {
        return endTime.format(dateTimeFormatter);
    }

}
