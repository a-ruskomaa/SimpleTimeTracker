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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * Uuden käyttäjän luomiseen käytettävän dialogin kontrolleriluokka.
 * @author aleks
 * @version 21 Feb 2020
 *
 */
public class NewUserDialogController extends AbstractController {
    

    @FXML
    private TextField newUserNameField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;
    
    
    private User user;
    private List<User> userList;
    
    
    /**
     * Luo uuden kontrollerin käyttäjän lisäämiseen käytettävälle näkymälle.
     * 
     * @param dataAccess Pääohjelmassa luotu DataAccess olio. Tätä välitetään parametreina muille kontrollereille.
     * @param stage Stage jota kontrolloidaan.
     */
    public NewUserDialogController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);
    }
    
    public void initialize() {
        okButton.disableProperty().bind(Bindings.equal(newUserNameField.textProperty(), ""));
    }

    /**
     * Tapahtumankäsittelijä OK-napille. Lisää uuden käyttäjän ja sulkee dialogin.
     * @param event Napin painamisen aiheuttama tapahtuma.
     */
    @FXML
    void handleOkButton(ActionEvent event) {
        String newName = newUserNameField.getText();
        if (checkIfNameExists(newName, userList)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Saman niminen käyttäjä on jo olemassa. Vaihda käyttäjän nimi!");
            alert.showAndWait();
            return;
        }
        this.user = dataAccess.addUser(newName);
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
    
    
    public User getUser() {
        return this.user;
    }
    
    
    public void setUserList(List<User> list) {
        this.userList = list;
    }
    
    
    private boolean checkIfNameExists(String name, List<User> list) {
        for (User user : list) {
            if (user.getName().equals(name)) return true;
        }
        
        return false;
    }
    
    
}
