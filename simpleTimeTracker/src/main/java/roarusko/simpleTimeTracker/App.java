package roarusko.simpleTimeTracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import roarusko.simpleTimeTracker.controller.ViewFactory;

/**
 * @author roarusko
 * @version 16.1.2020
 *
 * Tämä ohjelma on yksinkertainen apuväline projekteihin käytetyn ajan hallinnoimiseksi.
 * 
 */
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("käynnistys");

            ViewFactory.createStartView(primaryStage);
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
