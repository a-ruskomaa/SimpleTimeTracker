package roarusko.simpleTimeTracker;

import javafx.application.Application;
import javafx.stage.Stage;

import roarusko.simpleTimeTracker.view.ViewFactory;
import roarusko.simpleTimeTracker.controller.WindowController;
import roarusko.simpleTimeTracker.model.data.ConnectionManager;
import roarusko.simpleTimeTracker.model.data.ConnectionManagerImpl;
import roarusko.simpleTimeTracker.model.data.DataAccess;
import roarusko.simpleTimeTracker.model.data.DataAccessImpl;
import roarusko.simpleTimeTracker.model.data.SampleData;
import roarusko.simpleTimeTracker.model.data.db.EntryDAO;
import roarusko.simpleTimeTracker.model.data.db.ProjectDAO;
import roarusko.simpleTimeTracker.model.data.db.UserDAO;

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
            /*
             * Luodaan dataAccess, jonka avulla ohjelman käsittelemää dataa luetaan ja kirjoitetaan tietokantaan
             */
            ConnectionManager cm = new ConnectionManagerImpl();
            cm.initDb();
            
            SampleData.createTestDb(cm);
            
            DataAccess dataAccess = new DataAccessImpl(new UserDAO(cm), new ProjectDAO(cm), new EntryDAO(cm));

            WindowController wc = ViewFactory.createStartView(primaryStage, dataAccess);
            wc.showStage();
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
