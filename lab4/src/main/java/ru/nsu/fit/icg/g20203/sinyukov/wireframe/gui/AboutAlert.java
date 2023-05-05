package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AboutAlert extends Alert {

    public AboutAlert() {
        super(Alert.AlertType.INFORMATION);
        String about = "О программе";
        setTitle(about);
        setHeaderText(about);

        byte[] tabMessageBytes;
        try (InputStream resourceAsStream = getClass().getResourceAsStream("about.txt")) {
            tabMessageBytes = resourceAsStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TextArea textArea = new TextArea(new String(tabMessageBytes, StandardCharsets.UTF_8));
        textArea.setEditable(false);
        textArea.setWrapText(true);

        getDialogPane().setContent(textArea);
    }
}

