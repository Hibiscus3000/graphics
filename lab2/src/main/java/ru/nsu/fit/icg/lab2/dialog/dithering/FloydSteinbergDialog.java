package ru.nsu.fit.icg.lab2.dialog.dithering;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.dithering.DitheringFilter;

public class FloydSteinbergDialog extends DitheringDialog {

    public FloydSteinbergDialog(Filter filter) {
        super(filter);
        VBox vBox = getColorQuantizationBox();
        vBox.getChildren().add(getButtonBox());
        getDialogPane().setContent(vBox);

        quantizationEditBoxes[Filter.Color.RED.ordinal()].setChangeHandler(new QuantizationHandler(DitheringFilter.Color.RED));
        quantizationEditBoxes[Filter.Color.GREEN.ordinal()].setChangeHandler(new QuantizationHandler(DitheringFilter.Color.GREEN));
        quantizationEditBoxes[Filter.Color.BLUE.ordinal()].setChangeHandler(new QuantizationHandler(DitheringFilter.Color.BLUE));
    }
}
