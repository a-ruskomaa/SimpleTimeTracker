package fxTyoaika.controller.main;

import fxTyoaika.controller.AbstractController;
import fxTyoaika.controller.ModelAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteEntryDialogController extends AbstractController {


    @FXML
    private Button entryDialogDeleteButton;

    @FXML
    void entryDialogHandleDelete(ActionEvent event) {
        //
    }
    
    public DeleteEntryDialogController(ModelAccess modelAccess, Stage stage) {
        super(modelAccess, stage);
    }
    
    
}