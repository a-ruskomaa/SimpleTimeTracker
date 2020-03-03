package roarusko.simpleTimeTracker.controller.main;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.view.ViewFactory;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.Entries;
import roarusko.simpleTimeTracker.model.utility.EntryTimer;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author aleks
 * @version 27 Jan 2020
 *
 * Päänäkymän kontrolleriluokka. Päänäkymä sisältää alueen, jossa on valittuna olevan käyttäjän
 * projektit sisältävä alasvetovalikko sekä projektin kokonaiskeston näyttävä tekstikenttä. Myös ajastimen
 * kesto näytetään tällä alueella.
 * 
 * Päänäkymän kontrolleri ylläpitää ohjelman tilaa, ja sisältää propertyt joista muut kontrollerit
 * voivat selvittää valittuna olevan käyttäjän, projektin ja merkinnän sekä listat jotka sisältävät
 * valitun käyttäjän projektit sekä valitun projektin merkinnät. Käyttöliittymäkomponentit sidotaan suoraan
 * näihin propertyihin. Esimerkiksi 
 * 
 * Päänäkymään liitetään välilehtiä, joiden kontrollerit tarjoavat spesifimpää toiminnallisuutta omien
 * vastuualueidensa mukaisesti.
 */
public class MainController extends AbstractController {

    // private TimerTabController timerTabController;
    // private ProjectTabController projectTabController;

    private ListProperty<User> allUsers = new SimpleListProperty<User>();

    private ObjectProperty<User> selectedUser = new SimpleObjectProperty<User>();

    private ListProperty<Project> selectedUser_Projects = new SimpleListProperty<Project>();

    private ObjectProperty<Project> selectedProject = new SimpleObjectProperty<Project>();

    private ListProperty<Entry> selectedProject_Entries = new SimpleListProperty<Entry>();

    private ObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<Entry>();

    private EntryTimer timer;

    @FXML
    private Tab timerTab;

    @FXML
    private Tab projectTab;

    @FXML
    private ChoiceBox<Project> projectChoiceBox;

    @FXML
    private TextField totalTimeField;

    @FXML
    private TextField timerDurationField;

    /**
     * Pääikkunan kontrollerin konstruktori. Tallettaa viitteen modelaccessiin yläluokan attribuutiksi.
     * @param dataAccess viite pääohjelmassa luotuun dataAccessiin
     * @param stage stage jota kontrolloidaan
     */
    public MainController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);

        this.timer = new EntryTimer(dataAccess);
    }


    /**
     * Alustaa pääikkunan näkymän. Asettaa valitun projektin merkinnät näkyville ja sitoo käyttöliittymän komponentit
     * propertyihin tallennettuun dataan.
     */
    public void initialize() {

        // asetetaan valintalaatikon sisältö vastaamaan valitun käyttäjän
        // projekteja
        projectChoiceBox.setItems(this.selectedUser_Projects);

        // bindataan valitun projektin käärivä property ja valintalaatikon
        // valinta
        projectChoiceBox.valueProperty()
                .bindBidirectional(this.selectedProject);

        // luodaan sisältö välilehtiin
        timerTab.setContent(ViewFactory.createTimerTab(this, dataAccess));
        projectTab.setContent(ViewFactory.createProjecTab(this, dataAccess));

        /*
         * Asetetaan tapahtumankäsittelijä lataamaan kaikki projektiin kuuluvat
         * tapahtumat projektin vaihtuessa. Valitaan automaattisesti projektin
         * ensimmäinen merkintä ja päivitetaan myös projektin kokonaiskesto
         */
        //
        projectChoiceBox.valueProperty().addListener((prop, oldV, newV) -> {
            if (newV != null) {
                selectedProject_Entries.set(dataAccess.loadEntries(newV));
                // selectedEntry.set(selectedProject_Entries.isEmpty() ? null :
                // selectedProject_Entries.get(0));
                updateTotalTime();
            }
        });

        /*
         * Asetetaan tapahtumankäsittelijä kuuntelemaan projektivälilehden
         * päivitystapahtumia. Tapahtuma luodaan aina kun olemassa olevaa
         * merkintää muokataan. Tapahtuman perusteella päänäkymän kontrolleri
         * osaa tarvittaessa päivittää näyttämänsä kokonaiskeston.
         */
        projectTab.getContent().addEventHandler(UpdateEvent.UPDATE_EVENT,
                (UpdateEvent e) -> {
                    System.out.println("Event handled: " + e.toString());
                    updateTotalTime();
                });
        
        // asetetaan ajastinkenttä näyttämään ajastimen kesto
        timerDurationField.textProperty().bind(timer.elapsedTimeProperty().asString());

    }


    /**
     * Päivittää projektiin kuuluvien merkintöjen kokonaiskeston näkyville
     */
    private void updateTotalTime() {
        long time = calculateTotalTime(this.selectedProject_Entries.get());

        totalTimeField.setText(Entries.getDurationAsString(time));

    }


    /**
     * Laskee annetulla listalla olevien merkintöjen yhteiskeston
     * @param list Lista jonka merkintöjä tutkitaan
     * @return Palauttaa kokonaiskeston sekunneissa.
     */
    private long calculateTotalTime(List<Entry> list) {
        long totalTime = 0L;

        for (Entry entry : list) {
            totalTime += Entries.getDurationAsLong(entry.getStartDateTime(),
                    entry.getEndDateTime());
        }

        return totalTime;
    }

    // Propertyjen getterit


    /**
     * Palauttaa valitun käyttäjän sisältävän propertyn
     * @return Palauttaa valitun käyttäjän sisältävän propertyn
     */
    public ObjectProperty<User> selectedUserProperty() {
        return this.selectedUser;
    }


    /**
     * Palauttaa valitun projektin sisältävän propertyn
     * @return Palauttaa valitun projektin sisältävän propertyn
     */
    public ObjectProperty<Project> selectedProjectProperty() {
        return this.selectedProject;
    }


    /**
     * Palauttaa valitun merkinnän sisältävän propertyn
     * @return Palauttaa valitun merkinnän sisältävän propertyn
     */
    public ObjectProperty<Entry> selectedEntryProperty() {
        return this.selectedEntry;
    }


    /**
     * Palauttaa kaikki käyttäjät sisältävän listpropertyn
     * @return Palauttaa kaikki käyttäjät sisältävän propertyn
     */
    public ListProperty<User> allUsersProperty() {
        return this.allUsers;
    }


    /**
     * Palauttaa valitun käyttäjän kaikki projektit sisältävän listpropertyn
     * @return Palauttaa valitun käyttäjän kaikki projektit sisältävän propertyn
     */
    public ListProperty<Project> selectedUser_ProjectsProperty() {
        return this.selectedUser_Projects;
    }


    /**
     * Palauttaa valitun projektin kaikki merkinnät sisältävän listpropertyn
     * @return Palauttaa valitun projektin kaikki merkinnät sisältävän propertyn
     */
    public ListProperty<Entry> selectedProject_EntriesProperty() {
        return this.selectedProject_Entries;
    }


    /**
     * EntryTimerin getteri
     * @return palauttaa EntryTimerin
     */
    public EntryTimer getTimer() {
        return this.timer;
    }

}
