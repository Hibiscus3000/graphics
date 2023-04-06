package ru.nsu.fit.icg.lab2.menuToolbar.menu;

import javafx.scene.control.MenuItem;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.dialog.FilterDialogFactory;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.FilterParametersChanger;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler.FilterChangeHandler;

public class FilterParametersMenuItem extends MenuItem implements FilterParametersChanger {

    private final Filter filter;

    public FilterParametersMenuItem(Filter filter, FilterChangeHandler filterChangeHandler) {
        super("Параметры");
        this.filter = filter;
        setOnAction(filterChangeHandler::handle);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    @Override
    public FilterDialog getDialog() {
        return FilterDialogFactory.getInstance().getFilterDialog(filter);
    }
}
