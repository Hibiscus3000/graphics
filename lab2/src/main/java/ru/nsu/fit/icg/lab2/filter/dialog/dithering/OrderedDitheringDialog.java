package ru.nsu.fit.icg.lab2.filter.dialog.dithering;

import javafx.beans.Observable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.dialog.MatrixBox;
import ru.nsu.fit.icg.lab2.filter.dialog.ResizableDialog;
import ru.nsu.fit.icg.lab2.filter.dithering.DitheringFilter;
import ru.nsu.fit.icg.lab2.filter.dithering.OrderedDitheringFilter;


public class OrderedDitheringDialog extends DitheringDialog implements ResizableDialog {

    private final MatrixBox[] matrixBoxes = new MatrixBox[3];

    public OrderedDitheringDialog(Filter filter) {
        super(filter);
        OrderedDitheringFilter orderedDitheringFilter = (OrderedDitheringFilter) filter;
        VBox colorQuantizationBox = getColorQuantizationBox();
        HBox matricesBox = new HBox();
        matrixBoxes[DitheringFilter.Color.RED.ordinal()] = new MatrixBox("Красный",
                orderedDitheringFilter.getMatrix(DitheringFilter.Color.RED), this);
        matrixBoxes[DitheringFilter.Color.GREEN.ordinal()] = new MatrixBox("Зеленый",
                orderedDitheringFilter.getMatrix(DitheringFilter.Color.GREEN), this);
        matrixBoxes[DitheringFilter.Color.BLUE.ordinal()] = new MatrixBox("Синий",
                orderedDitheringFilter.getMatrix(DitheringFilter.Color.BLUE), this);
        DitheringFilter ditheringFilter = (DitheringFilter) filter;
        redQuantizationEditBox.setChangeHandler(new MatrixQuantizationHandler(DitheringFilter.Color.RED));
        greenQuantizationEditBox.setChangeHandler(new MatrixQuantizationHandler(DitheringFilter.Color.GREEN));
        blueQuantizationEditBox.setChangeHandler(new MatrixQuantizationHandler(DitheringFilter.Color.BLUE));
        matricesBox.getChildren().addAll(matrixBoxes[DitheringFilter.Color.RED.ordinal()],
                matrixBoxes[DitheringFilter.Color.GREEN.ordinal()],
                matrixBoxes[DitheringFilter.Color.BLUE.ordinal()]);
        colorQuantizationBox.getChildren().addAll(matricesBox, getButtonBox());
        getDialogPane().setContent(colorQuantizationBox);
    }

    @Override
    public void resize() {
        getDialogPane().getScene().getWindow().sizeToScene();
    }

    private class MatrixQuantizationHandler extends QuantizationHandler {

        public MatrixQuantizationHandler(DitheringFilter.Color color) {
            super(color);
        }

        @Override
        public void invalidated(Observable observable) {
            if (ditheringFilter.setQuantization(color, redQuantizationEditBox.getValue())) {
                matrixBoxes[color.ordinal()].drawMatrix();
            }
        }
    }
}
