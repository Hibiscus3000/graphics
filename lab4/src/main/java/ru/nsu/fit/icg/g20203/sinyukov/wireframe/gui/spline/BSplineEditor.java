package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.Point;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.color.ColorContainer;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.color.ColorHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline.color.ShapeColorHandler;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.spline.Spline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BSplineEditor extends StackPane {

    private double scale = 1.0;
    private Point center = new Point(0, 0);

    private final List<Line> mainAxes = new ArrayList<>();
    private final List<Line> secondaryAxes = new ArrayList<>();
    private final List<Line> serifs = new ArrayList<>();
    private final List<Line> anchorLines = new ArrayList<>();
    private final List<Line> generatrixLines = new ArrayList<>();

    private final List<Circle> AnchorPointsCircles = new ArrayList<>();
    private final List<Circle> splitPointsCircles = new ArrayList<>();

    private ColorContainer colorContainer = new ColorContainer();
    private final Map<String, ColorHandler> colorHandlers = new HashMap<>();

    public BSplineEditor() {
        addColorChangers();
        colorHandlers.get("backgroundColor").setDefault();
    }

    private void createCoordinatePlane() {
        Line xAxis = new Line();
        xAxis.setStartX(0);
        xAxis.startYProperty().bind(heightProperty().divide(2));
        xAxis.endXProperty().bind(widthProperty());
        xAxis.endYProperty().bind(heightProperty().divide(2));

        Line yAxis = new Line();
        yAxis.startXProperty().bind(widthProperty().divide(2));
        yAxis.setStartY(0);
        yAxis.endXProperty().bind(widthProperty().divide(2));
        yAxis.endYProperty().bind(heightProperty());
        mainAxes.add(xAxis);
        mainAxes.add(yAxis);

        colorHandlers.get("mainAxesColor").setDefault();
    }

    private void addColorChangers() {
        colorHandlers.put("backgroundColor", new ColorHandler("backgroundColor", Color.BLACK, colorContainer,
                color -> setBackground(new Background(new BackgroundFill(color, null, null)))));
        colorHandlers.put("mainAxesColor", new ShapeColorHandler<>("mainAxesColor",
                Color.WHITE, colorContainer, mainAxes));
        colorHandlers.put("secondaryAxesColor", new ShapeColorHandler<>("secondaryAxesColor",
                Color.rgb(200, 200, 200), colorContainer, mainAxes));
        colorHandlers.put("anchorLineColor", new ShapeColorHandler<>("anchorLineColor",
                Color.RED, colorContainer, mainAxes));
        colorHandlers.put("generatrixColor", new ShapeColorHandler<>("generatrixColor",
                Color.BLUE, colorContainer, mainAxes));
        colorHandlers.put("anchorPointsColor", new ShapeColorHandler<>("anchorPointsColor",
                Color.ORANGE, colorContainer, mainAxes));
        colorHandlers.put("chosenAnchorPointColor", new ShapeColorHandler<>("anchorPointsColor",
                Color.GREEN, colorContainer, mainAxes));
        colorHandlers.put("splitPointsColor", new ShapeColorHandler<>("splitPointsColor",
                Color.ORANGE, colorContainer, mainAxes));
        colorHandlers.put("serifs", new ShapeColorHandler<>("serifs",
                Color.WHITE, colorContainer, mainAxes));
    }

    private void zoom() {
        //TODO
    }

    private void moveVision() {
        //TODO
    }

    private void selectAnchorPoint() {
        //TODO
    }

    private void deleteAnchorPoint() {
        //TODO
    }

    public void setSpline(Spline spline) {
        //TODO
    }

    public void moveSelected(double x, double y) {
        //TODO
    }
}
