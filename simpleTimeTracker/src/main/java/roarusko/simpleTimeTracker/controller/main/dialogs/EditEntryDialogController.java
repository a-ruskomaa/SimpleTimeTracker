package roarusko.simpleTimeTracker.controller.main.dialogs;

import java.time.LocalDateTime;
import java.util.List;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.utility.Entries;
import roarusko.simpleTimeTracker.view.components.EnhancedDatePicker;
import roarusko.simpleTimeTracker.view.components.TimeField;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * Uuden merkinnän sekä muokattavan merkinnän tallentamiseen käytettävän ikkunan
 * kontrolleriluokka. Luokalle välitetään settereiden avulla muokattava merkintä.
 * 
 * Luokan toiminnan kannalta ei ole merkitystä, onko merkintä aiemmin luotu vaiko
 * uusi.
 * 
 * Osaa tallentaa dataAccess:n avulla muokatun merkinnän pysyvään muistiin. Tarjoaa
 * getterimetodin tallennetun merkinnän hakemiseen muun ohjelman käyttöön.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class EditEntryDialogController extends AbstractController {

    private Entry entry;
    private List<Entry> entryList;

    private boolean saved = false;

    @FXML
    private EnhancedDatePicker startDatePicker;

    @FXML
    private TimeField startTimeField;

    @FXML
    private EnhancedDatePicker endDatePicker;

    @FXML
    private TimeField endTimeField;

    @FXML
    private TextField durationField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    /**
     * 
     * @param dataAccess dataAccess
     * @param stage stage johon kontrolleri liittyy
     */
    public EditEntryDialogController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);
    }


    /**
     * Alustetaan käyttöliittymän komponentit.
     */
    public void initialize() {

        // Sidotaan tallennusnappi aktiiviseksi vain kun kaikilla kentillä on arvot
        saveButton.disableProperty()
                .bind(startTimeField.valueProperty().isNull()
                        .or(startDatePicker.valueProperty().isNull())
                        .or(endTimeField.valueProperty().isNull())
                        .or(endDatePicker.valueProperty().isNull()));

        // sidotaan tekstikenttä näyttämään merkinnän kesto automaattisesti päivittyen
        durationField.textProperty().bind(Bindings.createStringBinding(() -> {
            try {
                return Entries.getDurationAsString(startDatePicker.getValue(),
                        startTimeField.getValue(), endDatePicker.getValue(),
                        endTimeField.getValue());
            } catch (NullPointerException e) {
                return "";
            }
        }, startTimeField.valueProperty(), startDatePicker.valueProperty(),
                endTimeField.valueProperty(), endDatePicker.valueProperty()));
    }


    @FXML
    void handleCancelButton(ActionEvent event) {
        exitStage(event);
    }


    @FXML
    void handleSaveButton(ActionEvent event) {

        LocalDateTime start = LocalDateTime.of(this.startDatePicker.getValue(),
                this.startTimeField.getValue());
        LocalDateTime end = LocalDateTime.of(this.endDatePicker.getValue(),
                this.endTimeField.getValue());

        if (start.isAfter(end)) {
            showNegativeDurationAlert();
            return;
        }

        Entry existing = checkIfOverlappingEntryExists(start, end, entryList);

        if (existing != null) {
            showOverLappingAlert(existing);
            return;
        }

        entry.setStartDateTime(start);
        entry.setEndDateTime(end);

        this.entry = dataAccess.commitEntry(entry);

        this.saved = true;

        exitStage(event);
    }


    private void exitStage(ActionEvent event) {

        Node node = (Node) event.getSource();

        Window window = node.getScene().getWindow();

        window.fireEvent(
                new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }


    /**
     * Asettaa muokattavan merkinnän
     * @param entry muokattava merkintä
     */
    public void setEntry(Entry entry) {
        this.entry = entry;

        this.startDatePicker.setValue(entry.getStartDate());

        this.endDatePicker.setValue(entry.getEndDate());

        this.startTimeField.setValue(entry.getStartTime());

        this.endTimeField.setValue(entry.getEndTime());
    }


    /**
     * Asettaa kaikki merkinnät sisältävän listan
     * @param list Kaikki merkinnät sisältävä lista
     */
    public void setEntryList(List<Entry> list) {
        this.entryList = list;
    }


    /**
     * Palauttaa muokatun merkinnän
     * @return Palauttaa muokatun merkinnän
     */
    public Entry getEntry() {
        return this.entry;
    }


    /**
     * @return Palauttaa true jos merkintää on muokattu dialogissa
     */
    public boolean wasUpdated() {
        return saved;
    }


    private Entry checkIfOverlappingEntryExists(LocalDateTime start,
            LocalDateTime end, List<Entry> list) {
        // TODO jaetuissa projekteissa muuta tarkistamaan vain saman käyttäjän
        // tekemiä merkintöjä
        for (Entry existing : list) {
            if (existing.equals(this.entry)) {
                continue;
            }
            if (!(existing.getEndDateTime().isBefore(start)
                    || existing.getStartDateTime().isAfter(end))) {
                return existing;
            }
        }
        return null;
    }


    private void showOverLappingAlert(Entry existing) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(
                "Lisättävä merkintä on päällekkäin olemassa olevan merkinnän kanssa:\n\n"
                        + "\t"
                        + Entries.getDateTimeAsString(
                                existing.getStartDateTime())
                        + "\n" + "\t"
                        + Entries.getDateTimeAsString(existing.getEndDateTime())
                        + "\n\n" + "Tarkista lisättävän merkinnän ajankohta!");
        alert.showAndWait();
    }


    private void showNegativeDurationAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText("Merkinnän kesto ei voi olla negatiivinen:\n\n"
                + "Tarkista lisättävän merkinnän alku- ja loppuaika!");
        alert.showAndWait();
    }

}
