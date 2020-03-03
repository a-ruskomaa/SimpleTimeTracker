/**
 * simpleTimeTracker-projektin ainoa moduuli.
 * @author aleks
 *
 */
module simpleTimeTracker {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    
    opens roarusko.simpleTimeTracker.controller to javafx.fxml;
    opens roarusko.simpleTimeTracker.controller.main to javafx.fxml;
    opens roarusko.simpleTimeTracker.controller.main.dialogs to javafx.fxml;
    opens roarusko.simpleTimeTracker.controller.main.tabs to javafx.fxml;
    opens roarusko.simpleTimeTracker.controller.start to javafx.fxml;
    opens roarusko.simpleTimeTracker.controller.start.dialogs to javafx.fxml;
    
    exports roarusko.simpleTimeTracker.view.components to javafx.fxml;
    exports roarusko.simpleTimeTracker;
}