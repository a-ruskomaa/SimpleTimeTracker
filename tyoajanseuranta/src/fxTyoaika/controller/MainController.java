package fxTyoaika.controller;

import java.util.LinkedList;

import fxTyoaika.controller.mainTabs.ProjectTabController;
import fxTyoaika.controller.mainTabs.TimerTabController;
import fxTyoaika.model.Entry;
import fxTyoaika.model.ModelAccess;
import fxTyoaika.model.Project;
import fxTyoaika.model.Timer;
import fxTyoaika.view.ViewFactory;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * @author aleks
 * @version 27 Jan 2020
 *
 * Pääikkunan kontrolleriluokka.
 */
public class MainController extends AbstractController  {
    
    private TimerTabController timerTabController;
    private ProjectTabController projectTabController;
    
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
     */
    public MainController(ModelAccess modelAccess) {
        super(modelAccess);
    }


    /**
     * Alustaa pääikkunan näkymän. Asettaa valitun projektin merkinnät näkyville.
     */
    public void initialize() {
        
        timerTab.setContent(ViewFactory.createTimerTab());
        projectTab.setContent(ViewFactory.createProjecTab());
        
        /*
         * Lisätään alasvetovalikkoon valitun käyttäjän projektit, valitaan oikea projekti valmiiksi ja
         * luodaan tapahtumankäsittelijä projektin vaihtamiselle
         */
        projectChoiceBox.setItems(modelAccess.getSelectedUser().getProjects());
        
        projectChoiceBox.getSelectionModel().select(modelAccess.getSelectedProject());
        
        projectChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
//            modelAccess.setSelectedProject(projectChoiceBox.getSelectionModel().getSelectedItem());
            modelAccess.setSelectedProject(selected);
            updateTotalTime();
        });
            
        
//        projectChoiceBox.setOnAction(e -> {
//            modelAccess.setSelectedProject(projectChoiceBox.getSelectionModel().getSelectedItem());
//            updateTotalTime();
//        });
        
        
        modelAccess.getSelectedProject().getEntries().addListener((ListChangeListener<Entry>) c ->  updateTotalTime());
        
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

        ObservableList<Entry> entries = modelAccess.getSelectedProject().getEntries();
        
        long totalTime = 0L;
        
        for (Entry entry : entries) {
            totalTime += entry.getDuration();
        }
        
        totalProjectEntriesField.setText(String.format("%dh %02dmin", totalTime / 3600, (totalTime % 3600) / 60));

    }

}
