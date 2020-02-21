module roarusko.simpleTimeTracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens roarusko.simpleTimeTracker to javafx.fxml;
    opens roarusko.simpleTimeTracker.controller.main to javafx.fxml;
    opens roarusko.simpleTimeTracker.controller.start to javafx.fxml;
    exports roarusko.simpleTimeTracker;
}