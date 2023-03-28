package ru.nsu.fit.icg.lab2.filter;

import javafx.scene.image.WritableImage;

public interface Filter {

    enum Color {

        RED("Красный"), GREEN("Зеленый"), BLUE("Синий");

        Color(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }
    }

    String getName();

    String getJsonName();

    WritableImage filter(WritableImage original);
}
