module ru.nsu.fit.icg.g20203.sinyukov.wireframe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens ru.nsu.fit.icg.g20203.sinyukov.wireframe to javafx.fxml;
    exports ru.nsu.fit.icg.g20203.sinyukov.wireframe;
    exports ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;
    opens ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline to javafx.fxml;
    exports ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.bsplineeditor;
    opens ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.bsplineeditor to javafx.fxml;
    exports ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox;
    opens ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox to javafx.fxml;
    exports ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.container;
    opens ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.container to javafx.fxml;
}