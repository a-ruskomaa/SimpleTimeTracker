package roarusko.simpleTimeTracker.view.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.scene.control.DatePicker;
import javafx.util.converter.LocalDateStringConverter;
import roarusko.simpleTimeTracker.model.utility.Entries;

/**
 * Lisää DatePickeriin toiminnallisuuden, jossa sen arvo päivitetään aina kun focus poistuu
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class EnhancedDatePicker extends DatePicker {
    private DateTimeFormatter dateFormatter = Entries.getDateFormatter();
    private LocalDateStringConverter dateConverter = new LocalDateStringConverter(
            dateFormatter, dateFormatter);

    public EnhancedDatePicker() {
        super();
        this.setConverter(dateConverter);
        setListeners();
    }


    public EnhancedDatePicker(LocalDate localDate) {
        super(localDate);
        this.setConverter(dateConverter);
        setListeners();
    }
    
    private void setListeners() {
        this.focusedProperty().addListener((e) -> {
            try {
                this.setValue(this.getConverter()
                        .fromString(this.getEditor().getText()));
            } catch (DateTimeParseException ex) {
                System.out.println("invalid date format");
                this.getEditor().setText(this
                        .getConverter().toString(this.getValue()));
            }
        });
    }
   
    

}
