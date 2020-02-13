package fxTyoaika.controller.main;

import java.time.format.DateTimeFormatter;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.ModelAccess;
import fxTyoaika.controller.ViewFactory;
import fxTyoaika.model.Entries;
import fxTyoaika.model.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TimerTabController extends AbstractController {

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
    public TimerTabController(ModelAccess modelAccess) {
        super(modelAccess);
    }
    
    public TimerTabController(ModelAccess modelAccess, MainController parentController) {
        super(modelAccess);
        this.parentController = parentController;
    }
    

    @FXML
    void timerHandleReset(ActionEvent event) {
        Timer timer = modelAccess.getTimer();
        
        if (timer.isRunning()) {
            timer.stop();
            timer.reset();
        }
    }

    @FXML
    void timerHandleSave(ActionEvent event) {
        modelAccess.setSelectedEntry(modelAccess.getTimer().getEntry());
        ViewFactory.createEditEntryDialog();
    }

    @FXML
    void timerToggle(ActionEvent event) {
        Timer timer = modelAccess.getTimer();
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
        }
    }
}
