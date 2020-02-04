package fxTyoaika;
	
import fxTyoaika.controller.ModelAccess;
import fxTyoaika.controller.StartController;
import fxTyoaika.view.ViewFactory;
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
		    /*
		     * Luodaan modelAccess, jonka avulla ylläpidetään ohjelman tilaa.
		     * ModelAccess hakee luonnin yhteydessä tietorakenteeseen/tietokantaan tallennetut tiedot ohjelman
		     * käyttäjistä, projekteista sekä niihin tehdyistä merkinnöistä. Viitettä tähän olioon välitetään parametreina
		     * ohjelman kaikille kontrollereille.
		     */
		    System.out.println("käynnistys");
		    //ModelAccess modelAccess = new ModelAccess();
		    
//			BorderPane root;
//			FXMLLoader fxmlloader = new FXMLLoader();
//			
//			fxmlloader.setLocation(getClass().getClassLoader().getResource("fxTyoaika/view/StartView.fxml"));
//			StartController controller = new StartController(modelAccess);
//			fxmlloader.setController(controller);
//			root = fxmlloader.load();
//			
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getClassLoader().getResource("fxTyoaika/view/tyoaika.css").toExternalForm());
//			primaryStage.setScene(scene);
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
