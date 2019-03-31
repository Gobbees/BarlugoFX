package barlugofx.view.main;

import java.util.function.Function;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class CropArea {
    private static final int RECTANGLE_WIDTH = 2;
    private static final int CIRCLE_WIDTH = 4;
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
    

    public CropArea(int width, int height, int startX, int startY) {
        rectangle = new Rectangle(startX, startY, width, height);
        rectangle.setStroke(Color.WHITE);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(2);
        topLeft = new Circle(startX, startY, CIRCLE_WIDTH, Color.WHITE);
        topLeft.setCursor(Cursor.NW_RESIZE);
        topRight = new Circle(startX + width, startY, CIRCLE_WIDTH, Color.WHITE);
        topRight.setCursor(Cursor.NE_RESIZE);
        bottomLeft = new Circle(startX, startY + height, CIRCLE_WIDTH, Color.WHITE);
        bottomLeft.setCursor(Cursor.SW_RESIZE);
        bottomRight = new Circle(startX + height, startY + width, CIRCLE_WIDTH, Color.WHITE);
        bottomRight.setCursor(Cursor.SE_RESIZE);
        midTop = new Circle(startX + width / 2, startY, CIRCLE_WIDTH, Color.WHITE);
        midTop.setCursor(Cursor.N_RESIZE);
        midRight = new Circle(startX + width, startY + height / 2, CIRCLE_WIDTH, Color.WHITE);
        midRight.setCursor(Cursor.E_RESIZE);
        midBottom = new Circle(startX + width / 2, startY + height, CIRCLE_WIDTH, Color.WHITE);
        midBottom.setCursor(Cursor.S_RESIZE);
        midLeft = new Circle(startX, startY + height / 2, CIRCLE_WIDTH, Color.WHITE);
        midLeft.setCursor(Cursor.W_RESIZE);
        vertOne = new Line(startX + width / 3, startY, startX + width / 3, startY + height);
        vertOne.setStroke(Color.WHITE);
        vertOne.setStrokeWidth(1);
        vertTwo = new Line(startX + (2 * width) / 3, startY, startX + (2 * width) / 3, startY + height);
        vertTwo.setStroke(Color.WHITE);
        vertTwo.setStrokeWidth(1);
        horiOne = new Line(startX, startY + height / 3, startX + width, startY + height / 3);
        horiOne.setStroke(Color.WHITE);
        horiOne.setStrokeWidth(1);
        horiTwo = new Line(startX, startY + (2 * height) / 3, startX + width, startY + (2 * height) / 3);
        horiTwo.setStroke(Color.WHITE);
        horiTwo.setStrokeWidth(1);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Circle getTopLeftPoint() {
        return topLeft;
    }

    public Circle getTopRightPoint() {
        return topRight;
    }

    public Circle getBottomLeftPoint() {
        return bottomLeft;
    }

    public Circle getBottomRightPoint() {
        return bottomRight;
    }
    public Circle getMidTopPoint() {
        return midTop;
    }

    public Circle getMidRightPoint() {
        return midRight;
    }

    public Circle getMidBottomPoint() {
        return midBottom;
    }

    public Circle getMidLeftPoint() {
        return midLeft;
    }
    public void addEvent(Node node, EventType<MouseEvent> etype, EventHandler<MouseEvent> event) {
        node.addEventHandler(etype, event);
    }
    public void drag(final double startX, final double startY, final double endX, final double endY) {
        rectangle.setX(rectangle.getX() + endX - startX);
        rectangle.setY(rectangle.getY() + endY - startY);
        topLeft.setCenterX(rectangle.getX());
        topLeft.setCenterY(rectangle.getY());
        topRight.setCenterX(rectangle.getX() + rectangle.getWidth());
        topRight.setCenterY(rectangle.getY());
        bottomLeft.setCenterX(rectangle.getX());
        bottomLeft.setCenterY(rectangle.getY() + rectangle.getHeight());
        bottomRight.setCenterX(rectangle.getX() + rectangle.getWidth());
        bottomRight.setCenterY(rectangle.getY() + rectangle.getHeight());
        
        midTop.setCenterX(rectangle.getX() + rectangle.getWidth() / 2);
        midTop.setCenterY(rectangle.getY());
        
        midRight.setCenterX(rectangle.getX() + rectangle.getWidth());
        midRight.setCenterY(rectangle.getY() + rectangle.getHeight() / 2);
        
        midBottom.setCenterX(rectangle.getX() + rectangle.getWidth() / 2);
        midBottom.setCenterY(rectangle.getY() + rectangle.getHeight());
        
        midLeft.setCenterX(rectangle.getX());
        midLeft.setCenterY(rectangle.getY() + rectangle.getHeight() / 2);
        
        vertOne.setStartX(rectangle.getX() + rectangle.getWidth() / 3);
        vertOne.setStartY(rectangle.getY());
        vertOne.setEndX(rectangle.getX() + rectangle.getWidth() / 3);
        vertOne.setEndY(rectangle.getY() + rectangle.getHeight());
        
        vertTwo.setStartX(rectangle.getX() + (2 * rectangle.getWidth()) / 3);
        vertTwo.setStartY(rectangle.getY());
        vertTwo.setEndX(rectangle.getX() + (2 * rectangle.getWidth()) / 3);
        vertTwo.setEndY(rectangle.getY() + rectangle.getHeight());
        
        horiOne.setStartX(rectangle.getX());
        horiOne.setStartY(rectangle.getY() + rectangle.getHeight() / 3);
        horiOne.setEndX(rectangle.getX() + rectangle.getWidth());
        horiOne.setEndY(rectangle.getY() + rectangle.getHeight() / 3);
        
        horiTwo.setStartX(rectangle.getX());
        horiTwo.setStartY(rectangle.getY() + (2 * rectangle.getHeight()) / 3);
        horiTwo.setEndX(rectangle.getX() + rectangle.getWidth());
        horiTwo.setEndY(rectangle.getY() + (2 * rectangle.getHeight()) / 3);
    }
    public void resize(double x1, double y1, double x2, double y2) {
        rectangle.setWidth(Math.abs(x2 - x1));
        rectangle.setHeight(Math.abs(y2 - y1));
        rectangle.setX(x1);
        rectangle.setY(y1);
        topLeft.setCenterX(x1);
        topLeft.setCenterY(y1);
        topRight.setCenterX(x1 + rectangle.getWidth());
        topRight.setCenterY(y1);
        bottomLeft.setCenterX(x1);
        bottomLeft.setCenterY(y1 + rectangle.getHeight());
        bottomRight.setCenterX(x1 + rectangle.getWidth());
        bottomRight.setCenterY(y1 + rectangle.getHeight());
        
        midTop.setCenterX(rectangle.getX() + rectangle.getWidth() / 2);
        midTop.setCenterY(rectangle.getY());
        
        midRight.setCenterX(rectangle.getX() + rectangle.getWidth());
        midRight.setCenterY(rectangle.getY() + rectangle.getHeight() / 2);
        
        midBottom.setCenterX(rectangle.getX() + rectangle.getWidth() / 2);
        midBottom.setCenterY(rectangle.getY() + rectangle.getHeight());
        
        midLeft.setCenterX(rectangle.getX());
        midLeft.setCenterY(rectangle.getY() + rectangle.getHeight() / 2);
        
        vertOne.setStartX(rectangle.getX() + rectangle.getWidth() / 3);
        vertOne.setStartY(rectangle.getY());
        vertOne.setEndX(rectangle.getX() + rectangle.getWidth() / 3);
        vertOne.setEndY(rectangle.getY() + rectangle.getHeight());
        vertTwo.setStartX(rectangle.getX() + (2 * rectangle.getWidth()) / 3);
        vertTwo.setStartY(rectangle.getY());
        vertTwo.setEndX(rectangle.getX() + (2 * rectangle.getWidth()) / 3);
        vertTwo.setEndY(rectangle.getY() + rectangle.getHeight());
        horiOne.setStartX(rectangle.getX());
        horiOne.setStartY(rectangle.getY() + rectangle.getHeight() / 3);
        horiOne.setEndX(rectangle.getX() + rectangle.getWidth());
        horiOne.setEndY(rectangle.getY() + rectangle.getHeight() / 3);
        
        horiTwo.setStartX(rectangle.getX());
        horiTwo.setStartY(rectangle.getY() + (2 * rectangle.getHeight()) / 3);
        horiTwo.setEndX(rectangle.getX() + rectangle.getWidth());
        horiTwo.setEndY(rectangle.getY() + (2 * rectangle.getHeight()) / 3);
    }
    

    public void addInPane(Pane pane) {
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
    public void removeFromPane(Pane pane) {
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
}
