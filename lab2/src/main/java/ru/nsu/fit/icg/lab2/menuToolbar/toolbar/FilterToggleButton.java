package ru.nsu.fit.icg.lab2.menuToolbar.toolbar;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import ru.nsu.fit.icg.lab2.filter.Filter;

public class FilterToggleButton extends ToggleButton implements FilterChanger {

    private final Filter filter;

    public FilterToggleButton(Filter filter, FilterChangeHandler filterChangeHandler,
                              ToggleGroup toggleGroup) {
        super(filter.getName());
        this.filter = filter;
        setToggleGroup(toggleGroup);
        setOnAction(e -> {
            setSelected(true);
            filterChangeHandler.handle(e);
        });
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
}
