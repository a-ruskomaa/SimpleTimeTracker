package fxTyoaika.controller.main;


import java.util.ArrayList;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.ModelAccess;
import fxTyoaika.controller.ViewFactory;
import fxTyoaika.model.Entry;
import fxTyoaika.model.Project;
import fxTyoaika.model.Timer;
import fxTyoaika.model.User;
import javafx.collections.FXCollections;
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
    private Timer timer;
    private User user;
    private ObservableList<Project> projects;
    private ObservableList<Entry> entries;
    
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
     * Pääikkunan kontrollerin konstruktori. Tallettaa viitteen modelaccessiin yläluokan kenttään.
     * @param modelAccess viite pääohjelmassa luotuun modelAccessiin
     * @param stage stage jota kontrolloidaan
     */
    public MainController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
        this.timer = new Timer(modelAccess);
        this.user = modelAccess.getSelectedUser();
    }


    /**
     * Alustaa pääikkunan näkymän. Asettaa valitun projektin merkinnät näkyville.
     */
    public void initialize() {

        this.projects = modelAccess.loadProjects();
        
        Project selectedProject = modelAccess.getSelectedProject();
        
        projectChoiceBox.setItems(projects);
        
        projectChoiceBox.getSelectionModel().select(selectedProject);
        
        modelAccess.selectedProjectProperty().bind(projectChoiceBox.getSelectionModel().selectedItemProperty());
        
        this.entries = modelAccess.loadEntries();

        timerTab.setContent(ViewFactory.createTimerTab(this));
        projectTab.setContent(ViewFactory.createProjecTab(this));
        
        /*
         * Lisätään alasvetovalikkoon valitun käyttäjän projektit, valitaan oikea projekti valmiiksi ja
         * luodaan tapahtumankäsittelijä joka reagoi kun projektia vaihdetaan
         */
        
        
        
        
        projectTab.getContent().addEventHandler(UpdateEvent.UPDATE_EVENT, new EventHandler<UpdateEvent>() {

            @Override
            public void handle(UpdateEvent event) {
                // TODO Auto-generated method stub
                System.out.println("Event handled: " + event.toString());
                updateTotalTime();
            }
        });
        
       
        // Reagoi jos listaan tehdään muutoksia
//        ListChangeListener<Entry> entryListChangeListener = new WeakListChangeListener<Entry>(c ->  updateTotalTime());
        
        
//        ChangeListener<Number> durationChangeListener = new WeakChangeListener<Number>((obs, old, selected) -> updateTotalTime());
        
//        ChangeListener<Entry> entryChangeListener = new WeakChangeListener<Entry>((obs, old, selected) -> {
//            old.durationProperty().removeListener(durationChangeListener);
//            selected.durationProperty().addListener(durationChangeListener);
//        });
        
        // Tehdään jos valittu projekti vaihtuu
        modelAccess.selectedProjectProperty().addListener((obs, old, selected) -> {
            updateTotalTime();
            
            

            
//            modelAccess.selectedEntryProperty().addListener(entryChangeListener);
            
            
//            old.getEntries().removeListener(entryListChangeListener);
//            // Jos valittuun projektiiin lisätään merkintöjä
//            selected.getEntries().addListener(entryListChangeListener);
        });
              
        
//        modelAccess.getSelectedEntry().durationProperty().add
        
        // TODO lisää tapahtumankäsittelijä joka päivittää ajan jos jotain merkintää muokataan!
        // kannattanee tehdä luomalla projektiin totalDurationProperty ja bindaamalla se
        
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

        ArrayList<Entry> entries = (ArrayList<Entry>) modelAccess.getSelectedProject().getEntries();
        
        long totalTime = 0L;
        
        for (Entry entry : entries) {
            totalTime += entry.getDuration();
        }
        
        totalProjectEntriesField.setText(String.format("%dh %02dmin", totalTime / 3600, (totalTime % 3600) / 60));

    }
    
    
    /**
     * Ajastimen getteri
     * @return palauttaa viitteen ohjelman ajastimeen
     */
    public Timer getTimer() {
        return this.timer;
    }


}
