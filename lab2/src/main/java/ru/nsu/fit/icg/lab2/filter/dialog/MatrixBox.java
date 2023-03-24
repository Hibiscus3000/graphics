package ru.nsu.fit.icg.lab2.filter.dialog;

import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.filter.matrix.Matrix;

public class MatrixBox extends VBox {

    private final GridPane matrixPane = new GridPane();
    private final Matrix matrix;
    private final ResizableDialog owner;

    public MatrixBox(String name, Matrix matrix, ResizableDialog owner) {
        this.matrix = matrix;
        this.owner = owner;
        HBox buttonBox = new HBox();
        for (Integer matrixSide : matrix.getSizes()) {
            buttonBox.getChildren().add(new SizeButton(matrixSide));
        }
        drawMatrix();
        getChildren().addAll(new Label(name), buttonBox, matrixPane);
    }

    public void drawMatrix() {
        matrixPane.getChildren().clear();
        int[][] matrix = this.matrix.getMatrix();
        int matrixHeight = matrix.length, matrixWidth = matrix[0].length;
        Label[][] labels = new Label[matrixHeight][matrixWidth];
        for (int i = 0; i < matrixHeight; ++i) {
            for (int j = 0; j < matrixWidth; ++j) {
                labels[i][j] = new Label();
                labels[i][j].setText(String.valueOf(matrix[i][j] + "  "));
            }
            matrixPane.addRow(i, labels[i]);
        }
        owner.resize();
    }

    private final ToggleGroup buttonGroup = new ToggleGroup();

    private class SizeButton extends ToggleButton {

        public SizeButton(Integer size) {
            setText(null != size ? String.format("%1$dx%1$d", size) : "авто");
            setOnAction(e -> {
                if (matrix.setPreferredSide(size)) {
                    drawMatrix();
                }
            });
            setToggleGroup(buttonGroup);
            if (matrix.getSize().equals(size)) {
                setSelected(true);
            }
        }
    }
}
