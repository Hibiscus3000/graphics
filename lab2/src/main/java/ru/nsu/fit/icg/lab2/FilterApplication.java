package ru.nsu.fit.icg.lab2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.fit.icg.lab2.filter.BlackWhiteFilter;
import ru.nsu.fit.icg.lab2.filter.FilterChangeHandler;
import ru.nsu.fit.icg.lab2.filter.GammaFilter;
import ru.nsu.fit.icg.lab2.filter.NegativeFilter;
import ru.nsu.fit.icg.lab2.filter.matrix.convolution.*;
import ru.nsu.fit.icg.lab2.filter.matrix.dithering.FloydSteinbergFilter;
import ru.nsu.fit.icg.lab2.filter.matrix.dithering.OrderedDitheringFilter;

public class FilterApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        DrawParent drawParent = new DrawParent();
        FilterChangeHandler filterChangeHandler = new FilterChangeHandler(drawParent);
        MenuToolbarBox menuToolbarBox = new MenuToolbarBox(filterChangeHandler);
        menuToolbarBox.addFilter(new BlackWhiteFilter());
        menuToolbarBox.addFilter(new GammaFilter());
        menuToolbarBox.addFilter(new NegativeFilter());
        menuToolbarBox.addFilter(new FloydSteinbergFilter());
        menuToolbarBox.addFilter(new OrderedDitheringFilter());
        menuToolbarBox.addFilter(new EmbossingFilter());
        menuToolbarBox.addFilter(new SharpeningFilter());
        menuToolbarBox.addFilter(new SmoothingFilter());
        menuToolbarBox.addFilter(new RobertsFilter());
        menuToolbarBox.addFilter(new SobelFilter());
        Scene scene = new Scene(menuToolbarBox);
        stage.setScene(scene);
        stage.show();
    }
}