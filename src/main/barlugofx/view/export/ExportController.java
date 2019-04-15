package barlugofx.view.export;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;

import barlugofx.utils.Format;
import barlugofx.view.View;
import barlugofx.view.AbstractViewControllerWithManager;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static barlugofx.utils.Format.PNG;
import static barlugofx.utils.Format.JPEG;
import static barlugofx.utils.Format.GIF;

/**
 * This class manages the view events. IMPORTANT: set the app manager with
 * setManager() function. Creating a ExportController object is useless and it
 * probably will cause some sort of exception.
 */
public final class ExportController extends AbstractViewControllerWithManager {
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

    @Override
    public void setStage(final Stage stage) {
        super.setStage(stage);
        initComponents();
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
        checkStage();
        final double width = this.getStage().getScene().getWidth();
        final double height = this.getStage().getScene().getHeight();

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
        checkManager();
        final File file = getFileFromDialog(format);
        if (file != null) {
            checkManager();
            new Thread(() -> {
                try {
                    this.getManager().exportImage(file, format);
                } catch (IOException | InterruptedException | ExecutionException e) {
                    View.showErrorAlert(e.getMessage());
                    e.printStackTrace();
                }
            }, "Saving").start();
        }
    }

    private void exportImageJPEG(final float quality) {
        checkManager();
        final File file = getFileFromDialog(JPEG);
        if (file != null) {
            checkManager();
            new Thread(() -> {
                try {
                    this.getManager().exportImage(file, quality);
                } catch (IOException | InterruptedException | ExecutionException e) {
                    View.showErrorAlert(e.getMessage());
                    e.printStackTrace();
                }
            }, "Saving").start();
        }
    }

    private File getFileFromDialog(final Format format) {
        final FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(format.toString(), "*" + format.toExtension()));
        try {
            checkManager();
            fc.setInitialFileName(this.getManager().getInputFileName() + format.toExtension());
        } catch (IllegalStateException e) {
            View.showErrorAlert(e.getMessage());
            e.printStackTrace();
        }
        return fc.showSaveDialog(this.getStage());
    }
}
