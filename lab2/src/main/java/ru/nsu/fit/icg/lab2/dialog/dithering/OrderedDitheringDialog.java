package ru.nsu.fit.icg.lab2.dialog.dithering;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.ResizableDialog;
import ru.nsu.fit.icg.lab2.dialog.matrix.ChangeableMatrixBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.dithering.DitheringFilter;
import ru.nsu.fit.icg.lab2.filter.dithering.OrderedDitheringFilter;


public class OrderedDitheringDialog extends DitheringDialog implements ResizableDialog {

    private final ChangeableMatrixBox[] changeableMatrixBoxes = new ChangeableMatrixBox[3];

    public OrderedDitheringDialog(Filter filter) {
        super(filter);
        OrderedDitheringFilter orderedDitheringFilter = (OrderedDitheringFilter) filter;
        VBox colorQuantizationBox = getColorQuantizationBox();
        HBox matricesBox = new HBox();
        changeableMatrixBoxes[DitheringFilter.Color.RED.ordinal()] = new ChangeableMatrixBox("Красный",
                orderedDitheringFilter.getMatrix(DitheringFilter.Color.RED), this);
        changeableMatrixBoxes[DitheringFilter.Color.GREEN.ordinal()] = new ChangeableMatrixBox("Зеленый",
                orderedDitheringFilter.getMatrix(DitheringFilter.Color.GREEN), this);
        changeableMatrixBoxes[DitheringFilter.Color.BLUE.ordinal()] = new ChangeableMatrixBox("Синий",
                orderedDitheringFilter.getMatrix(DitheringFilter.Color.BLUE), this);
        quantizationEditBoxes[Filter.Color.RED.ordinal()].setChangeHandler(new MatrixQuantizationHandler(DitheringFilter.Color.RED));
        quantizationEditBoxes[Filter.Color.GREEN.ordinal()].setChangeHandler(new MatrixQuantizationHandler(DitheringFilter.Color.GREEN));
        quantizationEditBoxes[Filter.Color.BLUE.ordinal()].setChangeHandler(new MatrixQuantizationHandler(DitheringFilter.Color.BLUE));
        matricesBox.getChildren().addAll(changeableMatrixBoxes[DitheringFilter.Color.RED.ordinal()],
                changeableMatrixBoxes[DitheringFilter.Color.GREEN.ordinal()],
                changeableMatrixBoxes[DitheringFilter.Color.BLUE.ordinal()]);
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
        public void changed(ObservableValue<? extends Integer> observableValue, Integer oldVal, Integer newVal) {
            if (ditheringFilter.setQuantization(color, newVal)) {
                changeableMatrixBoxes[color.ordinal()].drawMatrix();
            }
        }
    }
}
