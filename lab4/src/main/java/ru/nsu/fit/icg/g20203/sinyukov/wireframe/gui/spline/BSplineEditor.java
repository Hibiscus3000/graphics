package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.spline;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.MouseButton;
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

    public final static double scaleStep = 0.01;
    public final static double scaleMin = 0.01;
    public final static double scaleMax = 10;
    private final DoubleProperty scale = new SimpleDoubleProperty(0.5);
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

    private Double prevU = null;
    private Double prevV = null;

    public BSplineEditor() {
        addColorHandlers();
        createMainAxes();
        colorHandlers.get("backgroundColor").setDefault();
        setOnMousePressed(e -> deselectAnchorPoint());
        widthProperty().addListener((observable, oldVal, newVal) -> {
            double mult = newVal.doubleValue() / oldVal.doubleValue();
            if (Double.isFinite(mult)) {
                scale.set(scale.get() * mult);
            }
        });

        setOnMouseDragged(e -> {
            if (MouseButton.SECONDARY == e.getButton()) {
                if (null != prevU && null != prevV)
                    moveVision(prevU - e.getX(), prevV - e.getY());
                prevU = e.getX();
                prevV = e.getY();
            }
        });
        setOnMouseReleased(e -> {
            prevU = null;
            prevV = null;
        });

        setOnScroll(e -> {
            double scaleChange = scaleStep * e.getDeltaY();
            if (changeScale(scaleChange)) {
                //TODO
                double scaleVal = scale.get();
                double du = (e.getX() - getWidth() / 2) * scaleChange / scaleVal;
                double dv = (e.getY() - getHeight() / 2) * scaleChange / scaleVal;
                System.out.println("(" + du + ", " + dv + ")");
                moveVision(du, dv);
            }
        });
    }

    private void createMainAxes() {
        Line uAxis = new Line();
        uAxis.setStartX(0);
        uAxis.startYProperty().bind(heightProperty().divide(2)
                .add(center.vProperty().multiply(scale)));
        uAxis.endXProperty().bind(widthProperty());
        uAxis.endYProperty().bind(heightProperty().divide(2)
                .add(center.vProperty().multiply(scale)));
        uAxis.strokeWidthProperty().bind(mainAxesStrokeWidth);

        Line vAxis = new Line();
        vAxis.startXProperty().bind(widthProperty().divide(2)
                .subtract(center.uProperty().multiply(scale)));
        vAxis.setStartY(0);
        vAxis.endXProperty().bind(widthProperty().divide(2)
                .subtract(center.uProperty().multiply(scale)));
        vAxis.endYProperty().bind(heightProperty());
        vAxis.strokeWidthProperty().bind(mainAxesStrokeWidth);

        mainAxes.add(uAxis);
        mainAxes.add(vAxis);

        colorHandlers.get("mainAxisColor").setDefault();
        getChildren().addAll(uAxis, vAxis);
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

    private boolean changeScale(double scaleChange) {
        double newScale = scale.get() + scaleChange;
        boolean scaleChanged = newScale > scaleMin && newScale < scaleMax;
        scale.set(Math.max(scaleMin, Math.min(newScale, scaleMax)));
        return scaleChanged;
    }

    private void moveVision(double du, double dv) {
        center.uProperty().set(center.uProperty().get() + du / scale.get());
        center.vProperty().set(center.vProperty().get() - dv / scale.get());
    }

    private void selectAnchorPoint() {
        //TODO
    }

    private void deleteAnchorPoint() {
        //TODO
    }

    public void setSpline(Spline spline) {
        clear();
        this.spline = spline;
        List<Point> anchorPoints = spline.getAnchorPoints();
        if (!anchorPoints.isEmpty()) {
            addAnchorPointCircle(anchorPoints.get(0));
        }
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
            if (Math.pow(e.getX() - anchorPointsCircle.getCenterX(), 2) +
                    Math.pow(e.getY() - anchorPointsCircle.getCenterY(), 2) > Math.pow(anchorPointROnRollover.get(), 2)) {
                anchorPointsCircle.radiusProperty().bind(anchorPointR);
            } else {
                anchorPointsCircle.radiusProperty().bind(anchorPointROnRollover);
            }
        });
        anchorPointsCircle.setOnMouseDragged(e -> {
            point.uProperty().set((e.getX() - widthProperty().get() / 2)
                    / scale.get() + center.uProperty().get());
            point.vProperty().set((e.getY() - heightProperty().get() / 2)
                    / scale.get() + center.vProperty().get());
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

    private void clear() {
        deselectAnchorPoint();
        generatrixLines.clear();
        splineLines.clear();
        anchorPointsCircles.clear();
    }

    private void repaint() {
        getChildren().clear();
        getChildren().addAll(mainAxes);
        getChildren().addAll(generatrixLines);
        getChildren().addAll(splineLines);
        getChildren().addAll(anchorPointsCircles);
    }

    private Line createLine(Point p1, Point p2, Color color, DoubleProperty strokeWidth) {
        Line line = new Line();
        line.startXProperty().bind(p1.uProperty().multiply(scale)
                .add(widthProperty().divide(2)).subtract(center.uProperty().multiply(scale)));
        line.endXProperty().bind(p2.uProperty().multiply(scale)
                .add(widthProperty().divide(2)).subtract(center.uProperty().multiply(scale)));
        line.startYProperty().bind(p1.vProperty().multiply(scale)
                .add(heightProperty().divide(2)).add(center.vProperty().multiply(scale)));
        line.endYProperty().bind(p2.vProperty().multiply(scale)
                .add(heightProperty().divide(2)).add(center.vProperty().multiply(scale)));
        line.strokeWidthProperty().bind(strokeWidth);
        line.setFill(color);
        line.setStroke(color);
        return line;
    }

    private Circle createCircle(Point point, DoubleProperty r, Color color) {
        Circle circle = new Circle();
        circle.centerXProperty().bind(point.uProperty().multiply(scale)
                .add(widthProperty().divide(2).subtract(center.uProperty().multiply(scale))));
        circle.centerYProperty().bind(point.vProperty().multiply(scale)
                .add(heightProperty().divide(2).add(center.vProperty().multiply(scale))));
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

    public void selectAnchorPoint(int selectedAPIndex) {
        if (selectedAPIndex >= 0 && anchorPointsCircles.size() > selectedAPIndex) {
            selectAnchorPoint(anchorPointsCircles.get(selectedAPIndex));
        }
    }

    private void deselectAnchorPoint() {
        if (null != selectedAnchorPoint) {
            Color anchorPointColor = colorContainer.getColor("anchorPointColor");
            selectedAnchorPoint.setStroke(anchorPointColor);
            selectedAnchorPoint.setFill(anchorPointColor);
            selectedAnchorPoint = null;
        }
    }

    public void moveSelected(double x, double y) {
        //TODO
    }

    public int getSelectedAPId() {
        return anchorPointsCircles.indexOf(selectedAnchorPoint);
    }

    public DoubleProperty scaleProperty() {
        return scale;
    }

    public DoubleProperty uCenterProperty() {
        return center.uProperty();
    }

    public DoubleProperty vCenterProperty() {
        return center.vProperty();
    }
}
