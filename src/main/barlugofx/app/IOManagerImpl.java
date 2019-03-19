package barlugofx.app;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageImpl;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.utils.Format;

/**
 *  Implementation of IOManager interface.
 *
 */
public final class IOManagerImpl implements IOManager {
    private String inputFileName;
    private final ExecutorService executor;
    /**
     * Class constructor.
     */
    public IOManagerImpl() {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public Image loadImageFromFile(final File file) throws IOException {
        inputFileName = file.getName();
        inputFileName = inputFileName.substring(0, inputFileName.indexOf('.'));
        return ImageImpl.buildFromBufferedImage(ImageIO.read(file));
    }

    @Override
    public String getFileName() {
        return inputFileName;
    }

    @Override
    public void exportImage(final Image image, final File file, final Format format) throws IOException {
        if (executor.submit(() -> ImageIO.write(ImageUtils.convertImageToBufferedImageWithAlpha(image), format.toOutputForm(), file)) == null) {
            throw new IOException();
        }
    }

    @Override
    public void exportJPEGWithQuality(final Image image, final File file, final float quality) throws IOException {
        final ImageWriter writer = ImageIO.getImageWritersByFormatName(Format.JPEG.toOutputForm()).next();
        final ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality(quality);
        final FileImageOutputStream output = new FileImageOutputStream(file);
        writer.setOutput(output);
        final IIOImage outputImage = new IIOImage(ImageUtils.convertImageToBufferedImageWithoutAlpha(image), null, null);
        final Callable<Boolean> saveOperation = () -> { 
            writer.write(null, outputImage, iwp);
            writer.dispose();
            return true;
        };
        if (executor.submit(() -> saveOperation) == null) {
            throw new IOException();
        }
    }
}
