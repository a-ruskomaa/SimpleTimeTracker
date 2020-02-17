package fxTyoaika.controller.start;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.ModelAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class NewProjectDialogController extends AbstractController {
    
    @FXML
    private TextField newProjectNameField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;
    
    
    public NewProjectDialogController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
    }

    @FXML
    void handleOkButton(ActionEvent event) {
        modelAccess.addProject(newProjectNameField.getText());
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
