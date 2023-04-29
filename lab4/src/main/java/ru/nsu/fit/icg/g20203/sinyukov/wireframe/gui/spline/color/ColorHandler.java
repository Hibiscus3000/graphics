package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.color;

import javafx.scene.paint.Color;

import java.util.function.Consumer;

public class ColorHandler {

    private final String name;
    private Color defaultColor;
    private ColorContainer colorContainer;
    private final Consumer<Color> colorChangeConsumer;

    public ColorHandler(String name, Color defaultColor, ColorContainer colorContainer,
                        Consumer<Color> colorChangeConsumer) {
        this.name = name;
        this.defaultColor = defaultColor;
        this.colorChangeConsumer = colorChangeConsumer;
        colorContainer.putColor(name, defaultColor);
        setColorContainer(colorContainer);
    }

    public void setColorContainer(ColorContainer colorContainer) {
        this.colorContainer = colorContainer;
        performColorChange(colorContainer.getColor(name));
    }

    public void setDefault() {
        performColorChange(defaultColor);
    }

    public void performColorChange(Color color) {
        colorChangeConsumer.accept(color);
        colorContainer.putColor(name, color);
    }
}
