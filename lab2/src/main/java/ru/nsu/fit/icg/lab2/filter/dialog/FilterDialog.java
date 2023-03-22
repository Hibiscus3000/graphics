package ru.nsu.fit.icg.lab2.filter.dialog;

import javafx.scene.control.Dialog;
import ru.nsu.fit.icg.lab2.filter.Filter;

public abstract class FilterDialog extends Dialog {

    private final Filter filter;
    protected final static int spacing = 5;

    protected FilterDialog(Filter filter) {
        this.filter = filter;
    }

}
