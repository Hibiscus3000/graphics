package ru.nsu.fit.icg.lab2.dialog.matrix;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MatrixPane extends GridPane {

    public void setMatrix(int[][] matrix) {
        getChildren().clear();
        int matrixHeight = matrix.length, matrixWidth = matrix[0].length;
        Label[][] labels = new Label[matrixHeight][matrixWidth];
        for (int i = 0; i < matrixHeight; ++i) {
            for (int j = 0; j < matrixWidth; ++j) {
                labels[i][j] = new Label();
                labels[i][j].setText(0 == matrix[i][j] ? "" :
                        String.valueOf(matrix[i][j] + "  "));
            }
            addRow(i, labels[i]);
        }
    }
}
