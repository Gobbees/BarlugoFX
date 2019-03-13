package barlugofx.app;

import java.io.File;
import java.io.IOException;

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

    @Override
    public Image loadImageFromFile(final File file) throws IOException {
        inputFileName = file.getName();
        inputFileName = inputFileName.substring(0, inputFileName.indexOf("."));
        return ImageImpl.buildFromBufferedImage(ImageIO.read(file));
    }

    @Override
    public String getFileName() {
        return inputFileName;
    }

    @Override
    public void exportImage(final Image image, final File file, final Format format) throws IOException {
        ImageIO.write(ImageUtils.convertImageToBufferedImageWithAlpha(image), "tif", file);
    }

    @Override
    public void exportJPEGWithQuality(final Image image, final File file, final float quality) throws IOException {
      ImageWriter writer = ImageIO.getImageWritersByFormatName(Format.JPEG.toOutputForm()).next();
      ImageWriteParam iwp = writer.getDefaultWriteParam();
      iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      iwp.setCompressionQuality(quality);
      FileImageOutputStream output = new FileImageOutputStream(file);
      writer.setOutput(output);
      IIOImage outputImage = new IIOImage(ImageUtils.convertImageToBufferedImageWithoutAlpha(image), null, null);
      writer.write(null, outputImage, iwp);
      writer.dispose();
    }
}
