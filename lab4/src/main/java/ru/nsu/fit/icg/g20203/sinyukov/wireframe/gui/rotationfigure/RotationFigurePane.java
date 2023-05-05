package ru.nsu.fit.icg.g20203.sinyukov.wireframe.gui.rotationfigure;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.MVP;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.MVPRotationFigure;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.RotationFigure;
import ru.nsu.fit.icg.g20203.sinyukov.wireframe.rotationfigure.point.Point2D;

import java.util.ArrayList;
import java.util.List;

public class RotationFigurePane extends Pane {

    private final DoubleProperty strokeWidth = new SimpleDoubleProperty(1.5);
    private final MVP mvp;
    private final List<Line> lines = new ArrayList<>();

    private RotationFigure rotationFigure;

    private final DoubleBinding Sh;
    private final DoubleBinding Sw;

    private final static double zoomStep = 0.03;
    private final static double angleStep = 0.02;

    private Double prevDragU = null;
    private Double prevDragV = null;

    public RotationFigurePane(MVP mvp) {
        Sh = Bindings.createDoubleBinding(() -> 0.0025);
        Sw = Bindings.createDoubleBinding(() -> Sh.get() * getHeight() / getWidth(),
                widthProperty(), heightProperty(), Sh);
        setOnScroll(e -> {
            mvp.changeN(-e.getDeltaY() * zoomStep);
        });

        setOnMouseDragged(e -> {
            e.consume();
            if (MouseButton.SECONDARY != e.getButton()) {
                return;
            }
            double u = e.getX();
            double v = e.getY();
            if (null != prevDragU && null != prevDragV) {
                double du = u - prevDragU;
                double dv = v - prevDragV;
//                double directionAng = Math.getAngle(java.lang.Math.abs(du), java.lang.Math.abs(dv));
//                double dx = directionAng - mvp.getAngleX() - java.lang.Math.PI / 2;
                mvp.changeAngle(0, -dv * angleStep, du * angleStep);
            }
            prevDragU = u;
            prevDragV = v;
        });
        setOnMouseReleased(e -> {
            prevDragU = null;
            prevDragV = null;
        });
        this.mvp = mvp;
    }

    public void setRotationFigure(RotationFigure rotationFigure) {
        this.rotationFigure = rotationFigure;
        MVPRotationFigure mvpRotationFigure = new MVPRotationFigure(mvp, rotationFigure);
        lines.clear();
        getChildren().clear();
        for (List<Point2D> line : mvpRotationFigure.getLines()) {
            for (int i = 1; i < line.size(); ++i) {
                Point2D previousPoint = line.get(i - 1);
                Point2D currentPoint = line.get(i);
                Line l = new Line();
                l.startXProperty().bind(widthProperty().divide(2)
                        .add(Bindings.createDoubleBinding(() -> previousPoint.getU() / Sw.get(), Sw)));
                l.endXProperty().bind(widthProperty().divide(2)
                        .add(Bindings.createDoubleBinding(() -> currentPoint.getU() / Sw.get(), Sw)));
                l.startYProperty().bind(heightProperty().divide(2)
                        .subtract(Bindings.createDoubleBinding(() -> previousPoint.getV() / Sh.get(), Sh)));
                l.endYProperty().bind(heightProperty().divide(2)
                        .subtract(Bindings.createDoubleBinding(() -> currentPoint.getV() / Sh.get(), Sh)));
                l.strokeWidthProperty().bind(strokeWidth);
                lines.add(l);
                getChildren().add(l);
            }
        }
        setLinesColor(color);
    }

    private Color color;

    public void setLinesColor(Color color) {
        if (null == color) {
            return;
        }
        this.color = color;
        for (Line line : lines) {
            line.setFill(color);
            line.setStroke(color);
        }
    }

    public void redraw() {
        setRotationFigure(rotationFigure);
        setLinesColor(color);
    }

    public void setBackgroundColor(Color color) {
        setBackground(new Background(new BackgroundFill(color, null, null)));
    }

    public DoubleProperty strokeWidthProperty() {
        return strokeWidth;
    }
}
