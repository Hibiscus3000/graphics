package ru.nsu.fit.icg.lab2.menuToolbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import ru.nsu.fit.icg.lab2.dialog.FilterDialogFactory;
import ru.nsu.fit.icg.lab2.dialog.InstrumentDialog;
import ru.nsu.fit.icg.lab2.dialog.RotationDialog;
import ru.nsu.fit.icg.lab2.dialog.ScalingDialog;
import ru.nsu.fit.icg.lab2.file.FileHandler;
import ru.nsu.fit.icg.lab2.filter.*;
import ru.nsu.fit.icg.lab2.filter.borders.RobertsFilter;
import ru.nsu.fit.icg.lab2.filter.borders.SobelFilter;
import ru.nsu.fit.icg.lab2.filter.dithering.FloydSteinbergFilter;
import ru.nsu.fit.icg.lab2.filter.dithering.ordered.OrderedDitheringFilter;
import ru.nsu.fit.icg.lab2.filter.window.MedianFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.SmoothingFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.EmbossingFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.MotionFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.SharpeningFilter;
import ru.nsu.fit.icg.lab2.image_box.ImageBox;
import ru.nsu.fit.icg.lab2.menuToolbar.menu.FilterParametersMenuItem;
import ru.nsu.fit.icg.lab2.menuToolbar.menu.FilterUseMenuItem;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.FilterChangeHandler;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.FilterToggleButton;

import java.lang.reflect.Constructor;

public class MenuToolbarBox extends VBox {

    private final double sizeScale = 0.5;

    private final MenuBar menuBar = new MenuBar();
    private final ToolBar toolBar = new ToolBar();

    private final Menu fileMenu = new Menu("Файл");

    private final Menu filterMenu = new Menu("Фильтр");
    private final FilterChangeHandler filterChangeHandler;
    private final ImageBox imageBox;
    private final ToggleGroup filterMenuGroup = new ToggleGroup();
    private final ToggleGroup filterToolBarGroup = new ToggleGroup();

    private final Menu transformationMenu = new Menu("Преобразования");

    public MenuToolbarBox(FilterChangeHandler filterChangeHandler, ImageBox imageBox) {
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        toolBar.setPrefWidth(sizeScale * screenSize.getWidth());

        getChildren().addAll(menuBar, toolBar);
        this.filterChangeHandler = filterChangeHandler;
        this.imageBox = imageBox;
        menuBar.getMenus().addAll(fileMenu, filterMenu, transformationMenu);
    }

    public void addFileHandler(FileHandler fileHandler) {
        String name = fileHandler.getName();
        MenuItem fileMenuItem = new MenuItem(name);
        fileMenu.getItems().add(fileMenuItem);
        Button fileButton = new Button(name);
        toolBar.getItems().add(fileButton);

        fileMenuItem.setOnAction(fileHandler);
        fileButton.setOnAction(fileHandler);
    }

    public void addFilters() {
        addFilter(new BlackWhiteFilter());
        addFilter(new MotionFilter());
        addFilter(new WaterColorizationFilter());
        addFilter(new MedianFilter());
        addFilter(new GammaFilter());
        addFilter(new NegativeFilter());
        addFilter(new FloydSteinbergFilter());
        addFilter(new OrderedDitheringFilter());
        addFilter(new EmbossingFilter());
        addFilter(new SharpeningFilter());
        addFilter(new SmoothingFilter());
        addFilter(new RobertsFilter());
        addFilter(new SobelFilter());
    }

    private void addFilter(Filter filter) {
        Menu thisFilterMenu = new Menu(filter.getName());
        Constructor dialogConstructor = FilterDialogFactory.getInstance().getConstructor(filter.getJsonName());
        if (null != dialogConstructor) {
            thisFilterMenu.getItems().add(new FilterParametersMenuItem(dialogConstructor, filter,
                    imageBox));
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

    public void addTransformations() {
        new TransformationDialogShower(ScalingDialog.class, "Подогнать размер изображения");
        new TransformationDialogShower(RotationDialog.class, "Повернуть изображение");
    }

    private class TransformationDialogShower {
        private InstrumentDialog instrumentDialog = null;

        public TransformationDialogShower(Class<? extends InstrumentDialog> instrumentDialogClass,
                                          String transformationName) {
            EventHandler<ActionEvent> transformationHandler = e -> {
                if (null == instrumentDialog) {
                    try {
                        instrumentDialog = instrumentDialogClass.getDeclaredConstructor(ImageBox.class)
                                .newInstance(imageBox);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                instrumentDialog.showAndWait();
            };

            final Button transormationButton;
            final MenuItem transormationMenuItem;

            transormationButton = new Button(transformationName);
            transormationButton.setOnAction(transformationHandler);
            toolBar.getItems().add(transormationButton);

            transormationMenuItem = new MenuItem(transformationName);
            transormationMenuItem.setOnAction(transformationHandler);
            transformationMenu.getItems().add(transormationMenuItem);
        }
    }
}
