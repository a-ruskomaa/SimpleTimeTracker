package roarusko.simpleTimeTracker.controller.main;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.controller.ViewFactory;
import roarusko.simpleTimeTracker.model.ModelAccess;
import roarusko.simpleTimeTracker.model.domainModel.Entry;
import roarusko.simpleTimeTracker.model.domainModel.Project;
import roarusko.simpleTimeTracker.model.domainModel.User;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
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
    private User user;
    private ListProperty<Entry> selectedProjectEntries = new SimpleListProperty<Entry>();
    
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
     * @param modelAccess viite pääohjelmassa luotuun modelAccessiin
     * @param stage stage jota kontrolloidaan
     */
    public MainController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
        this.user = modelAccess.getSelectedUser();
    }


    /**
     * Alustaa pääikkunan näkymän. Asettaa valitun projektin merkinnät näkyville.
     */
    public void initialize() {
        
        /*
         * Lisätään alasvetovalikkoon valitun käyttäjän projektit ja sidotaan modelAccessin propertyyn.
         * Kun alasvetovalikosta vaihdetaan valittua projektia, päivittyy se myös modelAccessiin.
         * Sama toimisi myös päinvastaiseen suuntaan, mutta tätä ei toistaiseksi hyödynnetä.
         */
        projectChoiceBox.setItems(modelAccess.selectedUserProjectsProperty());
        
        projectChoiceBox.valueProperty().bindBidirectional(modelAccess.selectedProjectProperty());
        
        //Liitetään tämän luokan propertyyn
        this.selectedProjectEntries.set(modelAccess.selectedProjectEntriesProperty());

        timerTab.setContent(ViewFactory.createTimerTab(this));
        projectTab.setContent(ViewFactory.createProjecTab(this));
        
        modelAccess.selectedProjectEntriesProperty().addListener(new InvalidationListener() {
            
            @Override
            public void invalidated(Observable observable) {
                updateTotalTime();
                
            }
        });
        
        
        projectTab.getContent().addEventHandler(UpdateEvent.UPDATE_EVENT, new EventHandler<UpdateEvent>() {

            @Override
            public void handle(UpdateEvent event) {
                // TODO Auto-generated method stub
                System.out.println("Event handled: " + event.toString());
                updateTotalTime();
            }
        });
        
        

        
        
        updateTotalTime();
        
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
        
        System.out.println("päivitetään kokonaisaika projektiin: " + this.selectedProjectEntries.get().get(0).getOwner());

        long totalTime = 0L;
        
        for (Entry entry : this.selectedProjectEntries.get()) {
            totalTime += entry.getDuration();
        }
        
        totalProjectEntriesField.setText(String.format("%dh %02dmin", totalTime / 3600, (totalTime % 3600) / 60));

    }
    
    



}
