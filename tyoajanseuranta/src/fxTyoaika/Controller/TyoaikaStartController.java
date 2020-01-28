package fxTyoaika.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fxTyoaika.Model.ModelAccess;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author roarusko
 * @version 16.1.2020
 *
 */
public class TyoaikaStartController implements Initializable {
    
    private ModelAccess modelAccess;

    @FXML
    private Button newUserButton;

    @FXML
    private Button newProjectButton;

    @FXML
    private ChoiceBox<String> userChoiceBox;

    @FXML
    private ChoiceBox<String> projectChoiceBox;

    @FXML
    private Button okButton;
    
    @FXML
    private void handleOkButton() {
        
        try {
            Stage oldStage = (Stage) okButton.getScene().getWindow();
            
            BorderPane root;
            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(getClass().getClassLoader().getResource("fxTyoaika/View/TyoaikaMainView.fxml"));
            
            TyoaikaMainController controller = new TyoaikaMainController();
            controller.setModelAccess(this.modelAccess);
            
            fxmlloader.setController(controller);
            
            root = fxmlloader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getClassLoader().getResource("fxTyoaika/View/tyoaika.css").toExternalForm());
            
            Stage secondStage = new Stage();
            secondStage.setScene(scene);
            secondStage.show();
            
            oldStage.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modelAccess = new ModelAccess();
        System.out.println("HELLOOOOO!");
    }
    
    public ModelAccess getModelAccess() {
        return this.modelAccess;
    }
}
