package roarusko.simpleTimeTracker.controller.main.tabs;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ListIterator;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.view.ViewFactory;
import roarusko.simpleTimeTracker.controller.WindowController;
import roarusko.simpleTimeTracker.controller.main.MainController;
import roarusko.simpleTimeTracker.controller.main.UpdateEvent;
import roarusko.simpleTimeTracker.controller.main.dialogs.DeleteEntryDialogController;
import roarusko.simpleTimeTracker.controller.main.dialogs.EditEntryDialogController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.DataObject;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.utility.Entries;
import roarusko.simpleTimeTracker.view.components.BindableListView;
import roarusko.simpleTimeTracker.view.components.DateTimeField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
    private BindableListView<Entry> entryListView;

    @FXML
    private TreeView<Entry> entryTreeView;

    @FXML
    private DateTimeField entryStartField;

    @FXML
    private DateTimeField entryEndField;

    @FXML
    private TextField entryDurationField;

    @FXML
    private Button editEntryButton;

    @FXML
    private Button deleteEntryButton;

    @FXML
    private Button addEntryButton;

    private final LongProperty duration = new SimpleLongProperty();

    DateTimeFormatter formatter = Entries.getDateTimeFormatter();

    /**
     * @param dataAccess dataAccess
     * @param stage stage jota kontrolloidaan
     */
    public ProjectTabController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);
    }


    /**
     * @param dataAccess dataAccess
     * @param stage stage jota kontrolloidaan
     * @param parentController asd
     */
    public ProjectTabController(DataAccess dataAccess, Stage stage,
            MainController parentController) {
        super(dataAccess, stage);
        this.parentController = parentController;
    }


    /**
     * 
     */
    public void initialize() {

        // haetaan valitun projektin merkinnät listalle, järjestetään päivämäärän mukaan uusin ylimmäksi
        entryListView.setItems(parentController.selectedProject_EntriesProperty().sorted((first, second) -> {
            return second.getStartDateTime().compareTo(first.getStartDateTime()); 
        }));
        
        entryListView.valueProperty().bindBidirectional(parentController.selectedEntryProperty());

        editEntryButton.disableProperty().bind(entryListView.valueProperty().isNull());
        deleteEntryButton.disableProperty().bind(entryListView.valueProperty().isNull());
        
        entryStartField.valueProperty().bind(Bindings.createObjectBinding(() -> {
            Entry entry = entryListView.valueProperty().get();
            return entry != null ? entry.getStartDateTime() : null;
        }, entryListView.valueProperty()));
        
        entryEndField.valueProperty().bind(Bindings.createObjectBinding(() -> {
            Entry entry = entryListView.valueProperty().get();
            return entry != null ? entry.getEndDateTime() : null;
        }, entryListView.valueProperty()));

        // sidotaan tekstikentän sisältö valitun merkinnän aikaleimojen dataan
        entryDurationField.textProperty().bind(Bindings.createStringBinding(() -> {
            Entry entry = entryListView.valueProperty().get();
            return entry != null ? Entries.getDurationAsString(entry.getStartDateTime(), entry.getEndDateTime()) : null;
        }, entryListView.valueProperty()));

        // pakotetaan merkinnän tiedot näyttävät kentät päivittymään valitsemalla sama merkintä uusiksi
        this.stage.addEventHandler(UpdateEvent.UPDATE_EVENT, (EventHandler<? super UpdateEvent>) (UpdateEvent e) -> {
            System.out.println("Event handled in project tab: " + e.toString());
            int current = entryListView.getSelectionModel().getSelectedIndex();
            entryListView.getSelectionModel().select(null);
            entryListView.getSelectionModel().select(current);
        }
   );
    }


    /**
     * Kutsutaan kun lisätään uusi merkintä.
     */
    @FXML
    void handleAddEntry() {
        Entry entry = dataAccess.newEntry(parentController.selectedProjectProperty().get());
        
        EditEntryDialogController controller = ViewFactory.createEditEntryDialog(dataAccess);
        controller.setEntry(entry);
        controller.setEntryList(parentController.selectedProject_EntriesProperty());

        controller.getStage().setOnCloseRequest((e) -> {
            
            if (entry.getId() != -1) {
                parentController.selectedProject_EntriesProperty().add(entry);
                parentController.selectedEntryProperty().set(entry);
            }
            
            entryListView.fireEvent(new UpdateEvent());
        });

        controller.showModalStage();
        
    }


    @FXML
    void handleEditEntry() {
        Entry entry = entryListView.valueProperty().get();
        
        EditEntryDialogController controller = ViewFactory.createEditEntryDialog(dataAccess);
        controller.setEntry(entry);
        controller.setEntryList(parentController.selectedProject_EntriesProperty());
        
        controller.getStage().setOnCloseRequest((e) -> {
            
            if (controller.wasUpdated()) {
                int index = findIndexById(entry, parentController.selectedProject_EntriesProperty());
                parentController.selectedProject_EntriesProperty().set(index, entry);
                parentController.selectedEntryProperty().set(entry);
            }
            
            entryListView.fireEvent(new UpdateEvent());
        });
        
        controller.showModalStage();
        
    }


    @FXML
    void handleRemoveEntry() {
        Entry entry = entryListView.valueProperty().get();
        DeleteEntryDialogController controller = ViewFactory.createDeleteEntryDialog(dataAccess);
        controller.setEntry(entry);
        
        controller.getStage().setOnCloseRequest((e) -> {
            if (controller.wasDeleted()) {
                parentController.selectedProject_EntriesProperty().remove(entry);
                parentController.selectedEntryProperty().set(null);
                
                entryListView.fireEvent(new UpdateEvent());
            }
        });

        controller.showModalStage();
    }
    

    /**
     * Apumetodi, joka hakee listalta valitun DataObjectin indeksin. Palauttaa -1 jos ei löydy. Siirretään myöhemmin
     * toiseen luokkaan kun projektien ja käyttäjien muokkaaminen mahdollistuu.
     * @param object Objekti jonka indeksi listalla halutaan selvittää
     * @param list Lista jolta etsitään
     * @return Palauttaa indeksin tai -1 jos ei löydy
     */
    private int findIndexById(DataObject object,
            List<? extends DataObject> list) {
        ListIterator<? extends DataObject> iterator = list.listIterator();

        while (iterator.hasNext()) {
            int index = iterator.nextIndex();
            if (iterator.next().getId() == object.getId())
                return index;
        }

        return -1;
    }


}
