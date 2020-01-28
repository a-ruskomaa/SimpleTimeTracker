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

public class ViewFactory {

    private static final String FXML_BASE_LOCATION = "fxTyoaika/view/";
    private static final String FXML_MAINVIEW_LOCATION = "fxTyoaika/view/MainView.fxml";

    private static final String FXML_STARTVIEW_USERDIALOG_PATH = "fxTyoaika/view/startDialogs/NewUserDialogView.fxml";
    private static final String FXML_STARTVIEW_PROJECTDIALOG_PATH = "fxTyoaika/view/startDialogs/NewProjectDialogView.fxml";

    private static final String FXML_MAINVIEW_SAVE_ENTRY_DIALOG_PATH = "fxTyoaika/view/mainDialogs/SaveEntryDialog.fxml";
    private static final String FXML_MAINVIEW_DELETE_ENTRY_DIALOG_PATH = "fxTyoaika/view/mainDialogs/DeleteEntryDialog.fxml";

    
    public static Scene createMainView(AbstractController parentController) {
        MainController controller = new MainController(parentController.getModelAccess());
        return createView(controller, FXML_MAINVIEW_LOCATION);
    }
    
    
    public static Scene createNewUserDialog(AbstractController parentController) {
        NewUserDialogController controller = new NewUserDialogController(parentController.getModelAccess());
        return createView(controller, FXML_STARTVIEW_USERDIALOG_PATH);
    }

    
    public static Scene createNewProjectDialog(AbstractController parentController) {
        NewProjectDialogController controller = new NewProjectDialogController(parentController.getModelAccess());
        return createView(controller, FXML_STARTVIEW_PROJECTDIALOG_PATH);
    }


    public static void createSaveEntryDialog(AbstractController parentController) {
        SaveEntryDialogController controller = new SaveEntryDialogController(parentController.getModelAccess());
        createPopup(controller, FXML_MAINVIEW_SAVE_ENTRY_DIALOG_PATH);
    }
    
    public static void createDeleteEntryDialog(AbstractController parentController) {
        DeleteEntryDialogController controller = new DeleteEntryDialogController(parentController.getModelAccess());
        createPopup(controller, FXML_MAINVIEW_DELETE_ENTRY_DIALOG_PATH);
    }
    

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
