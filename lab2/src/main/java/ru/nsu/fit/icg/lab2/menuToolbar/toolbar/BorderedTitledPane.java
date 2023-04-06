package ru.nsu.fit.icg.lab2.menuToolbar.toolbar;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class BorderedTitledPane extends StackPane {

    public BorderedTitledPane(String titleText, Node content) {
        Label title = new Label(" " + titleText + " ");
        title.getStyleClass().add("bordered-titled-title");
        setAlignment(title, Pos.TOP_LEFT);

        StackPane contentPane = new StackPane();
        content.getStyleClass().add("bordered-titled-content");
        contentPane.getChildren().add(content);

        getStyleClass().add("bordered-titled-border");
        minWidthProperty().bind(title.widthProperty().add(25));
        getChildren().addAll(title, contentPane);
    }
}
