package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.bsplineeditor.BSplineEditor;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.bsplineeditor.BSplineEditorBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.color.ColorContainer;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.BSplineColorConfigBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.BSplineGeneralConfigBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.BSplineWidthConfigBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

public class BSplinePane extends SplitPane {

    private static final int defaultNumberOfAnchorPoints = 5;
    private static final int defaultSplineSectorPartition = 5;

    private static final double editorWidthScale = 0.6;
    private static final double editorHeightScale = 0.95;

    private final BSplineEditor bSplineEditor = new BSplineEditor();
    private final BSplineGeneralConfigBox generalConfigBox;
    private final BSplineColorConfigBox colorConfigBox;
    private final BSplineWidthConfigBox widthConfigBox;

    public BSplinePane() {
        DoubleBinding editorSizeBinding = Bindings.createDoubleBinding(() ->
                        Math.min(editorWidthScale * getWidth(), editorHeightScale * getHeight()),
                widthProperty(), heightProperty());
        bSplineEditor.minWidthProperty().bind(editorSizeBinding);
        bSplineEditor.minHeightProperty().bind(editorSizeBinding);
        bSplineEditor.maxWidthProperty().bind(editorSizeBinding);
        bSplineEditor.maxHeightProperty().bind(editorSizeBinding);

        generalConfigBox = new BSplineGeneralConfigBox(bSplineEditor);
        colorConfigBox = new BSplineColorConfigBox();
        widthConfigBox = new BSplineWidthConfigBox();

        TabPane tabPane = createTabPane();
        tabPane.minWidthProperty().bind(widthProperty().subtract(editorSizeBinding));
        getItems().addAll(new BSplineEditorBox(bSplineEditor), tabPane);

        Spline initSpline = new Spline(defaultNumberOfAnchorPoints, defaultSplineSectorPartition);
        ColorContainer initColorContainer = new ColorContainer();
        setNew(initSpline, initColorContainer);
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("Основные настройки", new ScrollPane(generalConfigBox)));
        tabPane.getTabs().add(new Tab("Настройка цветов", new ScrollPane(colorConfigBox)));
        tabPane.getTabs().add(new Tab("Дополнительные настройки", new ScrollPane(widthConfigBox)));
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        return tabPane;
    }

    private void setNew(Spline spline, ColorContainer colorContainer) {
        generalConfigBox.setSpline(spline);
        bSplineEditor.setNew(spline, colorContainer);
        colorConfigBox.setColorContainer(colorContainer);
    }
}
