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

public class NewUserDialogController extends AbstractController {
    

    @FXML
    private TextField newUserNameField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;
    
    
    public NewUserDialogController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
    }

    @FXML
    void handleOkButton(ActionEvent event) {
        modelAccess.addUser(newUserNameField.getText());
        exitStage(event);
    }
    
    @FXML
    void handleCancelButton(ActionEvent event) {
        exitStage(event);
    }
    
    private void exitStage(ActionEvent event) {

        Node node = (Node) event.getSource();

        Window window = node.getScene().getWindow();

        window.fireEvent(
                new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
    
}
