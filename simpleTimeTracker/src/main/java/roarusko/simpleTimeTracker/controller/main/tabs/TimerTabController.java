package roarusko.simpleTimeTracker.controller.main.tabs;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.view.ViewFactory;
import roarusko.simpleTimeTracker.controller.main.MainController;
import roarusko.simpleTimeTracker.controller.main.UpdateEvent;
import roarusko.simpleTimeTracker.controller.main.dialogs.EditEntryDialogController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.utility.EntryTimer;
import roarusko.simpleTimeTracker.view.components.DateTimeField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Ajastinvälilehden kontrolleriluokka. Välilehti sisältää käyttöliittymäkomponentit
 * merkintöjen luomiseen käytettävän ajastimen käynnistämiseen, pysäyttämiseen ja merkinnän
 * tallentamiseen.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
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
        this.timer = parentController.getTimer();
    }
    

    /**
     * Sidotaan käyttöliittymäkomponentit reagoimaan automaattisesti ajastimen tilaan.
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

    
    /**
     * "Tallenna"-painikkeen tapahtumankäsittelijä.
     */
    @FXML
    void timerHandleSave() {
        // pyytää dataAccessia luomaan uuden merkinnän, joka on alustettu ajastimen alku- ja loppuajankohdalla
        Entry entry = dataAccess.newEntry(parentController.selectedProjectProperty().get(), timerStartField.getValue(), timerEndField.getValue());
        
        // avataan merkinnän tallentamiseen käytettävä dialogi, jolle välitetään äsken luotu merkintä
        // sekä kaikki aiemmin luodut merkinnät
        EditEntryDialogController controller = ViewFactory.createEditEntryDialog(dataAccess);
        controller.setEntry(entry);
        controller.setEntryList(parentController.selectedProject_EntriesProperty());
        
        // kutsutaan kun dialogi suljetaan:
        controller.getStage().setOnCloseRequest((e) -> {
            if (entry.getId() != -1) {
                // jos luotu merkintä tallennettiin pysyvään muistiin, lisätään se myös listalle
                parentController.selectedProject_EntriesProperty().add(entry);
                parentController.selectedEntryProperty().set(entry);
                
                // luodaan tapahtuma joka saa aikaan käyttöliittymän päivittämisen
                this.getStage().fireEvent(new UpdateEvent());
            }
            reset();
        });
        
        controller.showModalStage();
    }

    /**
     * Käynnistää tai pysäyttää ajastimen riippuen sen tilasta
     */
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
