package fxTyoaika.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fxTyoaika.model.ModelAccess;
import fxTyoaika.model.Project;
import fxTyoaika.model.TempUsers;
import fxTyoaika.model.User;
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
 * Ohjelman ensimmäinen näkymä, jossa valitaan käyttäjä ja projekti.
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
        
        
        // Lataa tallennetut käyttäjät. Toistaiseksi käytetään puhtaasti oliopohjaista ratkaisua datan ylläpitoon.
        modelAccess.setUsers(TempUsers.getUsers());
        
        //Haetaan ohjelmaan tallennetut käyttäjät ja lisätään valikkoon
        userChoiceBox.setItems(modelAccess.getUsers());

        // Valitaan listan ensimmäinen käyttäjä. Myöhemmässä toteutuksessa valitaan edellinen ohjelmaa käyttänyt henkilö?
        userChoiceBox.getSelectionModel().select(0);

        //Vaihdetaan käyttäjä valituksi myös modelAccessiin
        modelAccess.setSelectedUser(
                userChoiceBox.getSelectionModel().getSelectedItem());
        

        //Haetaan valitun käyttäjän projektit ja lisätään listalle
        projectChoiceBox.setItems(modelAccess.getSelectedUser().getProjects());
        
        // TODO null check käyttäjän ja projektin valintaan!, ok button disable jos jompi kumpi null

        //Valitaan listalta ensimmäinen projekti sekä vahvistetaan valinta modelAccessiin
        projectChoiceBox.getSelectionModel().select(0);
        modelAccess.setSelectedProject(
                projectChoiceBox.getSelectionModel().getSelectedItem());

        //Luodaan käsittelijä, joka vaihtaa projektilistan sisällön vastaamaan valittua käyttäjää sekä päivittää valinnan modelAccessiin
        userChoiceBox.setOnAction(e -> {
            modelAccess.setSelectedUser(
                    userChoiceBox.getSelectionModel().getSelectedItem());
            projectChoiceBox.setItems(modelAccess.getSelectedUser().getProjects());
            projectChoiceBox.getSelectionModel().select(0);
        });

        //Luodaan vastaava käsittelijä projektin valinnalle
        projectChoiceBox.setOnAction(e -> {
            modelAccess.setSelectedProject(
                    projectChoiceBox.getSelectionModel().getSelectedItem());
        });
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
