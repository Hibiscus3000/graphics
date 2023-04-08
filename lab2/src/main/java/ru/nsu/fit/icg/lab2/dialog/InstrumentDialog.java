package ru.nsu.fit.icg.lab2.dialog;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

public abstract class InstrumentDialog extends Dialog implements ResizableDialog {

    public InstrumentDialog() {
        String css = InstrumentDialog.class.getResource("dialogStyling.css").toExternalForm();
        getDialogPane().getScene().getStylesheets().add(css);
        setOnShown(e -> resize());
    }

    protected HBox getButtonBox() {
        HBox buttonBox = new HBox();
        buttonBox.getStyleClass().add("button-box");
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
