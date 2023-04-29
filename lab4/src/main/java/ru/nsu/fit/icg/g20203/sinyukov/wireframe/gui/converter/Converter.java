package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.converter;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.StringConverter;


public abstract class Converter<T extends Number> extends StringConverter<T> {

    private Alert savingFileErrorAlert;
    protected T prevValue;

    protected void formattingError() {
        if (null == savingFileErrorAlert) {
            savingFileErrorAlert = new Alert(Alert.AlertType.ERROR,
                    "Значение данного поля должно быть " + getNumberName(), ButtonType.OK);
            String errorTitle = "Ошибка форматирования";
            savingFileErrorAlert.setTitle(errorTitle);
            savingFileErrorAlert.setHeaderText(errorTitle);
        }
        if (!savingFileErrorAlert.isShowing()) {
            savingFileErrorAlert.showAndWait();
        }
    }

    protected abstract String getNumberName();
}
