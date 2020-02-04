package fxTyoaika.controller.mainDialogs;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.model.ModelAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class DeleteEntryDialogController extends AbstractController {


    @FXML
    private Button entryDialogDeleteButton;

    @FXML
    void entryDialogHandleDelete(ActionEvent event) {
        //
    }
    
    public DeleteEntryDialogController(ModelAccess modelAccess) {
        super(modelAccess);
    }
    
    
}
