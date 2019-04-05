package barlugofx.view.main.components.zoompane;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

/**
 * 
 *
 */
public class ZoomableImageView extends ImageView {
    //it sets the zoom multiplier
    private static final double DELTA = 1.1;

    private final DoubleProperty zoomRatio;

    /**
     * 
     */
    public ZoomableImageView() {
        zoomRatio = new SimpleDoubleProperty(1.0);
        this.scaleXProperty().bind(zoomRatio);
        this.scaleYProperty().bind(zoomRatio);
    }
//     * Add a grid to the canvas, send it to back.
//     */
//    public void addGrid() {
//
//        double w = getBoundsInLocal().getWidth();
//        double h = getBoundsInLocal().getHeight();
//
//        // add grid
//        Canvas grid = new Canvas(w, h);
//
//        // don't catch mouse events
//        grid.setMouseTransparent(true);
//
//        GraphicsContext gc = grid.getGraphicsContext2D();
//
//        gc.setStroke(Color.GRAY);
//        gc.setLineWidth(1);
//
//        // draw grid lines
//        double offset = 50;
//        for( double i=offset; i < w; i+=offset) {
//            gc.strokeLine( i, 0, i, h);
//            gc.strokeLine( 0, i, w, i);
//        }
//
//        getChildren().add( grid);
//
//        grid.toBack();
//    }

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
        final double deltaX = (eventX - (this.getBoundsInParent().getWidth() / 2 + this.getBoundsInParent().getMinX()));
        final double deltaY = (eventY - (this.getBoundsInParent().getHeight() / 2 + this.getBoundsInParent().getMinY()));
        this.setTranslateX(this.getTranslateX() - diff * deltaX);
        this.setTranslateY(this.getTranslateY() - diff * deltaY);
        zoomRatio.set(newRatio);
    }
}
