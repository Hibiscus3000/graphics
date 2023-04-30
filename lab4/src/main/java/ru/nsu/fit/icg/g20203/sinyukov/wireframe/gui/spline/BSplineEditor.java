package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
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

public class BSplineEditor extends Pane {

    private final DoubleProperty scale = new SimpleDoubleProperty(3);
    private Point center = new Point(0, 0);

    private Spline spline;

    private final List<Line> mainAxes = new ArrayList<>();
    private final List<Line> secondaryAxes = new ArrayList<>();
    private final List<Line> serifs = new ArrayList<>();
    private final List<Line> splineLines = new ArrayList<>();
    private final List<Line> generatrixLines = new ArrayList<>();

    private final List<Circle> anchorPointsCircles = new ArrayList<>();
    private final List<Circle> splitPointsCircles = new ArrayList<>();

    private ColorContainer colorContainer = new ColorContainer();
    private final Map<String, ColorHandler> colorHandlers = new HashMap<>();

    private final DoubleProperty mainAxesStrokeWidth = new SimpleDoubleProperty(2);

    private final DoubleProperty generatrixStrokeWidth = new SimpleDoubleProperty(2);
    private final DoubleProperty splineStrokeWidth = new SimpleDoubleProperty(3);

    private final DoubleProperty anchorPointR = new SimpleDoubleProperty(6.0);
    private final DoubleProperty splitPointR = new SimpleDoubleProperty(4.0);

    public BSplineEditor() {
        addColorHandlers();
        createCoordinatePlane();
        colorHandlers.get("backgroundColor").setDefault();
        minHeightProperty().bind(minWidthProperty());
        new Background(new BackgroundFill(Color.BLACK, null, null));
    }

    private void createCoordinatePlane() {
        Line xAxis = new Line();
        xAxis.setStartX(0);
        xAxis.startYProperty().bind(heightProperty().divide(2));
        xAxis.endXProperty().bind(widthProperty());
        xAxis.endYProperty().bind(heightProperty().divide(2));
        xAxis.strokeWidthProperty().bind(mainAxesStrokeWidth);

        Line yAxis = new Line();
        yAxis.startXProperty().bind(widthProperty().divide(2));
        yAxis.setStartY(0);
        yAxis.endXProperty().bind(widthProperty().divide(2));
        yAxis.endYProperty().bind(heightProperty());
        yAxis.strokeWidthProperty().bind(mainAxesStrokeWidth);

        mainAxes.add(xAxis);
        mainAxes.add(yAxis);

        colorHandlers.get("mainAxesColor").setDefault();
        getChildren().addAll(xAxis, yAxis);
    }

    private void addColorHandlers() {
        colorHandlers.put("backgroundColor", new ColorHandler("backgroundColor", Color.WHITE, colorContainer,
                color -> setBackground(new Background(new BackgroundFill(color, null, null)))));
        colorHandlers.put("mainAxesColor", new ShapeColorHandler<>("mainAxesColor",
                Color.BLACK, colorContainer, mainAxes));
        colorHandlers.put("secondaryAxesColor", new ShapeColorHandler<>("secondaryAxesColor",
                Color.rgb(200, 200, 200), colorContainer, secondaryAxes));
        colorHandlers.put("splineLinesColor", new ShapeColorHandler<>("splineLinesColor",
                Color.RED, colorContainer, splineLines));
        colorHandlers.put("generatrixColor", new ShapeColorHandler<>("generatrixColor",
                Color.BLUE, colorContainer, generatrixLines));
        colorHandlers.put("anchorPointsColor", new ShapeColorHandler<>("anchorPointsColor",
                Color.ORANGE, colorContainer, anchorPointsCircles));
//        colorHandlers.put("chosenAnchorPointColor", new ShapeColorHandler<>("anchorPointsColor",
//                Color.GREEN, colorContainer, mainAxes));
        colorHandlers.put("splitPointsColor", new ShapeColorHandler<>("splitPointsColor",
                Color.ORANGE, colorContainer, splitPointsCircles));
        colorHandlers.put("serifs", new ShapeColorHandler<>("serifs",
                Color.WHITE, colorContainer, serifs));
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
        this.spline = spline;
        List<Point> anchorPoints = spline.getAnchorPoints();
        addAnchorPointCircle(anchorPoints.get(0));
        for (int i = 1; i < anchorPoints.size(); ++i) {
            Line generatrixLine = createLine(anchorPoints.get(i - 1),
                    anchorPoints.get(i), colorContainer.getColor("generatrixColor"),
                    generatrixStrokeWidth);
            generatrixLines.add(generatrixLine);
            getChildren().add(generatrixLine);
            addAnchorPointCircle(anchorPoints.get(i));
        }

        for (List<Point> line : spline.getSplineLines()) {
            for (int i = 1; i < line.size(); ++i) {
                Line splineLine = createLine(line.get(i - 1), line.get(i),
                        colorContainer.getColor("splineLinesColor"),
                        splineStrokeWidth);
                getChildren().add(splineLine);
                splineLines.add(splineLine);
            }
        }
    }

    private void addAnchorPointCircle(Point point) {
        Circle anchorPointsCircle = createCircle(point, anchorPointR,
                colorContainer.getColor("anchorPointsColor"));
        getChildren().add(anchorPointsCircle);
        anchorPointsCircles.add(anchorPointsCircle);
    }

    private Line createLine(Point p1, Point p2, Color color, DoubleProperty strokeWidth) {
        Line line = new Line();
        line.startXProperty().bind(p1.uProperty().multiply(scale).add(widthProperty().divide(2)));
        line.endXProperty().bind(p2.uProperty().multiply(scale).add(widthProperty().divide(2)));
        line.startYProperty().bind(p1.vProperty().multiply(scale).add(heightProperty().divide(2)));
        line.endYProperty().bind(p2.vProperty().multiply(scale).add(heightProperty().divide(2)));
        line.strokeWidthProperty().bind(strokeWidth);
        line.setFill(color);
        line.setStroke(color);
        return line;
    }

    private Circle createCircle(Point point, DoubleProperty r, Color color) {
        Circle circle = new Circle();
        circle.centerXProperty().bind(point.uProperty().multiply(scale).add(widthProperty().divide(2)));
        circle.centerYProperty().bind(point.vProperty().multiply(scale).add(heightProperty().divide(2)));
        circle.radiusProperty().bind(r);
        circle.setFill(color);
        return circle;
    }

    public void moveSelected(double x, double y) {
        //TODO
    }
}
