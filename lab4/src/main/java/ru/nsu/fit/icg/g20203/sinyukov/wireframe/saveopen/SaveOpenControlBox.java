package ru.nsu.fit.icg.g20203.sinyukov.wireframe.saveopen;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure.RotationFigureHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.ColorHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.SplineHandler;

import java.io.*;

public class SaveOpenControlBox extends VBox {

    private ColorHandler colorHandler;
    private SplineHandler splineHandler;
    private RotationFigureHandler rotationFigureHandler;

    public SaveOpenControlBox() {
        Button saveButton = new Button("Сохранить");
        saveButton.setOnAction(e -> save());
        Button openButton = new Button("Открыть");
        openButton.setOnAction(e -> open());

        getChildren().addAll(saveButton, openButton);

        fileChooser.setTitle("Открыть файл wireframe");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл wireframe", "*.wrf"));

    }

    public void setColorHandler(ColorHandler colorHandler) {
        this.colorHandler = colorHandler;
    }

    public void setSplineHandler(SplineHandler splineHandler) {
        this.splineHandler = splineHandler;
    }

    public void setRotationFigureParameterContainer(RotationFigureHandler rotationFigureHandler) {
        this.rotationFigureHandler = rotationFigureHandler;
    }

    FileChooser fileChooser = new FileChooser();

    private void open() {
        File file = fileChooser.showOpenDialog(getScene().getWindow());
        if (null != file) {
            try {
                ObjectInputStream containerInputStream = new ObjectInputStream(new FileInputStream(file));
                SerializableContainer serializableContainer = (SerializableContainer) containerInputStream.readObject();
                splineHandler.setSpline(serializableContainer.getSerializableSpline());
                rotationFigureHandler.setSpline(splineHandler.getSpline());
                rotationFigureHandler.setRotationFigureParameterContainer(serializableContainer.getRotationFigureParameterContainer());
            } catch (IOException e) {
                showOpenErrorAlert(file.getName());
            } catch (ClassNotFoundException e) {
                showWrongFormatAlert(file.getName());
            }
        }
    }

    private void save() {
        File file = fileChooser.showSaveDialog(getScene().getWindow());
        if (null != file) {
            String fileName = file.getName();
            if (!fileName.endsWith(".wrf")) {
                file.renameTo(new File(fileName + ".wrf"));
            }
            try {
                ObjectOutputStream containerInputStream = new ObjectOutputStream(new FileOutputStream(file));
                containerInputStream.writeObject(getSerializableContainer());
            } catch (IOException e) {
                showOpenErrorAlert(file.getName());
            }
        }
    }

    private SerializableContainer getSerializableContainer() {
        return new SerializableContainer(splineHandler.getSerializableSpline(), rotationFigureHandler.getRotationFigureParameterContainer());
    }

    private final String errorTitle = "Неудача";

    private void showOpenErrorAlert(String fileName) {
        Alert unableToOpenFileAlert = new Alert(Alert.AlertType.ERROR, "Не удалось открыть файл "
                + fileName, ButtonType.OK);
        unableToOpenFileAlert.setTitle(errorTitle);
        unableToOpenFileAlert.setHeaderText(errorTitle);
        unableToOpenFileAlert.showAndWait();
    }

    private void showWrongFormatAlert(String fileName) {
        Alert unableToOpenFileAlert = new Alert(Alert.AlertType.ERROR, "Выбран неправильный или поврежденный файл: "
                + fileName, ButtonType.OK);
        unableToOpenFileAlert.setTitle(errorTitle);
        unableToOpenFileAlert.setHeaderText(errorTitle);
        unableToOpenFileAlert.showAndWait();
    }

}
