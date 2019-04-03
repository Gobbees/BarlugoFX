package barlugofx.view.main.utils;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    //
    private final Line line;
    private final Circle start;
    private final Circle end;
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
    public Circle getStart() {
        return start;
    }
    /**
     * Returns the ending point.
     * @return the end point
     */
    public Circle getEnd() {
        return end;
    }
    @Override
    public void addToPane(final Pane pane) {
        pane.getChildren().add(line);
        pane.getChildren().add(start);
        pane.getChildren().add(end);
    }
    @Override
    public void removeFromPane(final Pane pane) {
        pane.getChildren().remove(line);
        pane.getChildren().remove(start);
        pane.getChildren().remove(end);
    }
    private static Circle createPoint(final double x, final double y) {
        final Circle c = new Circle(x, y, CIRCLE_WIDTH);
        c.setStroke(Color.WHITE);
        c.setFill(Color.BLACK);
        return c;
    }
    private static Line createLine(final double x1, final double y1, final double x2, final double y2) {
        Line l = new Line(x1, y1, x2, y2);
        l.setStroke(Color.WHITE);
        l.setStrokeWidth(LINE_WIDTH);
        return l;
    }
}
