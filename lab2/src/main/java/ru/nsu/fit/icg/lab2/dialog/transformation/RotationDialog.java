package ru.nsu.fit.icg.lab2.dialog.transformation;

import javafx.scene.layout.VBox;
import ru.nsu.fit.icg.lab2.dialog.InstrumentDialog;
import ru.nsu.fit.icg.lab2.dialog.editBox.IntegerValueEditBox;
import ru.nsu.fit.icg.lab2.dialog.editBox.ValueEditBox;
import ru.nsu.fit.icg.lab2.imageBox.ImageBox;

public class RotationDialog extends InstrumentDialog {

    private final ValueEditBox angdegBox;
    private final ImageBox imageBox;

    public RotationDialog(ImageBox imageBox) {
        setTitle("Поворот");
        angdegBox = new IntegerValueEditBox("Угол", imageBox.angdegProperty(),
                -180, 180, 5);
        this.imageBox = imageBox;
        VBox rotationBox = new VBox(angdegBox, getButtonBox());
        getDialogPane().setContent(rotationBox);
    }

    @Override
    protected void ok() {
        angdegBox.ok();
        imageBox.rotate();
    }

    @Override
    protected void cancel() {
        angdegBox.cancel();
    }
}
