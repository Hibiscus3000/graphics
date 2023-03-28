package ru.nsu.fit.icg.lab2.dialog.dithering;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.ResizableDialog;
import ru.nsu.fit.icg.lab2.dialog.matrix.OrderedDitheringMatrixBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.ThreeColorFilter;
import ru.nsu.fit.icg.lab2.filter.dithering.ordered.OrderedDitheringFilter;
import ru.nsu.fit.icg.lab2.filter.dithering.ordered.OrderedDitheringMatrix;


public class OrderedDitheringDialog extends DitheringDialog implements ResizableDialog {

    OrderedDitheringMatrixBox[] orderedDitheringMatrixBoxes = new OrderedDitheringMatrixBox[Filter.Color.values().length];

    public OrderedDitheringDialog(ThreeColorFilter threeColorFilter) {
        super(threeColorFilter);
        VBox colorQuantizationBox = getColorQuantizationBox(labelText);
        HBox matricesBox = new HBox();
        OrderedDitheringFilter orderedDitheringFilter = (OrderedDitheringFilter) threeColorFilter;
        for (Filter.Color color : Filter.Color.values()) {
            OrderedDitheringMatrix matrix = orderedDitheringFilter.getMatrix(color);
            OrderedDitheringMatrixBox orderedDitheringMatrixBox
                    = orderedDitheringMatrixBoxes[color.ordinal()]
                    = new OrderedDitheringMatrixBox(color.getName(), matrix, this);
            matricesBox.getChildren().add(orderedDitheringMatrixBox);
            threeColorFilter.getColorProperty(color).addListener((observable, oldVal, newVal)
                    -> {
                if (matrix.сhanged()) {
                    orderedDitheringMatrixBox.drawMatrix();
                    matrix.setChanged(false);
                }
            });
        }
        colorQuantizationBox.getChildren().addAll(matricesBox, getButtonBox());
        getDialogPane().setContent(colorQuantizationBox);
    }

    @Override
    public void resize() {
        getDialogPane().getScene().getWindow().sizeToScene();
    }

    @Override
    protected void cancel() {
        super.cancel();
        for (Filter.Color color : Filter.Color.values()) {
            orderedDitheringMatrixBoxes[color.ordinal()].drawMatrix();
        }
    }
}
