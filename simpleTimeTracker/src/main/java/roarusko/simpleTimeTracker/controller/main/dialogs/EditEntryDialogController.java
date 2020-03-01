package roarusko.simpleTimeTracker.controller.main.dialogs;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.utility.Entries;
import roarusko.simpleTimeTracker.view.components.TimeField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalTimeStringConverter;

/**
 * Uuden merkinnän luomiseen käytettävän ikkunan kontrolleriluokka.
 * @author aleks
 * @version 8 Feb 2020
 *
 */

// TODO tarkistus onko ajastin käynnissä tai merkintä olemassa
// TODO terkistus onko kesto negatiivinen

public class EditEntryDialogController extends AbstractController {

    private Entry entry;
    private List<Entry> entryList;

    private DateTimeFormatter dateFormatter = Entries.getDateFormatter();
    private LocalDateStringConverter dateConverter = new LocalDateStringConverter(
            dateFormatter, dateFormatter);

    private DateTimeFormatter timeFormatter = Entries.getTimeFormatter();
    private LocalTimeStringConverter timeConverter = new LocalTimeStringConverter(
            timeFormatter, timeFormatter);

    private boolean updated = false;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TimeField startTimeField;

    @FXML
    private DatePicker endDatePicker;

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
     * @param dataAccess Olio, jonka avulla pidetään ohjelman tilaa yllä ja välitetään valittuja olioita luokalta toiselle.
     * @param stage stage johon kontrolleri liittyy
     */
    public EditEntryDialogController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);
    }


    public void initialize() {

        startDatePicker.setConverter(dateConverter);
        endDatePicker.setConverter(dateConverter);

        startTimeField.setConverter(timeConverter);
        endTimeField.setConverter(timeConverter);

        startDatePicker.valueProperty().addListener((changed) -> {
            System.out.println("value changed!");
        });

        // pakotetaan datepickerit parsimaan päivämäärää kun focus poistuu
        // tekstikentästä.
        // it's not pretty, but it works...
        startDatePicker.focusedProperty().addListener((e) -> {
            System.out.println("hep");
            try {
                startDatePicker.setValue(startDatePicker.getConverter()
                        .fromString(startDatePicker.getEditor().getText()));
            } catch (DateTimeParseException ex) {
                System.out.println("invalid date format");
                startDatePicker.getEditor().setText(startDatePicker
                        .getConverter().toString(startDatePicker.getValue()));
            }
        });

        endDatePicker.focusedProperty().addListener((e) -> {
            System.out.println("hep");
            try {
                endDatePicker.setValue(endDatePicker.getConverter()
                        .fromString(endDatePicker.getEditor().getText()));
            } catch (DateTimeParseException ex) {
                System.out.println("invalid date format");
                endDatePicker.getEditor().setText(endDatePicker.getConverter()
                        .toString(endDatePicker.getValue()));
            }
        });

        saveButton.disableProperty()
                .bind(startTimeField.valueProperty().isNull()
                        .or(startDatePicker.valueProperty().isNull())
                        .or(endTimeField.valueProperty().isNull()
                                .or(endDatePicker.valueProperty().isNull())));

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
        }

        Entry existing = checkIfOverlappingEntryExists(start, end, entryList);

        if (existing != null) {
            showOverLappingAlert(existing);
            return;
        }

        entry.setStartDateTime(start);
        entry.setEndDateTime(end);

        this.entry = dataAccess.commitEntry(entry);

        this.updated = true;

        exitStage(event);
    }


    private void exitStage(ActionEvent event) {

        Node node = (Node) event.getSource();

        Window window = node.getScene().getWindow();

        window.fireEvent(
                new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }


    public void setEntry(Entry entry) {
        this.entry = entry;

        this.startDatePicker.setValue(entry.getStartDate());

        this.endDatePicker.setValue(entry.getEndDate());

        this.startTimeField.setValue(entry.getStartTime());

        this.endTimeField.setValue(entry.getEndTime());
    }


    public void setEntryList(List<Entry> list) {
        this.entryList = list;
    }


    public Entry getEntry() {
        return this.entry;
    }


    public boolean wasUpdated() {
        return updated;
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
