module ru.nsu.fit.icg.lab4.g20203.sinyukov.wifeframe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens ru.nsu.fit.icg.lab4.g20203.sinyukov.wifeframe to javafx.fxml;
    exports ru.nsu.fit.icg.lab4.g20203.sinyukov.wifeframe;
}