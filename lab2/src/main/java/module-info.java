module ru.nsu.fit.icg.lab2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires json.simple;
    requires java.desktop;
    requires javafx.swing;

    opens ru.nsu.fit.icg.lab2 to javafx.fxml;
    exports ru.nsu.fit.icg.lab2;
    exports ru.nsu.fit.icg.lab2.filter;
    opens ru.nsu.fit.icg.lab2.filter to javafx.fxml;
    exports ru.nsu.fit.icg.lab2.filter.dithering;
    opens ru.nsu.fit.icg.lab2.filter.dithering to javafx.fxml;
    exports ru.nsu.fit.icg.lab2.menuToolbar.toolbar;
    opens ru.nsu.fit.icg.lab2.menuToolbar.toolbar to javafx.fxml;
    exports ru.nsu.fit.icg.lab2.menuToolbar;
    opens ru.nsu.fit.icg.lab2.menuToolbar to javafx.fxml;
    exports ru.nsu.fit.icg.lab2.filter.dithering.ordered;
    opens ru.nsu.fit.icg.lab2.filter.dithering.ordered to javafx.fxml;
    exports ru.nsu.fit.icg.lab2.filter.window.convolution;
    opens ru.nsu.fit.icg.lab2.filter.window.convolution to javafx.fxml;
    exports ru.nsu.fit.icg.lab2.filter.window;
    opens ru.nsu.fit.icg.lab2.filter.window to javafx.fxml;
    exports ru.nsu.fit.icg.lab2.filter.window.convolution.typed;
    opens ru.nsu.fit.icg.lab2.filter.window.convolution.typed to javafx.fxml;
}