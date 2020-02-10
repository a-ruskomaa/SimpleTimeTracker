package fxTyoaika.controller.mainTabs;

import java.time.format.DateTimeFormatter;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.ViewFactory;
import fxTyoaika.model.Entries;
import fxTyoaika.model.ModelAccess;
import fxTyoaika.model.Timer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TimerTabController extends AbstractController {
    
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
        ViewFactory.createSaveEntryDialog();
    }

    @FXML
    void timerToggle(ActionEvent event) {
        Timer timer = modelAccess.getTimer();
        DateTimeFormatter f = Entries.getDateTimeFormatter();
        
        if (!timer.isRunning()) {
            timer.start();
            timerToggleButton.setText("Pysäytä ajastin");
            timerStartField.setText(timer.getEntry().getStartTime().format(f));
            timerEndField.clear();
            timerSaveButton.setDisable(true);
            timerResetButton.setDisable(true);
        } else {
            timer.stop();
            timerToggleButton.setText("Käynnistä ajastin");
            timerEndField.setText(timer.getEntry().getEndTime().format(f));
            timerSaveButton.setDisable(false);
            timerResetButton.setDisable(false);
        }
    }
}
