package ru.nsu.fit.icg.lab2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.nsu.fit.icg.lab2.file.OpenFileHandler;
import ru.nsu.fit.icg.lab2.file.SaveFileHandler;
import ru.nsu.fit.icg.lab2.filter.FilterChangeHandler;

public class FilterApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        DrawParent drawParent = new DrawParent();
        FilterChangeHandler filterChangeHandler = new FilterChangeHandler(drawParent);
        MenuToolbarBox menuToolbarBox = new MenuToolbarBox(filterChangeHandler);
        Window owner = stage.getOwner();
        menuToolbarBox.addFileHandler(new OpenFileHandler(owner, drawParent));
        menuToolbarBox.addFileHandler(new SaveFileHandler(owner, drawParent));
        menuToolbarBox.addFilters();
        VBox appBox = new VBox(menuToolbarBox, drawParent);
        Scene scene = new Scene(appBox);
        stage.setScene(scene);
        stage.show();
    }
}