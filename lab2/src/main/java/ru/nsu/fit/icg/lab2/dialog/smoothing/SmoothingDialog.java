package ru.nsu.fit.icg.lab2.dialog.smoothing;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.neighborhood.convolution.SmoothingFilter;

public class SmoothingDialog extends FilterDialog {

    private final SmoothingFilter smoothingFilter;
    private int prevMatrixSide;

    public SmoothingDialog(Filter filter) {
        super(filter);
        smoothingFilter = (SmoothingFilter) filter;
        prevMatrixSide = smoothingFilter.getMatrixSide();
        VBox smoothingBox = new VBox(new SmoothingMatrixSideEditBox(
                smoothingFilter.matrixSideProperty()), getButtonBox());
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
