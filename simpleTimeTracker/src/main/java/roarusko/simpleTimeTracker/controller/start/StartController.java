package roarusko.simpleTimeTracker.controller.start;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.view.ViewFactory;
import roarusko.simpleTimeTracker.controller.WindowController;
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
 * @version 16.1.2020
 *
 * Ohjelman ensimmäinen näkymä, jossa valitaan käyttäjä ja projekti.
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
     * Luo uuden kontrollerin aloitusnäkymälle
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

        System.out.println("alustetaan startcontroller");

        // Haetaan tallennetut käyttäjät ja lisätään valikkoon
        userChoiceBox.setItems(dataAccess.loadUsers());
        
        userChoiceBox.valueProperty().addListener((prop, oldV, newV) -> {
            if (newV != null) {
                // Haetaan tallennetut projektit ja lisätään valikkoon
                projectChoiceBox.setItems(dataAccess.loadProjects(newV));
                
                //TODO projektiin property lastModified ja tähän sortedlist wrappays + comparator
                
                if (!projectChoiceBox.getItems().isEmpty()) {
                    projectChoiceBox.getSelectionModel().select(0);
                }
            };
        });
        
        if (!userChoiceBox.getItems().isEmpty()) {
            userChoiceBox.getSelectionModel().select(0);
        }
        
        
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

        MainController mc = ViewFactory.createMainView(dataAccess);
        
        System.out.println("selecting..");
        mc.allUsersProperty().set(this.userChoiceBox.getItems());
        mc.selectedUserProperty().set(this.userChoiceBox.getValue());
        mc.selectedUser_ProjectsProperty().set(this.projectChoiceBox.getItems());
        mc.selectedProjectProperty().set(this.projectChoiceBox.getValue());
        
        mc.showStage();
        
        oldStage.close();
    }


    /**
     * Avaa näkymän uuden käyttäjän lisäämiseksi
     */
    @FXML
    private void handleNewUserButton() {
        NewUserDialogController controller = ViewFactory.createNewUserDialog(dataAccess);
        controller.setUserList(userChoiceBox.getItems());
        controller.showModalStage();
        User newUser = controller.getUser();
        if (newUser != null) {
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
        controller.setUser(this.userChoiceBox.getValue());
        controller.setProjectList(projectChoiceBox.getItems());
        controller.showModalStage();
        Project newProject = controller.getProject();
        if (newProject != null) {
            this.projectChoiceBox.getItems().add(newProject);
            this.projectChoiceBox.getSelectionModel().select(newProject);
        }
    }

}
