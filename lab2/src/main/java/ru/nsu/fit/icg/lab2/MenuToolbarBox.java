package ru.nsu.fit.icg.lab2;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.FilterChangeHandler;
import ru.nsu.fit.icg.lab2.filter.dialog.FilterDialogFactory;
import ru.nsu.fit.icg.lab2.menu.FilterParametersMenuItem;
import ru.nsu.fit.icg.lab2.menu.FilterUseMenuItem;
import ru.nsu.fit.icg.lab2.toolbar.FilterToggleButton;

import java.lang.reflect.Constructor;

public class MenuToolbarBox extends VBox {

    private final MenuBar menuBar = new MenuBar();
    private final ToolBar toolBar = new ToolBar();

    private final Menu filterMenu = new Menu("Фильтр");
    private final FilterChangeHandler filterChangeHandler;
    private final ToggleGroup filterMenuGroup = new ToggleGroup();
    private final ToggleGroup filterToolBarGroup = new ToggleGroup();

    public MenuToolbarBox(FilterChangeHandler filterChangeHandler) {
        getChildren().addAll(menuBar, toolBar);
        menuBar.getMenus().add(filterMenu);
        this.filterChangeHandler = filterChangeHandler;
    }

    public void addFilter(Filter filter) {
        Menu thisFilterMenu = new Menu(filter.getName());
        Constructor dialogConstructor = FilterDialogFactory.getInstance().getConstructor(filter.getJsonName());
        if (null != dialogConstructor) {
            thisFilterMenu.getItems().add(new FilterParametersMenuItem(dialogConstructor, filter));
        }
        FilterUseMenuItem filterUseMenuItem = new FilterUseMenuItem(filter,
                filterChangeHandler, filterMenuGroup);
        thisFilterMenu.getItems().add(filterUseMenuItem);
        filterMenu.getItems().add(thisFilterMenu);

        FilterToggleButton filterToggleButton = new FilterToggleButton(filter,
                filterChangeHandler, filterToolBarGroup);
        toolBar.getItems().add(filterToggleButton);

        filterUseMenuItem.selectedProperty().bindBidirectional(filterToggleButton.selectedProperty());
    }
}
