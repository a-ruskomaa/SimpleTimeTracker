package fxTyoaika.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import fxTyoaika.Model.ModelAccess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * @author aleks
 * @version 27 Jan 2020
 *
 */
public class TyoaikaMainController implements Initializable {
    
    // olio, jonka avulla ladataan tiedostosta / tietokannasta käyttäjien ja projektien tiedot
    private ModelAccess modelAccess;

    @FXML
    private Button kaynnistaButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

    public ModelAccess getModelAccess() {
        return modelAccess;
    }

    public void setModelAccess(ModelAccess modelAccess) {
        this.modelAccess = modelAccess;
    }

}
