package roarusko.simpleTimeTracker.view.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class BindableListView<T> extends ListView<T> {
    final private ObjectProperty<T> valueProperty = new SimpleObjectProperty<T>();

    public BindableListView() {
        super();
        addListener();
    }


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


    public ObjectProperty<T> valueProperty() {
        return this.valueProperty;
    }
}
