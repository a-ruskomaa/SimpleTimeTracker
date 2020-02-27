package roarusko.simpleTimeTracker.controller.main;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.controller.ViewFactory;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.Entry;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import roarusko.simpleTimeTracker.model.utility.EntryWrapper;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author aleks
 * @version 27 Jan 2020
 *
 * Pääikkunan kontrolleriluokka.
 */
public class MainController extends AbstractController  {
    
    private TimerTabController timerTabController;
    private ProjectTabController projectTabController;
    
    private ListProperty<User> allUsers = new SimpleListProperty<User>();
    
    private ObjectProperty<User> selectedUser = new SimpleObjectProperty<User>();
    
    private ListProperty<Project> selectedUser_Projects = new SimpleListProperty<Project>();
        
    private ObjectProperty<Project> selectedProject = new SimpleObjectProperty<Project>();
    
    private ListProperty<Entry> selectedProject_Entries = new SimpleListProperty<Entry>();
    
    private ObjectProperty<Entry> selectedEntry = new SimpleObjectProperty<Entry>();
    
    
    
    @FXML
    private Tab timerTab;
    
    @FXML
    private Tab projectTab;

    @FXML
    private TextField manualStartField;

    @FXML
    private ChoiceBox<Project> projectChoiceBox;

    @FXML
    private TextField totalProjectEntriesField;

    @FXML
    private TextField timerDurationTopField;
    


    /**
     * Pääikkunan kontrollerin konstruktori. Tallettaa viitteen modelaccessiin yläluokan attribuutiksi.
     * @param dataAccess viite pääohjelmassa luotuun dataAccessiin
     * @param stage stage jota kontrolloidaan
     */
    public MainController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);
    }


    /**
     * Alustaa pääikkunan näkymän. Asettaa valitun projektin merkinnät näkyville.
     */
    public void initialize() {
        
        
        projectChoiceBox.setItems(this.selectedUser_Projects);
        
        projectChoiceBox.valueProperty().bindBidirectional(this.selectedProject);
        
        

        timerTab.setContent(ViewFactory.createTimerTab(this, dataAccess));
        projectTab.setContent(ViewFactory.createProjecTab(this, dataAccess));
        
        
        projectChoiceBox.valueProperty().addListener((prop, oldV, newV) -> {
            if (newV != null) {
                selectedProject_Entries.set(dataAccess.loadEntries(newV));
                selectedEntry.set(selectedProject_Entries.isEmpty() ? null : selectedProject_Entries.get(0));
                updateTotalTime();
            }
        });
               
        
        
        projectTab.getContent().addEventHandler(UpdateEvent.UPDATE_EVENT, new EventHandler<UpdateEvent>() {

            @Override
            public void handle(UpdateEvent event) {
                System.out.println("Event handled: " + event.toString());
                updateTotalTime();
            }
        });
        
        
        
//        projectEntryList.setCellFactory(new Callback<ListView<Entry>, ListCell<Entry>>(){
// 
//            @Override
//            public ListCell<Entry> call(ListView<Entry> p) {
//                 
//                ListCell<Entry> cell = new ListCell<Entry>(){
// 
//                    @Override
//                    protected void updateItem(Entry item, boolean empty) {
//                        super.updateItem(item, empty);
//
//                        if (empty || item == null) {
//                            setText(null);
//                            setGraphic(null);
//                        } else {
//                            setText(item.toString());
//                        }
//                    }
//                    
// 
//                };
//                 
//                return cell;
//            }
//        });

    }


    private void updateTotalTime() {
        
        System.out.println("päivitetään kokonaisaika");

        long totalTime = 0L;
        
        for (Entry entry : this.selectedProject_Entries.get()) {
            totalTime += entry.getDuration();
        }
        
        totalProjectEntriesField.setText(String.format("%dh %02dmin", totalTime / 3600, (totalTime % 3600) / 60));

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
     * Palauttaa kaikki käyttäjät sisältävän propertyn
     * @return Palauttaa kaikki käyttäjät sisältävän propertyn
     */
    public ListProperty<User> allUsersProperty() {
        return this.allUsers;
    }


    /**
     * Palauttaa valitun käyttäjän kaikki projektit sisältävän propertyn
     * @return Palauttaa valitun käyttäjän kaikki projektit sisältävän propertyn
     */
    public ListProperty<Project> selectedUser_ProjectsProperty() {
        return this.selectedUser_Projects;
    }

    /**
     * Palauttaa valitun projektin kaikki merkinnät sisältävän propertyn
     * @return Palauttaa valitun projektin kaikki merkinnät sisältävän propertyn
     */
    public ListProperty<Entry> selectedProject_EntriesProperty() {
        return this.selectedProject_Entries;
    }


}
