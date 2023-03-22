package ru.nsu.fit.icg.lab2;

import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.nsu.fit.icg.lab2.filter.Filter;

public class ImageBox extends VBox {

    private static final int inset = 5;

    private final ScrollPane scrollPane;

    private final Rectangle bounds = new Rectangle();
    private final Pane imagePane = new Pane();

    private final ImageView imageView = new ImageView();
    private WritableImage original;
    private WritableImage processed;
    private WritableImage current;

    private Double previousDragX;
    private Double previousDragY;

    public ImageBox() {
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
        getChildren().add(scrollPane);

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
            e.consume();
        });
        imagePane.setOnMouseDragged(e -> {
            if (MouseButton.SECONDARY == e.getButton()) {
                double x = e.getSceneX();
                double y = e.getSceneY();
                if (null != previousDragX && null != previousDragY) {
                    scrollPane.setHvalue(scrollPane.getHvalue() + (previousDragX - x) / current.getWidth());
                    scrollPane.setVvalue(scrollPane.getVvalue() + (previousDragY - y) / current.getHeight());
                }
                previousDragX = x;
                previousDragY = y;
            }
            e.consume();
        });
    }

    public Image getImage() {
        return null;
    }

    public void openImage(WritableImage image) {
        current = original = image;
        imageView.setImage(current);
        bounds.widthProperty().bind(image.widthProperty());
        bounds.heightProperty().bind(image.heightProperty());
        imagePane.prefWidthProperty().bind(image.widthProperty().add(2 * inset));
        imagePane.prefHeightProperty().bind(image.heightProperty().add(2 * inset));
    }

    private Filter filter;
    private boolean filterChanged = true;

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void setFilterChanged(boolean b) {
        filterChanged = b;
    }
}
