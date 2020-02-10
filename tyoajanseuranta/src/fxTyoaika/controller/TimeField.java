package fxTyoaika.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.function.UnaryOperator;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.LocalTimeStringConverter;

public class TimeField extends TextField {

    public TimeField() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        // String timePatternSeconds =
        // "([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        // String timePatternSeconds =
        // "([01]?[0-9]?|2?[0-3]?):?[0-5]?[0-9]?:?[0-5]?[0-9]?";
//         String timePatternSeconds = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
        // String timePattern = "([01][0-9]|2[0-3]):[0-5][0-9]";

        UnaryOperator<TextFormatter.Change> textFilter = change -> {
//            if (change.getControlNewText().length() >= 8) {
//                if (!change.getControlNewText().matches(timePatternSeconds)) {
//                    this.setStyle("-fx-text-box-border: red ;");
//                    return change;
//                }
//                this.setStyle("");
//                return change;
//            }
            // if (!change.getControlNewText().matches(timePatternSeconds)) {
            // return null;
            // }
            // return change;

            if (!change.getText().matches("([0-9]*|:)"))
                return null;
            return change;

        };

        this.setTextFormatter(new TextFormatter<LocalTime>(
                new LocalTimeStringConverter(formatter, formatter),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES), textFilter));

    }


    public LocalTime getValue() {
        return (LocalTime) this.getTextFormatter().getValue();
    }


    public ObjectProperty<LocalTime> valueProperty() {
        return (ObjectProperty<LocalTime>) this.getTextFormatter()
                .valueProperty();
    }


    public void setFormatter(DateTimeFormatter formatter) {
        this.setTextFormatter(new TextFormatter<LocalTime>(
                new LocalTimeStringConverter(formatter, formatter),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES)));
    }
}
