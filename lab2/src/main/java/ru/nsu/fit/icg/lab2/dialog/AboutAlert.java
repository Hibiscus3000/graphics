package ru.nsu.fit.icg.lab2.dialog;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AboutAlert extends Alert {

    public AboutAlert() {
        super(AlertType.INFORMATION);
        InputStream imageStream = getClass().getResourceAsStream("about.png");
        //<a href="https://www.flaticon.com/ru/free-icons/" title=" иконки"> иконки от Freepik - Flaticon</a>
        setGraphic(new ImageView(new Image(imageStream)));
        Map<String, String> tabInfos = new HashMap<>();
        tabInfos.put("О программе", "about.txt");
        tabInfos.put("Руководство пользователя", "guide.txt");
        String about = "О программе";
        setTitle(about);
        setHeaderText(about);

        final TabPane tabPane = new TabPane();
        getDialogPane().getScene().getStylesheets().add(getClass().getResource("aboutStyling.css").toExternalForm());
        for (Map.Entry<String, String> tabInto : tabInfos.entrySet().stream().toList()) {
            byte[] tabMessageBytes;
            try (InputStream resourceAsStream = getClass().getResourceAsStream(tabInto.getValue())) {
                tabMessageBytes = resourceAsStream.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            TextArea textArea = new TextArea(new String(tabMessageBytes, StandardCharsets.UTF_8));
            textArea.setEditable(false);
            textArea.setWrapText(true);

            Tab tab = new Tab(tabInto.getKey(), new ScrollPane(textArea));
            tabPane.getTabs().add(tab);
        }
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        getDialogPane().setContent(tabPane);
    }
}
