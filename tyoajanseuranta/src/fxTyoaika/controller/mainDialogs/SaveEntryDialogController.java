package fxTyoaika.controller.mainDialogs;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.model.Entry;
import fxTyoaika.model.ModelAccess;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class SaveEntryDialogController extends AbstractController {
    private final String dateTimePattern = "([0][1-9]|[1-2][0-9]|[3][01]).(0[1-9]|1[012]).[0-9]{4} ([01][0-9]|2[0-3]):[0-5][0-9]";
    private final String datePattern = "([0][1-9]|[1-2][0-9]|[3][01]).(0[1-9]|1[012]).[0-9]{4}";
    private final String timePattern = "([01][0-9]|2[0-3]):[0-5][0-9]";
    
    private Entry entry;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime; 
    private LocalTime endTime;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField endDateField;
    
    @FXML
    private TextField startTimeField;
    
    @FXML
    private TextField endTimeField;

    @FXML
    private TextField durationField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    public SaveEntryDialogController(ModelAccess modelAccess) {
        super(modelAccess);
    }
    
    public void initialize() {
        this.entry = modelAccess.getTempEntry();
        
        startDateField.focusedProperty().addListener((e) -> startDate = validateDateField(startDateField));
        endDateField.focusedProperty().addListener((e) -> endDate = validateDateField(endDateField));
        startTimeField.focusedProperty().addListener((e) -> startTime = validateTimeField(startTimeField));
        endTimeField.focusedProperty().addListener((e) -> endTime = validateTimeField(endTimeField));
    }
    
    @FXML
    void handleSaveButton(ActionEvent event) {
        
        if (startDate == null || endDate == null || startTime == null || endTime == null ) {
            return;
        }
        
        entry.setStartTime(LocalDateTime.of(startDate, startTime));
        entry.setEndTime(LocalDateTime.of(endDate, endTime));
        System.out.println(entry);
        
        System.out.println(entry.getStartTimeAsString());
        System.out.println(entry.getEndTimeAsString());
        
        modelAccess.getSelectedProject().addEntry(entry);
        
        exitStage(event);
    }
    
    @FXML
    void handleCancelButton(ActionEvent event) {
        exitStage(event);
    }
    
    
    private LocalDate validateDateField(TextField field) {
        LocalDate date;
        try {
            DateTimeFormatter formatter =  entry.getDateFormatter();
            date = LocalDate.parse(field.getText(), formatter);
            field.setStyle("");
        } catch (DateTimeException e) {
            System.out.println("invalid date!");
            field.setStyle("-fx-text-box-border: red ;");
            date = null;
        }

        setDurationField();
        
        return date;
    }
    
    private LocalTime validateTimeField(TextField field) {
        LocalTime time;
        try {
            DateTimeFormatter formatter =  entry.getTimeFormatter();
            time = LocalTime.parse(field.getText() + ":00", formatter);
            field.setStyle("");
        } catch (DateTimeException e) {
            System.out.println("invalid time!");
            field.setStyle("-fx-text-box-border: red ;");
            time = null;
        }
        
        setDurationField();

        return time;
    }
    
    private void setDurationField() {
        System.out.println("lasketaan kesto");
        long seconds;
        try {
            LocalDateTime start = LocalDateTime.of(startDate, startTime);
            LocalDateTime end = LocalDateTime.of(endDate, endTime);
            
            seconds = Duration.between(start, end).toSeconds();
            System.out.println("kesto laskettu");
        } catch (NullPointerException e) {
            seconds = 0;
            System.out.println("virhe kestossa");
        }
        durationField.setText(String.format("%dh %02dmin", seconds / 3600, (seconds % 3600) / 60));
    }
    

    private void exitStage(ActionEvent event) {
        
        Node node = (Node) event.getSource();
        
        Window window = node.getScene().getWindow();
        
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
    

}
