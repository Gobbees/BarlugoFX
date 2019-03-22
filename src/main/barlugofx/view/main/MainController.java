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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import barlugofx.app.AppManager;
import barlugofx.model.imagetools.ImageUtils;
import barlugofx.utils.MutablePair;
import barlugofx.view.InputOutOfBoundException;
import barlugofx.view.ViewController;
import barlugofx.view.export.ExportView;
import barlugofx.view.preset.PresetView;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * This class manages the view events. IMPORTANT: set the app manager with setManager() function.
 * Creating a MainController object is useless and it probably will cause some sort of exception.
 */
public final class MainController implements ViewController {
    //private constant fields
    private static final double RIGHT_COLUMN_MIN_MULTIPLIER = 0.15;
    private static final double RIGHT_COLUMN_MAX_MULTIPLIER = 0.5;
    //TODO avoid nullpointer on manager calls.
    @FXML
    private BorderPane bpaneMain;
    @FXML
    private AnchorPane apaneMenuBar;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TextFlow tflowLogo;
    @FXML
    private SplitPane spaneMain;
    @FXML
    private ScrollPane scpaneImage;
    @FXML
    private BorderPane bpaneImage;
    @FXML
    private ImageView iviewImage;
    @FXML
    private SplitPane spaneRightColumn;
    @FXML
    private ScrollPane scpaneAdjs;
    @FXML
    private VBox vboxAdjs;
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
    private Stage st;
    private AppManager manager;
    private Optional<ExportView> exportView;
    private final Map<Tool, MutablePair<Number, Boolean>> toolStatus;
    /**
     * The constructor of the class. It is public because FXML obligates to do so.
     */
    public MainController() {
        exportView = Optional.empty();
        toolStatus = new HashMap<>();
    }
    @Override
    public void setStage(final Stage stage) {
        st = stage;
        this.scene = st.getScene();
        initComponentSize();
        initToolStatus();
        addListeners();
        addKeyboardShortcuts();
    }
    /**
     * Export event triggered.
     */
    @FXML
    public void export() {
        if(!exportView.isPresent()) {
            exportView = Optional.of(new ExportView(manager));
        }
    }
    /**
     * Preset event triggered.
     */
    @FXML
    public void preset() {
        new PresetView(manager);
    }
    /**
     * This function sets the app manager (controller). It must be called in order to avoid future errors.
     * @param manager the input manager
     */
    public void setManager(final AppManager manager) {
        this.manager = manager;
        updateImage();
        setEventListeners();
    }
    private void updateImage() {
        Platform.runLater(() -> {
            iviewImage.setImage(SwingFXUtils.toFXImage(ImageUtils.convertImageToBufferedImageWithAlpha(manager.getImage()), null));
        });
    }
    //this function initializes all the components sizes in relation to the screen size.
    private void initComponentSize() {
        tflowLogo.setStyle("-fx-font-size: " + menuBar.getHeight());
        scpaneAdjs.setFitToWidth(true);
        spaneRightColumn.setMinWidth(scene.getWidth() * RIGHT_COLUMN_MIN_MULTIPLIER);
        spaneRightColumn.setMaxWidth(scene.getWidth() * RIGHT_COLUMN_MAX_MULTIPLIER);
        spaneMain.setDividerPosition(0, spaneRightColumn.getWidth());
        iviewImage.setFitWidth(scpaneImage.getWidth());
        iviewImage.setFitHeight(scpaneImage.getHeight());
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
        //TODO temp: to resolve the fact that if the user presses apply on default values the bw won't apply
        toolStatus.put(BWR, new MutablePair<>(slBWR.getMin() - 1, true));
        toolStatus.put(BWG, new MutablePair<>(slBWG.getMin() - 1, true));
        toolStatus.put(BWB, new MutablePair<>(slBWB.getMin() - 1, true));
    }
    //This function adds all the components listeners.
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
        try {
            checkManager();
        } catch (IllegalStateException e) {
            //TODO LOG
            e.printStackTrace();
        }
        tfExposure.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(EXPOSURE).getSecond() && Integer.parseInt(tfExposure.getText()) != toolStatus.get(EXPOSURE).getFirst().intValue()) {
                toolStatus.get(EXPOSURE).setFirst(Integer.parseInt(tfExposure.getText()));
                manager.setExposure(toolStatus.get(EXPOSURE).getFirst().intValue());
                updateImage();
            }
        });
        slExposure.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && (int) slExposure.getValue() != toolStatus.get(EXPOSURE).getFirst().intValue()) {
              toolStatus.get(EXPOSURE).setFirst((int) slExposure.getValue());
              manager.setExposure(toolStatus.get(EXPOSURE).getFirst().intValue());
              updateImage();
          }
        });

        tfContrast.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(CONTRAST).getSecond() && Integer.parseInt(tfContrast.getText()) != toolStatus.get(CONTRAST).getFirst().intValue()) {
                toolStatus.get(CONTRAST).setFirst(Integer.parseInt(tfContrast.getText()));
                manager.setContrast(toolStatus.get(CONTRAST).getFirst().intValue());
                updateImage();
            }
        });
        slContrast.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && (int) slContrast.getValue() != toolStatus.get(CONTRAST).getFirst().intValue()) {
              toolStatus.get(CONTRAST).setFirst((int) slContrast.getValue());
              manager.setContrast(toolStatus.get(CONTRAST).getFirst().intValue());
              updateImage();
          }
        });

        tfBrightness.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(BRIGHTNESS).getSecond() && Integer.parseInt(tfBrightness.getText()) != toolStatus.get(BRIGHTNESS).getFirst().intValue()) {
                toolStatus.get(BRIGHTNESS).setFirst(Integer.parseInt(tfBrightness.getText()));
                manager.setBrightness(toolStatus.get(BRIGHTNESS).getFirst().intValue());
                updateImage();
            }
        });
        slBrightness.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && (int) slBrightness.getValue() != toolStatus.get(BRIGHTNESS).getFirst().intValue()) {
              toolStatus.get(BRIGHTNESS).setFirst((int) slBrightness.getValue());
              manager.setBrightness(toolStatus.get(BRIGHTNESS).getFirst().intValue());
              updateImage();
          }
        });

        tfWhitebalance.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(WHITEBALANCE).getSecond() && Integer.parseInt(tfWhitebalance.getText()) != toolStatus.get(WHITEBALANCE).getFirst().intValue()) {
                toolStatus.get(WHITEBALANCE).setFirst(Integer.parseInt(tfWhitebalance.getText()));
                manager.setWB(toolStatus.get(WHITEBALANCE).getFirst().intValue());
                updateImage();
            }
        });
        slWhitebalance.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && (int) slWhitebalance.getValue() != toolStatus.get(WHITEBALANCE).getFirst().intValue()) {
              toolStatus.get(WHITEBALANCE).setFirst((int) slWhitebalance.getValue());
              manager.setWB(toolStatus.get(WHITEBALANCE).getFirst().intValue());
              updateImage();
          }
        });

        tfSaturation.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(SATURATION).getSecond() && Integer.parseInt(tfSaturation.getText()) != toolStatus.get(SATURATION).getFirst().intValue()) {
                toolStatus.get(SATURATION).setFirst(Integer.parseInt(tfSaturation.getText()));
                manager.setSaturation(toolStatus.get(SATURATION).getFirst().intValue());
                updateImage();
            }
        });
        slSaturation.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && (int) slSaturation.getValue() != toolStatus.get(SATURATION).getFirst().intValue()) {
              toolStatus.get(SATURATION).setFirst((int) slSaturation.getValue());
              manager.setSaturation(toolStatus.get(SATURATION).getFirst().intValue());
              updateImage();
          }
        });

        tfHue.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(HUE).getSecond() && Integer.parseInt(tfHue.getText()) != toolStatus.get(HUE).getFirst().intValue()) {
                toolStatus.get(HUE).setFirst(Integer.parseInt(tfHue.getText()));
                manager.setHue(toolStatus.get(HUE).getFirst().intValue());
                updateImage();
            }
        });
        slHue.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && (int) slHue.getValue() != toolStatus.get(HUE).getFirst().intValue()) {
              toolStatus.get(HUE).setFirst((int) slHue.getValue());
              manager.setHue(toolStatus.get(HUE).getFirst().intValue());
              updateImage();
          }
        });

        tfVibrance.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(VIBRANCE).getSecond() && Integer.parseInt(tfVibrance.getText()) != toolStatus.get(VIBRANCE).getFirst().intValue()) {
                toolStatus.get(VIBRANCE).setFirst(Integer.parseInt(tfVibrance.getText()));
                manager.setVibrance(toolStatus.get(VIBRANCE).getFirst().intValue());
                updateImage();
            }
        });
        slVibrance.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && (int) slVibrance.getValue() != toolStatus.get(VIBRANCE).getFirst().intValue()) {
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
                manager.setSC(toolStatus.get(SCR).getFirst().intValue(), toolStatus.get(SCG).getFirst().intValue(), toolStatus.get(SCB).getFirst().intValue());
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
                manager.setBW(toolStatus.get(BWR).getFirst().intValue(), toolStatus.get(BWG).getFirst().intValue(), toolStatus.get(BWB).getFirst().intValue());
                updateImage();
            }
        });

        st.setOnCloseRequest(ev -> {
            if (exportView.isPresent()) {
                exportView.get().closeStage();
            }
        });
    }
    private void addKeyboardShortcuts() {
      KeyCombination kc = new KeyCharacterCombination("e", KeyCombination.CONTROL_DOWN);
      Runnable runnable = () -> export();
      scene.getAccelerators().put(kc,  runnable);
      kc = new KeyCharacterCombination("f", KeyCombination.CONTROL_DOWN);
      runnable = () -> st.setFullScreen(true);
      scene.getAccelerators().put(kc,  runnable);
    }
    private void checkManager() throws IllegalStateException {
        if (manager == null) {
            throw new IllegalStateException("The manager is null");
        }
    }
}
