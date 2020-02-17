package fxTyoaika.controller.main;

import java.time.format.DateTimeFormatter;

import javax.swing.WindowConstants;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.ModelAccess;
import fxTyoaika.controller.Timer;
import fxTyoaika.controller.ViewFactory;
import fxTyoaika.controller.WindowController;
import fxTyoaika.model.Entries;
import fxTyoaika.model.Entry;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TimerTabController extends AbstractController {
    
    private Timer timer;
    
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
    public TimerTabController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
    }
    
    public TimerTabController(ModelAccess modelAccess, Stage stage, MainController parentController) {
        super(modelAccess, stage);
        this.parentController = parentController;
        timer = parentController.getTimer();
    }
    
    public void initialize() {
        //timerToggleButton.disableProperty().bind(Bindings.notEqual("", timerDurationField.textProperty()));
    }
    

    @FXML
    void timerHandleReset(ActionEvent event) {
        reset();
    }

    @FXML
    void timerHandleSave(ActionEvent event) {
        Entry entry = timer.getEntry();
        modelAccess.setEditedEntry(entry);
        WindowController wc = ViewFactory.createEditEntryDialog();
        
        wc.getStage().setOnCloseRequest((e) -> {
            
            
            modelAccess.setEditedEntry(null);
            reset();
        });
    }

    @FXML
    void timerToggle(ActionEvent event) {
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
        timerDurationField.setText("0 min");
        timerToggleButton.setDisable(false);
        timerSaveButton.setDisable(true);
        timerResetButton.setDisable(true);
    }
}
