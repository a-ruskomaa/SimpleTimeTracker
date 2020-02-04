package fxTyoaika.controller;

import fxTyoaika.model.ModelAccess;

/**
 * @author aleks
 * @version 28 Jan 2020
 * Muiden kontrollerien yläluokka. Sisältää viitteen ModelAccessiin. ViewFactory kelpuuttaa ikkunoiden luomiseen AbstractControllerin.
 */
public class AbstractController {
    protected final ModelAccess modelAccess;
    
    /**
     * @param modelAccess Ohjelman käynnistyksen yhteydessä luotu ModelAccess
     */
    public AbstractController(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
    }
    
    /**
     * 
     * @return palauttaa viitteen ModelAccessiin.
     */
    public ModelAccess getModelAccess(){
        return modelAccess;
    }

}
