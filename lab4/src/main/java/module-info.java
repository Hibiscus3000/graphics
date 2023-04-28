module ru.nsu.fit.icg.g20203.sinyukov.wireframe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens ru.nsu.fit.icg.g20203.sinyukov.wireframe to javafx.fxml;
    exports ru.nsu.fit.icg.g20203.sinyukov.wireframe;
}