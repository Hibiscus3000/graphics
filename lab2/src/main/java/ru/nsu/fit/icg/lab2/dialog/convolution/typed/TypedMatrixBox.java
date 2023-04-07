package ru.nsu.fit.icg.lab2.dialog.convolution.typed;

import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.ResizableDialog;
import ru.nsu.fit.icg.lab2.dialog.matrix.MatrixBox;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.MatrixType;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.MatrixTypedFilter;
import ru.nsu.fit.icg.lab2.menuToolbar.MenuToolbarBox;

import java.util.HashMap;
import java.util.Map;

public class TypedMatrixBox extends VBox {

    private final MatrixTypedFilter matrixTypedFilter;

    private final MatrixBox matrixBox;

    public TypedMatrixBox(ResizableDialog owner, MatrixTypedFilter matrixTypedFilter, String labelText) {
        this.matrixTypedFilter = matrixTypedFilter;
        prevMatrixType = matrixTypedFilter.getMatrixType();
        matrixBox = new MatrixBox(owner);
        getStyleClass().add("typed-matrix-box");
        getChildren().addAll(new Label(labelText), getMatrixButtonBox(), matrixBox);
    }

    private final Map<MatrixType, ToggleButton> toggleButtonMap = new HashMap<>();

    private HBox getMatrixButtonBox() {
        HBox matrixButtonBox = new HBox();
        matrixButtonBox.getStyleClass().add("matrix-button-box");
        ToggleGroup toggleGroup = new ToggleGroup();
        for (MatrixType matrixType : matrixTypedFilter.getMatrixTypes()) {
            ToggleButton matrixTypeButton = new ToggleButton();
            String imageName = matrixType.getImageName();
            if (null != imageName) {
                matrixTypeButton.setGraphic(new ImageView(MenuToolbarBox.getButtonImage(imageName)));
                matrixTypeButton.setTooltip(new Tooltip(matrixType.getName()));
            } else {
                matrixTypeButton.setText(matrixType.getName());
            }
            matrixTypeButton.setToggleGroup(toggleGroup);
            matrixTypeButton.setOnAction(e -> {
                matrixTypedFilter.setMatrixType(matrixType);
                matrixBox.setMatrix(matrixTypedFilter.getMatrixType().getMatrix());
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
