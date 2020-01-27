package fxTyoaika;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author roarusko
 * @version 16.1.2020
 *
 */
public class TyoaikaStartController {
	//
    
    @FXML
    private void handleOkButton() {
        BorderPane root;
        try {
            root = (BorderPane)FXMLLoader.load(getClass().getResource("TyoaikaStartView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            root = null;
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("tyoaika.css").toExternalForm());
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
