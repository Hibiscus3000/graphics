package ru.nsu.fit.icg.lab2.dialog;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.edit_box.OddIntegerSideEditBox;
import ru.nsu.fit.icg.lab2.filter.window.MedianFilter;

public class MedianDialog extends FilterDialog {

    private final MedianFilter medianFilter;

    public MedianDialog(MedianFilter medianFilter) {
        super(medianFilter);
        this.medianFilter = medianFilter;
        previousWindowSide = medianFilter.windowSideProperty().get();
        OddIntegerSideEditBox windowSideBox = new OddIntegerSideEditBox("Размер окна",
                medianFilter.windowSideProperty(), medianFilter.getMinWindowSide(), medianFilter.getMaxWindowSide());
        VBox medianBox = new VBox(windowSideBox, getButtonBox());
        getDialogPane().setContent(medianBox);
    }

    private int previousWindowSide;

    @Override
    protected void ok() {
        previousWindowSide = medianFilter.windowSideProperty().get();
    }

    @Override
    protected void cancel() {
        medianFilter.windowSideProperty().set(previousWindowSide);
    }
}
