package roarusko.simpleTimeTracker.view.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;


/**
 * ListView-käyttöliittymäkomponentin laajennus, joka mahdollistaa valitun alkion sisältävän
 * propertyn sitomisen suoraan toiseen propertyyn
 * @author aleks
 * @version 2 Mar 2020
 *
 * @param <T> Listan alkioiden tyyppiparametri
 */
public class BindableListView<T> extends ListView<T> {
    final private ObjectProperty<T> valueProperty = new SimpleObjectProperty<T>();

    
    /**
     * Luo uuden BindableListView:n. Luo tapahtumankäsittelijät kuuntelemaan valitun alkion
     * vaihtumista. Perittävä luokka vastaa kaikesta muusta logiikasta.
     */
    public BindableListView() {
        super();
        addListener();
    }


    /**
     * Luo uuden BindableListView:n. Luo tapahtumankäsittelijät kuuntelemaan valitun alkion
     * vaihtumista. Perittävä luokka vastaa kaikesta muusta logiikasta.
     * @param items Listanäkymään lisättävien alkioiden lista
     */
    public BindableListView(ObservableList<T> items) {
        super(items);
        addListener();
    }


    private void addListener() {
        getSelectionModel().selectedItemProperty().addListener((i) -> {
            if (getSelectionModel().selectedItemProperty()
                    .get() != valueProperty.get()) {
                valueProperty
                        .set(getSelectionModel().selectedItemProperty().get());
            }
        });

        valueProperty.addListener((i) -> {
            if (getSelectionModel().selectedItemProperty()
                    .get() != valueProperty.get()) {
                getSelectionModel().select(valueProperty.get());
            }
        });
    }


    /**
     * ValueProperty, jonka arvo vastaa kulloinkin valittuna olevan alkion arvoa. Voidaan
     * sitoa suoraan toiseen propertyyn.
     * @return Palauttaa ObjectPropertyn.
     */
    public ObjectProperty<T> valueProperty() {
        return this.valueProperty;
    }
}
