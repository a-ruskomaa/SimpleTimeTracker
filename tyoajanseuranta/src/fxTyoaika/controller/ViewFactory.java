package fxTyoaika.controller;

import java.io.IOException;
import java.net.URL;

import fxTyoaika.controller.main.*;
import fxTyoaika.controller.start.*;
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

    private static final String FXML_BASE_LOCATION = "fxTyoaika/view/";
    private static final String FXML_MAINVIEW_LOCATION = "fxTyoaika/view/main/MainView.fxml";
    private static final String FXML_STARTVIEW_LOCATION = "fxTyoaika/view/start/StartView.fxml";

    private static final String FXML_STARTVIEW_USERDIALOG_PATH = "fxTyoaika/view/start/NewUserDialogView.fxml";
    private static final String FXML_STARTVIEW_PROJECTDIALOG_PATH = "fxTyoaika/view/start/NewProjectDialogView.fxml";

    private static final String FXML_MAINVIEW_EDIT_ENTRY_DIALOG_PATH = "fxTyoaika/view/main/EditEntryDialog.fxml";
    private static final String FXML_MAINVIEW_DELETE_ENTRY_DIALOG_PATH = "fxTyoaika/view/main/DeleteEntryDialog.fxml";
    
    private static final String FXML_MAINVIEW_TIMERTAB_PATH = "fxTyoaika/view/main/TimerTabView.fxml";
    private static final String FXML_MAINVIEW_PROJECTTAB_PATH = "fxTyoaika/view/main/ProjectTabView.fxml";

    /*
     * Luodaan modelAccess, jonka avulla ylläpidetään ohjelman tilaa.
     * ModelAccess hakee luonnin yhteydessä tietorakenteeseen/tietokantaan tallennetut tiedot ohjelman
     * käyttäjistä, projekteista sekä niihin tehdyistä merkinnöistä. Viitettä tähän olioon välitetään parametreina
     * ohjelman kaikille kontrollereille.
     */
    private static final ModelAccess modelAccess = new ModelAccess();
    
    /**
     * Luo käynnistysikkunan.
     * @param stage ohjelman stage
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static StartController createStartView(Stage stage) {
        StartController controller = new StartController(modelAccess, stage);
        createAndShowWindow(controller, FXML_STARTVIEW_LOCATION, stage, false);
        return controller;
    }
    
    /**
     * Luo uuden käyttäjän luomiseen käytettävän modaalisen popup-dialogin
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static NewUserDialogController createNewUserDialog() {
        Stage stage = new Stage();
        NewUserDialogController controller = new NewUserDialogController(modelAccess, stage);
        createAndShowWindow(controller, FXML_STARTVIEW_USERDIALOG_PATH, stage, true);
        return controller;
    }
    
    /**
     * Luo uuden projektin luomiseen käytettävän modaalisen popup-dialogin
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static NewProjectDialogController createNewProjectDialog() {
        Stage stage = new Stage();
        NewProjectDialogController controller = new NewProjectDialogController(modelAccess, stage);
        createAndShowWindow(controller, FXML_STARTVIEW_PROJECTDIALOG_PATH, stage, true);
        return controller;
    }
    
    /**
     * Luo pääikkunan.
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static MainController createMainView() {
        Stage stage = new Stage();
        MainController controller = new MainController(modelAccess, stage);
        createAndShowWindow(controller, FXML_MAINVIEW_LOCATION, stage, false);
        return controller;
    }
    
    
    
    /**
     * Luo merkinnän muokkaamiseen käytettävän modaalisen popup-dialogin
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static EditEntryDialogController createEditEntryDialog() {
        Stage stage = new Stage();
        EditEntryDialogController controller = new EditEntryDialogController(modelAccess, stage);
        createAndShowWindow(controller, FXML_MAINVIEW_EDIT_ENTRY_DIALOG_PATH, stage, true);
        
        return controller;
    }
    
    
    /**
     * Luo merkinnän poistamiseen käytettävän modaalisen popup-dialogin
     * @return palauttaa luodun ikkunan kontrolleriluokan
     */
    public static DeleteEntryDialogController createDeleteEntryDialog() {
        Stage stage = new Stage();
        DeleteEntryDialogController controller = new DeleteEntryDialogController(modelAccess, stage);
        createAndShowWindow(controller, FXML_MAINVIEW_DELETE_ENTRY_DIALOG_PATH, stage, true);

        return controller;
    }
    
    
    /**
     * Luo pääikkunan ajastinvälilehden
     * @param parentController luotavan kontrollerin vanhempi
     * @return palauttaa Parent-muotoisen olion
     */
    public static Parent createTimerTab(MainController parentController) {
        Stage stage = parentController.getStage();
        TimerTabController timerTabController = new TimerTabController(modelAccess, stage, parentController);
        
        return createParentNode(timerTabController, FXML_MAINVIEW_TIMERTAB_PATH);
    }
    
    /**
     * Luo pääikkunan projektivälilehden
     * @param parentController luotavan kontrollerin vanhempi
     * @return palauttaa Parent-muotoisen olion
     */
    public static Parent createProjecTab(MainController parentController) {
        Stage stage = parentController.getStage();
        ProjectTabController projectTabController = new ProjectTabController(modelAccess, stage, parentController);
        
        return createParentNode(projectTabController, FXML_MAINVIEW_PROJECTTAB_PATH);
    }
    
    
    /**
     * Lataa FXML-tiedostosta uuden Parent-solmun annetuilla parametreilla. Asettaa solmulle kontrollerin.
     * @param controller Solmulle asetettava kontrolleriluokka
     * @param path Polku solmun sisällön määrittelevään FXML-tiedostoon
     * @return 
     */
    private static Parent createParentNode(WindowController controller, String path) {
        URL location = controller.getClass().getClassLoader()
                .getResource(path);
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
     * Luo annetussa polussa olevasta FXML-tiedostosta scene-olion.
     * @param controller Näkymään liitettävä kontrolleriluokka
     * @param path polku FXML-tiedostoon
     * @return palauttaa scene-olion
     */
    private static Scene createView(WindowController controller, String path) {
        Parent root = createParentNode(controller, path);
        Scene scene = new Scene(root);
        scene.getStylesheets()
                .add(controller.getClass().getClassLoader()
                        .getResource("fxTyoaika/view/tyoaika.css")
                        .toExternalForm());

        return scene;
    }
    
    /**
     * Luo annetussa polussa olevan näkymän, yhdistää näkymän stageen ja näyttää stagen.
     * 
     * @param controller Näkymään liitettävä kontrolleriluokka, joka välitetään parametrina ketjun seuraavalle metodille
     * @param path polku FXML-tiedostoon
     * @param stage stage, johon luotava näkymä liitetään.
     * @param modal Halutaanko näytettävästä ikkunasta modaalinen
     */
    private static void createAndShowWindow(WindowController controller, String path, Stage stage, boolean modal) {
        
        // TODO parametriksi voisi lisätä halutaanko kutsua showAndWait()?
        Parent root = createParentNode(controller, path);
        Scene scene = new Scene(root);
        scene.getStylesheets()
        .add(controller.getClass().getClassLoader()
                .getResource("fxTyoaika/view/tyoaika.css")
                .toExternalForm());
        stage.setScene(scene);
        if (modal) {            
            stage.initModality(Modality.APPLICATION_MODAL);
        }
        
        stage.show();
    }


}
