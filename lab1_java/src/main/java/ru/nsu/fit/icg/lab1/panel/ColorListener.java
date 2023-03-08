package ru.nsu.fit.icg.lab1.panel;

import javax.swing.*;
import java.awt.*;

public interface ColorListener {

    void setColor(Color color);

    void showColorDialog(JColorChooser colorChooser);
}
