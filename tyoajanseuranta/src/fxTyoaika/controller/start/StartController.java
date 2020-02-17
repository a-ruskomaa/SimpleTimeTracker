package fxTyoaika.controller.start;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fxTyoaika.SampleData;
import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.ModelAccess;
import fxTyoaika.controller.ViewFactory;
import fxTyoaika.controller.WindowController;
import fxTyoaika.model.Project;
import fxTyoaika.model.User;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * Ohjelman ensimmäinen näkymä, jossa valitaan käyttäjä ja projekti.
 */
public class StartController extends AbstractController {

    private ObservableList<User> users;

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
     * Luo uuden kontrollerin aloitusnäkymälle
     * 
     * @param modelAccess Pääohjelmassa luotu ModelAccess olio. Tätä välitetään parametreina muille kontrollereille.
     * @param stage stage jota kontrolloidaan
     * 
     */
    public StartController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
    }


    /**
     * Alustaa kontrollerin ohjaaman näkymän. Hakee modelAccessin avulla listan ohjelmaan tallennetuista käyttäjistä.
     */
    public void initialize() {

        System.out.println("alustetaan startcontroller");

        // Lataa tallennetut käyttäjät. Toistaiseksi käytetään puhtaasti
        // oliopohjaista ratkaisua datan ylläpitoon.
        users = modelAccess.loadUsers();

        // Haetaan ohjelmaan tallennetut käyttäjät ja lisätään valikkoon
        userChoiceBox.setItems(modelAccess.allUsersProperty());
        
        // Luovutetaan kontrolleriluokalle valitun käyttäjän hallinta
        userChoiceBox.valueProperty().bindBidirectional(modelAccess.selectedUserProperty());
        
        userChoiceBox.getSelectionModel().select(0);


        
        projectChoiceBox.setItems(modelAccess.selectedUserProjectsProperty());

        projectChoiceBox.valueProperty().bindBidirectional(modelAccess.selectedProjectProperty());
        
        okButton.disableProperty().bind(Bindings.isNull(projectChoiceBox.valueProperty()));
    }


    /**
     * Avaa pääikkunan, sulkee aloitusikkunan
     */
    @FXML
    private void handleOkButton() {

        Stage oldStage = (Stage) okButton.getScene().getWindow();

        ViewFactory.createMainView();

        oldStage.close();
    }


    /**
     * Avaa näkymän uuden käyttäjän lisäämiseksi
     */
    @FXML
    private void handleNewUserButton() {
//        WindowController controller = ViewFactory.createNewUserDialog();
        ViewFactory.createNewUserDialog();
    }


    /**
     * Avaa näkymän uuden projektin lisäämiseksi
     */
    @FXML
    private void handleNewProjectButton() {
//        WindowController controller = ViewFactory.createNewProjectDialog();
        ViewFactory.createNewProjectDialog();
    }

}
