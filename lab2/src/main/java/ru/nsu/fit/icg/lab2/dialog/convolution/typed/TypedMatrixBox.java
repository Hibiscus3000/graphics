package ru.nsu.fit.icg.lab2.dialog.convolution.typed;

import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.matrix.MatrixPane;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.MatrixType;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.MatrixTypedFilter;

import java.util.HashMap;
import java.util.Map;

public class TypedMatrixBox extends VBox {

    private final MatrixTypedFilter matrixTypedFilter;

    private final MatrixPane matrixPane;

    public TypedMatrixBox(MatrixTypedFilter matrixTypedFilter, String labelText) {
        this.matrixTypedFilter = matrixTypedFilter;
        prevMatrixType = matrixTypedFilter.getMatrixType();
        matrixPane = new MatrixPane();
        getChildren().addAll(new Label(labelText), getMatrixButtonBox(), matrixPane);
    }

    private final Map<MatrixType, ToggleButton> toggleButtonMap = new HashMap<>();

    private HBox getMatrixButtonBox() {
        HBox matrixButtonBox = new HBox();
        ToggleGroup toggleGroup = new ToggleGroup();
        for (MatrixType matrixType : matrixTypedFilter.getMatrixTypes()) {
            ToggleButton matrixTypeButton = new ToggleButton(matrixType.getName());
            matrixTypeButton.setToggleGroup(toggleGroup);
            matrixTypeButton.setOnAction(e -> {
                matrixTypedFilter.setMatrixType(matrixType);
                matrixPane.setMatrix(matrixTypedFilter.getMatrixType().getMatrix());
                matrixTypeButton.setSelected(true);
                e.consume();
            });
            if (matrixTypedFilter.getMatrixType().equals(matrixType)) {
                matrixTypeButton.fire();
            }
            toggleButtonMap.put(matrixType, matrixTypeButton);
            matrixButtonBox.getChildren().add(matrixTypeButton);
        }
        return matrixButtonBox;
    }

    private MatrixType prevMatrixType;

    public void ok() {
        prevMatrixType = matrixTypedFilter.getMatrixType();
    }

    public void cancel() {
        matrixTypedFilter.setMatrixType(prevMatrixType);
        toggleButtonMap.get(matrixTypedFilter.getMatrixType()).fire();
    }

    public void redrawMatrix() {
        toggleButtonMap.get(matrixTypedFilter.getMatrixType()).fire();
    }
}
