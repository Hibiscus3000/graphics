package ru.nsu.fit.icg.lab2.dialog.convolution.typed;

import javafx.beans.property.IntegerProperty;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.dialog.ResizableDialog;
import ru.nsu.fit.icg.lab2.dialog.editBox.OddSideEditBox;
import ru.nsu.fit.icg.lab2.filter.WaterColorizationFilter;
import ru.nsu.fit.icg.lab2.filter.window.MedianFilter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.SharpeningFilter;

public class WaterColorizationDialog extends FilterDialog implements ResizableDialog {

    private final MedianFilter medianFilter;
    private final SharpeningFilter sharpeningFilter;

    private final TypedMatrixBox sharpeningMatrixBox;

    public WaterColorizationDialog(WaterColorizationFilter waterColorizationFilter) {
        super(waterColorizationFilter);
        medianFilter = waterColorizationFilter.getMedianFilter();
        sharpeningFilter = waterColorizationFilter.getSharpeningFilter();
        IntegerProperty medianWindowSideProperty = medianFilter.windowSideProperty();
        OddSideEditBox medianWindowSideBox = new OddSideEditBox("Размер окна медианного фильтра",
                medianWindowSideProperty, medianFilter.getMinWindowSide(), medianFilter.getMaxWindowSide());
        prevMedianWindowSide = medianWindowSideProperty.get();
        sharpeningMatrixBox = new TypedMatrixBox(this, sharpeningFilter, "Степень увеличения резкости");

        VBox waterColorizationBox = new VBox(medianWindowSideBox, sharpeningMatrixBox, getButtonBox());
        getDialogPane().setContent(waterColorizationBox);
    }

    private int prevMedianWindowSide;

    @Override
    protected void ok() {
        prevMedianWindowSide = medianFilter.windowSideProperty().get();
        sharpeningMatrixBox.ok();
    }

    @Override
    protected void cancel() {
        medianFilter.windowSideProperty().set(prevMedianWindowSide);
        sharpeningMatrixBox.cancel();
    }
}
