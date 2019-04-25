package barlugofx.view.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import barlugofx.controller.AppManager;
import barlugofx.utils.Format;
import barlugofx.utils.MutablePair;
import barlugofx.view.View;
import barlugofx.view.AbstractViewControllerWithManager;
import barlugofx.view.InputOutOfBoundException;
import barlugofx.view.components.tools.CropArea;
import barlugofx.view.components.tools.RotateLine;
import barlugofx.view.components.zoompane.ZoomDirection;
import barlugofx.view.components.zoompane.ZoomableImageView;
import barlugofx.view.export.ExportView;
import barlugofx.view.preset.PresetView;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class manages the view events. IMPORTANT: set the app manager with
 * setManager() function. Creating a MainController object is useless and
 * calling its functions will cause some sort of exception.
 */
public final class MainController extends AbstractViewControllerWithManager {
    // private constant fields
    private static final double RIGHT_COLUMN_MIN_MULTIPLIER = 0.15;
    private static final double RIGHT_COLUMN_MAX_MULTIPLIER = 0.4;
    private static final double MIN_ZOOM_RATIO = 0.5d;
    private static final double DEFAULT_ZOOM_RATIO = 1d;
    private static final double MAX_ZOOM_RATIO = 10d;
    @FXML
    private BorderPane paneGeneral;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TextFlow tflowLogo;
    @FXML
    private SplitPane spaneMain;
    @FXML
    private BorderPane paneImage;
    @FXML
    private AnchorPane apaneImage;
    @FXML
    private ZoomableImageView iviewImage;
    @FXML
    private SplitPane spaneRightColumn;
    @FXML
    private TitledPane tpaneLights;
    @FXML
    private AnchorPane apaneExposure;
    @FXML
    private JFXSlider slExposure;
    @FXML
    private JFXTextField tfExposure;
    @FXML
    private AnchorPane apaneContrast;
    @FXML
    private JFXSlider slContrast;
    @FXML
    private JFXTextField tfContrast;
    @FXML
    private AnchorPane apaneBrightness;
    @FXML
    private JFXSlider slBrightness;
    @FXML
    private JFXTextField tfBrightness;
    @FXML
    private TitledPane tpaneColours;
    @FXML
    private AnchorPane apaneWhitebalance;
    @FXML
    private JFXSlider slWhitebalance;
    @FXML
    private JFXTextField tfWhitebalance;
    @FXML
    private AnchorPane apaneSaturation;
    @FXML
    private JFXSlider slSaturation;
    @FXML
    private JFXTextField tfSaturation;
    @FXML
    private AnchorPane apaneHue;
    @FXML
    private JFXSlider slHue;
    @FXML
    private JFXTextField tfHue;
    @FXML
    private AnchorPane apaneVibrance;
    @FXML
    private JFXSlider slVibrance;
    @FXML
    private JFXTextField tfVibrance;
    @FXML
    private TitledPane tpaneSC;
    @FXML
    private AnchorPane apaneSCR;
    @FXML
    private JFXSlider slSCR;
    @FXML
    private JFXTextField tfSCR;
    @FXML
    private AnchorPane apaneSCG;
    @FXML
    private JFXSlider slSCG;
    @FXML
    private JFXTextField tfSCG;
    @FXML
    private AnchorPane apaneSCB;
    @FXML
    private JFXSlider slSCB;
    @FXML
    private JFXTextField tfSCB;
    @FXML
    private JFXButton btnSCApply;
    @FXML
    private TitledPane tpaneBW;
    @FXML
    private AnchorPane apaneBWR;
    @FXML
    private JFXSlider slBWR;
    @FXML
    private JFXTextField tfBWR;
    @FXML
    private AnchorPane apaneBWG;
    @FXML
    private JFXSlider slBWG;
    @FXML
    private JFXTextField tfBWG;
    @FXML
    private AnchorPane apaneBWB;
    @FXML
    private JFXSlider slBWB;
    @FXML
    private JFXTextField tfBWB;
    @FXML
    private JFXButton btnBWApply;
    @FXML
    private Label lblHistory;
    @FXML
    private JFXListView<String> lvHistory;
    @FXML
    private JFXButton btnUndo;
    private Optional<ExportView> exportView;
    private Optional<PresetView> presetView;
    private final Map<ViewTools, MutablePair<Number, Boolean>> toolStatus;
    //list of undone operations
    private final List<String> undoneOps;

    /**
     * The constructor of the class. It is public because FXML obligates to do so.
     */
    public MainController() {
        super();
        exportView = Optional.empty();
        presetView = Optional.empty();
        toolStatus = new HashMap<>();
        undoneOps = new ArrayList<>();
    }

    @Override
    public void setStage(final Stage stage) {
        super.setStage(stage);
        initComponentSize();
        initToolStatus();
        addListeners();
        stage.setOnCloseRequest(ev -> {
            if (exportView.isPresent()) {
                exportView.get().closeStage();
            }
            if (presetView.isPresent()) {
                presetView.get().closeStage();
            }
        });
    }

    /**
     * This function sets the app manager (controller). It must be called in order
     * to avoid future errors.
     * 
     * @param manager the input manager
     */
    @Override
    public void setManager(final AppManager manager) {
        super.setManager(manager);
        updateImage();
        iviewImage.updateRealSizes();
        enableZoomAndColumnResize();
        setEventListeners();
    }

    /**
     * Resizes all the components relating to the new sizes.
     * 
     * @param width  the new width
     * @param height the new height
     */
    public void resizeComponents(final double width, final double height) {
        checkStage();
        paneGeneral.setPrefSize(width, height);
        menuBar.setPrefWidth(width);
        spaneMain.setPrefWidth(width);
        spaneMain.setPrefHeight(height - menuBar.getHeight());
        apaneImage.setPrefWidth(paneGeneral.getPrefWidth() - spaneRightColumn.getWidth());
        apaneImage.setPrefHeight(spaneMain.getPrefHeight());
        iviewImage.setFitWidth(apaneImage.getPrefWidth());
        iviewImage.setFitHeight(apaneImage.getPrefHeight());
    }

    /**
     * New photo event triggered.
     */
    @FXML
    public void newPhoto() {
        // TODO discard changes
        checkManager();
        final FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Select an image", Format.getAllPossibleInputs()));
        final File file = fc.showOpenDialog(this.getStage());
        if (file != null) {
            runNewThread("New photo", createCompleteRunnable(() -> {
                try {
                    this.getManager().setImage(file);
                    Platform.runLater(() -> {
                        this.getStage().setTitle(this.getManager().getInputFileName());
                        slExposure.setValue(ViewTools.EXPOSURE.getDefaultValue());
                        slContrast.setValue(ViewTools.CONTRAST.getDefaultValue());
                        slBrightness.setValue(ViewTools.BRIGHTNESS.getDefaultValue());
                        slWhitebalance.setValue(ViewTools.WHITEBALANCE.getDefaultValue());
                        slSaturation.setValue(ViewTools.SATURATION.getDefaultValue());
                        slHue.setValue(ViewTools.HUE.getDefaultValue());
                        slVibrance.setValue(ViewTools.VIBRANCE.getDefaultValue());
                        slSCR.setValue(ViewTools.SCR.getDefaultValue());
                        slSCG.setValue(ViewTools.SCG.getDefaultValue());
                        slSCB.setValue(ViewTools.SCB.getDefaultValue());
                        slBWR.setValue(ViewTools.BWR.getDefaultValue());
                        slBWG.setValue(ViewTools.BWG.getDefaultValue());
                        slBWB.setValue(ViewTools.BWB.getDefaultValue());
                        toolStatus.clear();
                        lvHistory.getItems().clear();
                        undoneOps.clear();
                        initToolStatus();
                    });
                } catch (IOException e) {
                    View.showErrorAlert(e.getMessage());
                    e.printStackTrace();
                }
            }));
        }
    }

    /**
     * Export event triggered.
     */
    @FXML
    public void export() {
        checkManager();
        if (exportView.isPresent()) {
            exportView.get().closeStage();
        }
        exportView = Optional.of(new ExportView(this.getManager()));
    }

    /**
     * Undo event triggered.
     */
    @FXML
    public void undo() {
        checkManager();
        runNewThread("Undo", createCompleteRunnable(() -> {
            try {
                this.getManager().undo();
                Platform.runLater(() -> {
                    if (lvHistory.getItems().size() >= 1) {
                        undoneOps.add(0, lvHistory.getItems().remove(lvHistory.getItems().size() - 1));
                    }
                });
            } catch (IllegalStateException e) {
                View.showErrorAlert(e.getMessage());
            }
        }));
    }

    /**
     * Redo event triggered.
     */
    @FXML
    public void redo() {
        checkManager();
        runNewThread("Redo", createCompleteRunnable(() -> {
            try {
                this.getManager().redo();
                Platform.runLater(() -> {
                    if (undoneOps.size() >= 1) {
                       lvHistory.getItems().add(undoneOps.remove(0));
                    }
                });
            } catch (IllegalStateException e) {
                View.showErrorAlert(e.getMessage());
            }
        }));
    }

    /**
     * Rotate event triggered.
     */
    @FXML
    public void rotate() {
        checkManager();
        final AtomicReference<Boolean> mousePressed = new AtomicReference<>(false);
        final AtomicReference<Double> startX = new AtomicReference<>(), startY = new AtomicReference<>();
        final AtomicReference<RotateLine> rotateLine = new AtomicReference<>();
        disableZoomAndColumnResize();
        resizeToDefault();
        apaneImage.getChildren().clear();
        apaneImage.setCursor(Cursor.HAND);
        final EventHandler<MouseEvent> mReleased = e -> {
            if (rotateLine.get() == null) {
                apaneImage.setOnMouseDragged(null);
                apaneImage.setOnMouseReleased(null);
                this.getScene().setCursor(Cursor.DEFAULT);
                return;
            }
            startX.set(rotateLine.get().getStartPoint().getCenterX());
            startY.set(rotateLine.get().getStartPoint().getCenterY());
            apaneImage.setCursor(Cursor.WAIT);
            rotateLine.get().removeFromPane(apaneImage);
            runNewThread("Rotator", () -> {
                this.getManager().rotate(rotateLine.get().getAngle());
                Platform.runLater(() -> {
                    updateImage();
                    lvHistory.getItems().add("Image rotated");
                    apaneImage.setCursor(Cursor.DEFAULT);
                    apaneImage.setOnMouseDragged(null);
                    apaneImage.setOnMouseReleased(null);
                    enableZoomAndColumnResize();
                });
            });
        };
        final EventHandler<MouseEvent> mDragged = e -> {
            apaneImage.getChildren().clear();
            if (e.getX() < apaneImage.getWidth() - (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2
                    && e.getX() > (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2
                    && e.getY() < apaneImage.getHeight() - (apaneImage.getHeight() - iviewImage.getRealHeight()) / 2
                    && e.getY() > (apaneImage.getHeight() - iviewImage.getRealHeight()) / 2) {
                rotateLine.get().move(e.getX(), e.getY());
                rotateLine.get().addToPane(apaneImage);
                apaneImage.setOnMouseReleased(mReleased);
            }
            e.consume();
        };
        final EventHandler<MouseEvent> mPressed = e -> {
            mousePressed.set(true);
            apaneImage.getChildren().clear();
            rotateLine.set(new RotateLine(e.getX(), e.getY(), e.getX(), e.getY()));
            apaneImage.setOnMousePressed(null);
            apaneImage.setOnMouseDragged(mDragged);
        };
        apaneImage.setOnMousePressed(mPressed);
        this.getScene().setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE)) {
                if (rotateLine.get() != null) {
                    rotateLine.get().removeFromPane(apaneImage);
                }
                if (mousePressed.get()) {
                    apaneImage.setOnMouseReleased(ev -> {
                        apaneImage.setOnMouseReleased(null);
                        enableZoomAndColumnResize();
                    });
                    apaneImage.setOnMouseDragged(null);
                    this.getScene().setOnKeyPressed(null);
                } else {
                    apaneImage.setOnMousePressed(null);
                    apaneImage.setOnMouseReleased(null);
                    apaneImage.setOnMouseDragged(null);
                    this.getScene().setOnKeyPressed(null);
                    enableZoomAndColumnResize();
                }
            }
        });
    }

    /**
     * Crop event triggered.
     */
    @FXML
    public void crop() {
        checkManager();
        disableZoomAndColumnResize();
        resizeToDefault();
        spaneMain.setDividerPosition(0, (this.getScene().getWidth() - spaneRightColumn.getMinWidth()) / this.getScene().getWidth());
        final AtomicReference<Double> startX = new AtomicReference<>(), startY = new AtomicReference<>();
        apaneImage.getChildren().clear();
        final CropArea cropper = new CropArea(iviewImage.getRealWidth() / 2, iviewImage.getRealHeight() / 2,
                apaneImage.getWidth() / 2 - iviewImage.getRealWidth() / 4,
                apaneImage.getHeight() / 2 - iviewImage.getRealHeight() / 4);
        cropper.addToPane(apaneImage);
        cropper.addEvent(cropper.getRectangle(), MouseEvent.MOUSE_PRESSED, e -> {
            startX.set(e.getX());
            startY.set(e.getY());
        });
        cropper.addEvent(cropper.getRectangle(), MouseEvent.MOUSE_DRAGGED, e -> {
            if (cropper.getRectangle().getX() + e.getX()
                    - startX.get() > (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2
                    && cropper.getRectangle().getX() + cropper.getRectangle().getWidth() + e.getX()
                            - startX.get() < apaneImage.getWidth()
                                    - (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2
                    && cropper.getRectangle().getY() + e.getY()
                            - startY.get() > (apaneImage.getHeight() - iviewImage.getRealHeight()) / 2
                    && cropper.getRectangle().getY() + cropper.getRectangle().getHeight() + e.getY()
                            - startY.get() < apaneImage.getHeight()
                                    - (apaneImage.getHeight() - iviewImage.getRealHeight()) / 2) {
                cropper.drag(startX.get(), startY.get(), e.getX(), e.getY());
                startX.set(e.getX());
                startY.set(e.getY());
            }
            e.consume();
        });
        cropper.addEvent(cropper.getTopLeftPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            if (e.getX() <= cropper.getTopRightPoint().getCenterX()
                    && e.getY() <= cropper.getBottomLeftPoint().getCenterY()
                    && e.getX() <= apaneImage.getWidth() - (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2
                    && e.getX() >= (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2
                    && e.getY() >= (apaneImage.getHeight() - iviewImage.getRealHeight()) / 2) {
                cropper.resize(e.getX(), e.getY(), cropper.getBottomRightPoint().getCenterX(),
                        cropper.getBottomRightPoint().getCenterY());
            }
        });
        cropper.addEvent(cropper.getTopRightPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            if (e.getX() >= cropper.getTopLeftPoint().getCenterX()
                    && e.getY() <= cropper.getBottomRightPoint().getCenterY()
                    && e.getX() <= apaneImage.getWidth() - (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2
                    && e.getY() >= (apaneImage.getHeight() - iviewImage.getRealHeight()) / 2) {
                cropper.resize(cropper.getTopLeftPoint().getCenterX(), e.getY(), e.getX(),
                        cropper.getBottomRightPoint().getCenterY());
            }
        });
        cropper.addEvent(cropper.getBottomLeftPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            if (e.getX() <= cropper.getBottomRightPoint().getCenterX()
                    && e.getY() >= cropper.getTopLeftPoint().getCenterY()
                    && e.getX() >= (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2
                    && e.getY() <= apaneImage.getHeight() - (apaneImage.getHeight() - iviewImage.getRealHeight()) / 2) {
                cropper.resize(e.getX(), cropper.getTopLeftPoint().getCenterY(),
                        cropper.getBottomRightPoint().getCenterX(), e.getY());
            }
        });
        cropper.addEvent(cropper.getBottomRightPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            if (e.getX() >= cropper.getBottomLeftPoint().getCenterX()
                    && e.getY() >= cropper.getTopRightPoint().getCenterY()
                    && e.getX() <= apaneImage.getWidth() - (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2
                    && e.getY() <= apaneImage.getHeight() - (apaneImage.getHeight() - iviewImage.getRealHeight()) / 2) {
                cropper.resize(cropper.getTopLeftPoint().getCenterX(), cropper.getTopLeftPoint().getCenterY(), e.getX(),
                        e.getY());
            }
        });
        cropper.addEvent(cropper.getMidTopPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            if (e.getY() <= cropper.getMidBottomPoint().getCenterY()
                    && e.getY() >= (apaneImage.getHeight() - iviewImage.getRealHeight()) / 2) {
                cropper.resize(cropper.getTopLeftPoint().getCenterX(), e.getY(),
                        cropper.getBottomRightPoint().getCenterX(), cropper.getBottomRightPoint().getCenterY());
            }
        });
        cropper.addEvent(cropper.getMidRightPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            if (e.getX() >= cropper.getMidLeftPoint().getCenterX()
                    && e.getX() <= apaneImage.getWidth() - (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2) {
                cropper.resize(cropper.getTopLeftPoint().getCenterX(), cropper.getTopLeftPoint().getCenterY(), e.getX(),
                        cropper.getBottomRightPoint().getCenterY());
            }
        });
        cropper.addEvent(cropper.getMidBottomPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            if (e.getY() >= cropper.getMidTopPoint().getCenterY()
                    && e.getY() <= apaneImage.getHeight() - (apaneImage.getHeight() - iviewImage.getRealHeight()) / 2) {
                cropper.resize(cropper.getTopLeftPoint().getCenterX(), cropper.getTopLeftPoint().getCenterY(),
                        cropper.getBottomRightPoint().getCenterX(), e.getY());
            }
        });
        cropper.addEvent(cropper.getMidLeftPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            if (e.getX() <= cropper.getMidRightPoint().getCenterX()
                    && e.getX() >= (apaneImage.getWidth() - iviewImage.getRealWidth()) / 2) {
                cropper.resize(e.getX(), cropper.getTopLeftPoint().getCenterY(),
                        cropper.getBottomRightPoint().getCenterX(), cropper.getBottomRightPoint().getCenterY());
            }
        });
        this.getScene().setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                int tmpSize = (int) ((cropper.getRectangle().getX()
                        - (iviewImage.getFitWidth() - iviewImage.getRealWidth()) / 2) * iviewImage.getImage().getWidth()
                        / iviewImage.getRealWidth());
                final int x1 = tmpSize < 0 ? 0 : tmpSize;
                tmpSize = (int) ((cropper.getRectangle().getY()
                        - (iviewImage.getFitHeight() - iviewImage.getRealHeight()) / 2)
                        * iviewImage.getImage().getHeight() / iviewImage.getRealHeight());
                final int y1 = tmpSize < 0 ? 0 : tmpSize;
                tmpSize = (int) ((cropper.getRectangle().getX() + cropper.getRectangle().getWidth()
                        - (iviewImage.getFitWidth() - iviewImage.getRealWidth()) / 2) * iviewImage.getImage().getWidth()
                        / iviewImage.getRealWidth());
                final int x2 = tmpSize > iviewImage.getImage().getWidth() ? (int) iviewImage.getImage().getWidth() : tmpSize;
                tmpSize = (int) ((cropper.getRectangle().getY() + cropper.getRectangle().getHeight()
                        - (iviewImage.getFitHeight() - iviewImage.getRealHeight()) / 2)
                        * iviewImage.getImage().getHeight() / iviewImage.getRealHeight());
                final int y2 = tmpSize > iviewImage.getImage().getHeight() ? (int) iviewImage.getImage().getHeight() : tmpSize; 
                runNewThread("Cropper", () -> {
                    this.getManager().crop(x1, y1, x2, y2);
                    Platform.runLater(() -> {
                        updateImage();
                        lvHistory.getItems().add("Image cropped");
                        cropper.removeFromPane(apaneImage);
                        apaneImage.setOnMouseDragged(null);
                        apaneImage.setOnMouseReleased(null);
                        this.getScene().setOnKeyPressed(null);
                        enableZoomAndColumnResize();
                    });
                });
            } else if (ke.getCode().equals(KeyCode.ESCAPE)) {
                cropper.removeFromPane(apaneImage);
                apaneImage.setOnMouseDragged(null);
                apaneImage.setOnMouseReleased(null);
                this.getScene().setOnKeyPressed(null);
                enableZoomAndColumnResize();
            }
        });
    }

    /**
     * Preset event triggered.
     */
    @FXML
    public void preset() {
        checkManager();
        if (presetView.isPresent()) {
            presetView.get().closeStage();
        }
        presetView = Optional.of(new PresetView(this.getManager()));
    }

    /**
     * Apply preset event triggered.
     */
    @FXML
    public void openPreset() {
        checkManager();
        final FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Select a .bps preset", "*.bps"));
        fc.setTitle("Select a .bps preset");
        final File input = fc.showOpenDialog(this.getStage());
        if (input == null) {
            return;
        }
        runNewThread("Preset", createCompleteRunnable(() -> {
            try {
                this.getManager().applyPreset(input);
            } catch (IOException e) {
                View.showErrorAlert(e.getMessage());
                e.printStackTrace();
            }
        }));
    }

    /**
     * Zoom in event triggered.
     */
    @FXML
    public void zoomIn() {
        iviewImage.zoom(ZoomDirection.ZOOM_IN, iviewImage.getRealWidth() / 2, iviewImage.getRealHeight() / 2);
    }

    /**
     * Zoom out event triggered.
     */
    @FXML
    public void zoomOut() {
        iviewImage.zoom(ZoomDirection.ZOOM_OUT, iviewImage.getRealWidth() / 2, iviewImage.getRealHeight() / 2);
    }

    /**
     * Toggle full screen event triggered.
     */
    @FXML
    public void toggleFullScreen() {
        checkStage();
        this.getStage().setFullScreen(true);
    }

    /**
     * Minimize event triggered.
     */
    @FXML
    public void toggleMinimize() {
        checkStage();
        this.getStage().setIconified(true);
    }

    /**
     * About clicked.
     */
    @FXML
    public void about() {
        try {
            Desktop.getDesktop().browse(new URI("https://gobbees.github.io/BarlugoFX"));
        } catch (IOException | URISyntaxException e) {
            View.showErrorAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    // updates the image and the real sizes
    private void updateImage() {
        iviewImage.setImage(SwingFXUtils.toFXImage(this.getManager().getBufferedImage(), null));
        // System.gc(); //it is necessary since I don't know why (testing with
        // jvisualvm) the gc doesn't perform after this operation.
        iviewImage.setFitWidth(apaneImage.getWidth());
        iviewImage.setFitHeight(apaneImage.getHeight());
        iviewImage.updateRealSizes();
    }

    // zoom and pane activation
    private void enableZoomAndColumnResize() {
        apaneImage.setCursor(Cursor.OPEN_HAND);
        apaneImage.setOnScroll(e -> {
            if (iviewImage.getZoomRatio() > MIN_ZOOM_RATIO && e.getDeltaY() > 0) {
                iviewImage.zoom(ZoomDirection.ZOOM_OUT, e.getSceneX(), e.getSceneY());
                iviewImage.updateRealSizes();
            } else if (iviewImage.getZoomRatio() < MAX_ZOOM_RATIO && e.getDeltaY() < 0) {
                iviewImage.zoom(ZoomDirection.ZOOM_IN, e.getSceneX(), e.getSceneY());
                iviewImage.updateRealSizes();
            }
        });
        apaneImage.setOnMousePressed(e -> {
            apaneImage.setCursor(Cursor.CLOSED_HAND);
            iviewImage.initDrag(e.getX(), e.getY());
        });
        apaneImage.setOnMouseDragged(e -> {
            iviewImage.drag(e.getX(), e.getY());
        });
        apaneImage.setOnMouseReleased(e -> {
            apaneImage.setCursor(Cursor.OPEN_HAND);
        });
        apaneImage.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                resizeToDefault();
            }
        });
        spaneRightColumn.setMaxWidth(this.getScene().getWidth() * RIGHT_COLUMN_MAX_MULTIPLIER);
    }

    private void disableZoomAndColumnResize() {
        apaneImage.setOnScroll(null);
        apaneImage.setOnMousePressed(null);
        apaneImage.setOnMouseDragged(null);
        apaneImage.setOnMouseReleased(null);
        apaneImage.setCursor(Cursor.DEFAULT);
        spaneRightColumn.setMaxWidth(spaneRightColumn.getWidth());
    }

    private void resizeToDefault() {
        iviewImage.setZoomToValue(DEFAULT_ZOOM_RATIO);
        iviewImage.setTranslateX(0);
        iviewImage.setTranslateY(0);
        iviewImage.updateRealSizes();
    }

    // this function initializes all the components sizes in relation to the screen size.
    private void initComponentSize() {
        tflowLogo.setStyle("-fx-font-size: " + menuBar.getHeight());
        tflowLogo.setVisible(true);
        spaneMain.setDividerPosition(0, (this.getScene().getWidth() - spaneRightColumn.getMinWidth()) / this.getScene().getWidth());
        spaneRightColumn.setMinWidth(this.getScene().getWidth() * RIGHT_COLUMN_MIN_MULTIPLIER);
        spaneRightColumn.setMaxWidth(this.getScene().getWidth() * RIGHT_COLUMN_MAX_MULTIPLIER);
        spaneMain.setMaxWidth(this.getScene().getWidth());
        spaneMain.setMinWidth(this.getScene().getWidth());
        spaneMain.setMaxHeight(this.getScene().getWidth() - menuBar.getHeight());
        spaneMain.setMinWidth(this.getScene().getWidth() - menuBar.getHeight());
        paneGeneral.setMaxWidth(this.getScene().getWidth());
        paneGeneral.setMinWidth(this.getScene().getWidth());
        lvHistory.setPrefHeight(spaneRightColumn.getHeight() - (spaneRightColumn.getHeight() * spaneRightColumn.getDividers().get(0).getPosition()) - lblHistory.getHeight() - btnUndo.getHeight());
    }

    private void initToolStatus() {
        toolStatus.put(ViewTools.EXPOSURE, new MutablePair<>(ViewTools.EXPOSURE.getDefaultValue(), true));
        toolStatus.put(ViewTools.CONTRAST, new MutablePair<>(ViewTools.CONTRAST.getDefaultValue(), true));
        toolStatus.put(ViewTools.BRIGHTNESS, new MutablePair<>(ViewTools.BRIGHTNESS.getDefaultValue(), true));
        toolStatus.put(ViewTools.WHITEBALANCE, new MutablePair<>(ViewTools.WHITEBALANCE.getDefaultValue(), true));
        toolStatus.put(ViewTools.SATURATION, new MutablePair<>(ViewTools.SATURATION.getDefaultValue(), true));
        toolStatus.put(ViewTools.HUE, new MutablePair<>(ViewTools.HUE.getDefaultValue(), true));
        toolStatus.put(ViewTools.VIBRANCE, new MutablePair<>(ViewTools.VIBRANCE.getDefaultValue(), true));
        toolStatus.put(ViewTools.SCR, new MutablePair<>(ViewTools.SCR.getDefaultValue(), true));
        toolStatus.put(ViewTools.SCG, new MutablePair<>(ViewTools.SCG.getDefaultValue(), true));
        toolStatus.put(ViewTools.SCB, new MutablePair<>(ViewTools.SCB.getDefaultValue(), true));
        //to resolve the fact that if the user presses apply on default
        // values the bw won't apply
        toolStatus.put(ViewTools.BWR, new MutablePair<>(slBWR.getMin() - 1, true));
        toolStatus.put(ViewTools.BWG, new MutablePair<>(slBWG.getMin() - 1, true));
        toolStatus.put(ViewTools.BWB, new MutablePair<>(slBWB.getMin() - 1, true));
    }

    // This function adds all the components listeners.
    private void addListeners() {
        addComponentProperties(tfExposure, slExposure, ViewTools.EXPOSURE);
        addComponentProperties(tfContrast, slContrast, ViewTools.CONTRAST);
        addComponentProperties(tfBrightness, slBrightness, ViewTools.BRIGHTNESS);
        addComponentProperties(tfWhitebalance, slWhitebalance, ViewTools.WHITEBALANCE);
        addComponentProperties(tfSaturation, slSaturation, ViewTools.SATURATION);
        addComponentProperties(tfHue, slHue, ViewTools.HUE);
        addComponentProperties(tfVibrance, slVibrance, ViewTools.VIBRANCE);
        addComponentProperties(tfSCR, slSCR, ViewTools.SCR);
        addComponentProperties(tfSCG, slSCG, ViewTools.SCG);
        addComponentProperties(tfSCB, slSCB, ViewTools.SCB);
        addComponentProperties(tfBWR, slBWR, ViewTools.BWR);
        addComponentProperties(tfBWG, slBWG, ViewTools.BWG);
        addComponentProperties(tfBWB, slBWB, ViewTools.BWB);
    }

    private void addComponentProperties(final JFXTextField tfield, final JFXSlider slider, final ViewTools tool) {
        tfield.textProperty().addListener((ev, ov, nv) -> {
            try {
                final int newValue = Integer.parseInt(nv);
                if (newValue >= slider.getMin() && newValue <= slider.getMax()) {
                    slider.setValue(newValue);
                    toolStatus.get(tool).setSecond(true);
                    tfield.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfield.setStyle("-jfx-focus-color: red");
                toolStatus.get(tool).setSecond(false);
            }
        });
        slider.valueProperty().addListener((ev, ov, nv) -> {
            tfield.setText(String.format("%d", nv.intValue()));
        });
        slider.focusedProperty().addListener((ev, ov, nv) -> {
            if (nv) {
                tfield.setText(String.format("%d", (int) slider.getValue()));
            }
        });
    }

    private void setEventListeners() {
        checkManager();
        // exposure
        addKeyListener(tfExposure, KeyCode.ENTER, ViewTools.EXPOSURE, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.EXPOSURE).setFirst(Integer.parseInt(tfExposure.getText()));
            this.getManager().setExposure(toolStatus.get(ViewTools.EXPOSURE).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Exposure set to: " + toolStatus.get(ViewTools.EXPOSURE).getFirst().intValue()));
        }));
        addKeyListener(slExposure, KeyCode.ENTER, ViewTools.EXPOSURE, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.EXPOSURE).setFirst(Integer.parseInt(tfExposure.getText()));
            this.getManager().setExposure(toolStatus.get(ViewTools.EXPOSURE).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Exposure set to: " + toolStatus.get(ViewTools.EXPOSURE).getFirst().intValue()));
        }));
        // contrast
        addKeyListener(tfContrast, KeyCode.ENTER, ViewTools.CONTRAST, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.CONTRAST).setFirst(Integer.parseInt(tfContrast.getText()));
            this.getManager().setContrast(toolStatus.get(ViewTools.CONTRAST).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Contrast set to: " + toolStatus.get(ViewTools.CONTRAST).getFirst().intValue()));
        }));
        addKeyListener(slContrast, KeyCode.ENTER, ViewTools.CONTRAST, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.CONTRAST).setFirst(Integer.parseInt(tfContrast.getText()));
            this.getManager().setContrast(toolStatus.get(ViewTools.CONTRAST).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Contrast set to: " + toolStatus.get(ViewTools.CONTRAST).getFirst().intValue()));
        }));
        // brightness
        addKeyListener(tfBrightness, KeyCode.ENTER, ViewTools.BRIGHTNESS, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.BRIGHTNESS).setFirst(Integer.parseInt(tfBrightness.getText()));
            this.getManager().setBrightness(toolStatus.get(ViewTools.BRIGHTNESS).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Brightness set to: " + toolStatus.get(ViewTools.BRIGHTNESS).getFirst().intValue()));
        }));
        addKeyListener(slBrightness, KeyCode.ENTER, ViewTools.BRIGHTNESS, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.BRIGHTNESS).setFirst(Integer.parseInt(tfBrightness.getText()));
            this.getManager().setBrightness(toolStatus.get(ViewTools.BRIGHTNESS).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Brightness set to: " + toolStatus.get(ViewTools.BRIGHTNESS).getFirst().intValue()));
        }));
        // wb
        addKeyListener(tfWhitebalance, KeyCode.ENTER, ViewTools.WHITEBALANCE, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.WHITEBALANCE).setFirst(Integer.parseInt(tfWhitebalance.getText()));
            this.getManager().setWhiteBalance(toolStatus.get(ViewTools.WHITEBALANCE).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("White Balance set to: " + toolStatus.get(ViewTools.WHITEBALANCE).getFirst().intValue()));
        }));
        addKeyListener(slWhitebalance, KeyCode.ENTER, ViewTools.WHITEBALANCE, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.WHITEBALANCE).setFirst(Integer.parseInt(tfWhitebalance.getText()));
            this.getManager().setWhiteBalance(toolStatus.get(ViewTools.WHITEBALANCE).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("White Balance set to: " + toolStatus.get(ViewTools.WHITEBALANCE).getFirst().intValue()));
        }));
        // saturation
        addKeyListener(tfSaturation, KeyCode.ENTER, ViewTools.SATURATION, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.SATURATION).setFirst(Integer.parseInt(tfSaturation.getText()));
            this.getManager().setSaturation(toolStatus.get(ViewTools.SATURATION).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Saturation set to: " + toolStatus.get(ViewTools.SATURATION).getFirst().intValue()));
        }));
        addKeyListener(slSaturation, KeyCode.ENTER, ViewTools.SATURATION, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.SATURATION).setFirst(Integer.parseInt(tfSaturation.getText()));
            this.getManager().setSaturation(toolStatus.get(ViewTools.SATURATION).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Saturation set to: " + toolStatus.get(ViewTools.SATURATION).getFirst().intValue()));
        }));
        // hue
        addKeyListener(tfHue, KeyCode.ENTER, ViewTools.HUE, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.HUE).setFirst(Integer.parseInt(tfHue.getText()));
            this.getManager().setHue(toolStatus.get(ViewTools.HUE).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Hue set to: " + toolStatus.get(ViewTools.HUE).getFirst().intValue()));
        }));
        addKeyListener(slHue, KeyCode.ENTER, ViewTools.HUE, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.HUE).setFirst(Integer.parseInt(tfHue.getText()));
            this.getManager().setHue(toolStatus.get(ViewTools.HUE).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Hue set to: " + toolStatus.get(ViewTools.HUE).getFirst().intValue()));
        }));
        // vibrance
        addKeyListener(tfVibrance, KeyCode.ENTER, ViewTools.VIBRANCE, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.VIBRANCE).setFirst(Integer.parseInt(tfVibrance.getText()));
            this.getManager().setVibrance(toolStatus.get(ViewTools.VIBRANCE).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Vibrance set to: " + toolStatus.get(ViewTools.VIBRANCE).getFirst().intValue()));
        }));
        addKeyListener(slVibrance, KeyCode.ENTER, ViewTools.VIBRANCE, createCompleteRunnable(() -> {
            toolStatus.get(ViewTools.VIBRANCE).setFirst(Integer.parseInt(tfVibrance.getText()));
            this.getManager().setVibrance(toolStatus.get(ViewTools.VIBRANCE).getFirst().intValue());
            Platform.runLater(() -> lvHistory.getItems().add("Vibrance set to: " + toolStatus.get(ViewTools.VIBRANCE).getFirst().intValue()));
        }));
        btnSCApply.setOnMouseClicked(ev -> {
            if (toolStatus.get(ViewTools.SCR).getSecond() && toolStatus.get(ViewTools.SCG).getSecond() && toolStatus.get(ViewTools.SCB).getSecond()) {
                runNewThread("Selective Color", createCompleteRunnable(() -> {
                    toolStatus.get(ViewTools.SCR).setFirst((int) slSCR.getValue());
                    toolStatus.get(ViewTools.SCG).setFirst((int) slSCG.getValue());
                    toolStatus.get(ViewTools.SCB).setFirst((int) slSCB.getValue());
                    this.getManager().setSelectiveColors(toolStatus.get(ViewTools.SCR).getFirst().intValue(),
                            toolStatus.get(ViewTools.SCG).getFirst().intValue(), toolStatus.get(ViewTools.SCB).getFirst().intValue());
                    Platform.runLater(() -> lvHistory.getItems().add("Sel. Col. set to: " + toolStatus.get(ViewTools.SCR).getFirst().intValue() + ", "
                            + toolStatus.get(ViewTools.SCG).getFirst().intValue() + " ," + toolStatus.get(ViewTools.SCB).getFirst().intValue()));
                }));
            }
        });
        btnBWApply.setOnMouseClicked(ev -> {
            if (toolStatus.get(ViewTools.BWR).getSecond() && toolStatus.get(ViewTools.BWG).getSecond() && toolStatus.get(ViewTools.BWB).getSecond()) {
                runNewThread("Black n White", createCompleteRunnable(() -> {
                    toolStatus.get(ViewTools.BWR).setFirst((int) slBWR.getValue());
                    toolStatus.get(ViewTools.BWG).setFirst((int) slBWG.getValue());
                    toolStatus.get(ViewTools.BWB).setFirst((int) slBWB.getValue());
                    this.getManager().setBlackAndWhite(toolStatus.get(ViewTools.BWR).getFirst().intValue(),
                            toolStatus.get(ViewTools.BWG).getFirst().intValue(), toolStatus.get(ViewTools.BWB).getFirst().intValue());
                    Platform.runLater(() -> lvHistory.getItems().add("B&W set to: " + toolStatus.get(ViewTools.BWR).getFirst().intValue() + ", "
                            + toolStatus.get(ViewTools.BWG).getFirst().intValue() + " ," + toolStatus.get(ViewTools.BWB).getFirst().intValue()));
                }));
            }
        });
        // set imageView width according to the divider position
        spaneMain.getDividers().get(0).positionProperty().addListener((ev, ov, nv) -> {
            if ((int) (this.getScene().getWidth() * nv.doubleValue()) + spaneRightColumn.getMinWidth() < spaneMain
                    .getMaxWidth()) {
                iviewImage.setFitWidth((int) (this.getScene().getWidth() * nv.doubleValue()) - 2); // if not -2 the scene resizes (idk why)
                iviewImage.updateRealSizes();
            }
        });
        //set history table height according to the divider position
        spaneRightColumn.getDividers().get(0).positionProperty().addListener((ev, ov, nv) -> {
            lvHistory.setPrefHeight(spaneRightColumn.getHeight() - (spaneRightColumn.getHeight() * nv.doubleValue()) - lblHistory.getHeight() - btnUndo.getHeight());
        });
    }

    private void addKeyListener(final JFXTextField node, final KeyCode kc, final ViewTools tool, final Runnable rn) {
        node.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(kc) && toolStatus.get(tool).getSecond()) {
                runNewThread(tool.toString(), rn);
            }
        });
    }

    private void addKeyListener(final JFXSlider node, final KeyCode kc, final ViewTools tool, final Runnable rn) {
        node.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(kc)) {
                runNewThread(tool.toString(), rn);
            }
        });
    }

    private Runnable createCompleteRunnable(final Runnable rn) {
        return () -> {
            this.getScene().setCursor(Cursor.WAIT);
            rn.run();
            Platform.runLater(() -> {
                updateImage();
                this.getScene().setCursor(Cursor.DEFAULT);
            });
        };
    }

    private void runNewThread(final String threadName, final Runnable task) {
        new Thread(task, threadName).start();
    }
}
