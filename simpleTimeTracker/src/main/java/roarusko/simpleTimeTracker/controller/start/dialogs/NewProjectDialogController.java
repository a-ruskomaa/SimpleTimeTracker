package roarusko.simpleTimeTracker.controller.start.dialogs;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * Uuden projektin luomiseen käytettävän dialogin kontrolleriluokka. Luo annetun nimen perusteella
 * uuden projektin, joka tallennetaan pysyvään muistiin DataAccess-luokan avulla. Tarjoaa getterin
 * luodulle projektille, jonka avulla aloitusikkunan kontrolleri voi saada tiedon luodusta projektista.
 * @author aleks
 * @version 02.03.2020
 *
 */
public class NewProjectDialogController extends AbstractController {
    
    @FXML
    private TextField newProjectNameField;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;
    
    private Project project;
    private User user;
    private List<Project> projectList;
    
    /**
     * Luo uuden kontrollerin projektin lisäämiseen käytettävälle näkymälle.
     * 
     * @param dataAccess Pääohjelmassa luotu DataAccess olio, juonka avulla uuden projektin tiedot tallennetaan
     * @param stage Stage jota kontrolloidaan.
     */
    public NewProjectDialogController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);
    }
    
    
    /**
     * Alustaa kontrollerin.
     */
    public void initialize() {
        // Sidotaan ok-button aktivoitumaan vain jos nimikenttä ei ole tyhjä
        okButton.disableProperty().bind(Bindings.equal(newProjectNameField.textProperty(), ""));
    }

    /**
     * Tapahtumankäsittelijä OK-napille. Lisää uuden projektin ja sulkee dialogin.
     * @param event Napin painamisen aiheuttama tapahtuma.
     */
    @FXML
    void handleOkButton(ActionEvent event) {
        String newName = newProjectNameField.getText();
        
        // Tarkistetaan ettei saman nimistä projektilla ole olemassa
        if (checkIfNameExists(newName, projectList)) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Käyttäjällä on jo saman niminen projekti. Vaihda projektin nimi!");
            alert.showAndWait();
            return;
        }
        
        // Tallennetaan luotu projekti tietokantaan, tallennetaan tieto luodusta projektista muiden luokkien saataville
        this.project = dataAccess.addProject(newName, this.user);
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

    
    
    /**
     * Näkymässä luodun projektin getteri.
     * @return Palauttaa dialogissa luodun projektin tai null ennen uuden projektin luomista.
     */
    public Project getProject() {
        return this.project;
    }
    
    
    /**
     * Setteri, jolla asetetaan luotavan projektin omistava käyttäjä
     * @param user Käyttäjä, jolle ollaan luomassa projektia
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    
    /**
     * Aiemmin luodut projektit sisältävän listan setteri
     * @param projectList Lista, jolle on lisättynä aiemmin luodut projektit.
     */
    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    /**
     * Apumetodi, jolla selvitetään onko käyttäjällä olemassa jo halutun niminen projekti
     */
    private boolean checkIfNameExists(String name, List<Project> list) {
        for (Project existing : list) {
            if (existing.getName().equals(name)) return true;
        }
        
        return false;
    }

}
