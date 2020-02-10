package fxTyoaika;
	
import fxTyoaika.controller.StartController;
import fxTyoaika.controller.ViewFactory;
import fxTyoaika.model.ModelAccess;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * @author roarusko
 * @version 16.1.2020
 *
 * Tämä ohjelma on yksinkertainen apuväline projekteihin käytetyn ajan hallinnoimiseksi.
 * 
 */
public class Tyoaika extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    System.out.println("käynnistys");

			primaryStage.setScene(ViewFactory.createStartView());
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
