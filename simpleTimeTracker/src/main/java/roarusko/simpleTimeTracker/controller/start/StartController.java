package roarusko.simpleTimeTracker.controller.start;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.view.ViewFactory;
import roarusko.simpleTimeTracker.controller.main.MainController;
import roarusko.simpleTimeTracker.controller.start.dialogs.NewProjectDialogController;
import roarusko.simpleTimeTracker.controller.start.dialogs.NewUserDialogController;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.domain.Project;
import roarusko.simpleTimeTracker.model.domain.User;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/**
 * @author roarusko
 * @version 02.03.2020
 *
 * Ohjelman ensimmäinen näkymä, jossa valitaan käyttäjä ja projekti. Aloitusnäkymästä
 * voi avata dialogin uuden käyttäjän ja uuden projektin lisäämiseksi.
 */
public class StartController extends AbstractController {

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
     * Luo uuden kontrollerin aloitusnäkymälle.
     * 
     * @param dataAccess Pääohjelmassa luotu DataAccess olio. Tätä välitetään parametreina muille kontrollereille.
     * @param stage stage jota kontrolloidaan
     * 
     */
    public StartController(DataAccess dataAccess, Stage stage) {
        super(dataAccess, stage);
    }


    /**
     * Alustaa kontrollerin ohjaaman näkymän. Hakee dataAccessin avulla listan ohjelmaan tallennetuista käyttäjistä.
     */
    public void initialize() {

        System.out.println("loading users");
        // Haetaan tallennetut käyttäjät ja lisätään valikkoon
        userChoiceBox.setItems(dataAccess.loadUsers());
        
        // Lisätään käyttäjävalikkoon kuuntelija, tehdään tämä ennen käyttäjän valintaa jotta
        // oletuksena valitun käyttäjän projektit ladataan automaattisesti
        userChoiceBox.valueProperty().addListener((prop, oldV, newV) -> {
            if (newV != null) {

                System.out.println("loading projects");
                // Haetaan käyttäjän vaihtuessa tämän tallennetut projektit ja lisätään valikkoon
                projectChoiceBox.setItems(dataAccess.loadProjects(newV));
                
                //TODO projektiin property lastModified ja tähän sortedlist wrappays + comparator
                
                // Valitaan automaattisesti listan ensimmäinen projekti
                if (!projectChoiceBox.getItems().isEmpty()) {
                    projectChoiceBox.getSelectionModel().select(0);
                }
            };
        });
        
        // Valitaan automaattisesti listan ensimmäinen käyttäjä
        if (!userChoiceBox.getItems().isEmpty()) {
            userChoiceBox.getSelectionModel().select(0);
        }
        
        
        // OK-nappia voi painaa vain jos joku projekti on valittuna (käyttäjä ei voi olla null jos 
        // joku projekti on valittuna)
        okButton.disableProperty().bind(Bindings.isNull(projectChoiceBox.valueProperty()));
    }


    /**
     * Ok-painike avaa pääikkunan ja sulkee aloitusikkunan. Pääikkunan kontrolleri luodaan
     * ViewFactory-luokan avulla. Ennen pääikkunan avaamista välitetään sille settereiden 
     * avulla tieto aloitusikkunassa tehdystä käyttäjän ja projektin valinnasta.
     */
    @FXML
    private void handleOkButton() {

        Stage oldStage = this.getStage();

        MainController mc = ViewFactory.createMainView(dataAccess);
        
        // Välitetään pääikkunalle kaikki ladatut käyttäjät ja tieto valitusta käyttäjästä
        mc.allUsersProperty().set(this.userChoiceBox.getItems());
        mc.selectedUserProperty().set(this.userChoiceBox.getValue());
        
        // Välitetään pääikkunalle kaikki valitun käyttäjän projektit ja tieto valitusta projektista
        mc.selectedUser_ProjectsProperty().set(this.projectChoiceBox.getItems());
        mc.selectedProjectProperty().set(this.projectChoiceBox.getValue());
        
        // Pyydetään pääikkunan kontrolleria näyttämään siihen liitetty näkymä
        mc.showStage();
        
        oldStage.close();
    }


    /**
     * Avaa dialogin uuden käyttäjän lisäämiseksi.
     */
    @FXML
    private void handleNewUserButton() {
        NewUserDialogController controller = ViewFactory.createNewUserDialog(dataAccess);
        
        // Välitetään kontrollerille tieto aiemmin luoduista käyttäjistä
        controller.setUserList(userChoiceBox.getItems());
        controller.showModalStage();
        
        // Dialogin sulkemisen jälkeen haetaan tieto mahdollisesti luodusta käyttäjästä
        User newUser = controller.getUser();
        if (newUser != null) {
            // Uusi käyttäjä tallennetaan pysyvään muistiin dialogin kontrollerissa, tässä
            // lisätään se vielä käyttöliittymän listalle
            this.userChoiceBox.getItems().add(newUser);
            this.userChoiceBox.getSelectionModel().select(newUser);
        }
    }


    /**
     * Avaa näkymän uuden projektin lisäämiseksi
     */
    @FXML
    private void handleNewProjectButton() {
        NewProjectDialogController controller = ViewFactory.createNewProjectDialog(dataAccess);
        
        // Välitetään kontrollerille tieto valitusta käyttäjästä ja aiemmin luoduista projekteista
        controller.setUser(this.userChoiceBox.getValue());
        controller.setProjectList(projectChoiceBox.getItems());
        controller.showModalStage();
        
        // Dialogin sulkemisen jälkeen haetaan tieto mahdollisesti luodusta projektista
        Project newProject = controller.getProject();
        if (newProject != null) {
            // Uusi projekti tallennetaan pysyvään muistiin dialogin kontrollerissa, tässä
            // lisätään se vielä käyttöliittymän listalle
            this.projectChoiceBox.getItems().add(newProject);
            this.projectChoiceBox.getSelectionModel().select(newProject);
        }
    }

}
