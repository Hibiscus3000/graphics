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

public class OpenFileHandler extends FileHandler {

    private FileChooser openFileChooser;

    public OpenFileHandler(Window owner, ImageBox imageBox) {
        super(owner, imageBox);
    }

    @Override
    public void handle(ActionEvent event) {
        if (null == openFileChooser) {
            openFileChooser = new FileChooser();
            openFileChooser.setTitle("Открыть изображение");
            openFileChooser.setInitialDirectory(new File("."));
            openFileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image file", "*.png", "*.jpeg", "*.jpg",
                            "*.bmp", "*.gif"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                    new FileChooser.ExtensionFilter("GIF", "*.gif")
            );
        }
        File file = openFileChooser.showOpenDialog(owner);
        try {
            if (null != file) {
                imageBox.openImage(SwingFXUtils.toFXImage(ImageIO.read(file), null));
            }
        } catch (IOException e) {
            Alert unableToOpenFileAlert = new Alert(Alert.AlertType.ERROR, "Не удалось открыть файл"
                    + file.getName(), ButtonType.OK);
            unableToOpenFileAlert.showAndWait();
        }
    }

    @Override
    public String getName() {
        return "Открыть файл";
    }
}
