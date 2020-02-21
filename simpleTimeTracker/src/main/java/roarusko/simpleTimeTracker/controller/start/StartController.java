package roarusko.simpleTimeTracker.controller.start;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import roarusko.simpleTimeTracker.SampleData;
import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.controller.ViewFactory;
import roarusko.simpleTimeTracker.controller.WindowController;
import roarusko.simpleTimeTracker.model.ModelAccess;
import roarusko.simpleTimeTracker.model.domainModel.Project;
import roarusko.simpleTimeTracker.model.domainModel.User;
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

        // Haetaan tallennetut käyttäjät ja lisätään valikkoon
        userChoiceBox.setItems(modelAccess.allUsersProperty());
        
        // Sidotaan valittuna olevan käyttäjän tila ja valikossa valittuna oleva käyttäjä toisiinsa
        userChoiceBox.valueProperty().bindBidirectional(modelAccess.selectedUserProperty());
        
        // Haetaan tallennetut projektit ja lisätään valikkoon
        projectChoiceBox.setItems(modelAccess.selectedUserProjectsProperty());

        // Sidotaan valittuna olevan projektin tila ja valikossa valittuna oleva projekti toisiinsa
        projectChoiceBox.valueProperty().bindBidirectional(modelAccess.selectedProjectProperty());
        
        // OK-nappia voi painaa vain jos joku projekti on valittuna (käyttäjä ei voi olla null jos 
        // joku projekti on valittuna)
        okButton.disableProperty().bind(Bindings.isNull(projectChoiceBox.valueProperty()));
    }


    /**
     * Avaa pääikkunan, sulkee aloitusikkunan
     */
    @FXML
    private void handleOkButton() {

        Stage oldStage = this.getStage();

        ViewFactory.createMainView();

        oldStage.close();
    }


    /**
     * Avaa näkymän uuden käyttäjän lisäämiseksi
     */
    @FXML
    private void handleNewUserButton() {
        ViewFactory.createNewUserDialog();
    }


    /**
     * Avaa näkymän uuden projektin lisäämiseksi
     */
    @FXML
    private void handleNewProjectButton() {
        ViewFactory.createNewProjectDialog();
    }

}
