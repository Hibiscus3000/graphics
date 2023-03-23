package ru.nsu.fit.icg.lab2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.nsu.fit.icg.lab2.filter.Filter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageBox extends VBox {

    private static final int inset = 5;

    private final ScrollPane scrollPane;

    private final Rectangle bounds = new Rectangle();
    private final Pane imagePane = new Pane();

    private final ImageView imageView = new ImageView();
    private WritableImage original;
    private WritableImage filtered;
    private WritableImage current;

    private Double previousDragX;
    private Double previousDragY;

    private final ExecutorService imageChanger = Executors.newSingleThreadExecutor();
    private final ToggleButton filterToggleButton = new ToggleButton("Фильтр");
    private final EventHandler<ActionEvent> imageChangeHandler;

    public ImageBox() {
        imageChangeHandler = event -> {
            if (filterChanged) {
                setCursor(Cursor.WAIT);
            }
            imageChanger.submit(ImageBox.this::changeImage);
            if (null != event) {
                event.consume();
            }
        };
        VBox filterBox = new VBox(filterToggleButton);
        filterBox.setAlignment(Pos.TOP_RIGHT);
        filterToggleButton.setOnAction(imageChangeHandler);

        bounds.setFill(Color.TRANSPARENT);
        bounds.getStrokeDashArray().addAll(10.0, 5.0);
        bounds.setStroke(Color.BLUE);

        imageView.layoutXProperty().set(inset);
        imageView.layoutYProperty().set(inset);

        bounds.widthProperty().bind(widthProperty().subtract(2 * inset));
        bounds.heightProperty().bind(heightProperty().subtract(2 * inset));
        bounds.layoutXProperty().set(inset);
        bounds.layoutYProperty().set(inset);
        imagePane.getChildren().addAll(imageView, bounds);
        scrollPane = new ScrollPane(imagePane);
        scrollPane.layoutXProperty().set(inset);
        scrollPane.layoutYProperty().set(inset);
        scrollPane.prefHeightProperty().bind(heightProperty());
        scrollPane.prefWidthProperty().bind(widthProperty());
        getChildren().addAll(filterBox, scrollPane);

        imagePane.setOnDragDetected(e -> {
            if (MouseButton.SECONDARY == e.getButton()) {
                setCursor(Cursor.CLOSED_HAND);
                previousDragX = e.getSceneX();
                previousDragY = e.getSceneY();
            }
            e.consume();
        });
        imagePane.setOnMouseReleased(e -> {
            setCursor(Cursor.DEFAULT);
            previousDragX = null;
            previousDragY = null;
            if (MouseButton.PRIMARY == e.getButton()) {
                imageChangeHandler.handle(null);
            }
            e.consume();
        });
        imagePane.setOnMouseDragged(e -> {
            if (MouseButton.SECONDARY == e.getButton()) {
                double x = e.getSceneX();
                double y = e.getSceneY();
                if (null != previousDragX && null != previousDragY) {
                    scrollPane.setHvalue(scrollPane.getHvalue() + (previousDragX - x) / scrollPane.getWidth());
                    scrollPane.setVvalue(scrollPane.getVvalue() + (previousDragY - y) / scrollPane.getHeight());
                }
                previousDragX = x;
                previousDragY = y;
            }
            e.consume();
        });
    }

    public Image getImage() {
        return current;
    }

    public void openImage(WritableImage image) {
        current = original = image;
        filtered = null;
        imageView.setImage(current);
        bounds.widthProperty().bind(image.widthProperty());
        bounds.heightProperty().bind(image.heightProperty());
        imagePane.prefWidthProperty().bind(image.widthProperty().add(2 * inset));
        imagePane.prefHeightProperty().bind(image.heightProperty().add(2 * inset));
        filterToggleButton.setSelected(false);
    }

    private Filter filter;
    private boolean filterChanged = true;

    public void setFilter(Filter filter) {
        if (filter != this.filter) {
            this.filter = filter;
            setFilterChanged(true);
        }
    }

    public void setFilterChanged(boolean b) {
        filterChanged = b;
    }

    private void changeImage() {
        boolean changeToFiltered = current == original && null != filter && null != original;
        filterToggleButton.setSelected(changeToFiltered);
        if (changeToFiltered) {
            if (filterChanged) {
                filtered = filter.filter(original);
                filterChanged = false;
            }
            current = filtered;
        } else {
            current = original;
        }
        imageView.setImage(current);
        setCursor(Cursor.DEFAULT);
    }
}
