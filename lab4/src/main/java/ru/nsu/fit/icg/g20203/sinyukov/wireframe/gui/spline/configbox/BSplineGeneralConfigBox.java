package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.Point;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.editbox.DoubleValueEditBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.bsplineeditor.BSplineEditor;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.textfield.DoubleTextField;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.textfield.IntegerTextField;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

public class BSplineGeneralConfigBox extends VBox {

    private Spline spline;

    private final BSplineEditor bSplineEditor;

    private final IntegerProperty numberOfAnchorPoints = new SimpleIntegerProperty();
    private final IntegerProperty splineSectorPartition = new SimpleIntegerProperty();

    private final static int maxAnchorPoints = 200;
    private final static int maxSplineSectors = 100;

    public BSplineGeneralConfigBox(BSplineEditor bSplineEditor) {
        this.bSplineEditor = bSplineEditor;
        IntegerTextField numberOfAPTextField = new IntegerTextField("Количество опорных точек",
                numberOfAnchorPoints, 0, maxAnchorPoints, 1);
        numberOfAnchorPoints.addListener(new NumberOfAPChangeListener());
        IntegerTextField splineSectorsPartitionTextField = new IntegerTextField(
                "Количество ломанных на одном участке B-сплайна",
                splineSectorPartition, 1, maxSplineSectors, 1);
        splineSectorPartition.addListener(((observableValue, oldVal, newVal) ->
        {
            Integer selectedAPId = bSplineEditor.selectedAPIdProperty().get();
            spline.calculateAllLines();
            bSplineEditor.setSpline(spline);
            bSplineEditor.selectAnchorPoint(selectedAPId);
        }));

        getChildren().addAll(numberOfAPTextField, splineSectorsPartitionTextField);
        createEditorPropertiesEditBoxes();
        createSelectedAPCoordinatesFields();
    }

    private void createEditorPropertiesEditBoxes() {
        DoubleValueEditBox centerUEditBox = new DoubleValueEditBox("U центра",
                bSplineEditor.uCenterProperty(), BSplineEditor.centerMin, BSplineEditor.centerMax, BSplineEditor.centerStep);
        DoubleValueEditBox centerVEditBox = new DoubleValueEditBox("V центра",
                bSplineEditor.vCenterProperty(), BSplineEditor.centerMin, BSplineEditor.centerMax, BSplineEditor.centerStep);
        DoubleValueEditBox scaleEditBox = new DoubleValueEditBox("Масштаб",
                bSplineEditor.scaleProperty(), BSplineEditor.scaleMin, BSplineEditor.scaleMax,
                BSplineEditor.scaleStep);
        getChildren().addAll(centerUEditBox, centerVEditBox, scaleEditBox);
    }

    private void createSelectedAPCoordinatesFields() {
        DoubleTextField selectedAPUTextField = new DoubleTextField("U выбранной опорной точки",
                null, BSplineEditor.aPCoordMin, BSplineEditor.aPCoordsMax, BSplineEditor.aPCoordStep);
        DoubleTextField selectedAPVTextField = new DoubleTextField("V выбранной опорной точки",
                null, BSplineEditor.aPCoordMin, BSplineEditor.aPCoordsMax, BSplineEditor.aPCoordStep);
        bSplineEditor.selectedAPIdProperty().addListener((observable, oldVal, newVal) -> {
            if (null != newVal) {
                Point selectedAP = bSplineEditor.getSelectedAP();
                selectedAPUTextField.bind(selectedAP.uProperty());
                selectedAPVTextField.bind(selectedAP.vProperty());
            } else {
                selectedAPUTextField.unbind();
                selectedAPVTextField.unbind();
            }
        });
        getChildren().addAll(selectedAPUTextField, selectedAPVTextField);
    }

    public void setSpline(Spline spline) {
        if (null != this.spline) {
            splineSectorPartition.unbindBidirectional(spline.splineSectorPartitionProperty());
            numberOfAnchorPoints.unbindBidirectional(spline.numberOfAnchorPointsProperty());
        }
        this.spline = spline;
        splineSectorPartition.bindBidirectional(spline.splineSectorPartitionProperty());
        numberOfAnchorPoints.bindBidirectional(spline.numberOfAnchorPointsProperty());
        numberOfAnchorPoints.set(spline.getAnchorPoints().size());
    }

    private class NumberOfAPChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
            Integer selectedAnchorPointId = bSplineEditor.selectedAPIdProperty().get();
            int newValue = newVal.intValue();
            int oldValue = spline.getAnchorPoints().size();
            int numberOfAnchorPoints = Math.abs(newValue - oldValue);
            if (oldValue > newValue) {
                if (null == selectedAnchorPointId) {
                    spline.removeAnchorPointsFromEnd(numberOfAnchorPoints);
                } else {
                    spline.removeAnchorPoints(numberOfAnchorPoints, selectedAnchorPointId);
                }
            } else {
                if (null == selectedAnchorPointId) {
                    spline.addAnchorPointsToEnd(numberOfAnchorPoints);
                } else {
                    spline.addAnchorPoints(numberOfAnchorPoints, selectedAnchorPointId);
                }
            }
            bSplineEditor.setSpline(spline);
            bSplineEditor.selectAnchorPoint(selectedAnchorPointId);
        }
    }

}
