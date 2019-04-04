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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import barlugofx.app.AppManager;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.utils.MutablePair;
import barlugofx.view.InputOutOfBoundException;
import barlugofx.view.ViewController;
import barlugofx.view.export.ExportView;
import barlugofx.view.main.utils.CropArea;
import barlugofx.view.main.utils.RotateLine;
import barlugofx.view.preset.PresetView;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * This class manages the view events. IMPORTANT: set the app manager with
 * setManager() function. Creating a MainController object is useless and it
 * probably will cause some sort of exception.
 */
public final class MainController implements ViewController {
    // private constant fields
    private static final double RIGHT_COLUMN_MIN_MULTIPLIER = 0.15;
    private static final double RIGHT_COLUMN_MAX_MULTIPLIER = 0.5;
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
    private ImageView iviewImage;
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
    private final Map<Tool, MutablePair<Number, Boolean>> toolStatus;
    // start coordinates of an event (crop or rotate)
    private double startX;
    private double startY;
    // real displayed image sizes
    private double realWidth;
    private double realHeight;

    /**
     * The constructor of the class. It is public because FXML obligates to do so.
     */
    public MainController() {
        exportView = Optional.empty();
        toolStatus = new HashMap<>();
    }

    @Override
    public void setStage(final Stage stage) {
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
            //TODO presetview same as export
        });
    }
    /**
     * This function sets the app manager (controller). It must be called in order
     * to avoid future errors.
     * 
     * @param manager the input manager
     */
    public void setManager(final AppManager manager) {
        this.manager = manager;
        updateImage();
        setEventListeners();
    }
    /**
     * Resizes all the components relating to the new sizes.
     * @param width the new width
     * @param height the new height
     */
    public void resizeComponents(final double width, final double height) {
        System.out.println(width + " " + height);
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
        //TODO Clear pane
        //TODO improve esc. write func to clean listeners
        final AtomicReference<RotateLine> rotateLine = new AtomicReference<>();
        apaneImage.getChildren().clear();
        scene.setCursor(Cursor.HAND);
        final EventHandler<MouseEvent> mPressed = e -> {
            apaneImage.getChildren().clear();
            startX = e.getX();
            startY = e.getY();
            e.consume();
            apaneImage.setOnMousePressed(null);
        };
        apaneImage.setOnMousePressed(mPressed);
        final EventHandler<MouseEvent> mDragged = e -> {
            apaneImage.getChildren().clear();
            if (e.getX() <= apaneImage.getWidth() - (apaneImage.getWidth() - realWidth) / 2 && e.getX() >= (apaneImage.getWidth() - realWidth) / 2 
                    && e.getY() <= apaneImage.getHeight() - (apaneImage.getWidth() - realHeight) / 2
                    && e.getY() >= (apaneImage.getHeight() - realHeight) / 2) {
                rotateLine.set(new RotateLine(startX, startY, e.getX(), e.getY()));
                rotateLine.get().addToPane(apaneImage);
            }
            e.consume();
        };
        apaneImage.setOnMouseDragged(mDragged);
        final EventHandler<MouseEvent> mReleased = e -> {
            if (rotateLine.get() == null) {
                apaneImage.setOnMouseDragged(null);
                apaneImage.setOnMouseReleased(null);
                scene.setCursor(Cursor.DEFAULT);
                return;
            }
            startX = rotateLine.get().getStartPoint().getCenterX();
            startY = rotateLine.get().getStartPoint().getCenterY();
            final double endX = rotateLine.get().getEndPoint().getCenterX();
            final double endY = rotateLine.get().getEndPoint().getCenterY();
            scene.setCursor(Cursor.WAIT);
            rotateLine.get().removeFromPane(apaneImage);
            double m;
            if (endY < startY) {
                m = Math.abs(endY - startY) / (endX - startX);
            } else if (endX < startX) {
                m = (endY - startY) / Math.abs(endX - startX);
            } else {
                m = -(endY - startY) / (endX - startX);
            }
            final double theta = Math.atan(m) * (180 / Math.PI);
            runNewThread("Rotator", () -> {
                manager.rotate(theta);
                Platform.runLater(() -> {
                    updateImage();
                    scene.setCursor(Cursor.DEFAULT);
                    apaneImage.setOnMouseDragged(null);
                    apaneImage.setOnMouseReleased(null);
                });
            });
        };
        apaneImage.setOnMouseReleased(mReleased);
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ESCAPE)) {
                apaneImage.getChildren().clear();
                scene.setCursor(Cursor.DEFAULT);
                apaneImage.setOnMouseDragged(null);
                apaneImage.setOnMouseReleased(null);
                scene.setOnKeyPressed(null);
            }
        });
    }
    /**
     * Crop event triggered.
     */
    @FXML
    public void crop() {
        //TODO manage crop after resize
        apaneImage.getChildren().clear();
        final CropArea cropper = new CropArea(realWidth / 2, realHeight / 2, apaneImage.getWidth() / 2 - realWidth / 4,
                apaneImage.getHeight() / 2 - realHeight / 4);
        cropper.addToPane(apaneImage);
        cropper.addEvent(cropper.getRectangle(), MouseEvent.MOUSE_PRESSED, e -> {
            startX = e.getX();
            startY = e.getY();
        });
        cropper.addEvent(cropper.getRectangle(), MouseEvent.MOUSE_DRAGGED, e -> { // TODO test
            if (cropper.getRectangle().getX() + e.getX() - startX > (apaneImage.getWidth() - realWidth) / 2
                    && cropper.getRectangle().getX() + cropper.getRectangle().getWidth() + e.getX()
                            - startX < apaneImage.getWidth() - (apaneImage.getWidth() - realWidth) / 2
                    && cropper.getRectangle().getY() + e.getY() - startY > (apaneImage.getHeight() - realHeight) / 2
                    && cropper.getRectangle().getY() + cropper.getRectangle().getHeight() + e.getY()
                            - startY < apaneImage.getHeight() - (apaneImage.getHeight() - realHeight) / 2) {
                cropper.drag(startX, startY, e.getX(), e.getY());
                startX = e.getX();
                startY = e.getY();
            }
            e.consume();
        });
        cropper.addEvent(cropper.getTopLeftPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            cropper.resize(e.getX(), e.getY(), cropper.getBottomRightPoint().getCenterX(),
                    cropper.getBottomRightPoint().getCenterY());
        });
        cropper.addEvent(cropper.getTopRightPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            cropper.resize(cropper.getTopLeftPoint().getCenterX(), e.getY(), e.getX(),
                    cropper.getBottomRightPoint().getCenterY());
        });
        cropper.addEvent(cropper.getBottomLeftPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            cropper.resize(e.getX(), cropper.getTopLeftPoint().getCenterY(), cropper.getBottomRightPoint().getCenterX(),
                    e.getY());
        });
        cropper.addEvent(cropper.getBottomRightPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            cropper.resize(cropper.getTopLeftPoint().getCenterX(), cropper.getTopLeftPoint().getCenterY(), e.getX(),
                    e.getY());
        });
        cropper.addEvent(cropper.getMidTopPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            cropper.resize(cropper.getTopLeftPoint().getCenterX(), e.getY(), cropper.getBottomRightPoint().getCenterX(),
                    cropper.getBottomRightPoint().getCenterY());
        });
        cropper.addEvent(cropper.getMidRightPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            cropper.resize(cropper.getTopLeftPoint().getCenterX(), cropper.getTopLeftPoint().getCenterY(), e.getX(),
                    cropper.getBottomRightPoint().getCenterY());
        });
        cropper.addEvent(cropper.getMidBottomPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            cropper.resize(cropper.getTopLeftPoint().getCenterX(), cropper.getTopLeftPoint().getCenterY(),
                    cropper.getBottomRightPoint().getCenterX(), e.getY());
        });
        cropper.addEvent(cropper.getMidLeftPoint(), MouseEvent.MOUSE_DRAGGED, e -> {
            cropper.resize(e.getX(), cropper.getTopLeftPoint().getCenterY(), cropper.getBottomRightPoint().getCenterX(),
                    cropper.getBottomRightPoint().getCenterY());
        });
        // TODO change scene with other shit
        scene.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                final int x1 = (int) ((cropper.getRectangle().getX() - (iviewImage.getFitWidth() - realWidth) / 2)
                        * iviewImage.getImage().getWidth() / realWidth);
                final int y1 = (int) ((cropper.getRectangle().getY() - (iviewImage.getFitHeight() - realHeight) / 2)
                        * iviewImage.getImage().getHeight() / realHeight);
                final int x2 = (int) ((cropper.getRectangle().getX() + cropper.getRectangle().getWidth()
                        - (iviewImage.getFitWidth() - realWidth) / 2) * iviewImage.getImage().getWidth() / realWidth);
                final int y2 = (int) ((cropper.getRectangle().getY() + cropper.getRectangle().getHeight()
                        - (iviewImage.getFitHeight() - realHeight) / 2) * iviewImage.getImage().getHeight()
                        / realHeight);
                runNewThread("Cropper", () -> {
                    manager.crop(x1, y1, x2, y2);
                    Platform.runLater(() -> {
                        updateImage();
                        cropper.removeFromPane(apaneImage);
                    });
                });
            }
        });
    }
    /**
     * Preset event triggered.
     */
    @FXML
    public void preset() {
        new PresetView(manager);
    }
    /**
     * Toggle full screen event triggered.
     */
    @FXML
    public void toggleFullScreen() {
        stage.setFullScreen(true);
    }
    /**
     * Minimize event triggered.
     */
    @FXML
    public void toggleMinimize() {
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //updates the image and the real sizes
    private void updateImage() {
        iviewImage.setImage(SwingFXUtils.toFXImage(ImageUtils.convertImageToBufferedImageWithAlpha(manager.getImage()), null));
        iviewImage.setFitWidth(apaneImage.getWidth());
        iviewImage.setFitHeight(spaneMain.getHeight());
        double aspectRatio = iviewImage.getImage().getWidth() / iviewImage.getImage().getHeight();
        realWidth = (int) Math.min(iviewImage.getFitWidth(), iviewImage.getFitHeight() * aspectRatio);
        realHeight = (int) Math.min(iviewImage.getFitHeight(), iviewImage.getFitWidth() / aspectRatio);
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
    //TODO refactoring with new thread.
    //TODO set cursor
    //TODO new threads
    private void setEventListeners() {
        try {
            checkManager();
        } catch (IllegalStateException e) {
            // TODO LOG
            e.printStackTrace();
        }
        tfExposure.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(EXPOSURE).getSecond()
                    && Integer.parseInt(tfExposure.getText()) != toolStatus.get(EXPOSURE).getFirst().intValue()) {
                toolStatus.get(EXPOSURE).setFirst(Integer.parseInt(tfExposure.getText()));
                manager.setExposure(toolStatus.get(EXPOSURE).getFirst().intValue());
                updateImage();
            }
        });
        slExposure.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)
                    && (int) slExposure.getValue() != toolStatus.get(EXPOSURE).getFirst().intValue()) {
                toolStatus.get(EXPOSURE).setFirst((int) slExposure.getValue());
                manager.setExposure(toolStatus.get(EXPOSURE).getFirst().intValue());
                updateImage();
            }
        });

        tfContrast.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(CONTRAST).getSecond()
                    && Integer.parseInt(tfContrast.getText()) != toolStatus.get(CONTRAST).getFirst().intValue()) {
                toolStatus.get(CONTRAST).setFirst(Integer.parseInt(tfContrast.getText()));
                manager.setContrast(toolStatus.get(CONTRAST).getFirst().intValue());
                updateImage();
            }
        });
        slContrast.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)
                    && (int) slContrast.getValue() != toolStatus.get(CONTRAST).getFirst().intValue()) {
                toolStatus.get(CONTRAST).setFirst((int) slContrast.getValue());
                manager.setContrast(toolStatus.get(CONTRAST).getFirst().intValue());
                updateImage();
            }
        });

        tfBrightness.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(BRIGHTNESS).getSecond()
                    && Integer.parseInt(tfBrightness.getText()) != toolStatus.get(BRIGHTNESS).getFirst().intValue()) {
                toolStatus.get(BRIGHTNESS).setFirst(Integer.parseInt(tfBrightness.getText()));
                manager.setBrightness(toolStatus.get(BRIGHTNESS).getFirst().intValue());
                updateImage();
            }
        });
        slBrightness.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)
                    && (int) slBrightness.getValue() != toolStatus.get(BRIGHTNESS).getFirst().intValue()) {
                toolStatus.get(BRIGHTNESS).setFirst((int) slBrightness.getValue());
                manager.setBrightness(toolStatus.get(BRIGHTNESS).getFirst().intValue());
                updateImage();
            }
        });

        tfWhitebalance.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(WHITEBALANCE).getSecond() && Integer
                    .parseInt(tfWhitebalance.getText()) != toolStatus.get(WHITEBALANCE).getFirst().intValue()) {
                toolStatus.get(WHITEBALANCE).setFirst(Integer.parseInt(tfWhitebalance.getText()));
                manager.setWB(toolStatus.get(WHITEBALANCE).getFirst().intValue());
                updateImage();
            }
        });
        slWhitebalance.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)
                    && (int) slWhitebalance.getValue() != toolStatus.get(WHITEBALANCE).getFirst().intValue()) {
                toolStatus.get(WHITEBALANCE).setFirst((int) slWhitebalance.getValue());
                manager.setWB(toolStatus.get(WHITEBALANCE).getFirst().intValue());
                updateImage();
            }
        });

        tfSaturation.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(SATURATION).getSecond()
                    && Integer.parseInt(tfSaturation.getText()) != toolStatus.get(SATURATION).getFirst().intValue()) {
                toolStatus.get(SATURATION).setFirst(Integer.parseInt(tfSaturation.getText()));
                manager.setSaturation(toolStatus.get(SATURATION).getFirst().intValue());
                updateImage();
            }
        });
        slSaturation.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)
                    && (int) slSaturation.getValue() != toolStatus.get(SATURATION).getFirst().intValue()) {
                toolStatus.get(SATURATION).setFirst((int) slSaturation.getValue());
                manager.setSaturation(toolStatus.get(SATURATION).getFirst().intValue());
                updateImage();
            }
        });

        tfHue.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(HUE).getSecond()
                    && Integer.parseInt(tfHue.getText()) != toolStatus.get(HUE).getFirst().intValue()) {
                toolStatus.get(HUE).setFirst(Integer.parseInt(tfHue.getText()));
                manager.setHue(toolStatus.get(HUE).getFirst().intValue());
                updateImage();
            }
        });
        slHue.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)
                    && (int) slHue.getValue() != toolStatus.get(HUE).getFirst().intValue()) {
                toolStatus.get(HUE).setFirst((int) slHue.getValue());
                manager.setHue(toolStatus.get(HUE).getFirst().intValue());
                updateImage();
            }
        });

        tfVibrance.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(VIBRANCE).getSecond()
                    && Integer.parseInt(tfVibrance.getText()) != toolStatus.get(VIBRANCE).getFirst().intValue()) {
                toolStatus.get(VIBRANCE).setFirst(Integer.parseInt(tfVibrance.getText()));
                manager.setVibrance(toolStatus.get(VIBRANCE).getFirst().intValue());
                updateImage();
            }
        });
        slVibrance.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)
                    && (int) slVibrance.getValue() != toolStatus.get(VIBRANCE).getFirst().intValue()) {
                toolStatus.get(VIBRANCE).setFirst((int) slVibrance.getValue());
                manager.setVibrance(toolStatus.get(VIBRANCE).getFirst().intValue());
                updateImage();
            }
        });

        btnSCApply.setOnMouseClicked(ev -> {
            if (toolStatus.get(SCR).getSecond() && toolStatus.get(SCG).getSecond() && toolStatus.get(SCB).getSecond()
                    && ((int) slSCR.getValue() != toolStatus.get(SCR).getFirst().intValue()
                            || (int) slSCG.getValue() != toolStatus.get(SCG).getFirst().intValue()
                            || (int) slSCB.getValue() != toolStatus.get(SCB).getFirst().intValue())) {
                toolStatus.get(SCR).setFirst((int) slSCR.getValue());
                toolStatus.get(SCG).setFirst((int) slSCG.getValue());
                toolStatus.get(SCB).setFirst((int) slSCB.getValue());
                manager.setSC(toolStatus.get(SCR).getFirst().intValue(), toolStatus.get(SCG).getFirst().intValue(),
                        toolStatus.get(SCB).getFirst().intValue());
                updateImage();
            }
        });

        btnBWApply.setOnMouseClicked(ev -> {
            if (toolStatus.get(BWR).getSecond() && toolStatus.get(BWG).getSecond() && toolStatus.get(BWB).getSecond()
                    && (int) slBWR.getValue() != toolStatus.get(BWR).getFirst().intValue()
                    || (int) slBWG.getValue() != toolStatus.get(BWG).getFirst().intValue()
                    || (int) slBWB.getValue() != toolStatus.get(BWB).getFirst().intValue()) {
                toolStatus.get(BWR).setFirst((int) slBWR.getValue());
                toolStatus.get(BWG).setFirst((int) slBWG.getValue());
                toolStatus.get(BWB).setFirst((int) slBWB.getValue());
                manager.setBW(toolStatus.get(BWR).getFirst().intValue(), toolStatus.get(BWG).getFirst().intValue(),
                        toolStatus.get(BWB).getFirst().intValue());
                updateImage();
            }
        });
        // set imageView width according to the divider position
        spaneMain.getDividers().get(0).positionProperty().addListener((ev, ov, nv) -> {
            //System.out.println(stage.getWidth() + " " + scene.getWidth());
            if ((int) (scene.getWidth() * nv.doubleValue()) + spaneRightColumn.getMinWidth() < spaneMain.getMaxWidth()) { 
                iviewImage.setFitWidth((int) (scene.getWidth() * nv.doubleValue()));
                //TODO realwidth and realheight
            }
        });
    }
    private void addKeyboardShortcuts() {
        KeyCombination kc = new KeyCharacterCombination("e", KeyCombination.CONTROL_DOWN);
        Runnable runnable = () -> export();
        scene.getAccelerators().put(kc, runnable);
        kc = new KeyCharacterCombination("f", KeyCombination.CONTROL_DOWN);
        runnable = () -> toggleFullScreen();
        scene.getAccelerators().put(kc, runnable);
    }
    private void checkManager() throws IllegalStateException {
        if (manager == null) {
            throw new IllegalStateException("The manager is null");
        }
    }
    private void runNewThread(final String threadName, final Runnable task) {
        new Thread(task, threadName).start();
    }
}
