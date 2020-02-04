package fxTyoaika.controller.mainDialogs;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.model.Entry;
import fxTyoaika.model.ModelAccess;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SaveEntryDialogController extends AbstractController {
    private final String dateTimePattern = "([0][1-9]|[1-2][0-9]|[3][01]).(0[1-9]|1[012]).[0-9]{4} ([01][0-9]|2[0-3]):[0-5][0-9]";
    private final String datePattern = "([0][1-9]|[1-2][0-9]|[3][01]).(0[1-9]|1[012]).[0-9]{4}";
    private final String timePattern = "([01][0-9]|2[0-3]):[0-5][0-9]";

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
    private Button entryDialogSaveButton;

    @FXML
    private Button cancelButton;

    @FXML
    void entryDialogHandleSave(ActionEvent event) {
        
        boolean startDateOK = validateDateField(startDateField);
        boolean endDateOK = validateDateField(endDateField);
        boolean startTimeOK = validateTimeField(startTimeField);
        boolean endTimeOK = validateTimeField(endTimeField);
        
        if (startDateOK && endDateOK && startTimeOK && endTimeOK) {
            Entry entry = new Entry();
            entry.setStartTime(startDateField.getText() + " " + startTimeField.getText() + ":00");
            entry.setEndTime(endDateField.getText() + " " + endTimeField.getText() + ":00");
            System.out.println(entry);
            
            System.out.println(entry.getStartTimeAsString());
            System.out.println(entry.getEndTimeAsString());
        }
        //
    }
    
    public void initialize() {
        startDateField.focusedProperty().addListener((e) -> validateDateField(startDateField));
        endDateField.focusedProperty().addListener((e) -> validateDateField(endDateField));
        startTimeField.focusedProperty().addListener((e) -> validateTimeField(startTimeField));
        endTimeField.focusedProperty().addListener((e) -> validateTimeField(endTimeField));
    }
    
    private boolean validateDateField(TextField field) {        
        if (! field.getText().matches(datePattern)) {
            field.setStyle("-fx-text-box-border: red ;");
            return false;
        }

        field.setStyle("");
        
        return true;
    }
    
    private boolean validateTimeField(TextField field) {
        if (! field.getText().matches(timePattern)) {
            field.setStyle("-fx-text-box-border: red ;");
            return false;
        }

        field.setStyle("");
        
        return true;
    }
    
    
    
    public SaveEntryDialogController(ModelAccess modelAccess) {
        super(modelAccess);
    }

}
