package fxTyoaika.controller.start;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fxTyoaika.SampleData;
import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.ModelAccess;
import fxTyoaika.controller.ViewFactory;
import fxTyoaika.model.Project;
import fxTyoaika.model.User;
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
     * 
     */
    public StartController(ModelAccess modelAccess) {
        super(modelAccess);
    }


    /**
     * Alustaa kontrollerin ohjaaman näkymän. Hakee modelAccessin avulla listan ohjelmaan tallennetuista käyttäjistä.
     */
    public void initialize() {

        System.out.println("alustetaan startcontroller");

        // Lataa tallennetut käyttäjät. Toistaiseksi käytetään puhtaasti
        // oliopohjaista ratkaisua datan ylläpitoon.
        users = FXCollections
                .observableArrayList(modelAccess.getUserDAO().list());

        // Haetaan ohjelmaan tallennetut käyttäjät ja lisätään valikkoon
        userChoiceBox.setItems(users);

        modelAccess.selectedUserProperty().bind(userChoiceBox.valueProperty());


        // Luodaan käsittelijä, joka vaihtaa projektilistan sisällön vastaamaan
        // valittua käyttäjää
        modelAccess.selectedUserProperty()
        .addListener((prop, old, selected) -> {
            if (old != null) {
                old.setProjects(null);
            }
            
            ObservableList<Project> projects = FXCollections
                    .observableArrayList(
                            modelAccess.getProjectDAO().list(selected));
            
            selected.setProjects(projects);
            
            projectChoiceBox.setItems(projects);
            
            
            if (!projectChoiceBox.getItems().isEmpty()) {
                projectChoiceBox.getSelectionModel().select(0);
            }
        });
        
        
        if (!userChoiceBox.getItems().isEmpty()) {
            userChoiceBox.getSelectionModel().select(0);
        }
        
//        // Valitaan listan ensimmäinen käyttäjä. Myöhemmässä toteutuksessa
//        // valitaan edellinen ohjelmaa käyttänyt henkilö?
//        if (!userChoiceBox.getItems().isEmpty()) {
//            userChoiceBox.getSelectionModel().select(0);
//        }
//
//        // Haetaan valitun käyttäjän projektit ja lisätään listalle
//        projectChoiceBox.setItems(modelAccess.getSelectedUser().getProjects());
//
//        if (!projectChoiceBox.getItems().isEmpty()) {
//            projectChoiceBox.getSelectionModel().select(0);
//        }

        // TODO ok button disable jos jompi kumpi null

        modelAccess.selectedProjectProperty()
                .bind(projectChoiceBox.valueProperty());


    }


    /**
     * Avaa pääikkunan, sulkee aloitusikkunan
     */
    @FXML
    private void handleOkButton() {

        Stage oldStage = (Stage) okButton.getScene().getWindow();
        Stage secondStage = new Stage();

        secondStage.setScene(ViewFactory.createMainView());
        secondStage.show();

        oldStage.close();
    }


    /**
     * Avaa näkymän uuden käyttäjän lisäämiseksi
     */
    @FXML
    private void handleNewUserButton() {
        Stage newUserDialog = ViewFactory.createNewUserDialog();
        newUserDialog.show();
    }


    /**
     * Avaa näkymän uuden projektin lisäämiseksi
     */
    @FXML
    private void handleNewProjectButton() {
        Stage newUserDialog = ViewFactory.createNewProjectDialog();
        newUserDialog.show();
    }

}
