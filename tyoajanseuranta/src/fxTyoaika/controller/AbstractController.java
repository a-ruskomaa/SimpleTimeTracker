package fxTyoaika.controller;

import javafx.stage.Stage;

/**
 * @author aleks
 * @version 28 Jan 2020
 * Muiden kontrollerien yläluokka. Sisältää viitteen ModelAccessiin. ViewFactory kelpuuttaa ikkunoiden luomiseen AbstractControllerin.
 */
public class AbstractController implements WindowController {
    protected final ModelAccess modelAccess;
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
