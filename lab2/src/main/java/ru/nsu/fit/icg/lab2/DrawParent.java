package ru.nsu.fit.icg.lab2;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Screen;

public class DrawParent extends Parent {

    private final static double initialSizeScale = 0.5;
    private WritableImage original;

    public DrawParent() {
        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        original = new WritableImage((int) (initialSizeScale * screenSize.getWidth()),
                (int) (initialSizeScale * screenSize.getHeight()));
        getChildren().add(new ImageView(original));
    }

    public Image getImage() {
        return null;
    }

    public void openImage(Image image) {

    }
}
