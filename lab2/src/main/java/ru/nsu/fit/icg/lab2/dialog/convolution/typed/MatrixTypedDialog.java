package ru.nsu.fit.icg.lab2.dialog.convolution.typed;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.FilterDialog;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.MatrixTypedFilter;

public abstract class MatrixTypedDialog extends FilterDialog {

    protected final TypedMatrixBox typedMatrixBox;

    private final Node upperNode;

    public MatrixTypedDialog(Node upperNode, MatrixTypedFilter matrixTypedFilter, String labelText) {
        super(matrixTypedFilter);
        typedMatrixBox = new TypedMatrixBox(matrixTypedFilter, labelText);
        VBox matrixTypedBox = new VBox();
        this.upperNode = upperNode;
        if (null != upperNode) {
            matrixTypedBox.getChildren().add(upperNode);
        }
        matrixTypedBox.getChildren().addAll(typedMatrixBox, getButtonBox());
        getDialogPane().setContent(matrixTypedBox);
    }

    public MatrixTypedDialog(MatrixTypedFilter matrixTypedFilter, String labelText) {
        this(null, matrixTypedFilter, labelText);
    }

    public Node getUpperNode() {
        return upperNode;
    }

    @Override
    protected void saveParameters() {
        typedMatrixBox.saveParameters();
    }

    @Override
    protected void cancel() {
        typedMatrixBox.cancel();
    }
}
