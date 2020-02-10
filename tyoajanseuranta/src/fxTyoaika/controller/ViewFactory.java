package fxTyoaika.controller;

import java.io.IOException;
import java.net.URL;

import fxTyoaika.controller.*;
import fxTyoaika.controller.mainDialogs.*;
import fxTyoaika.controller.mainTabs.ProjectTabController;
import fxTyoaika.controller.mainTabs.TimerTabController;
import fxTyoaika.controller.startDialogs.*;
import fxTyoaika.model.ModelAccess;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author aleks
 * @version 28 Jan 2020
 *
 * Luokka sisältää staattisia metodeja uusien ikkunoiden avaamiseksi. Fxml-tiedostojen polku on tallennettuna luokan vakioihin.
 * Uusia näkymiä luovat metodit hakevat vanhan näkymän kontrollerista viitteen pääohjelmassa luotuun ModelAccessiin, jolla varmistetaan
 * että ohjelma säilyttää tilan.
 */
public class ViewFactory {

    private static final String FXML_BASE_LOCATION = "fxTyoaika/view/";
    private static final String FXML_MAINVIEW_LOCATION = "fxTyoaika/view/MainView.fxml";
    private static final String FXML_STARTVIEW_LOCATION = "fxTyoaika/view/StartView.fxml";

    private static final String FXML_STARTVIEW_USERDIALOG_PATH = "fxTyoaika/view/startDialogs/NewUserDialogView.fxml";
    private static final String FXML_STARTVIEW_PROJECTDIALOG_PATH = "fxTyoaika/view/startDialogs/NewProjectDialogView.fxml";

    private static final String FXML_MAINVIEW_SAVE_ENTRY_DIALOG_PATH = "fxTyoaika/view/mainDialogs/SaveEntryDialog.fxml";
    private static final String FXML_MAINVIEW_EDIT_ENTRY_DIALOG_PATH = "fxTyoaika/view/mainDialogs/EditEntryDialog.fxml";
    private static final String FXML_MAINVIEW_DELETE_ENTRY_DIALOG_PATH = "fxTyoaika/view/mainDialogs/DeleteEntryDialog.fxml";
    
    private static final String FXML_MAINVIEW_TIMERTAB_PATH = "fxTyoaika/view/mainTabs/TimerTabView.fxml";
    private static final String FXML_MAINVIEW_PROJECTTAB_PATH = "fxTyoaika/view/mainTabs/ProjectTabView.fxml";

    /*
     * Luodaan modelAccess, jonka avulla ylläpidetään ohjelman tilaa.
     * ModelAccess hakee luonnin yhteydessä tietorakenteeseen/tietokantaan tallennetut tiedot ohjelman
     * käyttäjistä, projekteista sekä niihin tehdyistä merkinnöistä. Viitettä tähän olioon välitetään parametreina
     * ohjelman kaikille kontrollereille.
     */
    private static final ModelAccess modelAccess = new ModelAccess();
    
    /**
     * Luo käynnistysikkunan.
     * @return palauttaa Scene-olion
     */
    public static Scene createStartView() {
        StartController controller = new StartController(modelAccess);
        return createView(controller, FXML_STARTVIEW_LOCATION);
    }
    
    /**
     * Luo uuden käyttäjän luomiseen käytettävän popup-dialogin
     * @return palauttaa Scene-olion
     */
    public static Stage createNewUserDialog() {
        NewUserDialogController controller = new NewUserDialogController(modelAccess);
        return createModalStage(controller, FXML_STARTVIEW_USERDIALOG_PATH);
    }
    
    /**
     * Luo uuden projektin luomiseen käytettävän popup-dialogin
     * @return palauttaa Scene-olion
     */
    public static Stage createNewProjectDialog() {
        NewProjectDialogController controller = new NewProjectDialogController(modelAccess);
        return createModalStage(controller, FXML_STARTVIEW_PROJECTDIALOG_PATH);
    }
    
    /**
     * Luo pääikkunan.
     * @return palauttaa Scene-olion
     */
    public static Scene createMainView() {
        MainController parentController = new MainController(modelAccess);
        return createView(parentController, FXML_MAINVIEW_LOCATION);
    }
    
    /**
     * Luo pääikkunan ajastinvälilehden
     * @return palauttaa 
     */
    public static Parent createTimerTab() {
        TimerTabController timerTabController = new TimerTabController(modelAccess);
        return createParent(timerTabController, FXML_MAINVIEW_TIMERTAB_PATH);
    }
    
    /**
     * Luo pääikkunan ajastinvälilehden
     * @return palauttaa 
     */
    public static Parent createProjecTab() {
        ProjectTabController projectTabController = new ProjectTabController(modelAccess);
        return createParent(projectTabController, FXML_MAINVIEW_PROJECTTAB_PATH);
    }

    /**
     * Luo merkinnän muokkaamiseen käytettävän popup-dialogin
     * @return palauttaa stage-olion
     */
    public static Stage createSaveEntryDialog() {
        SaveEntryDialogController controller = new SaveEntryDialogController(modelAccess);
        Stage stage = createModalStage(controller, FXML_MAINVIEW_SAVE_ENTRY_DIALOG_PATH);
        stage.setOnCloseRequest((event) -> {
            modelAccess.resetCurrentlyEditedEntry();
            System.out.println("temp entry reset");
            });

        return stage;
    }
    
    /**
     * Luo merkinnän muokkaamiseen käytettävän popup-dialogin
     * @return palauttaa stage-olion
     */
    public static Stage createEditEntryDialog() {
        EditEntryDialogController controller = new EditEntryDialogController(modelAccess);
        Stage stage = createModalStage(controller, FXML_MAINVIEW_EDIT_ENTRY_DIALOG_PATH);
        stage.setOnCloseRequest((event) -> {
            modelAccess.resetCurrentlyEditedEntry();
            System.out.println("temp entry reset");
        });
        
        return stage;
    }
    
    /**
     * Luo merkinnän poistamiseen käytettävän popup-dialogin
     * @return palauttaa stage-olion
     */
    public static Stage createDeleteEntryDialog() {
        DeleteEntryDialogController controller = new DeleteEntryDialogController(modelAccess);
        Stage stage = createModalStage(controller, FXML_MAINVIEW_DELETE_ENTRY_DIALOG_PATH);

        return stage;
    }
    
    
    /**
     * Lataa FXML-tiedostosta uuden Parent-solmun annetuilla parametreilla. Asettaa solmulle kontrollerin.
     * @param controller Solmun kontrolleriluokka
     * @param path Polku solmun sisällön määrittelevään FXML-tiedostoon
     * @return
     */
    private static Parent createParent(AbstractController controller, String path) {
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
     * Asettaa näkymän scene-olioon.
     * @param controller Näkymään liitettävä kontrolleriluokka
     * @param path polku FXML-tiedostoon
     * @return palauttaa scene-olion
     */
    private static Scene createView(AbstractController controller, String path) {
        Parent root = createParent(controller, path);
        Scene scene = new Scene(root);
        scene.getStylesheets()
                .add(controller.getClass().getClassLoader()
                        .getResource("fxTyoaika/view/tyoaika.css")
                        .toExternalForm());

        return scene;
    }
    
    /**
     * Käytetään modaalisen popup-dialogin luomiseen. Lataa FXML-tiedostosta uuden näkymän annetuilla parametreilla.
     * Asettaa näkymälle kontrollerin. Asettaa näkymän scene-olioon. Palauttaa stage-olion, mutta asettaa myös uuden ikkunan suoraan näkyviin.
     * @param controller Näkymään liitettävä kontrolleriluokka
     * @param path polku FXML-tiedostoon
     * @return palauttaa stage-olion
     */
    private static Stage createModalStage(AbstractController controller, String path) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        
        Scene scene = createView(controller, path);
        
        stage.setScene(scene);
        
        return stage;
    }
    
    

}