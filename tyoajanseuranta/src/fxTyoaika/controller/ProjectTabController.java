package fxTyoaika.controller;

import java.util.LinkedList;

import fxTyoaika.model.Entry;
import fxTyoaika.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ProjectTabController extends AbstractController {
    
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
//
    public ProjectTabController(ModelAccess modelAccess) {
        super(modelAccess);
    }
    
    public void initialize() {
        ObservableList<Entry> entries = modelAccess.getSelectedProject().getEntries();
        
        //Lisätään valitun projektin tallennetut merkinnät näkyville ListView-elementtiin
        projectEntryList.setItems(FXCollections.observableArrayList(entries));
    }
    
    @FXML
    void entryAddHandle(ActionEvent event) {
        ViewFactory.createSaveEntryDialog();
    }

    @FXML
    void entryEditHandle(ActionEvent event) {
        ViewFactory.createSaveEntryDialog();
    }

    @FXML
    void entryRemoveHandle(ActionEvent event) {
        ViewFactory.createDeleteEntryDialog();
    }

    @FXML
    void manualSaveHandle(ActionEvent event) {
        ViewFactory.createSaveEntryDialog();
    }


    @FXML
    void projectEntryHandleClick(MouseEvent event) {
        //TODO valinnan asetus modelaccessin kautta
        Entry entry = projectEntryList.getSelectionModel().getSelectedItem();
        viewEntryStartField.setText(entry.getStartTimeAsString());
        viewEntryiEndField.setText(entry.getEndTimeAsString());
        viewEntryDurationField.setText(entry.getDurationAsString());
    }
}
