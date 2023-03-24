package ru.nsu.fit.icg.lab2.menuToolbar.menu;

import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.FilterChangeHandler;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.FilterChanger;

public class FilterUseMenuItem extends RadioMenuItem implements FilterChanger {

    private final Filter filter;

    public FilterUseMenuItem(Filter filter, FilterChangeHandler filterChangeHandler,
                             ToggleGroup toggleGroup) {
        super("Использовать");
        this.filter = filter;
        setToggleGroup(toggleGroup);
        setOnAction(filterChangeHandler);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}
