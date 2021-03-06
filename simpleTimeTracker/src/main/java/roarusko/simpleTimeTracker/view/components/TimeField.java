package roarusko.simpleTimeTracker.view.components;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.UnaryOperator;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.LocalTimeStringConverter;
import roarusko.simpleTimeTracker.model.utility.Entries;

/**
 * TextField-käyttöliittymäkomponentin laajennus, johon on liitetty tekstiä LocalTime-olioksi
 * ja LocalTime-olioita tekstiksi konvertoiva TextFormatter. Rajoittaa tekstikenttään 
 * @author aleks
 * @version 2 Mar 2020
 *
 */
public class TimeField extends TextField {
    DateTimeFormatter formatter = Entries.getTimeFormatter();

    /**
     * Luo uuden TimeFieldin, jonka formaatti vastaa Entries-luokkaan tallennettua formaattia.
     */
    public TimeField() {
        // String timePatternSeconds =
        // "([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        // String timePatternSeconds =
        // "([01]?[0-9]?|2?[0-3]?):?[0-5]?[0-9]?:?[0-5]?[0-9]?";
//         String timePatternSeconds = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
        // String timePattern = "([01][0-9]|2[0-3]):[0-5][0-9]";

        this.setTextFormatter(new TextFormatter<LocalTime>(
                new LocalTimeStringConverter(formatter, formatter),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES), createFilter()));

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
          if (!change.getText().matches("([0-9]*|:)")) {
              return null;
          }
          return change;
            }
        };
    }
    
    /**
     * @return Palauttaa kentän arvon
     */
    public LocalTime getValue() {
        return (LocalTime) this.getTextFormatter().getValue();
    }

    
    /**
     * @return Palauttaa kentän arvon propertyna
     */
    @SuppressWarnings("unchecked")
    public ObjectProperty<LocalTime> valueProperty() {
        return (ObjectProperty<LocalTime>) this.getTextFormatter()
                .valueProperty();
    }

    
    /**
     * @param converter converter
     */
    public void setConverter(LocalTimeStringConverter converter) {
        this.setTextFormatter(new TextFormatter<LocalTime>(converter,
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES), this.getTextFormatter().getFilter()));
    }

    /**
     * @param value Asettaa kentän arvon
     */
    public void setValue(LocalTime value) {
        this.valueProperty().set(value);
    }
}
