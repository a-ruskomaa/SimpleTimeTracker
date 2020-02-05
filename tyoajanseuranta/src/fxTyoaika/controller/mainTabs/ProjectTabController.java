package fxTyoaika.controller.mainTabs;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.LinkedList;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.model.Entry;
import fxTyoaika.model.Entries;
import fxTyoaika.model.ModelAccess;
import fxTyoaika.view.ViewFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
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
        
        projectEntryList.setItems(modelAccess.getSelectedProject().getEntries());
        
        modelAccess.selectedProjectProperty().addListener((e) -> {
            projectEntryList.setItems(modelAccess.getSelectedProject().getEntries());
//            projectEntryList.getSelectionModel().select(0);
        });
        
        projectEntryList.getSelectionModel().selectedItemProperty().addListener((e) -> {
            Entry entry = projectEntryList.getSelectionModel().getSelectedItem();
//            Entry entry = modelAccess.getSelectedEntry();
            DateTimeFormatter f = Entries.getDateTimeFormatter();
          viewEntryStartField.textProperty().bind(Bindings.createStringBinding(() -> entry.getStartTime().format(f), entry.startTimeProperty()));
          viewEntryiEndField.textProperty().bind(Bindings.createStringBinding(() -> entry.getEndTime().format(f), entry.endTimeProperty()));
//          viewEntryStartField.textProperty().bind(modelAccess.getSelectedEntry().startTimeStringProperty());
//          viewEntryiEndField.textProperty().bind(modelAccess.getSelectedEntry().endTimeStringProperty());
          viewEntryDurationField.textProperty().bind(Bindings.createStringBinding(() -> entry.getDurationAsString(), entry.durationProperty()));
          modelAccess.setSelectedEntry(entry);
        });
        
    }
    
    public void loadEntries() {
        //Lisätään valitun projektin tallennetut merkinnät näkyville ListView-elementtiin
        projectEntryList.setItems(modelAccess.getSelectedProject().getEntries());
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


}
