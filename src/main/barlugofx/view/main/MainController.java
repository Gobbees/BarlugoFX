package barlugofx.view.main;

//to silence the warning
import static barlugofx.view.main.Tool.BRIGHTNESS;
import static barlugofx.view.main.Tool.BWB;
import static barlugofx.view.main.Tool.BWG;
import static barlugofx.view.main.Tool.BWR;
import static barlugofx.view.main.Tool.CONTRAST;
import static barlugofx.view.main.Tool.EXPOSURE;
import static barlugofx.view.main.Tool.HUE;
import static barlugofx.view.main.Tool.SATURATION;
import static barlugofx.view.main.Tool.SCB;
import static barlugofx.view.main.Tool.SCG;
import static barlugofx.view.main.Tool.SCR;
import static barlugofx.view.main.Tool.VIBRANCE;
import static barlugofx.view.main.Tool.WHITEBALANCE;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import barlugofx.controller.AppManager;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.utils.Format;
import barlugofx.utils.MutablePair;
import barlugofx.view.AbstractView;
import barlugofx.view.InputOutOfBoundException;
import barlugofx.view.ViewController;
import barlugofx.view.export.ExportView;
import barlugofx.view.main.components.tools.CropArea;
import barlugofx.view.main.components.tools.RotateLine;
import barlugofx.view.preset.PresetView;
import barlugofx.view.main.components.zoompane.ZoomDirection;
import barlugofx.view.main.components.zoompane.ZoomableImageView;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
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
public final class MainController implements ViewController {
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
    private Scene scene;
    private Stage stage;
    private AppManager manager;
    private Optional<ExportView> exportView;
    private Optional<PresetView> presetView;
    private final Map<Tool, MutablePair<Number, Boolean>> toolStatus;

    /**
     * The constructor of the class. It is public because FXML obligates to do so.
     */
    public MainController() {
        exportView = Optional.empty();
        presetView = Optional.empty();
        toolStatus = new HashMap<>();
    }

    @Override
    public void setStage(final Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("The stage must not be null");
        }
        this.stage = stage;
        this.scene = stage.getScene();
        initComponentSize();
        initToolStatus();
        addListeners();
        addKeyboardShortcuts();
        // stage closing
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
    public void setManager(final AppManager manager) {
        if (stage == null) {
            throw new IllegalArgumentException("The manager must not be null");
        }
        this.manager = manager;
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
    public void newPhoto() { // TODO reset history and all components
        // TODO discard changes
        checkManager();
        final FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Select an image", Format.getAllPossibleInputs()));
        final File file = fc.showOpenDialog(stage);
        if (file != null) {
            runNewThread("New photo", createCompleteRunnable(() -> {
                try {
                    manager.setImage(file);
                    Platform.runLater(() -> {
                        stage.setTitle(manager.getInputFileName());
                    });
                } catch (IOException e) {
                    AbstractView.showErrorAlert(e.getMessage());
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
        if (exportView.isPresent()) {
            exportView.get().closeStage();
        }
        exportView = Optional.of(new ExportView(manager));
    }

    /**
     * Rotate event triggered.
     */
    @FXML
    public void rotate() {
        checkManager();
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
                scene.setCursor(Cursor.DEFAULT);
                return;
            }
            startX.set(rotateLine.get().getStartPoint().getCenterX());
            startY.set(rotateLine.get().getStartPoint().getCenterY());
            apaneImage.setCursor(Cursor.WAIT);
            rotateLine.get().removeFromPane(apaneImage);
            runNewThread("Rotator", () -> {
                manager.rotate(rotateLine.get().getAngle());
                Platform.runLater(() -> {
                    updateImage();
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
            apaneImage.getChildren().clear();
            rotateLine.set(new RotateLine(e.getX(), e.getY(), e.getX(), e.getY()));
            apaneImage.setOnMousePressed(null);
            apaneImage.setOnMouseDragged(mDragged);
        };
        apaneImage.setOnMousePressed(mPressed);
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE)) {
                if (rotateLine != null) {
                    rotateLine.get().removeFromPane(apaneImage);
                }
                apaneImage.setOnMousePressed(mPressed);
                apaneImage.setOnMouseDragged(null);
                scene.setOnKeyPressed(null);
                apaneImage.setOnMouseReleased(ev -> {
                    enableZoomAndColumnResize();
                    apaneImage.setOnMouseReleased(null);
                });
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
        spaneRightColumn.setMaxWidth(spaneRightColumn.getWidth());
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
        scene.setOnKeyPressed(ke -> {
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
                    manager.crop(x1, y1, x2, y2);
                    Platform.runLater(() -> {
                        updateImage();
                        cropper.removeFromPane(apaneImage);
                        apaneImage.setOnMouseDragged(null);
                        apaneImage.setOnMouseReleased(null);
                        scene.setOnKeyPressed(null);
                        enableZoomAndColumnResize();
                    });
                });
            } else if (ke.getCode().equals(KeyCode.ESCAPE)) {
                cropper.removeFromPane(apaneImage);
                apaneImage.setOnMouseDragged(null);
                apaneImage.setOnMouseReleased(null);
                scene.setOnKeyPressed(null);
                enableZoomAndColumnResize();
            }
        });
    }

    /**
     * Preset event triggered.
     */
    @FXML
    public void preset() {
        if (presetView.isPresent()) {
            presetView.get().closeStage();
        }
        presetView = Optional.of(new PresetView(manager));
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
        final File input = fc.showOpenDialog(stage);
        if (input == null) {
            System.out.println("ciao");
            return;
        }
        final Properties properties = new Properties();
        String filterName = "";
        int value;
        int[] values;
        final Class<?>[] paramTypes = { int.class, int.class, int.class };
        Method m;
        try {
            final FileInputStream fStream = new FileInputStream(input);
            properties.load(fStream);
            fStream.close();
            final Enumeration<?> e = properties.propertyNames();
            while (e.hasMoreElements()) {
                filterName = (String) e.nextElement();
                if (filterName.equals("SelectiveColors") || filterName.equals("BlackAndWhite")) {
                    values = Arrays.asList(properties.getProperty(filterName).split(",")).stream()
                            .mapToInt(Integer::parseInt).toArray();
                    m = manager.getClass().getDeclaredMethod("set" + filterName, paramTypes);
                    m.invoke(manager, values[0], values[1], values[2]);
                } else {
                    value = Integer.parseInt(properties.getProperty(filterName));
                    m = manager.getClass().getDeclaredMethod("set" + filterName, paramTypes[0]);
                    m.invoke(manager, value);
                }
            }
            updateImage();
        } catch (InvocationTargetException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            System.out.println("vaccamado");
            showErrorMessage();
        } catch (NoSuchMethodException | SecurityException | IOException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("porcodio");
        }
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
        stage.setFullScreen(true);
    }

    /**
     * Minimize event triggered.
     */
    @FXML
    public void toggleMinimize() {
        checkStage();
        stage.setIconified(true);
    }

    /**
     * About clicked.
     */
    @FXML
    public void about() {
        try {
            Desktop.getDesktop().browse(new URI("https://gobbees.github.io/BarlugoFX"));
        } catch (IOException | URISyntaxException e) {
            AbstractView.showErrorAlert(e.getMessage());
            e.printStackTrace();
        }
    }

    // updates the image and the real sizes
    private void updateImage() {
        iviewImage.setImage(
                SwingFXUtils.toFXImage(ImageUtils.convertImageToBufferedImageWithAlpha(manager.getImage()), null));
        // System.gc(); //it is necessary since I don't know why (testing with
        // jvisualvm) the gc doesn't perform after this operation.
        iviewImage.setFitWidth(apaneImage.getWidth());
        iviewImage.setFitHeight(spaneMain.getHeight());
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
        apaneImage.setOnMouseClicked(e -> { // TODO Tutorial
            if (e.getClickCount() == 2) {
                resizeToDefault();
            }
        });
        spaneRightColumn.setMaxWidth(scene.getWidth() * RIGHT_COLUMN_MAX_MULTIPLIER);
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

    // this function initializes all the components sizes in relation to the screen
    // size.
    private void initComponentSize() {
        tflowLogo.setStyle("-fx-font-size: " + menuBar.getHeight());
        tflowLogo.setVisible(true);
        spaneRightColumn.setMinWidth(scene.getWidth() * RIGHT_COLUMN_MIN_MULTIPLIER);
        spaneRightColumn.setMaxWidth(scene.getWidth() * RIGHT_COLUMN_MAX_MULTIPLIER);
        spaneMain.setDividerPosition(0, spaneRightColumn.getWidth());
        spaneMain.setMaxWidth(scene.getWidth());
    }

    private void initToolStatus() {
        toolStatus.put(EXPOSURE, new MutablePair<>(slExposure.getValue(), true));
        toolStatus.put(CONTRAST, new MutablePair<>(slContrast.getValue(), true));
        toolStatus.put(BRIGHTNESS, new MutablePair<>(slBrightness.getValue(), true));
        toolStatus.put(WHITEBALANCE, new MutablePair<>(slWhitebalance.getValue(), true));
        toolStatus.put(SATURATION, new MutablePair<>(slSaturation.getValue(), true));
        toolStatus.put(HUE, new MutablePair<>(slHue.getValue(), true));
        toolStatus.put(VIBRANCE, new MutablePair<>(slVibrance.getValue(), true));
        toolStatus.put(SCR, new MutablePair<>(slSCR.getValue(), true));
        toolStatus.put(SCG, new MutablePair<>(slSCG.getValue(), true));
        toolStatus.put(SCB, new MutablePair<>(slSCB.getValue(), true));
        // TODO temp: to resolve the fact that if the user presses apply on default
        // values the bw won't apply
        toolStatus.put(BWR, new MutablePair<>(slBWR.getMin() - 1, true));
        toolStatus.put(BWG, new MutablePair<>(slBWG.getMin() - 1, true));
        toolStatus.put(BWB, new MutablePair<>(slBWB.getMin() - 1, true));
    }

    // This function adds all the components listeners.
    private void addListeners() {
        addComponentProperties(tfExposure, slExposure, EXPOSURE);
        addComponentProperties(tfContrast, slContrast, CONTRAST);
        addComponentProperties(tfBrightness, slBrightness, BRIGHTNESS);
        addComponentProperties(tfWhitebalance, slWhitebalance, WHITEBALANCE);
        addComponentProperties(tfSaturation, slSaturation, SATURATION);
        addComponentProperties(tfHue, slHue, HUE);
        addComponentProperties(tfVibrance, slVibrance, VIBRANCE);
        addComponentProperties(tfSCR, slSCR, SCR);
        addComponentProperties(tfSCG, slSCG, SCG);
        addComponentProperties(tfSCB, slSCB, SCB);
        addComponentProperties(tfBWR, slBWR, BWR);
        addComponentProperties(tfBWG, slBWG, BWG);
        addComponentProperties(tfBWB, slBWB, BWB);
    }

    private void addComponentProperties(final JFXTextField tfield, final JFXSlider slider, final Tool tool) {
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
        addKeyListener(tfExposure, KeyCode.ENTER, EXPOSURE, createCompleteRunnable(() -> {
            toolStatus.get(EXPOSURE).setFirst(Integer.parseInt(tfExposure.getText()));
            manager.setExposure(toolStatus.get(EXPOSURE).getFirst().intValue());
        }));
        addKeyListener(slExposure, KeyCode.ENTER, EXPOSURE, createCompleteRunnable(() -> {
            toolStatus.get(EXPOSURE).setFirst(Integer.parseInt(tfExposure.getText()));
            manager.setExposure(toolStatus.get(EXPOSURE).getFirst().intValue());
        }));
        // contrast
        addKeyListener(tfContrast, KeyCode.ENTER, CONTRAST, createCompleteRunnable(() -> {
            toolStatus.get(CONTRAST).setFirst(Integer.parseInt(tfContrast.getText()));
            manager.setContrast(toolStatus.get(CONTRAST).getFirst().intValue());
        }));
        addKeyListener(slContrast, KeyCode.ENTER, CONTRAST, createCompleteRunnable(() -> {
            toolStatus.get(CONTRAST).setFirst(Integer.parseInt(tfContrast.getText()));
            manager.setContrast(toolStatus.get(CONTRAST).getFirst().intValue());
        }));
        // brightness
        addKeyListener(tfBrightness, KeyCode.ENTER, BRIGHTNESS, createCompleteRunnable(() -> {
            toolStatus.get(BRIGHTNESS).setFirst(Integer.parseInt(tfBrightness.getText()));
            manager.setBrightness(toolStatus.get(BRIGHTNESS).getFirst().intValue());
        }));
        addKeyListener(slBrightness, KeyCode.ENTER, BRIGHTNESS, createCompleteRunnable(() -> {
            toolStatus.get(BRIGHTNESS).setFirst(Integer.parseInt(tfBrightness.getText()));
            manager.setBrightness(toolStatus.get(BRIGHTNESS).getFirst().intValue());
        }));
        // wb
        addKeyListener(tfWhitebalance, KeyCode.ENTER, WHITEBALANCE, createCompleteRunnable(() -> {
            toolStatus.get(WHITEBALANCE).setFirst(Integer.parseInt(tfWhitebalance.getText()));
            manager.setWhiteBalance(toolStatus.get(WHITEBALANCE).getFirst().intValue());
        }));
        addKeyListener(slWhitebalance, KeyCode.ENTER, WHITEBALANCE, createCompleteRunnable(() -> {
            toolStatus.get(WHITEBALANCE).setFirst(Integer.parseInt(tfWhitebalance.getText()));
            manager.setWhiteBalance(toolStatus.get(WHITEBALANCE).getFirst().intValue());
        }));
        // saturation
        addKeyListener(tfSaturation, KeyCode.ENTER, SATURATION, createCompleteRunnable(() -> {
            toolStatus.get(SATURATION).setFirst(Integer.parseInt(tfSaturation.getText()));
            manager.setSaturation(toolStatus.get(SATURATION).getFirst().intValue());
        }));
        addKeyListener(slSaturation, KeyCode.ENTER, SATURATION, createCompleteRunnable(() -> {
            toolStatus.get(SATURATION).setFirst(Integer.parseInt(tfSaturation.getText()));
            manager.setSaturation(toolStatus.get(SATURATION).getFirst().intValue());
        }));
        // hue
        addKeyListener(tfHue, KeyCode.ENTER, HUE, createCompleteRunnable(() -> {
            toolStatus.get(HUE).setFirst(Integer.parseInt(tfHue.getText()));
            manager.setHue(toolStatus.get(HUE).getFirst().intValue());
        }));
        addKeyListener(slHue, KeyCode.ENTER, HUE, createCompleteRunnable(() -> {
            toolStatus.get(HUE).setFirst(Integer.parseInt(tfHue.getText()));
            manager.setHue(toolStatus.get(HUE).getFirst().intValue());
        }));
        // vibrance
        addKeyListener(tfVibrance, KeyCode.ENTER, VIBRANCE, createCompleteRunnable(() -> {
            toolStatus.get(VIBRANCE).setFirst(Integer.parseInt(tfVibrance.getText()));
            manager.setVibrance(toolStatus.get(VIBRANCE).getFirst().intValue());
        }));
        addKeyListener(slVibrance, KeyCode.ENTER, VIBRANCE, createCompleteRunnable(() -> {
            toolStatus.get(VIBRANCE).setFirst(Integer.parseInt(tfVibrance.getText()));
            manager.setVibrance(toolStatus.get(VIBRANCE).getFirst().intValue());
        }));
        btnSCApply.setOnMouseClicked(ev -> {
            if (toolStatus.get(SCR).getSecond() && toolStatus.get(SCG).getSecond() && toolStatus.get(SCB).getSecond()
                    && ((int) slSCR.getValue() != toolStatus.get(SCR).getFirst().intValue()
                            || (int) slSCG.getValue() != toolStatus.get(SCG).getFirst().intValue()
                            || (int) slSCB.getValue() != toolStatus.get(SCB).getFirst().intValue())) {
                runNewThread("Selective Color", createCompleteRunnable(() -> {
                    toolStatus.get(SCR).setFirst((int) slSCR.getValue());
                    toolStatus.get(SCG).setFirst((int) slSCG.getValue());
                    toolStatus.get(SCB).setFirst((int) slSCB.getValue());
                    manager.setSelectiveColors(toolStatus.get(SCR).getFirst().intValue(),
                            toolStatus.get(SCG).getFirst().intValue(), toolStatus.get(SCB).getFirst().intValue());
                }));
            }
        });
        btnBWApply.setOnMouseClicked(ev -> {
            if (toolStatus.get(BWR).getSecond() && toolStatus.get(BWG).getSecond() && toolStatus.get(BWB).getSecond()
                    && (int) slBWR.getValue() != toolStatus.get(BWR).getFirst().intValue()
                    || (int) slBWG.getValue() != toolStatus.get(BWG).getFirst().intValue()
                    || (int) slBWB.getValue() != toolStatus.get(BWB).getFirst().intValue()) {
                runNewThread("Black n White", createCompleteRunnable(() -> {
                    toolStatus.get(BWR).setFirst((int) slBWR.getValue());
                    toolStatus.get(BWG).setFirst((int) slBWG.getValue());
                    toolStatus.get(BWB).setFirst((int) slBWB.getValue());
                    manager.setBlackAndWhite(toolStatus.get(BWR).getFirst().intValue(),
                            toolStatus.get(BWG).getFirst().intValue(), toolStatus.get(BWB).getFirst().intValue());
                }));
            }
        });
        // set imageView width according to the divider position
        spaneMain.getDividers().get(0).positionProperty().addListener((ev, ov, nv) -> {
            if ((int) (scene.getWidth() * nv.doubleValue()) + spaneRightColumn.getMinWidth() < spaneMain
                    .getMaxWidth()) {
                iviewImage.setFitWidth((int) (scene.getWidth() * nv.doubleValue()) - 2); // if not -2 the scene resizes
                                                                                         // (idk why)
                iviewImage.updateRealSizes();
            }
        });
    }

    private void addKeyListener(final JFXTextField node, final KeyCode kc, final Tool tool, final Runnable rn) {
        node.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(kc) && toolStatus.get(tool).getSecond()
                    && Integer.parseInt(node.getText()) != toolStatus.get(tool).getFirst().intValue()) {
                runNewThread(tool.toString(), rn);
            }
        });
    }

    private void addKeyListener(final JFXSlider node, final KeyCode kc, final Tool tool, final Runnable rn) {
        node.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(kc) && (int) node.getValue() != toolStatus.get(tool).getFirst().intValue()) {
                runNewThread(tool.toString(), rn);
            }
        });
    }

    private Runnable createCompleteRunnable(final Runnable rn) {
        return () -> {
            scene.setCursor(Cursor.WAIT);
            rn.run();
            Platform.runLater(() -> {
                updateImage();
                scene.setCursor(Cursor.DEFAULT);
            });
        };
    }

    private void addKeyboardShortcuts() {
        checkStage();
        KeyCombination kc = new KeyCharacterCombination("e", KeyCombination.CONTROL_DOWN);
        Runnable runnable = () -> export();
        scene.getAccelerators().put(kc, runnable);
        kc = new KeyCharacterCombination("f", KeyCombination.CONTROL_DOWN);
        runnable = () -> toggleFullScreen();
        scene.getAccelerators().put(kc, runnable);
    }

    private void runNewThread(final String threadName, final Runnable task) {
        new Thread(task, threadName).start();
    }

    private void checkManager() {
        if (manager == null) {
            throw new IllegalStateException("The manager is null");
        }
    }

    private void checkStage() {
        if (stage == null) {
            throw new IllegalStateException("The stage is null");
        }
    }
    private void showErrorMessage() {
        final Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("The selected file is corrupted!");
        alert.showAndWait();
    }
}
