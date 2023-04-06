package ru.nsu.fit.icg.lab2.dialog.matrix;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.ResizableDialog;

public class MatrixBox extends VBox {

    private final CheckBox showMatrixCheckBox = new CheckBox("Скрыть матрицу");
    private final Label matrixSizeLabel = new Label();
    private final GridPane matrixPane = new GridPane();

    private final ResizableDialog owner;

    public MatrixBox(ResizableDialog owner) {
        this.owner = owner;
        showMatrixCheckBox.selectedProperty().addListener(((observable, oldVal, newVal) -> {
            if (!newVal) {
                drawMatrix();
            } else {
                matrixPane.getChildren().clear();
                owner.resize();
            }
        }));
        showMatrixCheckBox.setSelected(true);
        getChildren().addAll(showMatrixCheckBox, matrixSizeLabel, matrixPane);
    }

    private int[][] matrix;

    public void setMatrix(int[][] matrix) {
        matrixSizeLabel.setText(matrix.length + "x" + matrix.length);
        this.matrix = matrix;
        if (!showMatrixCheckBox.isSelected()) {
            drawMatrix();
        }
    }

    public void drawMatrix() {
        if (null == matrix) {
            return;
        }
        matrixPane.getChildren().clear();
        int matrixHeight = matrix.length, matrixWidth = matrix[0].length;
        Label[][] labels = new Label[matrixHeight][matrixWidth];
        for (int i = 0; i < matrixHeight; ++i) {
            for (int j = 0; j < matrixWidth; ++j) {
                labels[i][j] = new Label();
                labels[i][j].setText(0 == matrix[i][j] ? "" :
                        String.valueOf(matrix[i][j] + "  "));
            }
            matrixPane.addRow(i, labels[i]);
        }
        owner.resize();
    }
}
