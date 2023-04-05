package ru.nsu.fit.icg.lab2.dialog;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

public abstract class InstrumentDialog extends Dialog {

    protected final static int spacing = 5;

    protected HBox getButtonBox() {
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(spacing);
        Button okButton = new Button("Ок");
        okButton.setOnAction(e -> {
            setResult(ButtonType.OK);
            hide();
            ok();
            e.consume();
        });
        Button cancelButton = new Button("Отмена");
        cancelButton.setOnAction(e -> {
            setResult(ButtonType.CANCEL);
            hide();
            cancel();
            e.consume();
        });
        buttonBox.getChildren().addAll(okButton, cancelButton);
        return buttonBox;
    }

    protected abstract void ok();

    protected abstract void cancel();
}
