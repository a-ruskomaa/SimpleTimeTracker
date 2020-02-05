package fxTyoaika.controller.mainDialogs;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.model.Entry;
import fxTyoaika.model.Entries;
import fxTyoaika.model.ModelAccess;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class SaveEntryDialogController extends AbstractController {
    // private final String dateTimePattern =
    // "([0][1-9]|[1-2][0-9]|[3][01]).(0[1-9]|1[012]).[0-9]{4}
    // ([01][0-9]|2[0-3]):[0-5][0-9]";
    // private final String datePattern =
    // "([0][1-9]|[1-2][0-9]|[3][01]).(0[1-9]|1[012]).[0-9]{4}";
    // private final String timePattern = "([01][0-9]|2[0-3]):[0-5][0-9]";

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
        this.entry = modelAccess.getCurrentlyEditedEntry();
        
        startDate = entry.getStartTime().toLocalDate();
        startTime = entry.getStartTime().toLocalTime();
        endDate = entry.getEndTime().toLocalDate();
        endTime = entry.getEndTime().toLocalTime();
        
        if (this.entry.getId() != -1) {
            startDateField.setText(Entries.getDateAsString(startDate));
            startTimeField.setText(Entries.getTimeAsString(startTime));
            endDateField.setText(Entries.getDateAsString(endDate));
            endTimeField.setText(Entries.getTimeAsString(endTime));
        }

        System.out.println(entry);

        startDateField.setOnKeyReleased(e -> {
            if (handleInput(e, startDate)) {
                setDurationField();
            };
        });
        endDateField.setOnKeyReleased(e -> {
            if (handleInput(e, endDate)) {
                setDurationField();
            };
        });
        startTimeField.setOnKeyReleased(e -> {
            if (handleInput(e, startTime)) {
                setDurationField();
            };
        });
        endTimeField.setOnKeyReleased(e -> {
            if (handleInput(e, endTime)) {
                setDurationField();
            };
        });

    }


    private boolean handleInput(KeyEvent event, Temporal target) {
        TextField field = (TextField) event.getSource();
        if (field.getText().length() != field.getPromptText().length()) {
            return false;
        }
        
        return validateField(field, target);
    }


    @FXML
    private void handleSaveButton(ActionEvent event) {

        boolean startDateOK = validateField(startDateField, startDate);
        boolean endDateOK = validateField(endDateField, endDate);
        boolean startTimeOK = validateField(startTimeField, startTime);
        boolean endTimeOK = validateField(endTimeField, endTime);

        if (!startDateOK || !endDateOK || !startTimeOK || !endTimeOK) {
            return;
        }
        
        LocalDateTime startDateTime = parseDateTimeFromFields(startDateField, startTimeField);
        LocalDateTime endDateTime = parseDateTimeFromFields(endDateField, endTimeField);

        if (this.entry.getId() == -1) {
            Entry newEntry = new Entry(startDateTime, endDateTime);
            modelAccess.getSelectedProject().addEntry(newEntry);
        } else {
            entry.setStartTime(startDateTime);
            entry.setEndTime(endDateTime);
        }

        exitStage(event);
    }


    @FXML
    private void handleCancelButton(ActionEvent event) {
        exitStage(event);
    }


    private boolean validateField(TextField field, Temporal target) {
        String text = field.getText();
        try {
            if (target.getClass().equals(LocalDate.class)) {
                Entries.parseDateFromString(text);
            } else {
                Entries.parseTimeFromStringNoSeconds(text);
            }
            field.setStyle("");
        } catch (DateTimeException e) {
            field.setStyle("-fx-text-box-border: red ;");
            return false;
        }

        return true;
    }
    
    
    private LocalDateTime parseDateTimeFromFields(TextField date, TextField time) {
        String dateText = date.getText();
        String timeText = time.getText();
        
        return Entries.parseDateTimeFromStringNoSeconds(dateText, timeText);
    }


    private void setDurationField() {
        long seconds;
        try {
            LocalDateTime start = parseDateTimeFromFields(startDateField, startTimeField);
            LocalDateTime end = parseDateTimeFromFields(endDateField, endTimeField);

            seconds = Duration.between(start, end).toSeconds();
            System.out.println("count good");
        } catch (DateTimeParseException e) {
            seconds = 0;
            System.out.println("count bad");
        }

        if (seconds < 0)
            seconds = 0;

        durationField.setText(String.format("%dh %02dmin", seconds / 3600,
                (seconds % 3600) / 60));
    }


    private void exitStage(ActionEvent event) {

        Node node = (Node) event.getSource();

        Window window = node.getScene().getWindow();

        window.fireEvent(
                new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
