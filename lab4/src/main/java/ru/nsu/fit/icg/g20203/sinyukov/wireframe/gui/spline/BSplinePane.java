package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.*;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.SaveOpenControlBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.bsplineeditor.BSplineEditor;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.bsplineeditor.BSplineEditorBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.BSplineColorConfigBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.BSplineGeneralConfigBox;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.ColorHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.configbox.SplineHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.container.ColorContainer;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

public class BSplinePane extends SplitPane {

    private static final int defaultNumberOfAnchorPoints = 5;
    private static final int defaultSplineSectorPartition = 5;

    private static final double editorWidthScale = 0.6;
    private static final double editorHeightScale = 0.95;

    private final BSplineEditor bSplineEditor = new BSplineEditor();
    private final BSplineGeneralConfigBox generalConfigBox;
    private final BSplineColorConfigBox colorConfigBox;

    public BSplinePane(Button changeScenesButton, SaveOpenControlBox saveOpenControlBox) {
        DoubleBinding editorSizeBinding = Bindings.createDoubleBinding(() ->
                        Math.min(editorWidthScale * getWidth(), editorHeightScale * getHeight()),
                widthProperty(), heightProperty());
        bSplineEditor.minWidthProperty().bind(editorSizeBinding);
        bSplineEditor.minHeightProperty().bind(editorSizeBinding);
        bSplineEditor.maxHeightProperty().bind(editorSizeBinding);

        generalConfigBox = new BSplineGeneralConfigBox(bSplineEditor, saveOpenControlBox,
                changeScenesButton);
        colorConfigBox = new BSplineColorConfigBox(bSplineEditor);

        TabPane tabPane = createTabPane();
        getItems().addAll(new BSplineEditorBox(bSplineEditor), tabPane);

        Spline initSpline = new Spline(defaultNumberOfAnchorPoints, defaultSplineSectorPartition);
        ColorContainer initColorContainer = new ColorContainer();
        setNew(initSpline, initColorContainer);
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("Основные настройки", new ScrollPane(generalConfigBox)));
        tabPane.getTabs().add(new Tab("Настройка цветов", new ScrollPane(colorConfigBox)));
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        return tabPane;
    }

    private void setNew(Spline spline, ColorContainer colorContainer) {
        bSplineEditor.setNew(spline, colorContainer);
        generalConfigBox.setSpline(spline);
        colorConfigBox.setColorContainer(colorContainer);
    }

    public ColorHandler getColorHandler() {
        return colorConfigBox;
    }

    public SplineHandler getSplineHandler() {
        return generalConfigBox;
    }
}
