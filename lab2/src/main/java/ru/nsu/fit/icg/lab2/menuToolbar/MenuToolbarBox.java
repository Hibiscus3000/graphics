package ru.nsu.fit.icg.lab2.menuToolbar;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Window;
import ru.nsu.fit.icg.lab2.dialog.FilterDialogFactory;
import ru.nsu.fit.icg.lab2.dialog.InstrumentDialog;
import ru.nsu.fit.icg.lab2.dialog.RotationDialog;
import ru.nsu.fit.icg.lab2.dialog.ScalingDialog;
import ru.nsu.fit.icg.lab2.file.FileHandler;
import ru.nsu.fit.icg.lab2.file.OpenFileHandler;
import ru.nsu.fit.icg.lab2.file.SaveFileHandler;
import ru.nsu.fit.icg.lab2.filter.*;
import ru.nsu.fit.icg.lab2.filter.borders.RobertsFilter;
import ru.nsu.fit.icg.lab2.filter.borders.SobelFilter;
import ru.nsu.fit.icg.lab2.filter.dithering.FloydSteinbergFilter;
import ru.nsu.fit.icg.lab2.filter.dithering.ordered.OrderedDitheringFilter;
import ru.nsu.fit.icg.lab2.filter.window.MedianFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.GaussFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.EmbossingFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.MotionFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.SharpeningFilter;
import ru.nsu.fit.icg.lab2.imageBox.ImageBox;
import ru.nsu.fit.icg.lab2.menuToolbar.menu.FilterParametersMenuItem;
import ru.nsu.fit.icg.lab2.menuToolbar.menu.FilterUseMenuItem;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.BorderedTitledPane;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.FilterToggleButton;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler.FilterChangeHandler;
import ru.nsu.fit.icg.lab2.menuToolbar.toolbar.handler.FilterDialogShower;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MenuToolbarBox extends VBox {

    private final double sizeScale = 0.5;

    private final MenuBar menuBar = new MenuBar();
    private final ToolBar toolBar = new ToolBar();

    private final Menu fileMenu = new Menu("Файл");

    private final Menu filterMenu = new Menu("Фильтр");
    private final FilterChangeHandler filterChangeHandler;
    private final FilterDialogShower filterDialogShower;
    private final ImageBox imageBox;
    private final ToggleGroup filterMenuGroup = new ToggleGroup();
    private final ToggleGroup filterToolBarGroup = new ToggleGroup();

    private final Menu transformationMenu = new Menu("Преобразования");

    public MenuToolbarBox(FilterChangeHandler filterChangeHandler,
                          FilterDialogShower filterDialogShower,
                          Window owner,
                          ImageBox imageBox) {
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        toolBar.setPrefWidth(sizeScale * screenSize.getWidth());

        this.imageBox = imageBox;
        addAbout();
        addFileHandlers(owner);
        transformationBox.getStyleClass().add("button-box");
        fileBox.getStyleClass().add("button-box");

        getChildren().addAll(menuBar, toolBar);
        this.filterChangeHandler = filterChangeHandler;
        this.filterDialogShower = filterDialogShower;
        menuBar.getMenus().addAll(fileMenu, filterMenu, transformationMenu);
    }

    private void addAbout() {
        final String about = "О программе";
        Button aboutButton = new Button();
        aboutButton.setTooltip(new Tooltip(about));
        aboutButton.setGraphic(new ImageView(getButtonImage("about")));
        toolBar.getItems().add(aboutButton);
        aboutButton.setOnAction(e -> {
            e.consume();
            showAboutDialog();
        });

        MenuItem aboutMenuItem = new MenuItem(about);
        fileMenu.getItems().add(aboutMenuItem);
        aboutMenuItem.setOnAction(e -> {
            e.consume();
            showAboutDialog();
        });
    }

    private void showAboutDialog() {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("О программе");
            alert.setHeaderText("О программе");
            byte[] aboutBytes = getClass().getResourceAsStream("about.txt").readAllBytes();
            TextArea textArea = new TextArea(new String(aboutBytes, StandardCharsets.UTF_8));
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setContent(expContent);
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final HBox fileBox = new HBox();

    private void addFileHandlers(Window owner) {
        addFileHandler(new OpenFileHandler(owner, imageBox));
        addFileHandler(new SaveFileHandler(owner, imageBox));
        toolBar.getItems().add(new BorderedTitledPane("Файл", fileBox));
    }

    private void addFileHandler(FileHandler fileHandler) {
        String name = fileHandler.getName();
        MenuItem fileMenuItem = new MenuItem(name);
        fileMenu.getItems().add(fileMenuItem);
        Button fileButton = new Button();
        fileButton.setGraphic(new ImageView(getButtonImage(fileHandler.getImageName())));
        fileButton.setTooltip(new Tooltip(fileHandler.getName()));
        fileBox.getChildren().add(fileButton);

        fileMenuItem.setOnAction(fileHandler);
        fileButton.setOnAction(fileHandler);
    }

    public void addFilters() {
        addFilterGroup("Размытие", new GaussFilter(), new MedianFilter(),
                new MotionFilter(), new RoundBlurFilter());
        addFilterGroup("Граничные фильтры", new RobertsFilter(), new SobelFilter());
        addFilterGroup("Дизеринг", new OrderedDitheringFilter(),
                new FloydSteinbergFilter());
        addFilterGroup("Прочие фильтры", new BlackWhiteFilter(), new InversionFilter(),
                new GammaFilter(), new SharpeningFilter(),
                new EmbossingFilter(), new WaterColorizationFilter());
    }

    private void addFilterGroup(String filterGroupName, Filter... filters) {
        HBox filtersBox = new HBox();
        filtersBox.getStyleClass().add("button-box");
        Menu filterGroupMenu = new Menu(filterGroupName);
        for (int i = 0; i < filters.length; ++i) {
            addFilter(filters[i], filterGroupMenu, filtersBox.getChildren());
        }
        filterMenu.getItems().add(filterGroupMenu);
        toolBar.getItems().add(new BorderedTitledPane(filterGroupName, filtersBox));
    }

    private void addFilter(Filter filter, Menu menu, ObservableList<Node> children) {
        Menu thisFilterMenu = new Menu(filter.getName());
        FilterToggleButton filterToggleButton;
        if (FilterDialogFactory.getInstance().hasParameters(filter.getJsonName())) {
            thisFilterMenu.getItems().add(new FilterParametersMenuItem(filter, filterDialogShower));
        }
        FilterUseMenuItem filterUseMenuItem = new FilterUseMenuItem(filter,
                filterChangeHandler, filterMenuGroup);
        thisFilterMenu.getItems().add(filterUseMenuItem);
        menu.getItems().add(thisFilterMenu);

        filterToggleButton = new FilterToggleButton(filter, filterChangeHandler,
                filterDialogShower, filterToolBarGroup);
        children.add(filterToggleButton);
    }

    public void addTransformations() {
        new TransformationDialogShower(ScalingDialog.class, "resize",
                "Интерполировать размер изображения");
        new TransformationDialogShower(RotationDialog.class, "rotate",
                "Повернуть изображение");
        toolBar.getItems().add(new BorderedTitledPane("Преобразования изображения", transformationBox));
    }

    private final HBox transformationBox = new HBox();

    private class TransformationDialogShower {
        private InstrumentDialog instrumentDialog = null;

        public TransformationDialogShower(Class<? extends InstrumentDialog> instrumentDialogClass,
                                          String imageName, String transformationName) {
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

            transormationButton = new Button();
            transormationButton.setGraphic(new ImageView(getButtonImage(imageName)));
            transormationButton.setTooltip(new Tooltip(transformationName));
            transormationButton.setOnAction(transformationHandler);
            transformationBox.getChildren().add(transormationButton);

            transormationMenuItem = new MenuItem(transformationName);
            transormationMenuItem.setOnAction(transformationHandler);
            transformationMenu.getItems().add(transormationMenuItem);
        }
    }

    private final static int imageSide = 28;

    public static Image getButtonImage(String imageName) {
        imageName = imageName + ".png";
        InputStream imageStream = MenuToolbarBox.class.getResourceAsStream(imageName);
        Image image = new Image(imageStream);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        if (imageSide != (int) image.getWidth()) {
            double scale = imageSide / image.getWidth();
            AffineTransform affineScale = AffineTransform.getScaleInstance(scale, scale);
            AffineTransformOp transform = new AffineTransformOp(affineScale, AffineTransformOp.TYPE_BICUBIC);
            BufferedImage scaledBufferedImage = transform.filter(bufferedImage, null);
            return SwingFXUtils.toFXImage(scaledBufferedImage, null);
        } else {
            return image;
        }
    }
}
