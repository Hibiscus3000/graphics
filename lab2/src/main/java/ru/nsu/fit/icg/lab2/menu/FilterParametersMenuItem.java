package ru.nsu.fit.icg.lab2.menu;

import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import ru.nsu.fit.icg.lab2.filter.Filter;

import java.lang.reflect.Constructor;

public class FilterParametersMenuItem extends MenuItem {

    private Dialog filterDialog;

    public FilterParametersMenuItem(Constructor<Dialog> dialogConstructor, Filter filter) {
        super("Параметры");
        setOnAction(e -> {
            if (null == filterDialog) {
                try {
                    filterDialog = dialogConstructor.newInstance();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            filterDialog.show();
        });
    }
}
