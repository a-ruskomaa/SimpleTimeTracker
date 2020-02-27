package roarusko.simpleTimeTracker.controller;

import java.io.IOException;
import java.net.URL;

import roarusko.simpleTimeTracker.App;
import roarusko.simpleTimeTracker.controller.main.*;
import roarusko.simpleTimeTracker.controller.start.*;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author aleks
 * @version 28 Jan 2020
 *
 * Luokka sisältää staattisia metodeja uusien ikkunoiden avaamiseksi. Kaikki ikkunoita avaavat metodit
 * palauttavat viitteen luotua ikkunaa hallinnoivaan kontrolleriin. Kontrollerin yksityiseksi attribuutiksi on tallennettu viite stageen, jota
 * kontrolloidaan.
 * Fxml-tiedostojen polku on tallennettuna luokan vakioihin.
 */
public class ViewFactory {

    private static final String FXML_MAINVIEW_LOCATION = "main/MainView.fxml";
    private static final String FXML_STARTVIEW_LOCATION = "start/StartView.fxml";

    private static final String FXML_STARTVIEW_USERDIALOG_PATH = "start/NewUserDialog.fxml";
    private static final String FXML_STARTVIEW_PROJECTDIALOG_PATH = "start/NewProjectDialog.fxml";

    private static final String FXML_MAINVIEW_EDIT_ENTRY_DIALOG_PATH = "main/EditEntryDialog.fxml";
    private static final String FXML_MAINVIEW_DELETE_ENTRY_DIALOG_PATH = "main/DeleteEntryDialog.fxml";
    
    private static final String FXML_MAINVIEW_TIMERTAB_PATH = "main/TimerTab.fxml";
    private static final String FXML_MAINVIEW_PROJECTTAB_PATH = "main/ProjectTab.fxml";

    
    /**
     * Luo käynnistysikkunan.
     * @param stage ohjelman stage
     * @param dataAccess dataAccess 
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static StartController createStartView(Stage stage, DataAccess dataAccess) {
        StartController controller = new StartController(dataAccess, stage);
        addSceneToStage(controller, FXML_STARTVIEW_LOCATION, stage);
        return controller;
    }
    
    /**
     * Luo uuden käyttäjän luomiseen käytettävän modaalisen popup-dialogin
     * @param dataAccess dataAccess 
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static NewUserDialogController createNewUserDialog(DataAccess dataAccess) {
        Stage stage = new Stage();
        NewUserDialogController controller = new NewUserDialogController(dataAccess, stage);
        addSceneToStage(controller, FXML_STARTVIEW_USERDIALOG_PATH, stage);
        return controller;
    }
    
    /**
     * Luo uuden projektin luomiseen käytettävän modaalisen popup-dialogin
     * @param dataAccess dataAccess 
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static NewProjectDialogController createNewProjectDialog(DataAccess dataAccess) {
        Stage stage = new Stage();
        NewProjectDialogController controller = new NewProjectDialogController(dataAccess, stage);
        addSceneToStage(controller, FXML_STARTVIEW_PROJECTDIALOG_PATH, stage);
        return controller;
    }
    
    /**
     * Luo pääikkunan.
     * @param dataAccess dataAccess 
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static MainController createMainView(DataAccess dataAccess) {
        Stage stage = new Stage();
        MainController controller = new MainController(dataAccess, stage);
        addSceneToStage(controller, FXML_MAINVIEW_LOCATION, stage);
        return controller;
    }
    
    
    
    /**
     * Luo merkinnän muokkaamiseen käytettävän modaalisen popup-dialogin
     * @param dataAccess dataAccess 
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static EditEntryDialogController createEditEntryDialog(DataAccess dataAccess) {
        Stage stage = new Stage();
        EditEntryDialogController controller = new EditEntryDialogController(dataAccess, stage);
        addSceneToStage(controller, FXML_MAINVIEW_EDIT_ENTRY_DIALOG_PATH, stage);
        
        return controller;
    }
    
    
    /**
     * Luo merkinnän poistamiseen käytettävän modaalisen popup-dialogin
     * @param dataAccess dataAccess 
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static DeleteEntryDialogController createDeleteEntryDialog(DataAccess dataAccess) {
        Stage stage = new Stage();
        DeleteEntryDialogController controller = new DeleteEntryDialogController(dataAccess, stage);
        addSceneToStage(controller, FXML_MAINVIEW_DELETE_ENTRY_DIALOG_PATH, stage);

        return controller;
    }
    
    
    /**
     * Luo pääikkunan ajastinvälilehden
     * @param parentController luotavan kontrollerin vanhempi
     * @param dataAccess dataAccess 
     * @return palauttaa Parent-muotoisen olion
     */
    public static Parent createTimerTab(MainController parentController, DataAccess dataAccess) {
        Stage stage = parentController.getStage();
        TimerTabController timerTabController = new TimerTabController(dataAccess, stage, parentController);
        
        return createParentNode(timerTabController, FXML_MAINVIEW_TIMERTAB_PATH);
    }
    
    /**
     * Luo pääikkunan projektivälilehden
     * @param parentController luotavan kontrollerin vanhempi
     * @param dataAccess dataAccess 
     * @return palauttaa Parent-muotoisen olion
     */
    public static Parent createProjecTab(MainController parentController, DataAccess dataAccess) {
        Stage stage = parentController.getStage();
        ProjectTabController projectTabController = new ProjectTabController(dataAccess, stage, parentController);
        
        return createParentNode(projectTabController, FXML_MAINVIEW_PROJECTTAB_PATH);
    }
    
    
    /**
     * Lataa FXML-tiedostosta uuden Parent-solmun annetuilla parametreilla. Asettaa solmulle kontrollerin.
     * @param controller Solmulle asetettava kontrolleriluokka
     * @param path Polku solmun sisällön määrittelevään FXML-tiedostoon
     * @return 
     */
    private static Parent createParentNode(WindowController controller, String path) {
        URL location = App.class.getResource(path);
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(location);
        fxmlloader.setController(controller);
        
        try {
            return fxmlloader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    
    /**
     * Luo annetussa polussa olevan näkymän, yhdistää näkymän stageen ja palauttaa stagen.
     * 
     * @param controller Näkymään liitettävä kontrolleriluokka, joka välitetään parametrina ketjun seuraavalle metodille
     * @param path polku FXML-tiedostoon
     * @param stage stage, johon luotava näkymä liitetään.
     * @param modal Halutaanko näytettävästä ikkunasta modaalinen
     */
    private static Stage addSceneToStage(WindowController controller, String path, Stage stage) {
        
        Parent root = createParentNode(controller, path);
        Scene scene = new Scene(root);
        scene.getStylesheets()
        .add(App.class
                .getResource("tyoaika.css")
                .toExternalForm());
        stage.setScene(scene);
        
        return stage;
    }


}
