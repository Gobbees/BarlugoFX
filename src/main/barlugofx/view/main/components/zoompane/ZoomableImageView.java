package barlugofx.view.main.components.zoompane;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

/**
 * A zoomable and draggable JavaFX ImageView.
 */
public class ZoomableImageView extends ImageView {
    //it sets the zoom multiplier
    private static final double DELTA = 1.1;
    private final DoubleProperty zoomRatio;
    private double initDragX;
    private double initDragY; 
    private double initTranslateX;
    private double initTranslateY;
    /**
     * The class constructor. It initiates the zoom ratio to ratio.
     * @param ratio the initial zoom ratio
     */
    public ZoomableImageView(final double ratio) {
        super();
        zoomRatio = new SimpleDoubleProperty(ratio);
        this.scaleXProperty().bind(zoomRatio);
        this.scaleYProperty().bind(zoomRatio);
        initDragX = 0;
        initDragY = 0;
        initTranslateX = 0;
        initTranslateY = 0;
    }
    /**
     * The class constructor. It initiates the zoom ratio to 1.0.
     * Change it via setZoomToValue function.
     */
    public ZoomableImageView() {
        this(1.0);
    }
    /**
     * Returns the zoom ratio.
     * @return the zoom ratio
     */
    public double getZoomRatio() {
        return zoomRatio.get();
    }

    /**
     * Sets the zoom ratio.
     * @param ratio the zoom ratio
     */
    public void setZoomRatio(final double ratio) {
        this.zoomRatio.set(ratio);
    }

    /**
     * Zooms the imageView.
     * @param direction the direction of the zoom
     * @param eventX the event x coordinate (mouse pointer)
     * @param eventY the event y coordinate (mouse pointer)
     */
    public void zoom(final ZoomDirection direction, final double eventX, final double eventY) {
        final double oldRatio = zoomRatio.get();
        double newRatio;
        if (direction == ZoomDirection.ZOOM_OUT) {
            newRatio = oldRatio / DELTA;
        } else {
            newRatio = oldRatio * DELTA;
        }
        final double diff = (newRatio / oldRatio) - 1;
        zoom(diff, eventX, eventY);
        zoomRatio.set(newRatio);
    }
    /**
     * Sets the zoom ratio to value.
     * @param value the zoom ratio
     */
    public void setZoomToValue(final double value) {
        final double oldRatio = zoomRatio.get();
        final double diff = (value / oldRatio) - 1;
        zoom(diff, this.getFitWidth(), this.getFitHeight());
        zoomRatio.set(value);
    }
    /**
     * Initializes the drag coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void initDrag(final double x, final double y) {
          initDragX = x;
          initDragY = y;
          initTranslateX = this.getTranslateX();
          initTranslateY = this.getTranslateY();
    }
    /**
     * Drags the image.
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void drag(final double x, final double y) {
        this.setTranslateX(initTranslateX + x - initDragX);
        this.setTranslateY(initTranslateY + y - initDragY);
    }
    private void zoom(final double diff, final double eventX, final double eventY) {
        final double deltaX = eventX - (this.getBoundsInParent().getWidth() / 2 + this.getBoundsInParent().getMinX());
        final double deltaY = eventY - (this.getBoundsInParent().getHeight() / 2 + this.getBoundsInParent().getMinY());
        this.setTranslateX(this.getTranslateX() - diff * deltaX);
        this.setTranslateY(this.getTranslateY() - diff * deltaY);
    }
}
