package ru.nsu.fit.icg.lab2.dialog.matrix;

import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.ResizableDialog;
import ru.nsu.fit.icg.lab2.filter.dithering.ordered.OrderedDitheringMatrix;

import java.util.HashMap;
import java.util.Map;

public class OrderedDitheringMatrixBox extends VBox {

    private final MatrixBox matrixBox;
    private final OrderedDitheringMatrix matrix;
    private final ResizableDialog owner;

    public OrderedDitheringMatrixBox(String name, OrderedDitheringMatrix matrix,
                                     ResizableDialog owner) {
        this.matrix = matrix;
        prevMatrixSide = matrix.getSide();
        this.owner = owner;
        matrixBox = new MatrixBox(owner);
        matrixBox.getStyleClass().add("ordered-matrix-button-box");
        HBox buttonBox = new HBox();
        for (Integer matrixSide : matrix.getSides()) {
            buttonBox.getChildren().add(new SizeButton(matrixSide));
        }
        drawMatrix();
        getChildren().addAll(new Label(name), buttonBox, matrixBox);
    }

    public void drawMatrix() {
        matrixBox.setMatrix(matrix.getMatrix());
    }

    private final ToggleGroup buttonGroup = new ToggleGroup();
    private final Map<Integer, SizeButton> sizeButtonMap = new HashMap<>(); // matrixSide => sizeButton

    private class SizeButton extends ToggleButton {

        public SizeButton(Integer size) {
            setText(null != size ? String.format("%1$dx%1$d", size) : "авто");
            setOnAction(e -> {
                if (isSelected()) {
                    matrix.setPreferredSide(size);
                    if (matrix.сhanged()) {
                        drawMatrix();
                        matrix.setChanged(false);
                    }
                } else {
                    setSelected(true);
                }
                e.consume();
            });
            sizeButtonMap.put(size, this);
            setToggleGroup(buttonGroup);
            if (matrix.getSide().equals(size)) {
                fire();
            }
        }
    }

    private Integer prevMatrixSide;

    public void saveMatrixSide() {
        prevMatrixSide = matrix.getSide();
    }

    public void cancel() {
        matrix.setPreferredSide(prevMatrixSide);
        sizeButtonMap.get(matrix.getSide()).fire();
    }
}
