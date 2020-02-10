package fxTyoaika.controller.mainDialogs;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.TimeField;
import fxTyoaika.model.Entries;
import fxTyoaika.model.Entry;
import fxTyoaika.model.ModelAccess;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
public class EditEntryDialogController extends AbstractController {

    private Entry entry;
    private DateTimeFormatter dateFormatter = Entries.getDateFormatter();
    private DateTimeFormatter timeFormatter = Entries.getTimeFormatter();
    private LocalDateStringConverter dateConverter = new LocalDateStringConverter(
            dateFormatter, dateFormatter);
    private LocalTimeStringConverter timeConverter = new LocalTimeStringConverter(
            timeFormatter, timeFormatter);

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
     * @param modelAccess Olio, jonka avulla pidetään ohjelman tilaa yllä ja välitetään valittuja olioita luokalta toiselle.
     */
    public EditEntryDialogController(ModelAccess modelAccess) {
        super(modelAccess);
        this.entry = modelAccess.getCurrentlyEditedEntry();

    }


    public void initialize() {

        startDatePicker.setConverter(dateConverter);
        endDatePicker.setConverter(dateConverter);

        if (entry.getId() == -1) {
            startDatePicker.setValue(LocalDate.now());
            endDatePicker.setValue(LocalDate.now());
            startTimeField.valueProperty()
                    .set(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
            endTimeField.valueProperty()
                    .set(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        }

        else {
            startDatePicker.setValue(entry.getStartDate());
            endDatePicker.setValue(entry.getEndDate());
            startTimeField.valueProperty().set(entry.getStartTime());
            endTimeField.valueProperty().set(entry.getEndTime());
        }

        // TODO käännä bindaukset toisin päin, muuta uuden entryn päivämäärä ja kellonaika!
        entry.startTimeProperty()
                .bindBidirectional(startTimeField.valueProperty());
        entry.startDateProperty()
                .bindBidirectional(startDatePicker.valueProperty());
        entry.endTimeProperty().bindBidirectional(endTimeField.valueProperty());
        entry.endDateProperty()
                .bindBidirectional(endDatePicker.valueProperty());

        durationField.textProperty().bind(Bindings.createStringBinding(() -> {
            return this.entry.getDurationAsString();
        }, this.entry.durationProperty()));
    }


    @FXML
    void handleCancelButton(ActionEvent event) {
        exitStage(event);
    }


    @FXML
    void handleSaveButton(ActionEvent event) {
        if (this.entry.getId() == -1) {
            Entry newEntry = new Entry(this.entry.getStartDateTime(),
                    this.entry.getEndDateTime());
            modelAccess.getSelectedProject().addEntry(newEntry);
        } else {
            if (modelAccess.getSelectedEntry().getId() == this.entry.getId()) {
                modelAccess.getSelectedEntry()
                        .setStartDateTime(this.entry.getStartDateTime());
                modelAccess.getSelectedEntry()
                        .setEndDateTime(this.entry.getEndDateTime());
            }
        }

        exitStage(event);
    }


    private void exitStage(ActionEvent event) {

        Node node = (Node) event.getSource();

        Window window = node.getScene().getWindow();

        window.fireEvent(
                new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
