package ru.nsu.fit.icg.lab2.menuToolbar.menu;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.imageBox.ImageBox;

import java.lang.reflect.Constructor;

public class FilterParametersMenuItem extends MenuItem {

    private Dialog filterDialog;

    public FilterParametersMenuItem(Constructor<Dialog> dialogConstructor, Filter filter,
                                    ImageBox imageBox) {
        super("Параметры");
        setOnAction(e -> {
            if (null == filterDialog) {
                try {
                    filterDialog = dialogConstructor.newInstance(filter);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            filterDialog.showAndWait();
            if (ButtonType.OK == filterDialog.getResult() && filter == imageBox.getFilter()) {
                imageBox.setFilterChanged(true);
            }
        });
    }
}
