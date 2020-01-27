package fxTyoaika;
	
import fxTyoaika.Controller.TyoaikaStartController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * @author roarusko
 * @version 16.1.2020
 *
 */
public class Tyoaika extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root;
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getClassLoader().getResource("fxTyoaika/View/TyoaikaStartView.fxml"));
			
			TyoaikaStartController controller = new TyoaikaStartController();
			
			fxmlloader.setController(controller);
			
			root = fxmlloader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("fxTyoaika/View/tyoaika.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
