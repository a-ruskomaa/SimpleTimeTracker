package roarusko.simpleTimeTracker.controller;

import roarusko.simpleTimeTracker.model.ModelAccess;
import javafx.stage.Stage;

/**
 * @author aleks
 * @version 28 Jan 2020
 * Muiden kontrollerien yläluokka. Sisältää viitteen ModelAccessiin. ViewFactory kelpuuttaa ikkunoiden luomiseen AbstractControllerin.
 */
public class AbstractController implements WindowController {
    /**
     * Ohjelman käsittelemää dataa mallintavien luokkien "access point".
     */
    protected final ModelAccess modelAccess;
    
    /**
     * Stage johon kontrolleriluokka on liitetty. Hyödynnetään ikkunoiden sulkemisessa ja tapahtumien luomisessa.
     */
    protected final Stage stage;
    
    /**
     * @param modelAccess Ohjelman käynnistyksen yhteydessä luotu ModelAccess
     * @param stage näkymä, jota tämä kontrolleri ohjaa
     */
    public AbstractController(ModelAccess modelAccess, Stage stage) {
        this.modelAccess = modelAccess;
        this.stage = stage;
    }
    
    /**
     * @return palauttaa stagen jota kontrolleri ohjaa
     */
    @Override
    public Stage getStage() {
        return this.stage;
    }
}
