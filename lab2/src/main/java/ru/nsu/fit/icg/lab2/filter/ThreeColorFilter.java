package ru.nsu.fit.icg.lab2.filter;

import javafx.beans.property.IntegerProperty;

public abstract class ThreeColorFilter implements Filter {

    public enum Color {

        RED("Красный"), GREEN("Зеленый"), BLUE("Синий");

        Color(String name) {
            this.name = name;
        }

        private final String name;

        public String getName() {
            return name;
        }
    }

    protected IntegerProperty[] colorProperties;

    public IntegerProperty getColorProperty(Color color) {
        return colorProperties[color.ordinal()];
    }
}
