package fxTyoaika.view;

import java.net.URL;

import fxTyoaika.controller.*;
import fxTyoaika.controller.mainDialogs.*;
import fxTyoaika.controller.startDialogs.*;
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

    private static final String FXML_STARTVIEW_USERDIALOG_PATH = "fxTyoaika/view/startDialogs/NewUserDialogView.fxml";
    private static final String FXML_STARTVIEW_PROJECTDIALOG_PATH = "fxTyoaika/view/startDialogs/NewProjectDialogView.fxml";

    private static final String FXML_MAINVIEW_SAVE_ENTRY_DIALOG_PATH = "fxTyoaika/view/mainDialogs/SaveEntryDialog.fxml";
    private static final String FXML_MAINVIEW_DELETE_ENTRY_DIALOG_PATH = "fxTyoaika/view/mainDialogs/DeleteEntryDialog.fxml";

    
    /**
     * Luo pääikkunan.
     * @param parentController viite kontrolleriin, josta uusi ikkuna luodaan
     * @return palauttaa Scene-olion
     */
    public static Scene createMainView(AbstractController parentController) {
        MainController controller = new MainController(parentController.getModelAccess());
        return createView(controller, FXML_MAINVIEW_LOCATION);
    }
    
    /**
     * Luo uuden käyttäjän luomiseen käytettävän popup-dialogin
     * @param parentController viite kontrolleriin, josta uusi ikkuna luodaan
     * @return palauttaa Scene-olion
     */
    public static Scene createNewUserDialog(AbstractController parentController) {
        NewUserDialogController controller = new NewUserDialogController(parentController.getModelAccess());
        return createView(controller, FXML_STARTVIEW_USERDIALOG_PATH);
    }

    /**
     * Luo uuden projektin luomiseen käytettävän popup-dialogin
     * @param parentController viite kontrolleriin, josta uusi ikkuna luodaan
     * @return palauttaa Scene-olion
     */
    public static Scene createNewProjectDialog(AbstractController parentController) {
        NewProjectDialogController controller = new NewProjectDialogController(parentController.getModelAccess());
        return createView(controller, FXML_STARTVIEW_PROJECTDIALOG_PATH);
    }

    /**
     * Luo merkinnän muokkaamiseen käytettävän popup-dialogin
     * @param parentController viite kontrolleriin, josta uusi ikkuna luodaan
     */
    public static void createSaveEntryDialog(AbstractController parentController) {
        SaveEntryDialogController controller = new SaveEntryDialogController(parentController.getModelAccess());
        createPopup(controller, FXML_MAINVIEW_SAVE_ENTRY_DIALOG_PATH);
    }
    
    /**
     * Luo merkinnän poistamiseen käytettävän popup-dialogin
     * @param parentController viite kontrolleriin, josta uusi ikkuna luodaan
     */
    public static void createDeleteEntryDialog(AbstractController parentController) {
        DeleteEntryDialogController controller = new DeleteEntryDialogController(parentController.getModelAccess());
        createPopup(controller, FXML_MAINVIEW_DELETE_ENTRY_DIALOG_PATH);
    }
    

    /**
     * Lataa FXML-tiedostosta uuden näkymän annetuilla parametreilla. Asettaa näkymälle kontrollerin. Asettaa näkymän scene-olioon.
     * @param controller FXML-tiedoston kontrolleriluokka
     * @param path polku FXML-tiedostoon
     * @return palauttaa scene-olion
     */
    private static Scene createView(AbstractController controller,
            String path) {
        Scene scene;
        try {
            URL location = controller.getClass().getClassLoader()
                    .getResource(path);
            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(location);
            fxmlloader.setController(controller);

            Parent root = fxmlloader.load();
            scene = new Scene(root);
            scene.getStylesheets()
                    .add(controller.getClass().getClassLoader()
                            .getResource("fxTyoaika/view/tyoaika.css")
                            .toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
            scene = null;
        }

        return scene;
    }
    
    /**
     * Käytetään modaalisen popup-dialogin luomiseen. Lataa FXML-tiedostosta uuden näkymän annetuilla parametreilla.
     * Asettaa näkymälle kontrollerin. Asettaa näkymän scene-olioon. Palauttaa stage-olion, mutta asettaa myös uuden ikkunan suoraan näkyviin.
     * @param controller FXML-tiedoston kontrolleriluokka
     * @param path polku FXML-tiedostoon
     * @return palauttaa stage-olion
     */
    private static Stage createPopup(AbstractController controller, String path) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene;
        try {
            URL location = controller.getClass().getClassLoader()
                    .getResource(path);
            FXMLLoader fxmlloader = new FXMLLoader();
            fxmlloader.setLocation(location);
            fxmlloader.setController(controller);
            
            Parent root = fxmlloader.load();
            scene = new Scene(root);
            scene.getStylesheets()
            .add(controller.getClass().getClassLoader()
                    .getResource("fxTyoaika/view/tyoaika.css")
                    .toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
            scene = null;
        }
        
        stage.setScene(scene);
        stage.show();
        
        return stage;
    }
    
    

}
