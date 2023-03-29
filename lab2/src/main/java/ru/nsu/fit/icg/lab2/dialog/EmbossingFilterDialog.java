package ru.nsu.fit.icg.lab2.dialog;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.matrix.MatrixPane;
import ru.nsu.fit.icg.lab2.filter.convolution.EmbossingFilter;

import java.util.HashMap;
import java.util.Map;

public class EmbossingFilterDialog extends FilterDialog {

    private final EmbossingFilter embossingFilter;

    private final MatrixPane matrixPane;

    public EmbossingFilterDialog(EmbossingFilter embossingFilter) {
        super(embossingFilter);
        this.embossingFilter = embossingFilter;
        prevDirection = embossingFilter.getDirection();
        matrixPane = new MatrixPane();
        VBox embossingBox = new VBox(getMatrixButtonBox(), matrixPane, getButtonBox());
        getDialogPane().setContent(embossingBox);
    }

    private final Map<EmbossingFilter.Direction, ToggleButton> toggleButtonMap = new HashMap<>();

    private HBox getMatrixButtonBox() {
        HBox matrixButtonBox = new HBox();
        ToggleGroup toggleGroup = new ToggleGroup();
        for (EmbossingFilter.Direction direction : EmbossingFilter.Direction.values()) {
            ToggleButton directionButton = new ToggleButton(direction.getName());
            directionButton.setToggleGroup(toggleGroup);
            directionButton.setOnAction(e -> {
                if (directionButton.isSelected()) {
                    embossingFilter.setDirection(direction);
                    matrixPane.setMatrix(embossingFilter.getDirection().getMatrix());
                } else {
                    directionButton.setSelected(true);
                }
                e.consume();
            });
            if (embossingFilter.getDirection().equals(direction)) {
                directionButton.fire();
            }
            toggleButtonMap.put(direction, directionButton);
            matrixButtonBox.getChildren().add(directionButton);
        }
        return matrixButtonBox;
    }

    private EmbossingFilter.Direction prevDirection;

    @Override
    protected void saveParameters() {
        prevDirection = embossingFilter.getDirection();
    }

    @Override
    protected void cancel() {
        embossingFilter.setDirection(prevDirection);
        toggleButtonMap.get(embossingFilter.getDirection()).fire();
    }
}
