package roarusko.simpleTimeTracker.controller.main;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.controller.ViewFactory;
import roarusko.simpleTimeTracker.controller.WindowController;
import roarusko.simpleTimeTracker.model.ModelAccess;
import roarusko.simpleTimeTracker.model.domainModel.Entry;
import roarusko.simpleTimeTracker.model.domainModel.Project;
import roarusko.simpleTimeTracker.model.utility.Entries;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class ProjectTabController extends AbstractController {

    private MainController parentController;

    @FXML
    private TextField manualEndField;

    @FXML
    private TextField manualDurationField;

    @FXML
    private Button manualSaveButton;

    @FXML
    private ListView<Entry> projectEntryList;

    @FXML
    private TreeView<Entry> projectEntryTree;

    @FXML
    private TextField entryStartField;

    @FXML
    private TextField entryEndField;

    @FXML
    private TextField entryDurationField;

    @FXML
    private Button editEntryButton;

    @FXML
    private Button removeEntryButton;

    @FXML
    private Button addEntryButton;

    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<LocalDate>();
    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<LocalTime>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<LocalDate>();
    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<LocalTime>();
    private final LongProperty duration = new SimpleLongProperty();

    DateTimeFormatter formatter = Entries.getDateTimeFormatter();

    /**
     * @param modelAccess modelAccess
     * @param stage stage jota kontrolloidaan
     */
    public ProjectTabController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
    }


    /**
     * @param modelAccess modelAccess
     * @param stage stage jota kontrolloidaan
     * @param parentController asd
     */
    public ProjectTabController(ModelAccess modelAccess, Stage stage,
            MainController parentController) {
        super(modelAccess, stage);
        this.parentController = parentController;
    }


    /**
     * 
     */
    public void initialize() {

        // haetaan valitun projektin merkinnät listalle
        projectEntryList.setItems(modelAccess.selectedProjectEntriesProperty());


        // luodaan kuuntelija reagoimaan valitun merkinnän vaihtumiseen
        ChangeListener<Entry> selectedEntryListener = ((obs, old, selected) -> {
            System.out.println("Entry selection changed. old: " + old + " new: "
                    + selected);

            if (selected == null) {
                System.out.println("No entry selected");
                return;
            }
            
            modelAccess.setSelectedEntry(selected);
            
            // TODO näiden asetus muualle, päivitys kun merkintä muuttuu
            startDate.set(selected.getStartDate());
            startTime.set(selected.getStartTime());
            endDate.set(selected.getEndDate());
            endTime.set(selected.getEndTime());
        });

        // asetetaan äsken luotu kuuntelija
        projectEntryList.getSelectionModel().selectedItemProperty()
                .addListener(selectedEntryListener);

//        // luodaan kuuntelija reagoimaan valitun projektin vaihtumiseen
//        ChangeListener<Project> selectedProjectListener = ((obs, old,
//                selected) -> {
//            if (selected == null) {
//                System.out.println("No project selected");
//                return;
//            }
//
//            // poistetaan kuuntelija listan päivityksen ajaksi, voi olla turha?
//            projectEntryList.getSelectionModel().selectedItemProperty()
//                    .removeListener(selectedEntryListener);
//
//            // päivitetään uuden projektin merkinnät listalle
//            ObservableList<Entry> newEntries = FXCollections.observableArrayList(modelAccess.getEntryDAO().list(selected));
//            projectEntryList.setItems(newEntries);
//
//            // palautetaan kuuntelija paikalleen
//            projectEntryList.getSelectionModel().selectedItemProperty()
//                    .addListener(selectedEntryListener);
//        });
//
//        // asetetaan äsken luotu kuuntelija model accessiin
//        modelAccess.selectedProjectProperty()
//                .addListener(selectedProjectListener);

        // sidotaan tekstikentän sisältö valitun merkinnän aikaleimojen dataan
        
        entryStartField.textProperty().bind(new StringBinding() {

            {
                bind(startDate, startTime);
            }

            @Override
            protected String computeValue() {
                try {
                    return LocalDateTime.of(startDate.get(), startTime.get())
                            .format(formatter);
                } catch (NullPointerException e) {
                    return "";
                }

            }

        });

        // sidotaan tekstikentän sisältö valitun merkinnän aikaleimojen dataan
        entryEndField.textProperty().bind(new StringBinding() {

            {
                bind(endDate, endTime);
            }

            @Override
            protected String computeValue() {
                try {
                    return LocalDateTime.of(endDate.get(), endTime.get())
                            .format(formatter);
                } catch (NullPointerException e) {
                    return "";
                }
            }

        });

        // sidotaan tekstikentän sisältö valitun merkinnän aikaleimojen dataan
        entryDurationField.textProperty().bind(new StringBinding() {

            {
                bind(entryStartField.textProperty(),
                        entryEndField.textProperty());
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


    /**
     * Kutsutaan kun lisätään uusi merkintä. Poistetaan aluksi bindaus modelAccessista,
     * jotta voidaan luoda uusi merkintä ja asettaa se valituksi
     */
    @FXML
    void handleAddEntry() {

        modelAccess.setEditedEntry(modelAccess.newEntry());
        
        WindowController wc = ViewFactory.createEditEntryDialog();

        // TODO tämä uusiksi! Kaksisuuntainen bindaus valinnan välille? Muutoksen validointi boolean isChanged tjsp?
        wc.getStage().setOnCloseRequest((e) -> {
            projectEntryList.getSelectionModel().select(modelAccess.getSelectedEntry());

            modelAccess.setEditedEntry(null);
            
            projectEntryList.fireEvent(new UpdateEvent());
        });

    }


    @FXML
    void handleEditEntry() {
        modelAccess.setEditedEntry(modelAccess.getSelectedEntry());
        WindowController wc = ViewFactory.createEditEntryDialog();
        
        wc.getStage().setOnCloseRequest((e) -> {
            projectEntryList.getSelectionModel().select(modelAccess.getSelectedEntry());
            
            modelAccess.setEditedEntry(null);
            
            projectEntryList.fireEvent(new UpdateEvent());
        });
    }


    @FXML
    void handleRemoveEntry() {
        WindowController wc = ViewFactory.createDeleteEntryDialog();

        wc.getStage().setOnCloseRequest((e) -> {
            projectEntryList.fireEvent(new UpdateEvent());
        });
    }

}