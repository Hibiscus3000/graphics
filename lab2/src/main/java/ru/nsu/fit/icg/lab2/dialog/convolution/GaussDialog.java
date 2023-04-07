package ru.nsu.fit.icg.lab2.dialog.convolution;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.dialog.editBox.OddSideEditBox;
import ru.nsu.fit.icg.lab2.filter.Filter;
import ru.nsu.fit.icg.lab2.filter.window.convolution.GaussFilter;

public class GaussDialog extends FilterDialog {

    private final GaussFilter gaussFilter;
    private int prevMatrixSide;

    public GaussDialog(Filter filter) {
        super(filter);
        gaussFilter = (GaussFilter) filter;
        prevMatrixSide = gaussFilter.getMatrixSide();
        VBox smoothingBox = new VBox(new OddSideEditBox("Размер матрицы",
                gaussFilter.matrixSideProperty(), 3, 11), getButtonBox());
        getDialogPane().setContent(smoothingBox);
    }

    @Override
    protected void ok() {
        prevMatrixSide = gaussFilter.getMatrixSide();
    }

    @Override
    protected void cancel() {
        gaussFilter.matrixSideProperty().set(prevMatrixSide);
    }
}
