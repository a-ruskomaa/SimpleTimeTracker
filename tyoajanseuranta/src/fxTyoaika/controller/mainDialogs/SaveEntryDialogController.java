package fxTyoaika.controller.mainDialogs;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.ModelAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SaveEntryDialogController extends AbstractController {

    @FXML
    private Button entryDialogSaveButton;

    @FXML
    void entryDialogHandleSave(ActionEvent event) {
        //
    }
    
    public SaveEntryDialogController(ModelAccess modelAccess) {
        super(modelAccess);
    }

}
