package ru.nsu.fit.icg.lab2.dialog.convolution.typed;

import ru.nsu.fit.icg.lab2.dialog.edit_box.OddIntegerSideEditBox;
import ru.nsu.fit.icg.lab2.filter.window.convolution.typed.MotionFilter;

public class MotionDialog extends MatrixTypedDialog {

    private final MotionFilter motionFilter;

    private final OddIntegerSideEditBox windowSideBox;

    public MotionDialog(MotionFilter motionFilter) {
        super(new OddIntegerSideEditBox("Размер окна",
                        motionFilter.windowSideProperty(),
                        9, motionFilter.getMaxWindowSide()),
                motionFilter, "Выберите направление размытия");
        windowSideBox = (OddIntegerSideEditBox) getUpperNode();
        this.motionFilter = motionFilter;
        motionFilter.windowSideProperty().addListener(((observable, oldVal, newVal) -> {
            if (!oldVal.equals(newVal) && 1 == (int) newVal % 2) {
                typedMatrixBox.redrawMatrix();
                getDialogPane().getScene().getWindow().sizeToScene();
            }
        }));
    }

    @Override
    protected void ok() {
        super.ok();
        windowSideBox.ok();
    }

    @Override
    protected void cancel() {
        super.cancel();
        windowSideBox.cancel();
    }
}
