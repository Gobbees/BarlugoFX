package barlugofx.view.main;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


/**
 * A component that generates a draggable, resizable CropArea.
 * It creates a rectangle with the two thirds photography rule scheme and all the draggable points.
 */
public final class CropArea implements ComplexNode {
    //constant sizes
    private static final int RECTANGLE_WIDTH = 2;
    private static final int CIRCLE_WIDTH = 4;
    private static final int LINE_WIDTH = 1;
    //
    private final Rectangle rectangle;
    private final Circle topLeft;
    private final Circle topRight;
    private final Circle bottomLeft;
    private final Circle bottomRight;
    private final Circle midTop;
    private final Circle midRight;
    private final Circle midBottom;
    private final Circle midLeft;
    private final Line vertOne;
    private final Line vertTwo;
    private final Line horiOne;
    private final Line horiTwo;
    /**
     * The class constructor that initiates all the components.
     * @param width the rectangle starting width
     * @param height the rectangle starting height
     * @param startX the start X coordinate (of the top left corner)
     * @param startY the start Y coordinate (of the top left corner)
     */
    public CropArea(final double width, final double height, final double startX, final double startY) {
        //rectangle init
        rectangle = new Rectangle(startX, startY, width, height);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(RECTANGLE_WIDTH);
        //corners init
        topLeft = createPoint(startX, startY, Cursor.NW_RESIZE);
        topRight = createPoint(startX + width, startY, Cursor.NE_RESIZE);
        bottomLeft = createPoint(startX, startY + height, Cursor.SW_RESIZE);
        bottomRight = createPoint(startX + width, startY + height, Cursor.SE_RESIZE);
        //mid points init
        midTop = createPoint(startX + width / 2, startY, Cursor.N_RESIZE);
        midRight = createPoint(startX + width, startY + height / 2, Cursor.E_RESIZE);
        midBottom = createPoint(startX + width / 2, startY + height, Cursor.S_RESIZE);
        midLeft = createPoint(startX, startY + height / 2, Cursor.W_RESIZE);
        //two thirds lines init
        vertOne = createLine(startX + width / 3, startY, startX + width / 3, startY + height);
        vertTwo = createLine(startX + (2 * width) / 3, startY, startX + (2 * width) / 3, startY + height);
        horiOne = createLine(startX, startY + height / 3, startX + width, startY + height / 3);
        horiTwo = createLine(startX, startY + (2 * height) / 3, startX + width, startY + (2 * height) / 3);
    }
    /**
     * Returns the rectangle component.
     * @return the rectangle component
     */
    public Rectangle getRectangle() {
        return rectangle;
    }
    /**
     * Returns the top left point.
     * @return the top left point
     */
    public Circle getTopLeftPoint() {
        return topLeft;
    }
    /**
     * Returns the top right point.
     * @return the top right point
     */
    public Circle getTopRightPoint() {
        return topRight;
    }
    /**
     * Returns the bottom left point.
     * @return the bottom left point
     */
    public Circle getBottomLeftPoint() {
        return bottomLeft;
    }
    /**
     * Returns the bottom right point.
     * @return the bottom right point
     */
    public Circle getBottomRightPoint() {
        return bottomRight;
    }
    /**
     * Returns the mid top point.
     * @return the mid top point
     */
    public Circle getMidTopPoint() {
        return midTop;
    }
    /**
     * Returns the mid right point.
     * @return the mid right point
     */
    public Circle getMidRightPoint() {
        return midRight;
    }
    /**
     * Returns the mid bottom point.
     * @return the mid bottom point
     */
    public Circle getMidBottomPoint() {
        return midBottom;
    }
    /**
     * Returns the mid left point.
     * @return the mid left point
     */
    public Circle getMidLeftPoint() {
        return midLeft;
    }

    /**
     * Adds an event to a component.
     * @param node the component whom the event will be added
     * @param etype the event type
     * @param event the eventhandler
     * @param <T> the event
     */
    public <T extends Event> void addEvent(final Node node, final EventType<T> etype, final EventHandler<? super T> event) {
        node.addEventHandler(etype, event);
    }
    /**
     * Updates the CropArea position.
     * @param startX the start X coordinate 
     * @param startY the start Y coordinate 
     * @param endX the end X coordinate
     * @param endY the end Y coordinate
     */
    public void drag(final double startX, final double startY, final double endX, final double endY) {
        //rectangle
        rectangle.setX(rectangle.getX() + endX - startX);
        rectangle.setY(rectangle.getY() + endY - startY);
        //corners
        setPointPosition(topLeft, rectangle.getX(), rectangle.getY());
        setPointPosition(topRight, rectangle.getX() + rectangle.getWidth(), rectangle.getY());
        setPointPosition(bottomRight, rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight());
        setPointPosition(bottomLeft, rectangle.getX(), rectangle.getY() + rectangle.getHeight());
        //mid points
        setPointPosition(midTop, rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY());
        setPointPosition(midRight, rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight() / 2);
        setPointPosition(midBottom, rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight());
        setPointPosition(midLeft, rectangle.getX(), rectangle.getY() + rectangle.getHeight() / 2);
        //two thirds lines
        setLinePosition(vertOne, rectangle.getX() + rectangle.getWidth() / 3, rectangle.getY(), 
                rectangle.getX() + rectangle.getWidth() / 3, rectangle.getY() + rectangle.getHeight());
        setLinePosition(vertTwo, rectangle.getX() + (2 * rectangle.getWidth()) / 3, rectangle.getY(), 
                rectangle.getX() + (2 * rectangle.getWidth()) / 3, rectangle.getY() + rectangle.getHeight());
        setLinePosition(horiOne, rectangle.getX(), rectangle.getY() + rectangle.getHeight() / 3, 
                rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight() / 3);
        setLinePosition(horiTwo, rectangle.getX(), rectangle.getY() + (2 * rectangle.getHeight()) / 3, 
                rectangle.getX() + rectangle.getWidth(), rectangle.getY() + (2 * rectangle.getHeight()) / 3);
    }
    /**
     * Permits the resize of the crop area.
     * @param x1 the X coordinate of the top left corner
     * @param y1 the Y coordinate of the top left corner
     * @param x2 the X coordinate of the bottom right corner
     * @param y2 the Y coordinate of the bottom right corner
     */
    public void resize(final double x1, final double y1, final double x2, final double y2) {
        //rectangle resize
        rectangle.setWidth(Math.abs(x2 - x1));
        rectangle.setHeight(Math.abs(y2 - y1));
        rectangle.setX(x1);
        rectangle.setY(y1);
        //corners
        setPointPosition(topLeft, x1, y1);
        setPointPosition(topRight, x1 + rectangle.getWidth(), y1);
        setPointPosition(bottomRight, x1 + rectangle.getWidth(), y1 + rectangle.getHeight());
        setPointPosition(bottomLeft, x1, y1 + rectangle.getHeight());
        //mid points
        setPointPosition(midTop, rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY());
        setPointPosition(midRight, rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight() / 2);
        setPointPosition(midBottom, rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight());
        setPointPosition(midLeft, rectangle.getX(), rectangle.getY() + rectangle.getHeight() / 2);
        //two thirds lines
        setLinePosition(vertOne, rectangle.getX() + rectangle.getWidth() / 3, rectangle.getY(), 
                rectangle.getX() + rectangle.getWidth() / 3, rectangle.getY() + rectangle.getHeight());
        setLinePosition(vertTwo, rectangle.getX() + (2 * rectangle.getWidth()) / 3, rectangle.getY(), 
                rectangle.getX() + (2 * rectangle.getWidth()) / 3, rectangle.getY() + rectangle.getHeight());
        setLinePosition(horiOne, rectangle.getX(), rectangle.getY() + rectangle.getHeight() / 3, 
                rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight() / 3);
        setLinePosition(horiTwo, rectangle.getX(), rectangle.getY() + (2 * rectangle.getHeight()) / 3, 
                rectangle.getX() + rectangle.getWidth(), rectangle.getY() + (2 * rectangle.getHeight()) / 3);
    }
    @Override
    public void addToPane(final Pane pane) {
        pane.getChildren().add(rectangle);
        pane.getChildren().add(vertOne);
        pane.getChildren().add(vertTwo);
        pane.getChildren().add(horiOne);
        pane.getChildren().add(horiTwo);
        pane.getChildren().add(topLeft);
        pane.getChildren().add(topRight);
        pane.getChildren().add(bottomLeft);
        pane.getChildren().add(bottomRight);
        pane.getChildren().add(midTop);
        pane.getChildren().add(midRight);
        pane.getChildren().add(midBottom);
        pane.getChildren().add(midLeft);
    }
    @Override
    public void removeFromPane(final Pane pane) {
        pane.getChildren().remove(rectangle);
        pane.getChildren().remove(vertOne);
        pane.getChildren().remove(vertTwo);
        pane.getChildren().remove(horiOne);
        pane.getChildren().remove(horiTwo);
        pane.getChildren().remove(topLeft);
        pane.getChildren().remove(topRight);
        pane.getChildren().remove(bottomLeft);
        pane.getChildren().remove(bottomRight);
        pane.getChildren().remove(midTop);
        pane.getChildren().remove(midRight);
        pane.getChildren().remove(midBottom);
        pane.getChildren().remove(midLeft);
    }
    private static Circle createPoint(final double x, final double y, final Cursor cursor) {
        final Circle c = new Circle(x, y, CIRCLE_WIDTH);
        c.setStroke(Color.WHITE);
        c.setFill(Color.BLACK);
        c.setCursor(cursor);
        return c;
    }
    private static Line createLine(final double x1, final double y1, final double x2, final double y2) {
        Line l = new Line(x1, y1, x2, y2);
        l.setStroke(Color.WHITE);
        l.setStrokeWidth(LINE_WIDTH);
        return l;
    }
    private void setPointPosition(final Circle point, final double x, final double y) {
        point.setCenterX(x);
        point.setCenterY(y);
    }
    private void setLinePosition(final Line line, final double x1, final double y1, final double x2, final double y2) {
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
    }
}
