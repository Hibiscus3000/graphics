package ru.nsu.fit.icg.lab2.menuToolbar.menu;

import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.FilterChanger;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler.FilterChangeHandler;

public class FilterUseMenuItem extends RadioMenuItem implements FilterChanger {

    private final Filter filter;

    public FilterUseMenuItem(Filter filter, FilterChangeHandler filterChangeHandler,
                             ToggleGroup toggleGroup) {
        super("Использовать");
        this.filter = filter;
        setToggleGroup(toggleGroup);
        filter.isSelectedProperty().bindBidirectional(selectedProperty());
        setOnAction(filterChangeHandler::handle);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}
