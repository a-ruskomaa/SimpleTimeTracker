package roarusko.simpleTimeTracker.view.components;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.UnaryOperator;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.LocalDateTimeStringConverter;
import roarusko.simpleTimeTracker.model.utility.Entries;


/**
 * TextField-käyttöliittymäkomponentin laajennus, johon on liitetty tekstiä LocalDateTime-olioksi
 * ja LocalDateTime-olioita tekstiksi konvertoiva TextFormatter.
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class DateTimeField extends TextField {
    DateTimeFormatter formatter = Entries.getDateTimeFormatter();

    
    /**
     * Luo uuden DateTimeFieldin, jonka formaatti vastaa Entries-luokkaan tallennettua formaattia
     */
    public DateTimeField() {

        this.setTextFormatter(new TextFormatter<LocalDateTime>(
                new LocalDateTimeStringConverter(formatter, formatter),
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), createFilter()));
    }
    
    
    private UnaryOperator<TextFormatter.Change> createFilter() {
        return new UnaryOperator<TextFormatter.Change>() {

            @Override
            public Change apply(Change change) {
//              if (change.getControlNewText().length() >= 8) {
//              if (!change.getControlNewText().matches(timePatternSeconds)) {
//                  this.setStyle("-fx-text-box-border: red ;");
//                  return change;
//              }
//              this.setStyle("");
//              return change;
//          }
          // if (!change.getControlNewText().matches(timePatternSeconds)) {
          // return null;
          // }
          // return change;

          //hyväksytään vain numeeriset merkit, piste tai kaksoispiste
          // ainakaan toistaiseksi sen kummempaa tarkastelua ei suoriteta
          if (!change.getText().matches("([0-9]*|.|:)")) {
              return null;
          }
          return change;
            }
        };
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
     * @param converter converter
     */
    @SuppressWarnings("exports")
    public void setConverter(LocalDateTimeStringConverter converter) {
        this.setTextFormatter(new TextFormatter<LocalDateTime>(converter,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), this.getTextFormatter().getFilter()));
    }
}
