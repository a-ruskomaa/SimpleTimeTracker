package roarusko.simpleTimeTracker.controller.main.tabs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ListIterator;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.view.ViewFactory;
import roarusko.simpleTimeTracker.controller.main.MainController;
import roarusko.simpleTimeTracker.controller.main.UpdateEvent;
import roarusko.simpleTimeTracker.controller.main.dialogs.DeleteEntryDialogController;
import roarusko.simpleTimeTracker.controller.main.dialogs.EditEntryDialogController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.DataObject;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.utility.Entries;
import roarusko.simpleTimeTracker.view.components.BindableListView;
import roarusko.simpleTimeTracker.view.components.DateTimeField;
import roarusko.simpleTimeTracker.view.components.EnhancedDatePicker;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Projektimerkintöjä käsittelevän välilehden kontrolleriluokka. Välilehdellä voi 
 * tarkastella kaikkia projektiin kuuluvia merkintöjä. Myös lisäys-, muokkaus- ja 
 * poisto-operaatiot suoritetaan täällä.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class ProjectTabController extends AbstractController {

    DateTimeFormatter formatter = Entries.getDateTimeFormatter();

    private MainController parentController;

    @FXML
    private BindableListView<Entry> entryListView;

    @FXML
    private DateTimeField entryStartField;

    @FXML
    private DateTimeField entryEndField;

    @FXML
    private TextField entryDurationField;
    
    @FXML
    private EnhancedDatePicker startDatePicker;

    @FXML
    private EnhancedDatePicker endDatePicker;

    @FXML
    private Button editEntryButton;

    @FXML
    private Button deleteEntryButton;

    @FXML
    private Button addEntryButton;
    
    @FXML
    private Button clearFilterButton;
    
    FilteredList<Entry> filteredEntries;
    
    SortedList<Entry> sortedEntries;

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
     * Projektivälilehden alustusoperaatiot. Sidotaan käyttöliittymäkomponentit
     * reagoimaan automaattisesti valitun projektin tai merkinnän vaihtumiseen.
     */
    public void initialize() {
        // pakataan merkinnät FilteredListille, asetetaan predikaatiksi null
        filteredEntries = new FilteredList<Entry>(parentController.selectedProject_EntriesProperty(), null);
        
        // pakataan suodatetut merkinnät SortedListille, järjestetään päivämäärän mukaan uusin ylimmäksi
        sortedEntries = new SortedList<Entry>(filteredEntries, (first, second) -> {
            return second.getStartDateTime().compareTo(first.getStartDateTime()); 
        });

        // lisätään suodatetut ja järjestetyt merkinnät näkyville
        entryListView.setItems(sortedEntries);
        
        // sidotaan listan valinta sekä maincontrollerin valintaproperty toisiinsa
        entryListView.valueProperty().bindBidirectional(parentController.selectedEntryProperty());

        // estetään muokkaus- ja poistonappien painaminen kun merkintää ei ole valittuna
        editEntryButton.disableProperty().bind(entryListView.valueProperty().isNull());
        deleteEntryButton.disableProperty().bind(entryListView.valueProperty().isNull());
        
        // näytetään valitun merkinnän alkuaika
        entryStartField.valueProperty().bind(Bindings.createObjectBinding(() -> {
            Entry entry = entryListView.valueProperty().get();
            return entry != null ? entry.getStartDateTime() : null;
        }, entryListView.valueProperty()));
        
        // näytetään valitun merkinnän loppuaika
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
        });
        
        // lisätään tapahtumankäsittelijät reagoimaan rajausehtojen muuttumiseen
        startDatePicker.valueProperty().addListener((i) -> setFilter());
        endDatePicker.valueProperty().addListener((i) -> setFilter());
   
    }
    
    
    /**
     * Metodia kutsutaan, kun näytettävien merkintöjen rajausehtona käytetyn alku- tai loppupäivämäärän
     * valinta muuttuu. Ensimmäinen toteutus, tätä hiotaan vielä elegantimmaksi.
     */
    private void setFilter() {
        filteredEntries.setPredicate((entry) -> { 
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();
            return (start == null || entry.getStartDate().isAfter(start)) &&
                (end == null || entry.getEndDate().isBefore(end));
        });
    }
    
    
    /**
     * Tyhjentää näytettävien merkintöjen rajaamiseen käytetyt datepickerit
     */
    @FXML
    void handleClearFilter() {
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }


    /**
     * "Lisää uusi"-painikkeen tapahtumankäsittelijä
     */
    @FXML
    void handleAddEntry() {
        // haetaan dataAccessilta uusi merkintä, joka on liitetty valittuna olevaan projektiin
        Entry entry = dataAccess.newEntry(parentController.selectedProjectProperty().get());
        
        // luodaan merkinnän tallennukseen käytettävä dialogi, välitetään dialogille äsken luotu merkintä
        // sekä lista joka sisältää kaikki merkinnät
        EditEntryDialogController controller = ViewFactory.createEditEntryDialog(dataAccess);
        controller.setEntry(entry);
        controller.setEntryList(parentController.selectedProject_EntriesProperty());

        // kutsutaan dialogin sulkeutuessa, oikeastaan tarpeeton nyt kun dialogi on modaalinen
        controller.getStage().setOnCloseRequest((e) -> {
            // jos merkinnän id on päivittynyt (eli merkintä on tallennettu pysyvästi), lisätään
            // uusi merkintä myös käyttöliittymään
            if (entry.getId() != -1) {
                parentController.selectedProject_EntriesProperty().add(entry);
                parentController.selectedEntryProperty().set(entry);

                // luodaan tapahtuma joka saa aikaan käyttöliittymän päivittämisen
                this.getStage().fireEvent(new UpdateEvent());
            }
        });

        controller.showModalStage();
        
    }


    /**
     * "Muokkaa"-painikkeen tapahtumankäsittelijä
     */
    @FXML
    void handleEditEntry() {
        // haetaan viite valittuna olevaan merkintään
        Entry entry = entryListView.valueProperty().get();
        
        // luodaan merkinnän tallennukseen käytettävä dialogi, välitetään dialogille äsken haettu merkintä
        // sekä lista joka sisältää kaikki merkinnät
        EditEntryDialogController controller = ViewFactory.createEditEntryDialog(dataAccess);
        controller.setEntry(entry);
        controller.setEntryList(parentController.selectedProject_EntriesProperty());

        // kutsutaan dialogin sulkeutuessa, oikeastaan tarpeeton nyt kun dialogi on modaalinen
        controller.getStage().setOnCloseRequest((e) -> {
            
            if (controller.wasUpdated()) {
                // jos merkintää päivitettiin, päivitetään myös lista ja kokonaiskesto
                int index = findIndexById(entry, parentController.selectedProject_EntriesProperty());
                parentController.selectedProject_EntriesProperty().set(index, entry);
                parentController.selectedEntryProperty().set(entry);

                // luodaan tapahtuma joka saa aikaan käyttöliittymän päivittämisen
                this.getStage().fireEvent(new UpdateEvent());
            }
            
        });
        
        controller.showModalStage();
        
    }

    /**
     * "Poista"-painikkeen tapahtumankäsittelijä
     */
    @FXML
    void handleRemoveEntry() {
        // haetaan viite valittuna olevaan merkintään
        Entry entry = entryListView.valueProperty().get();
        
        // luodaan dialogi, jossa varmistetaan merkinnän poistaminen
        DeleteEntryDialogController controller = ViewFactory.createDeleteEntryDialog(dataAccess);
        controller.setEntry(entry);

        // kutsutaan dialogin sulkeutuessa, oikeastaan tarpeeton nyt kun dialogi on modaalinen
        controller.getStage().setOnCloseRequest((e) -> {
            if (controller.wasDeleted()) {
                // jos merkintä poistettiin pysyvästä muistista, poistetaan myös käyttöliittymän listalta
                parentController.selectedProject_EntriesProperty().remove(entry);
                parentController.selectedEntryProperty().set(null);

                // luodaan tapahtuma joka saa aikaan käyttöliittymän päivittämisen
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
