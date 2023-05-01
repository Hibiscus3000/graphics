package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.textfield.DoubleTextField;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.textfield.IntegerTextField;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

public class BSplineConfigBox extends VBox {

    private Spline spline;

    private final BSplineEditor bSplineEditor;

    private final IntegerProperty numberOfAnchorPoints = new SimpleIntegerProperty();
    private final IntegerProperty splineSectorPartition = new SimpleIntegerProperty();

    private final IntegerTextField numberOfAPTextField;
    private final IntegerTextField splineSectorsPartitionTextField;

    private final static int maxAnchorPoints = 200;
    private final static int maxSplineSectors = 100;

    public BSplineConfigBox(Spline spline, BSplineEditor bSplineEditor) {
        this.bSplineEditor = bSplineEditor;
        numberOfAPTextField = new IntegerTextField("Количество опорных точек",
                numberOfAnchorPoints, 0, maxAnchorPoints, 1);
        numberOfAnchorPoints.addListener(new NumberOfAPChangeListener());
        splineSectorsPartitionTextField = new IntegerTextField(
                "Количество ломанных на одном участке B-сплайна",
                splineSectorPartition, 1, maxSplineSectors, 1);
        splineSectorPartition.addListener(((observableValue, oldVal, newVal) ->
        {
            int selectedAPId = bSplineEditor.getSelectedAPId();
            spline.calculateAllLines();
            bSplineEditor.setSpline(spline);
            bSplineEditor.selectAnchorPoint(selectedAPId);
        }));

        getChildren().addAll(numberOfAPTextField, splineSectorsPartitionTextField);
        createEditorPropertiesFields();
        setSpline(spline);
    }

    private void createEditorPropertiesFields() {
        DoubleTextField centerUField = new DoubleTextField("U центра",
                bSplineEditor.uCenterProperty(), -1000, 1000, 5);
        DoubleTextField centerVField = new DoubleTextField("V центра",
                bSplineEditor.vCenterProperty(), -1000, 1000, 5);
        DoubleTextField scaleField = new DoubleTextField("Масштаб",
                bSplineEditor.scaleProperty(), BSplineEditor.scaleMin, BSplineEditor.scaleMax,
                0.1);
        getChildren().addAll(centerUField, centerVField, scaleField);
    }

    private void setSpline(Spline spline) {
        if (null != this.spline) {
            splineSectorPartition.unbindBidirectional(spline.splineSectorPartitionProperty());
            numberOfAnchorPoints.unbindBidirectional(spline.numberOfAnchorPointsProperty());
        }
        this.spline = spline;
        this.bSplineEditor.setSpline(spline);
        if (null != spline) {
            splineSectorPartition.bindBidirectional(spline.splineSectorPartitionProperty());
            numberOfAnchorPoints.bindBidirectional(spline.numberOfAnchorPointsProperty());
        }
        numberOfAnchorPoints.set(spline.getAnchorPoints().size());
    }

    private class NumberOfAPChangeListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldVal, Number newVal) {
            int selectedAnchorPointId = bSplineEditor.getSelectedAPId();
            int newValue = newVal.intValue();
            int oldValue = spline.getAnchorPoints().size();
            int numberOfAnchorPoints = Math.abs(newValue - oldValue);
            if (oldValue > newValue) {
                if (-1 == selectedAnchorPointId) {
                    spline.removeAnchorPointsFromEnd(numberOfAnchorPoints);
                } else {
                    spline.removeAnchorPoints(numberOfAnchorPoints, selectedAnchorPointId);
                }
            } else {
                if (-1 == selectedAnchorPointId) {
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
