package roarusko.simpleTimeTracker.controller.main.dialogs;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.utility.Entries;

import java.time.Duration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class DeleteEntryDialogController extends AbstractController {
    private Entry entry;
    
    private boolean deleted;

    @FXML
    private Label startTimeLabel;

    @FXML
    private Label endTimeLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    @FXML
    void handleCancelButton(ActionEvent event) {
        deleted = false;
        exitStage(event);
    }

    @FXML
    void handleDeleteButton(ActionEvent event) {
        deleted = dataAccess.deleteEntry(entry);
        exitStage(event);
    }
    
    
    public DeleteEntryDialogController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);
    }

    private void exitStage(ActionEvent event) {

        Node node = (Node) event.getSource();

        Window window = node.getScene().getWindow();

        window.fireEvent(
                new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
    public void setEntry(Entry entry) {
        this.entry = entry;
        
        this.startTimeLabel.setText(Entries.getDateTimeAsString(entry.getStartDateTime()));
        this.endTimeLabel.setText(Entries.getDateTimeAsString(entry.getEndDateTime()));
        
        this.durationLabel.setText(Entries.getDurationAsString(entry.getStartDateTime(), entry.getEndDateTime()));
    }
    
    public boolean wasDeleted() {
        return deleted;
    }
    
    
}

