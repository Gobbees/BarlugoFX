package barlugofx.app;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;

/**
 *  Implementation of IOManager interface.
 *
 */
public class IOManagerImpl implements IOManager {

    /* (non-Javadoc)
     * @see com.barlugofx.app.IOManager#loadImageFromFile(java.io.File)
     */
    @Override
    public Image loadImageFromFile(final File file) throws IOException {
        return ImageImpl.buildFromBufferedImage(ImageIO.read(file));
    }

}
