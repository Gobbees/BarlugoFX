package barlugofx.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

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
 */
public final class IOManagerImpl implements IOManager {
    private String inputFileName;
    /**
     * Class constructor.
     */
    public IOManagerImpl() {
        inputFileName = null;
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
    public void exportImage(final Image image, final File file, final Format format) throws IOException, InterruptedException, ExecutionException {
        ImageIO.write(ImageUtils.convertImageToBufferedImageWithAlpha(image), format.toOutputForm(), file);
    }
    @Override
    public void exportJPEGWithQuality(final Image image, final File file, final float quality) throws IOException, InterruptedException, ExecutionException {
        final ImageWriter writer = ImageIO.getImageWritersByFormatName(Format.JPEG.toOutputForm()).next();
        final ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality(quality);
        final FileImageOutputStream output = new FileImageOutputStream(file);
        writer.setOutput(output);
        final IIOImage outputImage = new IIOImage(ImageUtils.convertImageToBufferedImageWithoutAlpha(image), null, null);
        writer.write(null, outputImage, iwp);
        writer.dispose();
    }
    @Override
    public void writePreset(final Properties filters, final File file) throws IOException, InterruptedException, ExecutionException {
        final OutputStream output = new FileOutputStream(file);
        filters.store(output, "Preset properties");
        output.close();
    }
    @Override
    public Properties loadPreset(final File file) throws IOException {
        final Properties properties = new Properties();
        final FileInputStream fStream = new FileInputStream(file);
        properties.load(fStream);
        fStream.close();
        return properties;
    }
}
