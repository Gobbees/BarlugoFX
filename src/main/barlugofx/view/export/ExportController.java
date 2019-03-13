package barlugofx.view.export;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;

import barlugofx.app.AppManager;
import barlugofx.utils.Format;
import barlugofx.view.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static barlugofx.utils.Format.PNG;
import static barlugofx.utils.Format.JPEG;
import static barlugofx.utils.Format.GIF;

/**
 * This class manages the view events. IMPORTANT: set the app manager with setManager() function.
 * Creating a ExportController object is useless and it probably will cause some sort of exception.
 */
public final class ExportController implements ViewController {
    private static final double BTN_WIDTH_MULTIPLIER = 0.333;
    private static final double BTN_HEIGHT_MULTIPLIER = 1;
    private static final double SLIDER_WIDTH_MULTIPLIER = 0.2;
    private static final double VERT_SEPARATOR_MULTIPLIER = 0.2;
    private static final double HORIZ_SEPARATOR_MULTIPLIER = 0.1;
    @FXML
    private HBox vboxMain;
    @FXML
    private JFXButton btnPNG;
    @FXML
    private JFXButton btnJPEG;
    @FXML
    private JFXButton btnGIF;
    @FXML
    private HBox hboxQuality;
    @FXML
    private JFXSlider sliderQuality;
    @FXML
    private Separator horizDistance;
    @FXML
    private Separator verticDistanceTop;
    @FXML
    private Separator verticDistanceBottom;
    private Stage stage;
    private AppManager manager;

    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;
        //TEMP TODO
        initComponents();
    }
    /**
     * This function sets the app manager (controller). It must be called in order to avoid future errors.
     * @param manager the input manager
     */
    public void setManager(final AppManager manager) {
        this.manager = manager;
    }
    /**
     * PNG button clicked event.
     */
    @FXML
    public void pngClicked() {
        exportImage(PNG);
    }
    /**
     * JPEG button clicked event.
     */
    @FXML
    public void jpegClicked() {
        hboxQuality.setVisible(true);
        btnPNG.setDisable(true);
        btnJPEG.setDisable(true);
        btnGIF.setDisable(true);
    }
    /**
     * GIF button clicked event.
     */
    @FXML
    public void gifClicked() {
        exportImage(GIF);
    }
    /**
     * Export button clicked event.
     */
    @FXML
    public void exportJPEG() {
        exportImageJPEG((float) sliderQuality.getValue() / 100);
    }
    /**
     * Undo button clicked event.
     */
    @FXML
    public void undoJPEG() {
        btnPNG.setDisable(false);
        btnJPEG.setDisable(false);
        btnGIF.setDisable(false);
        hboxQuality.setVisible(false);
    }
    private void initComponents() {
        double width = stage.getScene().getWidth();
        double height = stage.getScene().getHeight();

        btnPNG.setMinWidth(width * BTN_WIDTH_MULTIPLIER);
        btnPNG.setPrefHeight(height * BTN_HEIGHT_MULTIPLIER);
        btnJPEG.setMinWidth(width * BTN_WIDTH_MULTIPLIER);
        btnJPEG.setPrefHeight(height * BTN_HEIGHT_MULTIPLIER);
        btnGIF.setMinWidth(width * BTN_WIDTH_MULTIPLIER - 1);
        btnGIF.setPrefHeight(height * BTN_HEIGHT_MULTIPLIER);
        horizDistance.setPrefWidth(width * VERT_SEPARATOR_MULTIPLIER);
        verticDistanceTop.setPrefHeight(height * HORIZ_SEPARATOR_MULTIPLIER);
        verticDistanceBottom.setPrefHeight(height * HORIZ_SEPARATOR_MULTIPLIER);
        sliderQuality.setPrefWidth(width * SLIDER_WIDTH_MULTIPLIER);
    }
    private void exportImage(final Format format) {
        File file = getFileFromDialog(format);
        if (file != null) {
            try {
                manager.exportImage(file, format);
            } catch (IOException | NullPointerException e) {
                // TODO log
                e.printStackTrace();
            }
        }
    }
    private void exportImageJPEG(final float quality) {
        File file = getFileFromDialog(JPEG);
        if (file != null) {
            try {
                manager.exportImage(file, quality);
            } catch (IOException e) {
                // TODO log
                e.printStackTrace();
            }
        }
    }
    private File getFileFromDialog(final Format format) {
        FileChooser fc = new FileChooser();
        fc.setInitialFileName(manager.getInputFileName() + format.toExtension());
        return fc.showSaveDialog(stage);
    }

}
