package ru.nsu.fit.icg.lab2.file;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import ru.nsu.fit.icg.lab2.ImageBox;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SaveFileHandler extends FileHandler {

    private FileChooser saveFileChooser;

    public SaveFileHandler(Window owner, ImageBox imageBox) {
        super(owner, imageBox);
    }

    @Override
    public String getName() {
        return "Сохранить файл";
    }

    @Override
    public void handle(ActionEvent event) {
        if (null == saveFileChooser) {
            saveFileChooser = new FileChooser();
            saveFileChooser.setTitle("Сохранить изображение");
            saveFileChooser.setInitialDirectory(new File("."));
            saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        }
        File file = saveFileChooser.showSaveDialog(owner);
        if (null != file) {
            String name = file.getName();
            if (name.endsWith(".png")) {
                file.renameTo(new File(name + ".png"));
            }
            if (file.exists()) {
                Alert fileExistsAlert = new Alert(Alert.AlertType.WARNING, "Файл с именем "
                        + file.getName() + " уже существует. Вы уверены что хотите перезаписать его?",
                        ButtonType.YES, ButtonType.NO);
                fileExistsAlert.showAndWait();
                if (ButtonType.NO == fileExistsAlert.getResult()) {
                    handle(event);
                    return;
                }
            }
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imageBox.getImage(), null),
                        "png", file);
            } catch (IOException e) {
                Alert savingFileErrorAlert = new Alert(Alert.AlertType.ERROR, "Не удалось сохранить файл "
                        + name + "\n" + e.getMessage(), ButtonType.OK);
                savingFileErrorAlert.showAndWait();
            }
        }
    }
}
