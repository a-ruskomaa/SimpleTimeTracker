package roarusko.simpleTimeTracker.controller.start;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.model.ModelAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    
    /**
     * Luo uuden kontrollerin projektin lisäämiseen käytettävälle näkymälle.
     * 
     * @param modelAccess Pääohjelmassa luotu ModelAccess olio. Tätä välitetään parametreina muille kontrollereille.
     * @param stage Stage jota kontrolloidaan.
     */
    public NewProjectDialogController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
    }

    /**
     * Tapahtumankäsittelijä OK-napille. Lisää uuden projektin ja sulkee dialogin.
     * @param event Napin painamisen aiheuttama tapahtuma.
     */
    @FXML
    void handleOkButton(ActionEvent event) {
        modelAccess.addProject(newProjectNameField.getText());
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

}
