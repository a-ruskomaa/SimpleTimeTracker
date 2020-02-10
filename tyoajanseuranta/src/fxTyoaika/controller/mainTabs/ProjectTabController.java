package fxTyoaika.controller.mainTabs;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.LinkedList;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.ViewFactory;
import fxTyoaika.model.Entry;
import fxTyoaika.model.Entries;
import fxTyoaika.model.ModelAccess;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private TreeView<Entry> projectEntryTree;
    
    @FXML
    private TextField entryStartField;

    @FXML
    private TextField entryEndField;

    @FXML
    private TextField entryDurationField;

    @FXML
    private Button editEntryButton;

    @FXML
    private Button removeEntryButton;

    @FXML
    private Button addEntryButton;

    
    /**
     * @param modelAccess modelAccess
     */
    public ProjectTabController(ModelAccess modelAccess) {
        super(modelAccess);
    }
    
    /**
     * 
     */
    public void initialize() {
        
        
        projectEntryList.setItems(modelAccess.getSelectedProject().getEntries());
        
        InvalidationListener selectedProjectListener = (e -> {
            projectEntryList.setItems(modelAccess.getSelectedProject().getEntries());
            
            // TODO poista valitun merkinnän kuuntelija ennen projektin vaihtoa
            if (!projectEntryList.getItems().isEmpty()) {
                projectEntryList.getSelectionModel().select(0);
            }
        });
        
        modelAccess.selectedProjectProperty().addListener(selectedProjectListener);
        
        projectEntryList.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected == null) return;
            DateTimeFormatter f = Entries.getDateTimeFormatter();
          entryStartField.textProperty().bind(Bindings.createStringBinding(() -> selected.getStartDateTime().format(f), selected.startTimeProperty(), selected.startDateProperty()));
          entryEndField.textProperty().bind(Bindings.createStringBinding(() -> selected.getEndDateTime().format(f), selected.endTimeProperty(), selected.endDateProperty()));
          
          entryDurationField.textProperty().bind(Bindings.createStringBinding(() -> selected.getDurationAsString(), selected.durationProperty()));
          modelAccess.setSelectedEntry(selected);
        });
        
    }
    
    
    @FXML
    void handleAddEntry(ActionEvent event) {
        modelAccess.setCurrentlyEditedEntry(new Entry());
        Stage stage = ViewFactory.createEditEntryDialog();
        stage.show();
    }

    @FXML
    void handleEditEntry(ActionEvent event) {
        try {
            modelAccess.setCurrentlyEditedEntry(projectEntryList.getSelectionModel().getSelectedItem().clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Stage stage = ViewFactory.createEditEntryDialog();
        stage.show();
    }

    @FXML
    void handleRemoveEntry(ActionEvent event) {
        Stage stage = ViewFactory.createDeleteEntryDialog();
        stage.show();
    }


}
