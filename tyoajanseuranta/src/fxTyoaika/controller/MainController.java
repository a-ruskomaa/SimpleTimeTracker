package fxTyoaika.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fxTyoaika.model.Entry;
import fxTyoaika.model.Project;
import fxTyoaika.model.Timer;
import fxTyoaika.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * @author aleks
 * @version 27 Jan 2020
 *
 * Pääikkunan kontrolleriluokka.
 */
public class MainController extends AbstractController  {

    @FXML
    private TextField timerStartField;

    @FXML
    private TextField timerEndField;

    @FXML
    private TextField timerDurationField;

    @FXML
    private Button timerToggleButton;

    @FXML
    private Button timerSaveButton;

    @FXML
    private Button timerResetButton;

    @FXML
    private TextField manualStartField;

    @FXML
    private TextField manualEndField;

    @FXML
    private TextField manualDurationField;

    @FXML
    private Button manualSaveButton;

    @FXML
    private ListView<Entry> projectEntryList;

    @FXML
    private TextField viewEntryStartField;

    @FXML
    private TextField viewEntryiEndField;

    @FXML
    private TextField viewEntryDurationField;

    @FXML
    private Button editEntryButton;

    @FXML
    private Button removeEntryButton;

    @FXML
    private Button addEntryButton;

    @FXML
    private ChoiceBox<Project> projectChoiceBox;

    @FXML
    private TextField totalProjectEntriesField;

    @FXML
    private TextField timerDurationTopField;
    
    @FXML
    void entryAddHandle(ActionEvent event) {
        ViewFactory.createSaveEntryDialog(this);
    }

    @FXML
    void entryEditHandle(ActionEvent event) {
        ViewFactory.createSaveEntryDialog(this);
    }

    @FXML
    void entryRemoveHandle(ActionEvent event) {
        ViewFactory.createDeleteEntryDialog(this);
    }

    @FXML
    void manualSaveHandle(ActionEvent event) {
        ViewFactory.createSaveEntryDialog(this);
    }

    @FXML
    void timerHandleReset(ActionEvent event) {
        Timer timer = modelAccess.getTimer();
        
        if (timer.isRunning()) {
            timer.stop();
            timer.reset();
        }
    }

    @FXML
    void timerHandleSave(ActionEvent event) {
        ViewFactory.createSaveEntryDialog(this);
    }

    @FXML
    void timerToggle(ActionEvent event) {
        Timer timer = modelAccess.getTimer();
        
        if (!timer.isRunning()) {
            timer.start();
            timerToggleButton.setText("Pysäytä ajastin");
            timerStartField.setText(timer.getEntry().getStartTimeAsString());
            timerEndField.clear();
            timerSaveButton.setDisable(true);
            timerResetButton.setDisable(true);
        } else {
            timer.stop();
            timerToggleButton.setText("Käynnistä ajastin");
            timerEndField.setText(timer.getEntry().getEndTimeAsString());
            timerSaveButton.setDisable(false);
            timerResetButton.setDisable(false);
        }
    }

    @FXML
    void projectEntryHandleClick(MouseEvent event) {
        //TODO valinnan asetus modelaccessin kautta
        Entry entry = projectEntryList.getSelectionModel().getSelectedItem();
        viewEntryStartField.setText(entry.getStartTimeAsString());
        viewEntryiEndField.setText(entry.getEndTimeAsString());
        viewEntryDurationField.setText(entry.getDurationAsString());
    }

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
        
        /*
         * Lisätään alasvetovalikkoon valitun käyttäjän projektit, valitaan oikea projekti valmiiksi ja
         * luodaan tapahtumankäsittelijä projektin vaihtamiselle
         */
        projectChoiceBox.setItems(FXCollections.observableArrayList(modelAccess.getSelectedUser().getProjects()));
        
        projectChoiceBox.getSelectionModel().select(modelAccess.getSelectedProject());
        
        projectChoiceBox.setOnAction(e -> {
            modelAccess.setSelectedProject(projectChoiceBox.getSelectionModel().getSelectedItem());
            projectEntryList.setItems(FXCollections.observableArrayList(modelAccess.getSelectedProject().getEntries()));
        });
        
        //Lisätään valitun projektin tallennetut merkinnät näkyville ListView-elementtiin
        projectEntryList.setItems(FXCollections.observableArrayList(modelAccess.getSelectedProject().getEntries()));
        
        
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

}
