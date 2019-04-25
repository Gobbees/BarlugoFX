package barlugofx.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import barlugofx.model.imagetools.Image;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.model.parallelhandler.ParallelFilterExecutor;
import barlugofx.model.procedure.AdjustmentAlreadyPresentException;
import barlugofx.model.procedure.AdjustmentImpl;
import barlugofx.model.procedure.NoMoreActionsException;
import barlugofx.model.procedure.Procedure;
import barlugofx.model.procedure.ProcedureImpl;
import barlugofx.model.tools.BlackAndWhite;
import barlugofx.model.tools.Brightness;
import barlugofx.model.tools.Contrast;
import barlugofx.model.tools.Cropper;
import barlugofx.model.tools.Exposure;
import barlugofx.model.tools.Hue;
import barlugofx.model.tools.Rotator;
import barlugofx.model.tools.Saturation;
import barlugofx.model.tools.SelectiveRGBChanger;
import barlugofx.model.tools.Tools;
import barlugofx.model.tools.Vibrance;
import barlugofx.model.tools.WhiteBalance;
import barlugofx.model.tools.common.ImageTool;
import barlugofx.model.tools.common.ParallelizableImageTool;
import barlugofx.model.tools.common.Parameter;
import barlugofx.model.tools.common.ParameterImpl;
import barlugofx.model.tools.common.ParameterName;
import barlugofx.utils.Format;

/**
 * The main controller class.
 */
public final class AppManagerImpl implements AppManager {
    // multipliers used to adapt the input from the view to the model
    private static final float HSB_MULTIPLIER = 0.01f;
    private static final float SATURATION_MULTIPLIER = 0.005f;
    private static final float WB_MULTIPLIER = 0.015f;
    private static final float VIBRANCE_MULTIPLIER = 0.01f;
    private static final float BW_MULTIPLIER = 0.004f;
    private static final float BW_SHIFTER = 0.8f;

    private Image image;
    private final IOManager fileManager;
    private Procedure procedure;

    /**
     * The constructor of the class. It takes the input file chosen by the user and
     * initiates all the elements
     *
     * @param file the input file
     * @throws IOException if the file opening fails
     */
    public AppManagerImpl(final File file) throws IOException {
        fileManager = new IOManagerImpl();
        setImage(file);
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return ImageUtils.convertImageToBufferedImageWithAlpha(image);
    }

    @Override
    public synchronized void setImage(final File file) throws IOException {
        image = fileManager.loadImageFromFile(file);
        procedure = new ProcedureImpl(image, ParallelFilterExecutor.shouldYouParallelize(image));
    }

    @Override
    public String getInputFileName() {
        return fileManager.getFileName();
    }

    @Override
    public synchronized void setExposure(final int value) {
        final Exposure exposure = Exposure.createExposure();
        exposure.addParameter(ParameterName.EXPOSURE, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
        image = uploadProcedure(Tools.EXPOSURE, exposure);
    }

    @Override
    public synchronized void setContrast(final int value) {
        final Contrast contrast = Contrast.createContrast();
        contrast.addParameter(ParameterName.CONTRAST, new ParameterImpl<Integer>(value));
        image = uploadProcedure(Tools.CONTRAST, contrast);
    }

    @Override
    public synchronized void setBrightness(final int value) {
        final Brightness brightness = Brightness.createBrightness();
        brightness.addParameter(ParameterName.BRIGHTNESS, new ParameterImpl<Integer>(value));
        image = uploadProcedure(Tools.BRIGHTNESS, brightness);
    }

    @Override
    public synchronized void setWhiteBalance(final int value) {
        final WhiteBalance wb = WhiteBalance.createWhiteBalance();
        wb.addParameter(ParameterName.WHITEBALANCE, new ParameterImpl<Float>(value * WB_MULTIPLIER));
        image = uploadProcedure(Tools.WHITEBALANCE, wb);
    }

    @Override
    public synchronized void setSaturation(final int value) {
        final Saturation saturation = Saturation.createSaturation();
        saturation.addParameter(ParameterName.SATURATION, new ParameterImpl<Float>(value * SATURATION_MULTIPLIER));
        image = uploadProcedure(Tools.SATURATION, saturation);
    }

    @Override
    public synchronized void setHue(final int value) {
        final Hue hue = Hue.createHue();
        hue.addParameter(ParameterName.HUE, new ParameterImpl<Float>(value * HSB_MULTIPLIER));
        image = uploadProcedure(Tools.HUE, hue);
    }

    @Override
    public synchronized void setVibrance(final int value) {
        final Vibrance vibrance = Vibrance.createVibrance();
        vibrance.addParameter(ParameterName.VIBRANCE_INCREMENT, new ParameterImpl<Float>(value * VIBRANCE_MULTIPLIER));
        image = uploadProcedure(Tools.VIBRANCE, vibrance);
    }

    @Override
    public synchronized void setSelectiveColors(final int r, final int g, final int b) {
        final SelectiveRGBChanger srgb = SelectiveRGBChanger.createSelective();
        srgb.addParameter(ParameterName.RED, new ParameterImpl<Integer>(r));
        srgb.addParameter(ParameterName.GREEN, new ParameterImpl<Integer>(g));
        srgb.addParameter(ParameterName.BLUE, new ParameterImpl<Integer>(b));
        image = uploadProcedure(Tools.SELECTIVECOLOR, srgb);
    }

    @Override
    public synchronized void setBlackAndWhite(final double r, final double g, final double b) {
        final BlackAndWhite bw = BlackAndWhite.createBlackAndWhite();
        bw.addParameter(ParameterName.WRED, new ParameterImpl<Double>(r * BW_MULTIPLIER + BW_SHIFTER));
        bw.addParameter(ParameterName.WGREEN, new ParameterImpl<Double>(g * BW_MULTIPLIER + BW_SHIFTER));
        bw.addParameter(ParameterName.WBLUE, new ParameterImpl<Double>(b * BW_MULTIPLIER + BW_SHIFTER));
        image = uploadProcedure(Tools.BLACKANDWHITE, bw);
    }

    @Override
    public void exportImage(final File file, final Format format)
            throws IOException, InterruptedException, ExecutionException {
        fileManager.exportImage(image, file, format);
    }

    @Override
    public void exportImage(final File file, final float quality)
            throws IOException, InterruptedException, ExecutionException {
        fileManager.exportJPEGWithQuality(image, file, quality);
    }

    @Override
    public synchronized void rotate(final double angle) {
        final Rotator rotator = Rotator.createRotator();
        if (!procedure.canAdd(Tools.ROTATOR)) {
            final Optional<Parameter<? extends Number>> oldAngle = procedure.getValue(Tools.ROTATOR, ParameterName.ANGLE);
            if (oldAngle.isPresent()) {
                rotator.addParameter(ParameterName.ANGLE, new ParameterImpl<Double>(angle + oldAngle.get().getValue().doubleValue()));
            } else {
                rotator.addParameter(ParameterName.ANGLE, new ParameterImpl<Double>(angle));
            }
        } else {
            rotator.addParameter(ParameterName.ANGLE, new ParameterImpl<Double>(angle));
        }
        image = uploadProcedure(Tools.ROTATOR, rotator);
    }

    @Override
    public synchronized void crop(final int x1, final int y1, final int x2, final int y2) {
        final Cropper cropper = Cropper.createCropper();
        if (!procedure.canAdd(Tools.CROPPER)) {
            final Optional<Parameter<? extends Number>> oldX1 = procedure.getValue(Tools.CROPPER, ParameterName.X1);
            final Optional<Parameter<? extends Number>> oldY1 = procedure.getValue(Tools.CROPPER, ParameterName.Y1);
            final Optional<Parameter<? extends Number>> oldX2 = procedure.getValue(Tools.CROPPER, ParameterName.X2);
            final Optional<Parameter<? extends Number>> oldY2 = procedure.getValue(Tools.CROPPER, ParameterName.Y1);
            if (oldX1.isPresent() && oldY1.isPresent() && oldX2.isPresent() && oldY2.isPresent()) {
                addParametersToCropper(cropper, oldX1.get().getValue().intValue() + x1,
                        oldY1.get().getValue().intValue() + y1,
                        oldX2.get().getValue().intValue() - (oldX2.get().getValue().intValue() - oldX1.get().getValue().intValue() - x2),
                        oldY2.get().getValue().intValue() - (oldY2.get().getValue().intValue() - oldY1.get().getValue().intValue() - y2));
            } else {
                addParametersToCropper(cropper, x1, y1, x2, y2);
            }
        } else {
            addParametersToCropper(cropper, x1, y1, x2, y2);
        }
        image = uploadProcedure(Tools.CROPPER, cropper);
    }

    @Override
    public synchronized void undo() throws IllegalStateException {
        try {
            image = procedure.undo();
        } catch (final NoMoreActionsException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public synchronized void redo() throws IllegalStateException {
        try {
            image = procedure.redo();
        } catch (final NoMoreActionsException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public void savePreset(final Properties filters, final File file)
            throws IOException, InterruptedException, ExecutionException {
        fileManager.writePreset(filters, file);
    }

    @Override
    public synchronized List<String> applyPreset(final File file) throws IOException, IllegalStateException {
        final Properties properties = fileManager.loadPreset(file);
        String filterName = "";
        int value;
        int[] intValues;
        double[] doubleValues;
        final Class<?> typeInt = int.class;
        final Class<?> typeDouble = double.class;
        final List<String> list = new ArrayList<>();
        final StringBuilder sb = new StringBuilder();
        Method m;
        try {
            final Enumeration<?> e = properties.propertyNames();
            while (e.hasMoreElements()) {
                filterName = (String) e.nextElement();
                sb.append(filterName);
                sb.append(" set to: ");
                if (filterName.equals("SelectiveColors")) {
                    intValues = Arrays.asList(properties.getProperty(filterName).split(",")).stream()
                            .mapToInt(Integer::parseInt).toArray();
                    m = this.getClass().getDeclaredMethod("set" + filterName, typeInt, typeInt, typeInt);
                    m.invoke(this, intValues[0], intValues[1], intValues[2]);
                    sb.append(intValues[0]);
                    sb.append(", ");
                    sb.append(intValues[1]);
                    sb.append(", ");
                    sb.append(intValues[2]);
                    list.add(sb.toString());
                } else if (filterName.equals("BlackAndWhite")) {
                    doubleValues = Arrays.asList(properties.getProperty(filterName).split(",")).stream()
                            .mapToDouble(Double::parseDouble).toArray();
                    m = this.getClass().getDeclaredMethod("set" + filterName, typeDouble, typeDouble, typeDouble);
                    m.invoke(this, doubleValues[0], doubleValues[1], doubleValues[2]);
                    sb.append((int) doubleValues[0]);
                    sb.append(", ");
                    sb.append((int) doubleValues[1]);
                    sb.append(", ");
                    sb.append((int) doubleValues[2]);
                    list.add(sb.toString());
                } else {
                    value = Integer.parseInt(properties.getProperty(filterName));
                    m = this.getClass().getDeclaredMethod("set" + filterName, typeInt);
                    m.invoke(this, value);
                    sb.append(value);
                    list.add(sb.toString());
                }
                sb.setLength(0);
            }
        } catch (InvocationTargetException | IllegalArgumentException ex) {
            throw new IllegalStateException("The selected file is corrupted.");
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
    //To avoid big copy paste of this section of code
    private void addParametersToCropper(final Cropper cropper, final int x1, final int y1, final int x2, final int y2) {
        cropper.addParameter(ParameterName.X1, new ParameterImpl<Integer>(x1));
        cropper.addParameter(ParameterName.X2, new ParameterImpl<Integer>(x2));
        cropper.addParameter(ParameterName.Y1, new ParameterImpl<Integer>(y1));
        cropper.addParameter(ParameterName.Y2, new ParameterImpl<Integer>(y2));
    }
    //Model choices. I cannot delete this copy paste.
    private Image uploadProcedure(final Tools toolType, final ParallelizableImageTool tool) {
        if (procedure.canAdd(toolType)) {
            try {
                return procedure.add(new AdjustmentImpl(tool));
            } catch (final AdjustmentAlreadyPresentException e) {
                throw new IllegalStateException();
            }
        } else {
            return procedure.edit(toolType, new AdjustmentImpl(tool));
        }
    }
    private Image uploadProcedure(final Tools toolType, final ImageTool tool) {
        if (procedure.canAdd(toolType)) {
            try {
                return procedure.add(new AdjustmentImpl(tool));
            } catch (final AdjustmentAlreadyPresentException e) {
                throw new IllegalStateException();
            }
        } else {
            return procedure.edit(toolType, new AdjustmentImpl(tool));
        }
    }
}
