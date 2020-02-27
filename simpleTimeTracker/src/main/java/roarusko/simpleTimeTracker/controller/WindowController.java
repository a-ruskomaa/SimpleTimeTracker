package roarusko.simpleTimeTracker.controller;

import javafx.stage.Stage;

/**
 * Rajapinta, jonka kontrolleriluokat toteuttavat.
 * @author aleks
 * @version 21 Feb 2020
 *
 */
public interface WindowController {
    
    /**
     * Palauttaa stagen, johon kontrolleri on liitetty.
     * @return Palauttaa stagen, johon kontrolleri on liitetty.
     */
    public Stage getStage();
    
    /**
     * Käskee kontrolleria näyttämään siihen liitetyn stagen
     */
    public void showStage();

}
