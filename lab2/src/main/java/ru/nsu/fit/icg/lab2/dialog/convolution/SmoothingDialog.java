package ru.nsu.fit.icg.lab2.dialog.convolution;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.dialog.edit_box.OddIntegerSideEditBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.SmoothingFilter;

public class SmoothingDialog extends FilterDialog {

    private final SmoothingFilter smoothingFilter;
    private int prevMatrixSide;

    public SmoothingDialog(Filter filter) {
        super(filter);
        smoothingFilter = (SmoothingFilter) filter;
        prevMatrixSide = smoothingFilter.getMatrixSide();
        VBox smoothingBox = new VBox(new OddIntegerSideEditBox("Размер матрицы сглаживания",
                smoothingFilter.matrixSideProperty(), 3, 11), getButtonBox());
        getDialogPane().setContent(smoothingBox);
    }

    @Override
    protected void saveParameters() {
        prevMatrixSide = smoothingFilter.getMatrixSide();
    }

    @Override
    protected void cancel() {
        smoothingFilter.matrixSideProperty().set(prevMatrixSide);
    }
}
