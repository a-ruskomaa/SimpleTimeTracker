package roarusko.simpleTimeTracker.view.components;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.function.UnaryOperator;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import roarusko.simpleTimeTracker.model.utility.Entries;

public class DateTimeField extends TextField {

    public DateTimeField() {
        DateTimeFormatter formatter = Entries.getDateTimeFormatter();

        this.setTextFormatter(new TextFormatter<LocalDateTime>(
                new LocalDateTimeStringConverter(formatter, formatter)));
    }


    /**
     * @return Palauttaa kentän arvon
     */
    public LocalDateTime getValue() {
        return (LocalDateTime) this.getTextFormatter().getValue();
    }
    
    
    /**
     * @param value Asettaa kentän arvon
     */
    public void setValue(LocalDateTime value) {
        this.valueProperty().set(value);
    }


    /**
     * @return Palauttaa kentän arvon propertyna
     */
    @SuppressWarnings("unchecked")
    public ObjectProperty<LocalDateTime> valueProperty() {
        return (ObjectProperty<LocalDateTime>) this.getTextFormatter()
                .valueProperty();
    }


    /**
     * @param formatter formatter
     */
    public void setFormatter(DateTimeFormatter formatter) {
        this.setTextFormatter(new TextFormatter<LocalDateTime>(
                new LocalDateTimeStringConverter(formatter, formatter)));
    }
    
    /**
     * @param converter formatter
     */
    public void setConverter(LocalDateTimeStringConverter converter) {
        this.setTextFormatter(new TextFormatter<LocalDateTime>(converter));
    }
}
