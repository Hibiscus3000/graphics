package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private Circle selectedAnchorPoint;
    private final List<Circle> splitPointsCircles = new ArrayList<>();

    private ColorContainer colorContainer = new ColorContainer();
    private final Map<String, ColorHandler> colorHandlers = new HashMap<>();

    private final DoubleProperty mainAxesStrokeWidth = new SimpleDoubleProperty(2);

    private final DoubleProperty generatrixStrokeWidth = new SimpleDoubleProperty(2);
    private final DoubleProperty splineStrokeWidth = new SimpleDoubleProperty(3);

    private final DoubleProperty anchorPointROnRollover = new SimpleDoubleProperty(10.0);
    private final DoubleProperty anchorPointR = new SimpleDoubleProperty(6.0);
    private final DoubleProperty splitPointR = new SimpleDoubleProperty(4.0);

    public BSplineEditor() {
        addColorHandlers();
        createCoordinatePlane();
        colorHandlers.get("backgroundColor").setDefault();
        setOnMousePressed(e -> deselectAnchorPoint());
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

        colorHandlers.get("mainAxisColor").setDefault();
        getChildren().addAll(xAxis, yAxis);
    }

    private void addColorHandlers() {
        colorHandlers.put("backgroundColor", new ColorHandler("backgroundColor", Color.WHITE, colorContainer,
                color -> setBackground(new Background(new BackgroundFill(color, null, null)))));
        colorHandlers.put("mainAxisColor", new ShapeColorHandler<>("mainAxisColor",
                Color.BLACK, colorContainer, mainAxes));
        colorHandlers.put("secondaryAxisColor", new ShapeColorHandler<>("secondaryAxisColor",
                Color.rgb(200, 200, 200), colorContainer, secondaryAxes));
        colorHandlers.put("splineLineColor", new ShapeColorHandler<>("splineLineColor",
                Color.RED, colorContainer, splineLines));
        colorHandlers.put("generatrixColor", new ShapeColorHandler<>("generatrixColor",
                Color.BLUE, colorContainer, generatrixLines));
        colorHandlers.put("anchorPointColor", new ShapeColorHandler<>("anchorPointColor",
                Color.ORANGE, colorContainer, anchorPointsCircles));
        colorHandlers.put("selectedAnchorPointColor", new ColorHandler("selectedAnchorPointColor",
                Color.GREEN, colorContainer, color -> {
            if (null != selectedAnchorPoint) {
                selectedAnchorPoint.setStroke(color);
                selectedAnchorPoint.setFill(color);
            }
        }));
        colorHandlers.put("splitPointColor", new ShapeColorHandler<>("splitPointColor",
                Color.ORANGE, colorContainer, splitPointsCircles));
        colorHandlers.put("serif", new ShapeColorHandler<>("serif",
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
            addAnchorPointCircle(anchorPoints.get(i));
        }

        for (List<Point> line : spline.getSplineLines()) {
            for (int i = 1; i < line.size(); ++i) {
                Line splineLine = createLine(line.get(i - 1), line.get(i),
                        colorContainer.getColor("splineLineColor"),
                        splineStrokeWidth);
                splineLines.add(splineLine);
            }
        }
        repaint();
    }

    private void addAnchorPointCircle(Point point) {
        Circle anchorPointsCircle = createCircle(point, anchorPointR,
                colorContainer.getColor("anchorPointColor"));
        anchorPointsCircle.setOnMousePressed(e -> {
            selectAnchorPoint(anchorPointsCircle);
            anchorPointsCircle.radiusProperty().bind(anchorPointR);
            e.consume();
        });
        anchorPointsCircle.setOnMouseReleased(e -> {
            if (Math.pow(e.getX() - anchorPointsCircle.getCenterX(),2) +
                    Math.pow(e.getY() - anchorPointsCircle.getCenterY(),2) > Math.pow(anchorPointROnRollover.get(),2)) {
                anchorPointsCircle.radiusProperty().bind(anchorPointR);
            } else {
                anchorPointsCircle.radiusProperty().bind(anchorPointROnRollover);
            }
        });
        anchorPointsCircle.setOnMouseDragged(e -> {
            point.uProperty().set((e.getX() - widthProperty().get() / 2) / scale.get());
            point.vProperty().set((e.getY() - heightProperty().get() / 2) / scale.get());
        });
        anchorPointsCircle.setOnMouseEntered(e -> {
            if (!e.isPrimaryButtonDown()) {
                anchorPointsCircle.radiusProperty().bind(anchorPointROnRollover);
            } else {
                anchorPointsCircle.radiusProperty().bind(anchorPointR);
            }
        });
        anchorPointsCircle.setOnMouseExited(e -> anchorPointsCircle.radiusProperty().bind(anchorPointR));
        anchorPointsCircles.add(anchorPointsCircle);
    }

    private void repaint() {
        getChildren().removeAll(anchorPointsCircles);
        getChildren().removeAll(generatrixLines);
        getChildren().removeAll(splineLines);
        getChildren().addAll(generatrixLines);
        getChildren().addAll(splineLines);
        getChildren().addAll(anchorPointsCircles);
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

    private void selectAnchorPoint(Circle anchorPointCircle) {
        deselectAnchorPoint();
        Color selectedAPColor = colorContainer.getColor("selectedAnchorPointColor");
        selectedAnchorPoint = anchorPointCircle;
        selectedAnchorPoint.setFill(selectedAPColor);
        selectedAnchorPoint.setStroke(selectedAPColor);
    }

    private void deselectAnchorPoint() {
        if (null != selectedAnchorPoint) {
            Color anchorPointColor = colorContainer.getColor("anchorPointColor");
            selectedAnchorPoint.setStroke(anchorPointColor);
            selectedAnchorPoint.setFill(anchorPointColor);
        }
    }

    public void moveSelected(double x, double y) {
        //TODO
    }
}
