package roarusko.simpleTimeTracker.controller;

import roarusko.simpleTimeTracker.model.data.DataAccess;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author aleks
 * @version 28 Jan 2020
 * Muiden kontrollerien yläluokka. Sisältää viitteen DataAccessiin. ViewFactory kelpuuttaa ikkunoiden luomiseen AbstractControllerin.
 */
public class AbstractController implements WindowController {
    /**
     * DataAccess-olio, jonka avulla kontrollerit keskustelevat tietokannan kanssa
     */
    protected final DataAccess dataAccess;
    
    /**
     * Stage johon kontrolleriluokka on liitetty. Hyödynnetään ikkunoiden sulkemisessa ja tapahtumien luomisessa.
     */
    protected final Stage stage;
    
    /**
     * @param dataAccess Ohjelman käynnistyksen yhteydessä luotu DataAccess
     * @param stage näkymä, jota tämä kontrolleri ohjaa
     */
    public AbstractController(DataAccess dataAccess, Stage stage) {
        this.dataAccess = dataAccess;
        this.stage = stage;
    }
    
    /**
     * @return palauttaa stagen jota kontrolleri ohjaa
     */
    @Override
    public Stage getStage() {
        return this.stage;
    }
    
    
    @Override
    public void showStage() {
        this.stage.show();
    }
    
    
    /**
     * Käskee kontrolleria näyttämään siihen liitetyn stagen modaalisena
     */
    public void showModalStage() {
        this.stage.initModality(Modality.APPLICATION_MODAL);
        
        this.stage.showAndWait();
    }
}
