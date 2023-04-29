package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.editbox.IntegerValueEditBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

public class BSplineConfigBox extends VBox {

    private Spline spline;

    private final BSplineEditor bSplineEditor;

    private final IntegerProperty numberOfAnchorPoints = new SimpleIntegerProperty();
    private final IntegerProperty splineSectorPartition = new SimpleIntegerProperty();

    private final IntegerValueEditBox numberOfAPEditBox;
    private final IntegerValueEditBox numberOfSplineSectorsEditBox;

    private final static int maxAnchorPoints = 200;
    private final static int maxSplineSectors = 100;

    public BSplineConfigBox(Spline spline, BSplineEditor bSplineEditor) {
        this.bSplineEditor = bSplineEditor;
        numberOfAPEditBox = new IntegerValueEditBox("Количество опорных точек",
                numberOfAnchorPoints, 0, maxAnchorPoints, 1);
        numberOfSplineSectorsEditBox = new IntegerValueEditBox(
                "Количество ломанных на одном участке B-сплайна",
                splineSectorPartition, 1, maxSplineSectors, 1);

        getChildren().addAll(numberOfAPEditBox, numberOfSplineSectorsEditBox);
        setSpline(spline);
    }

    private void setSpline(Spline spline) {
        if (null != this.spline) {
            splineSectorPartition.unbindBidirectional(spline.splineSectorPartitionProperty());
        }
        this.spline = spline;
        this.bSplineEditor.setSpline(spline);
        if (null != spline) {
            splineSectorPartition.bindBidirectional(spline.splineSectorPartitionProperty());
        }
    }

}
