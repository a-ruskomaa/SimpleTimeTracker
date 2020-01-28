package fxTyoaika.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fxTyoaika.model.Project;
import fxTyoaika.model.User;
import fxTyoaika.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author roarusko
 * @version 16.1.2020
 *
 * Ohjelman ensimmäinen näkymä. Luo konstruktorissa ModelAccess-olion, jolla pidetään yllä valitun projektin tilaa.
 * Viite tähän olioon välitetään muiden kontrolleriluokkien konstruktoreiden avulla. Myöhemmin elegantimpi toteutus?
 */
public class StartController extends AbstractController {

    // private final ModelAccess modelAccess;
    private final String FXML_LOCATION = "fxTyoaika/view/";

    @FXML
    private Button newUserButton;

    @FXML
    private Button newProjectButton;

    @FXML
    private ChoiceBox<User> userChoiceBox;

    @FXML
    private ChoiceBox<Project> projectChoiceBox;

    @FXML
    private Button okButton;

    /**
     * @param modelAccess asd
     * 
     */
    public StartController(ModelAccess modelAccess) {
        super(modelAccess);
    }


    /**
     * Alustaa kontrollerin ohjaaman näkymän. Hakee modelAccessin avulla listan ohjelmaan tallennetuista käyttäjistä.
     */

    public void initialize() {
        
        System.out.println("kutsuttu initialize");

        userChoiceBox.setItems(
                FXCollections.observableArrayList(modelAccess.getUserList()));

        userChoiceBox.getSelectionModel().select(0);

        modelAccess.setSelectedUser(
                userChoiceBox.getSelectionModel().getSelectedItem());

        projectChoiceBox.setItems(FXCollections.observableArrayList(
                modelAccess.getSelectedUser().getProjects()));

        projectChoiceBox.getSelectionModel().select(0);
        modelAccess.setSelectedProject(
                projectChoiceBox.getSelectionModel().getSelectedItem());

        userChoiceBox.setOnAction(e -> {
            modelAccess.setSelectedUser(
                    userChoiceBox.getSelectionModel().getSelectedItem());
            projectChoiceBox.setItems(FXCollections.observableArrayList(
                    modelAccess.getSelectedUser().getProjects()));
            projectChoiceBox.getSelectionModel().select(0);
        });

        projectChoiceBox.setOnAction(e -> {
            modelAccess.setSelectedProject(
                    projectChoiceBox.getSelectionModel().getSelectedItem());
        });
    }


    @FXML
    private void handleOkButton() {

        Stage oldStage = (Stage) okButton.getScene().getWindow();
        try {

            BorderPane root;
            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(getClass().getClassLoader()
                    .getResource(FXML_LOCATION + "MainView.fxml"));

            MainController controller = new MainController(this.modelAccess);

            fxmlloader.setController(controller);

            root = fxmlloader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets()
                    .add(getClass().getClassLoader()
                            .getResource(FXML_LOCATION + "tyoaika.css")
                            .toExternalForm());

        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage secondStage = new Stage();
        secondStage.setScene(ViewFactory.createMainView(this));
        secondStage.show();
        
        oldStage.close();
    }


    // TODO: vaihda popupiksi
    @FXML
    private void handleNewUserButton() {
        Stage newUserDialog = new Stage();
        newUserDialog.initModality(Modality.APPLICATION_MODAL);
        newUserDialog.setScene(ViewFactory.createNewUserDialog(this));
        newUserDialog.show();
    }


    // TODO: vaihda popupiksi
    @FXML
    private void handleNewProjectButton() {
        Stage newUserDialog = new Stage();
        newUserDialog.initModality(Modality.APPLICATION_MODAL);
        newUserDialog.setScene(ViewFactory.createNewProjectDialog(this));
        newUserDialog.show();
    }


}
