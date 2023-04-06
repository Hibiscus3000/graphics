package ru.nsu.fit.icg.lab2.dialog.convolution;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.dialog.editBox.OddSideEditBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.GaussFilter;

public class SmoothingDialog extends FilterDialog {

    private final GaussFilter smoothingFilter;
    private int prevMatrixSide;

    public SmoothingDialog(Filter filter) {
        super(filter);
        smoothingFilter = (GaussFilter) filter;
        prevMatrixSide = smoothingFilter.getMatrixSide();
        VBox smoothingBox = new VBox(new OddSideEditBox("Размер матрицы сглаживания",
                smoothingFilter.matrixSideProperty(), 3, 11), getButtonBox());
        getDialogPane().setContent(smoothingBox);
    }

    @Override
    protected void ok() {
        prevMatrixSide = smoothingFilter.getMatrixSide();
    }

    @Override
    protected void cancel() {
        smoothingFilter.matrixSideProperty().set(prevMatrixSide);
    }
}
