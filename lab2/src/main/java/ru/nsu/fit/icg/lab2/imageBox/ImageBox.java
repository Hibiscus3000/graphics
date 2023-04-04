package ru.nsu.fit.icg.lab2.imageBox;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
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

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageBox extends VBox {


    private static final int inset = 5;

    private final ScrollPane scrollPane;

    private final Rectangle bounds = new Rectangle();
    private final Pane imagePane = new Pane();

    private final ImageView imageView = new ImageView();
    private WritableImage originalSizeImage;
    private WritableImage notRotatedImage;
    private WritableImage original;
    private WritableImage filtered;
    private WritableImage current;

    private Double previousDragX;
    private Double previousDragY;

    private final ExecutorService imageChanger = Executors.newSingleThreadExecutor();

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
            if (MouseButton.PRIMARY == e.getButton()) {
                filter();
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

    public void filter() {
        if (changeToFiltered() && filterChanged) {
            setCursor(Cursor.WAIT);
        }
        imageChanger.submit(ImageBox.this::changeImage);
    }

    private void addImage(WritableImage image) {
        current = image;
        filterChanged = true;
        imageView.setImage(image);
        bounds.widthProperty().bind(image.widthProperty());
        bounds.heightProperty().bind(image.heightProperty());
        imagePane.prefWidthProperty().bind(image.widthProperty().add(2 * inset));
        imagePane.prefHeightProperty().bind(image.heightProperty().add(2 * inset));
    }

    public void openNewImage(WritableImage image) {
        angdegProperty.set(0);
        addImage(image);
        originalSizeImage = notRotatedImage = original = image;
    }

    private Filter filter;
    private boolean filterChanged = true;

    public void setFilter(Filter filter) {
        if (filter != this.filter) {
            this.filter = filter;
            setFilterChanged(true);
        }
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilterChanged(boolean b) {
        filterChanged = b;
    }

    private void changeImage() {
        boolean changeToFiltered = changeToFiltered();
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

    public void scale(ScalingType scalingType) {
        if (null != originalSizeImage) {
            double xScale = (scrollPane.getWidth() - 3 * inset) / originalSizeImage.getWidth();
            double yScale = (scrollPane.getHeight() - 3 * inset) / originalSizeImage.getHeight();
            double scale = Math.min(xScale, yScale);
            notRotatedImage = scalingType.scaleImage(originalSizeImage, scale);
            if (null == notRotatedImage) {
                notRotatedImage = originalSizeImage;
            }
            rotate();
        }
    }

    private final SimpleIntegerProperty angdegProperty = new SimpleIntegerProperty(0);

    public IntegerProperty angdegProperty() {
        return angdegProperty;
    }

    public void rotate() {
        if (null != current) {
            boolean wasOriginal = current == original;

            final double rads = Math.toRadians(angdegProperty.get());

            final double cos = Math.abs(Math.cos(rads));
            final double sin = Math.abs(Math.sin(rads));
            final int w = (int) (notRotatedImage.getWidth() * cos + notRotatedImage.getHeight() * sin);
            final int h = (int) (notRotatedImage.getHeight() * cos + notRotatedImage.getWidth() * sin);

            BufferedImage bufferedCurrent = SwingFXUtils.fromFXImage(notRotatedImage, null);
            final BufferedImage rotatedBufferedImage = new BufferedImage(w, h, bufferedCurrent.getType());
            Graphics2D g2d = (Graphics2D) rotatedBufferedImage.getGraphics();
            g2d.setColor(java.awt.Color.white);
            g2d.fillRect(0, 0, w - 1, h - 1);
            final AffineTransform at = new AffineTransform();
            at.translate(w / 2, h / 2);
            at.rotate(rads, 0, 0);
            at.translate(-notRotatedImage.getWidth() / 2, -notRotatedImage.getHeight() / 2);

            final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
            WritableImage rotatedImage = SwingFXUtils.toFXImage(rotateOp.filter(bufferedCurrent, rotatedBufferedImage), null);
            if (!wasOriginal && null != filter) {
                rotatedImage = filtered = filter.filter(rotatedImage);
            } else {
                original = rotatedImage;
            }
            addImage(rotatedImage);
        }
    }

    private boolean changeToFiltered() {
        return current == original && null != filter && null != original;
    }
}
