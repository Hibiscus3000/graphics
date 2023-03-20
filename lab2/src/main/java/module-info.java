module ru.nsu.fit.icg.lab2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires json.simple;

    opens ru.nsu.fit.icg.lab2 to javafx.fxml;
    exports ru.nsu.fit.icg.lab2;
    exports ru.nsu.fit.icg.lab2.filter;
    opens ru.nsu.fit.icg.lab2.filter to javafx.fxml;
    exports ru.nsu.fit.icg.lab2.filter.matrix;
    opens ru.nsu.fit.icg.lab2.filter.matrix to javafx.fxml;
}