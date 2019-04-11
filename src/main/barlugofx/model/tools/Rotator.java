package barlugofx.model.tools;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.model.tools.common.AbstractImageTool;
import barlugofx.model.tools.common.ParameterName;

/**
 * This class allows an {@link Image} to be Rotated. It accepts one parameter, ANGLE,
 * which must be an integer between -180 and +180.
 *
 *  Eventual other value will result in an {@link IllegalStateException}.
 */
public final class Rotator extends AbstractImageTool {
    private static final int MAX = 180;
    private static final double DEFAULT = 0.0;

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
    /*
     * (non-Javadoc)
     * @see barlugofx.model.tools.common.ImageTool#applyFilter(barlugofx.model.imagetools.Image)
     *
     * Code is largely taken from https://stackoverflow.com/questions/10426883/affinetransform-truncates-image?rq=1
     * Only minor adaptations has been done.
     */
    @Override
    public Image applyTool(final Image toApply) {
        final double degreesToRotate = super.getValueFromParameter(ParameterName.ANGLE, -MAX, MAX, DEFAULT);
        final BufferedImage src = ImageUtils.convertImageToBufferedImageWithAlpha(toApply);
        int width = src.getWidth();
        int height = src.getHeight();

        final AffineTransform transform = new AffineTransform();
        final double ang = Math.toRadians(degreesToRotate);
        transform.setToRotation(ang, width / 2d, height / 2d);
        final Point[] points = {
                new Point(0, 0),
                new Point(width, 0),
                new Point(width, height),
                new Point(0, height)
        };
        transform.transform(points, 0, points, 0, 4);
        final Point min = new Point(points[0]);
        final Point max = new Point(points[0]);
        final int n = points.length;
        for (int i = 1; i < n; i++) {
            final Point p = points[i];
            final double pX = p.getX();
            final double pY = p.getY();
            if (pX < min.getX()) {
                min.setLocation(pX, min.getY());
            }
            if (pX > max.getX()) {
                max.setLocation(pX, max.getY());
            }
            if (pY < min.getY()) {
                min.setLocation(min.getX(), pY);
            }
            if (pY > max.getY()) {
                max.setLocation(max.getX(), pY);
            }
        }
        width = (int) (max.getX() - min.getX());
        height = (int) (max.getY() - min.getY());
        final double tx = min.getX();
        final double ty = min.getY();
        final AffineTransform translation = new AffineTransform();
        translation.translate(-tx, -ty);
        transform.preConcatenate(translation);
        final AffineTransformOp op = new AffineTransformOp(transform, null);
        final BufferedImage dst = new BufferedImage(width, height, src.getType());
        op.filter(src, dst);
        return ImageImpl.buildFromBufferedImage(dst);
    }

    @Override
    protected boolean isAccepted(final ParameterName name) {
        return name == ParameterName.ANGLE;
    }

}
