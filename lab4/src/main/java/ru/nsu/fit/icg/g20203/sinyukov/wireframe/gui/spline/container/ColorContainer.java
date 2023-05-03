package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.container;

import javafx.scene.paint.Color;

public class ColorContainer extends Container<Color> {

    public ColorContainer() {
        putInContainer("background", Color.WHITE);
        putInContainer("mainAxis", Color.BLACK);
        putInContainer("secondaryAxis", Color.rgb(200, 200, 200));
        putInContainer("splineLine", Color.RED);
        putInContainer("generatrix", Color.BLUE);
        putInContainer("anchorPoint", Color.ORANGE);
        putInContainer("selectedAnchorPoint", Color.GREEN);
        putInContainer("splitPoint", Color.ORANGE);
        putInContainer("serif", Color.BLACK);
    }
}
