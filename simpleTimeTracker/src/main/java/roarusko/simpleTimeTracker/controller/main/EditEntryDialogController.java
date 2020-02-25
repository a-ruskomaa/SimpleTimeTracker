package roarusko.simpleTimeTracker.controller.main;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.model.ModelAccess;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.utility.Entries;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
public class EditEntryDialogController extends AbstractController {

    private Entry editedEntry;
    private DateTimeFormatter dateFormatter = Entries.getDateFormatter();
    private DateTimeFormatter timeFormatter = Entries.getTimeFormatter();
    private LocalDateStringConverter dateConverter = new LocalDateStringConverter(
            dateFormatter, dateFormatter);
    private LocalTimeStringConverter timeConverter = new LocalTimeStringConverter(
            timeFormatter, timeFormatter);

    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<LocalDate>();
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<LocalTime>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<LocalDate>();
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<LocalTime>();
    private final LongProperty duration = new SimpleLongProperty();

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
     * @param stage stage johon kontrolleri liittyy
     */
    public EditEntryDialogController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
        
        // TODO kannattaisiko tämä välittää parametrina?
        // TODO tarvitseeko olla propertyna?
        this.editedEntry = modelAccess.editedEntryProperty().get();
    }


    public void initialize() {

        startDatePicker.setConverter(dateConverter);
        endDatePicker.setConverter(dateConverter);

        // TODO mieti nämä uusiksi. Kannattaako luoda entrywrapper johon bindaa suoraan nuo komponentit?
        startDate.set(editedEntry.getStartDate());
        startTime.set(editedEntry.getStartTime());
        endDate.set(editedEntry.getEndDate());
        endTime.set(editedEntry.getEndTime());

        startTimeField.valueProperty().bindBidirectional(this.startTime);
        startDatePicker.valueProperty().bindBidirectional(this.startDate);
        endTimeField.valueProperty().bindBidirectional(this.endTime);
        endDatePicker.valueProperty().bindBidirectional(this.endDate);
        
        saveButton.disableProperty().bind(startTimeField.valueProperty().isNull().or(startDatePicker.valueProperty().isNull())
                .or(endTimeField.valueProperty().isNull().or(endDatePicker.valueProperty().isNull())));

        durationField.textProperty().bind(new StringBinding() {

            {
                bind(startTimeField.valueProperty(),
                        startDatePicker.valueProperty(),
                        endTimeField.valueProperty(),
                        endDatePicker.valueProperty());
            }

            @Override
            protected String computeValue() {
                try {
                    Long seconds = Duration.between(
                            LocalDateTime.of(startDate.get(), startTime.get()),
                            LocalDateTime.of(endDate.get(), endTime.get()))
                            .toSeconds();
                    return String.format(String.format("%dh %02dmin",
                            seconds / 3600, (seconds % 3600) / 60));
                } catch (NullPointerException e) {

                    return "";
                }
            }
        });
    }


    @FXML
    void handleCancelButton(ActionEvent event) {
        exitStage(event);
    }


    @FXML
    void handleSaveButton(ActionEvent event) {
        
        editedEntry.setStartDate(this.startDate.get());
        editedEntry.setEndDate(this.endDate.get());
        editedEntry.setStartTime(this.startTime.get());
        editedEntry.setEndTime(this.endTime.get());
        
        modelAccess.setEditedEntry(editedEntry);
        
        modelAccess.commitEntry();

        exitStage(event);
    }


    private void exitStage(ActionEvent event) {

        Node node = (Node) event.getSource();

        Window window = node.getScene().getWindow();

        window.fireEvent(
                new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

}
