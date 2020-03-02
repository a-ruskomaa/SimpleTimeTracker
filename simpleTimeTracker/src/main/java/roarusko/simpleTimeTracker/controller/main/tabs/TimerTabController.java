package roarusko.simpleTimeTracker.controller.main.tabs;

import java.time.format.DateTimeFormatter;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.view.ViewFactory;
import roarusko.simpleTimeTracker.controller.WindowController;
import roarusko.simpleTimeTracker.controller.main.MainController;
import roarusko.simpleTimeTracker.controller.main.dialogs.EditEntryDialogController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.utility.Entries;
import roarusko.simpleTimeTracker.model.utility.EntryTimer;
import roarusko.simpleTimeTracker.view.components.DateTimeField;
import roarusko.simpleTimeTracker.view.components.TimeField;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;

public class TimerTabController extends AbstractController {
    
    private EntryTimer timer;
    
    private MainController parentController;
    
    @FXML
    private DateTimeField timerStartField;

    @FXML
    private DateTimeField timerEndField;

    @FXML
    private TextField timerDurationField;

    @FXML
    private Button timerToggleButton;

    @FXML
    private Button timerSaveButton;

    @FXML
    private Button timerResetButton;
    
    
    
    /**
     * Luo uuden ajastinvälilehden kontrollerin
     * @param dataAccess dataAccess
     * @param stage stage
     * @param parentController parentController
     */
    public TimerTabController(DataAccess dataAccess, Stage stage, MainController parentController) {
        super(dataAccess, stage);
        this.parentController = parentController;
        timer = parentController.getTimer();
    }
    

    /**
     * 
     */
    public void initialize() {
        timerDurationField.textProperty().bind(timer.elapsedTimeProperty().asString());
        
        timerStartField.valueProperty().bind(timer.startTimeProperty());
        timerEndField.valueProperty().bind(timer.endTimeProperty());
        
        timerSaveButton.disableProperty().bind(timer.runningProperty().or(timer.endTimeProperty().isNull()));
        timerResetButton.disableProperty().bind(timer.runningProperty());
    }
    

    @FXML
    void timerHandleReset() {
        reset();
    }

    @FXML
    void timerHandleSave() {
        Entry entry = dataAccess.newEntry(parentController.selectedProjectProperty().get(), timerStartField.getValue(), timerEndField.getValue());
        
        EditEntryDialogController controller = ViewFactory.createEditEntryDialog(dataAccess);
        controller.setEntry(entry);
        controller.setEntryList(parentController.selectedProject_EntriesProperty());
        
        controller.getStage().setOnCloseRequest((e) -> {
            reset();
        });
        
        controller.showModalStage();
        
        Entry newEntry = controller.getEntry();
        
        if (newEntry != null) {
            parentController.selectedProject_EntriesProperty().add(newEntry);
            parentController.selectedEntryProperty().set(newEntry);
        }
    }

    @FXML
    void timerToggle() {
        
        if (!timer.isRunning()) {
            timerStart();
        } else {
            timerStop();
        }
    }
    
    private void timerStart() {
        // käynnistetään ajastin. ajastin tallentaa alku- ja loppuajan annettuun merkintään
        timer.start();
        timerToggleButton.setText("Pysäytä ajastin");
        
    }
    
    private void timerStop() {
        timer.stop();
        timerToggleButton.setText("Käynnistä ajastin");
        timerToggleButton.setDisable(true);
    }
    
    private void reset() {
        timer.reset();
        timerToggleButton.setDisable(false);
    }
}
