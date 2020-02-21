package roarusko.simpleTimeTracker.controller.main;

import java.time.format.DateTimeFormatter;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.controller.ViewFactory;
import roarusko.simpleTimeTracker.controller.WindowController;
import roarusko.simpleTimeTracker.model.ModelAccess;
import roarusko.simpleTimeTracker.model.domainModel.Entry;
import roarusko.simpleTimeTracker.model.utility.Entries;
import roarusko.simpleTimeTracker.model.utility.EntryTimer;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TimerTabController extends AbstractController {
    
    private EntryTimer timer;
    
    private MainController parentController;
    
    @FXML
    private TextField timerStartField;

    @FXML
    private TextField timerEndField;

    @FXML
    private TextField timerDurationField;

    @FXML
    private Button timerToggleButton;

    @FXML
    private Button timerSaveButton;

    @FXML
    private Button timerResetButton;
//
    /**
     * Luo uuden ajastinvälilehden kontrollerin
     * @param modelAccess modelAccess
     * @param stage stage
     */
    public TimerTabController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
    }
    
    /**
     * Luo uuden ajastinvälilehden kontrollerin
     * @param modelAccess modelAccess
     * @param stage stage
     * @param parentController parentController
     */
    public TimerTabController(ModelAccess modelAccess, Stage stage, MainController parentController) {
        super(modelAccess, stage);
        this.parentController = parentController;
        timer = modelAccess.getTimer();
    }
    
    /**
     * 
     */
    public void initialize() {
        //timerToggleButton.disableProperty().bind(Bindings.notEqual("", timerDurationField.textProperty()));
        timerDurationField.textProperty().bind(timer.elapsedTimeProperty().asString());
    }
    

    @FXML
    void timerHandleReset() {
        reset();
    }

    @FXML
    void timerHandleSave() {
        Entry entry = timer.getEntry();
        modelAccess.setEditedEntry(entry);
        WindowController wc = ViewFactory.createEditEntryDialog();
        
        wc.getStage().setOnCloseRequest((e) -> {
            
            
            modelAccess.setEditedEntry(null);
            reset();
        });
    }

    @FXML
    void timerToggle() {
        DateTimeFormatter f = Entries.getDateTimeFormatter();
        
        if (!timer.isRunning()) {
            timer.start();
            timerToggleButton.setText("Pysäytä ajastin");
            timerStartField.setText(timer.getEntry().getStartDateTime().format(f));
            timerEndField.clear();
            timerSaveButton.setDisable(true);
            timerResetButton.setDisable(true);
        } else {
            timer.stop();
            timerToggleButton.setText("Käynnistä ajastin");
            timerEndField.setText(timer.getEntry().getEndDateTime().format(f));
            timerSaveButton.setDisable(false);
            timerResetButton.setDisable(false);
            timerToggleButton.setDisable(true);
        }
    }
    
    private void reset() {
        if (timer.isRunning()) {
            timer.stop();
        }
        timer.reset();
        timerEndField.clear();
        timerStartField.clear();
        timerToggleButton.setDisable(false);
        timerSaveButton.setDisable(true);
        timerResetButton.setDisable(true);
    }
}
