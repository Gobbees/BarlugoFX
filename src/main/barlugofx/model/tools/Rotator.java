package barlugofx.model.tools;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.model.tools.common.ImageToolImpl;
import barlugofx.model.tools.common.ParametersName;

/**
 * This class allows an Image to be Rotated. It accepts one parameter, ANGLE,
 * which must be an integer between -360 and +360.
 *
 */
public final class Rotator extends ImageToolImpl {
    private static final int MAX = 360;
    private static final int DEFAULT = 0;

    private Rotator() {
        super();
    }

    /**
     * This functions instantiate a new Rotate.
     *
     * @return the rotate instance.
     */
    public static Rotator createRotator() {
        return new Rotator();
    }

    @Override
    public Image applyFilter(final Image toApply) {
        final int degreesToRotate = super.getValueFromParameter(ParametersName.ANGLE, -MAX, MAX, DEFAULT);
        final BufferedImage src = ImageUtils.convertImageToBufferedImageWithAlpha(toApply);
        int width = src.getWidth();
        int height = src.getHeight();

        final AffineTransform transform = new AffineTransform();
        final double ang = Math.toRadians(degreesToRotate);
        transform.setToRotation(ang, width / 2d, height / 2d);

        // source image rectangle
        final Point[] points = {
                new Point(0, 0),
                new Point(width, 0),
                new Point(width, height),
                new Point(0, height)
        };

        // transform to destination rectangle
        transform.transform(points, 0, points, 0, 4);

        // find max point and min point so we can then calculate new width and heigth
        final Point min = new Point(points[0]);
        final Point max = new Point(points[0]);
        final int n = points.length;
        for (int i = 1; i < n; i++) {
            final Point p = points[i];
            final double pX = p.getX(), pY = p.getY();

            // update min/max x
            if (pX < min.getX()) {
                min.setLocation(pX, min.getY());
            }
            if (pX > max.getX()) {
                max.setLocation(pX, max.getY());
            }

            // update min/max y
            if (pY < min.getY()) {
                min.setLocation(min.getX(), pY);
            }
            if (pY > max.getY()) {
                max.setLocation(max.getX(), pY);
            }
        }

        // determine new width, height
        width = (int) (max.getX() - min.getX());
        height = (int) (max.getY() - min.getY());

        // determine required translation
        final double tx = min.getX();
        final double ty = min.getY();

        // append required translation
        final AffineTransform translation = new AffineTransform();
        translation.translate(-tx, -ty);
        transform.preConcatenate(translation);

        final AffineTransformOp op = new AffineTransformOp(transform, null);
        final BufferedImage dst = new BufferedImage(width, height, src.getType());
        op.filter(src, dst);
        return ImageImpl.buildFromBufferedImage(dst);
    }

    @Override
    protected boolean isAccepted(final ParametersName name) {
        return name == ParametersName.ANGLE;
    }

}