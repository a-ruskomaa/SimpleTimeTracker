package roarusko.simpleTimeTracker.controller.start.dialogs;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * Uuden projektin luomiseen käytettävän dialogin kontrolleriluokka.
 * @author aleks
 * @version 21 Feb 2020
 *
 */
public class NewProjectDialogController extends AbstractController {
    
    @FXML
    private TextField newProjectNameField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;
    
    private Project project;
    private User user;
    private List<Project> projectList;
    
    /**
     * Luo uuden kontrollerin projektin lisäämiseen käytettävälle näkymälle.
     * 
     * @param dataAccess Pääohjelmassa luotu DataAccess olio. Tätä välitetään parametreina muille kontrollereille.
     * @param stage Stage jota kontrolloidaan.
     */
    public NewProjectDialogController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);
    }
    
    public void initialize() {
        okButton.disableProperty().bind(Bindings.equal(newProjectNameField.textProperty(), ""));
    }

    /**
     * Tapahtumankäsittelijä OK-napille. Lisää uuden projektin ja sulkee dialogin.
     * @param event Napin painamisen aiheuttama tapahtuma.
     */
    @FXML
    void handleOkButton(ActionEvent event) {
        String newName = newProjectNameField.getText();
        if (checkIfNameExists(newName, projectList)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Käyttäjällä on jo saman niminen projekti. Vaihda projektin nimi!");
            alert.showAndWait();
            return;
        }
        this.project = dataAccess.addProject(newName, this.user);
        exitStage(event);
    }
    
    /**
     * Tapahtumankäsittelijä Cancel-napille. Sulkee dialogin.
     * @param event Napin painamisen aiheuttama tapahtuma.
     */
    @FXML
    void handleCancelButton(ActionEvent event) {
        exitStage(event);
    }
    
    /**
     * Sulkee dialogin
     * @param event Napin painamisen aiheuttama tapahtuma.
     */
    private void exitStage(ActionEvent event) {

        Node node = (Node) event.getSource();

        Window window = node.getScene().getWindow();

        window.fireEvent(
                new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    
    public Project getProject() {
        return this.project;
    }
    
    
    public void setUser(User user) {
        this.user = user;
    }
    
    
    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    
    private boolean checkIfNameExists(String name, List<Project> list) {
        for (Project project : list) {
            if (project.getName().equals(name)) return true;
        }
        
        return false;
    }

}
