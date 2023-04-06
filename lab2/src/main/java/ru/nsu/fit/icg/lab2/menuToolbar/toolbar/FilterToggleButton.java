package ru.nsu.fit.icg.lab2.menuToolbar.toolbar;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.dialog.FilterDialogFactory;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler.FilterChangeHandler;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler.FilterDialogShower;

public class FilterToggleButton extends ToggleButton implements FilterParametersChanger {

    protected final Filter filter;

    public FilterToggleButton(Filter filter, FilterChangeHandler filterChangeHandler,
                              FilterDialogShower filterDialogShower,
                              ToggleGroup toggleGroup) {
        super(filter.getName());
        this.filter = filter;
        setToggleGroup(toggleGroup);
        filter.isSelectedProperty().bindBidirectional(selectedProperty());
        setOnMouseReleased(e -> {
            if (MouseButton.PRIMARY == e.getButton()) {
                filterChangeHandler.handle(e);
            }
            if (MouseButton.SECONDARY == e.getButton()) {
                filterDialogShower.handle(e);
            }
        });
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
