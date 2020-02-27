package roarusko.simpleTimeTracker.controller.start;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    
    
    /**
     * Luo uuden kontrollerin käyttäjän lisäämiseen käytettävälle näkymälle.
     * 
     * @param dataAccess Pääohjelmassa luotu DataAccess olio. Tätä välitetään parametreina muille kontrollereille.
     * @param stage Stage jota kontrolloidaan.
     */
    public NewUserDialogController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);
    }

    /**
     * Tapahtumankäsittelijä OK-napille. Lisää uuden käyttäjän ja sulkee dialogin.
     * @param event Napin painamisen aiheuttama tapahtuma.
     */
    @FXML
    void handleOkButton(ActionEvent event) {
        this.user = dataAccess.addUser(newUserNameField.getText());
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
    
    
}
