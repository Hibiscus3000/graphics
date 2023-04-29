package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.SplitPane;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

public class BSplinePane extends SplitPane {

    private static final int defaultNumberOfAnchorPoints = 5;
    private static final int defaultSplineSectorPartition = 5;

    private static final double editorWidthScale = 0.6;
    private static final double editorHeightScale = 1;

    public BSplinePane() {
        Spline initSpline = new Spline(defaultNumberOfAnchorPoints, defaultSplineSectorPartition);
        BSplineEditor bSplineEditor = new BSplineEditor();
        DoubleBinding editorSizeBinding = Bindings.createDoubleBinding(() -> Math.min(editorWidthScale * getWidth(), editorHeightScale * getHeight()),
                widthProperty(), heightProperty());
        bSplineEditor.minWidthProperty().bind(editorSizeBinding);
        bSplineEditor.maxWidthProperty().bind(editorSizeBinding);
        bSplineEditor.minHeightProperty().bind(editorSizeBinding);
        bSplineEditor.maxHeightProperty().bind(editorSizeBinding);
        BSplineConfigBox bSplineConfigBox = new BSplineConfigBox(initSpline, bSplineEditor);
        getItems().addAll(bSplineEditor, bSplineConfigBox);
    }
}
