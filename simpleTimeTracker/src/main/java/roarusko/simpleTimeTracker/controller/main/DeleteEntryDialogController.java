package roarusko.simpleTimeTracker.controller.main;

import roarusko.simpleTimeTracker.controller.AbstractController;
import roarusko.simpleTimeTracker.model.ModelAccess;
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
