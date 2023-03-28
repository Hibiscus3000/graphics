package ru.nsu.fit.icg.lab2.dialog.matrix;

import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.ResizableDialog;
import ru.nsu.fit.icg.lab2.filter.dithering.ordered.OrderedDitheringMatrix;

public class OrderedDitheringMatrixBox extends VBox {

    private final MatrixPane matrixPane = new MatrixPane();
    private final OrderedDitheringMatrix matrix;
    private final ResizableDialog owner;

    public OrderedDitheringMatrixBox(String name, OrderedDitheringMatrix matrix,
                                     ResizableDialog owner) {
        this.matrix = matrix;
        this.owner = owner;
        HBox buttonBox = new HBox();
        for (Integer matrixSide : matrix.getSides()) {
            buttonBox.getChildren().add(new SizeButton(matrixSide));
        }
        drawMatrix();
        getChildren().addAll(new Label(name), buttonBox, matrixPane);
    }

    public void drawMatrix() {
        matrixPane.setMatrix(matrix.getMatrix());
        owner.resize();
    }

    private final ToggleGroup buttonGroup = new ToggleGroup();

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
            setToggleGroup(buttonGroup);
            if (matrix.getSide().equals(size)) {
                setSelected(true);
            }
        }
    }
}
