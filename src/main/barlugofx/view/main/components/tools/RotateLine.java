package barlugofx.view.main.components.tools;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * A component that generates a RotateLine.
 * It creates a line with start and end point.
 */
public final class RotateLine implements ComplexNode {
    //constant fields
    private static final int LINE_WIDTH = 2;
    private static final int CIRCLE_WIDTH = 4;
    private static final int LABEL_SHIFTER = 30;
    private static final Paint LIGHT_BLUE_COLOR = Color.web("rgb(90,255,208)");
    private final Line line;
    private final Circle start;
    private final Circle end;
    private final Circle circle;
    private final Label degrees;
    private double angle;
    /**
     * The class constructor.
     * @param startX the start X coordinate
     * @param startY the start Y coordinate
     * @param endX the end X coordinate
     * @param endY the end Y coordinate
     */
    public RotateLine(final double startX, final double startY, final double endX, final double endY) {
        line = createLine(startX, startY, endX, endY);
        start = createPoint(startX, startY);
        end = createPoint(endX, endY);
        degrees = new Label();
        degrees.setTextFill(LIGHT_BLUE_COLOR);
        circle = new Circle();
        circle.setStroke(LIGHT_BLUE_COLOR);
        circle.setFill(Color.BLACK);
    }
    /**
     * Returns the line.
     * @return the line
     */
    public Line getLine() {
        return line;
    }
    /**
     * Returns the starting point.
     * @return the start point
     */
    public Circle getStartPoint() {
        return start;
    }
    /**
     * Returns the ending point.
     * @return the end point
     */
    public Circle getEndPoint() {
        return end;
    }
    /**
     * Returns the line angle.
     * @return the line angle
     */
    public double getAngle() {
        return angle;
    }
    /**
     * Moves the line to (x, y).
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void move(final double x, final double y) {
        line.setEndX(x);
        line.setEndY(y);
        end.setCenterX(x);
        end.setCenterY(y);
        degrees.setLayoutX(x - LABEL_SHIFTER);
        degrees.setLayoutY(y - LABEL_SHIFTER);
        degrees.setText(String.format("%1$,.2f°", angle));
        circle.setRadius(degrees.getWidth() / 2 + 1);
        circle.setCenterX(degrees.getLayoutX() + degrees.getWidth() / 2);
        circle.setCenterY(degrees.getLayoutY() + degrees.getHeight() / 2);
        angle = computeAngle(start.getCenterX(), start.getCenterY(), x, y);
    }
    @Override
    public void addToPane(final Pane pane) {
        pane.getChildren().add(line);
        pane.getChildren().add(start);
        pane.getChildren().add(end);
        pane.getChildren().add(circle);
        pane.getChildren().add(degrees);
    }
    @Override
    public void removeFromPane(final Pane pane) {
        pane.getChildren().remove(line);
        pane.getChildren().remove(start);
        pane.getChildren().remove(end);
        pane.getChildren().remove(circle);
        pane.getChildren().remove(degrees);
    }
    private static Circle createPoint(final double x, final double y) {
        final Circle c = new Circle(x, y, CIRCLE_WIDTH);
        c.setStroke(Color.WHITE);
        c.setFill(Color.BLACK);
        return c;
    }
    private static Line createLine(final double x1, final double y1, final double x2, final double y2) {
        final Line l = new Line(x1, y1, x2, y2);
        l.setStroke(Color.WHITE);
        l.setStrokeWidth(LINE_WIDTH);
        return l;
    }
    private double computeAngle(final double x1, final double y1, final double x2, final double y2) {
        double m;
        if (y2 < start.getCenterY()) {
            m = Math.abs(y2 - y1) / (x2 - x1);
        } else if (x2 < x1) {
            m = (y2 - y1) / Math.abs(x2 - x1);
        } else {
            m = -(y2 - y1) / (x2 - x1);
        }
        return Math.atan(m) * (180 / Math.PI);
    }
}
