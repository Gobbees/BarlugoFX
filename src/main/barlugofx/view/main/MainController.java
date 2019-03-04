package barlugofx.view.main;

import static barlugofx.view.main.Tool.*;

import java.util.HashMap;
import java.util.Map;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import barlugofx.app.AppManager;
import barlugofx.model.imageTools.ImageUtilities;
import barlugofx.utils.MutablePair;
import barlugofx.view.main.Tool;
import barlugofx.view.InputOutOfBoundException;
import barlugofx.view.ViewController;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyCharacterCombination;
//import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * This class sets the sizes of the components in relation to the screen size and manages all the view events.
 */
public class MainController implements ViewController {
    //private constant fields
    private static final double RIGHT_COLUMN_MIN_MULTIPLIER = 0.15;
    private static final double RIGHT_COLUMN_MAX_MULTIPLIER = 0.5;

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
    private TitledPane tpaneColors;
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
    private Stage stage;
    private Scene scene;
    private AppManager manager;
    private Map<Tool, MutablePair<Number, Boolean>> toolStatus;

    /**
     * The constructor of the class. It is private because FXML obligates to do so.
     */
    public MainController() {
        toolStatus = new HashMap<>();
    }
 
    /* (non-Javadoc)
     * @see com.barlugofx.ui.ViewController#setStage(javafx.stage.Stage)
     */
    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;
        this.scene = this.stage.getScene();
        initComponentSize();
        initToolStatus();
        addListeners();
//KEYCOMBINATION
//        KeyCombination kc = new KeyCharacterCombination("+", KeyCombination.CONTROL_DOWN);
//        Runnable rn = () -> System.out.println("CIAO");
//        stage.getScene().getAccelerators().put(kc,  rn);
    }

    private void updateImage() {
        Platform.runLater(() -> {
            iviewImage.setImage(SwingFXUtils.toFXImage(ImageUtilities.convertImageToBufferedImageWithAlpha(manager.getImage()), null));
        });
    }
    //TODO
    /**
     * @param manager
     */
    public void setManager(final AppManager manager) {
        this.manager = manager;
        updateImage();
    }

    //this function initializes all the components sizes in relation to the screen size.
    private void initComponentSize() {
        tflowLogo.setStyle("-fx-font-size: " + menuBar.getHeight());
        scpaneAdjs.setFitToWidth(true);
        spaneRightColumn.setMinWidth(scene.getWidth() * RIGHT_COLUMN_MIN_MULTIPLIER);
        spaneRightColumn.setMaxWidth(scene.getWidth() * RIGHT_COLUMN_MAX_MULTIPLIER);
        iviewImage.setImage(new Image("file:res/img/logo.png"));
        iviewImage.setFitWidth(scpaneImage.getWidth());
        iviewImage.setFitHeight(scpaneImage.getHeight());
    }
    //
    private void initToolStatus() {
        toolStatus.put(EXPOSURE, new MutablePair<>(slExposure.getValue(), true));
        toolStatus.put(CONTRAST, new MutablePair<>(slContrast.getValue(), true));
        toolStatus.put(BRIGHTNESS, new MutablePair<>(slBrightness.getValue(), true));
        toolStatus.put(WHITEBALANCE, new MutablePair<>(slWhitebalance.getValue(), true));
        toolStatus.put(SATURATION, new MutablePair<>(slSaturation.getValue(), true));
        toolStatus.put(HUE, new MutablePair<>(slHue.getValue(), true));
        toolStatus.put(SCR, new MutablePair<>(slSCR.getValue(), true));
        toolStatus.put(SCG, new MutablePair<>(slSCG.getValue(), true));
        toolStatus.put(SCB, new MutablePair<>(slSCB.getValue(), true));
        toolStatus.put(BWR, new MutablePair<>(slBWR.getValue(), true));
        toolStatus.put(BWG, new MutablePair<>(slBWG.getValue(), true));
        toolStatus.put(BWB, new MutablePair<>(slBWB.getValue(), true));
    }
    //This function adds all the components listeners.
    private void addListeners() {
        addComponentProperties(tfExposure, slExposure, EXPOSURE);
        addComponentProperties(tfContrast, slContrast, CONTRAST);
        addComponentProperties(tfBrightness, slBrightness, BRIGHTNESS);
        addComponentProperties(tfWhitebalance, slWhitebalance, WHITEBALANCE);
        addComponentProperties(tfSaturation, slSaturation, SATURATION);
        addComponentProperties(tfHue, slHue, HUE);
        addComponentProperties(tfSCR, slSCR, SCR);
        addComponentProperties(tfSCG, slSCG, SCG);
        addComponentProperties(tfSCB, slSCB, SCB);
        addComponentProperties(tfBWR, slBWR, BWR);
        addComponentProperties(tfBWG, slBWG, BWG);
        addComponentProperties(tfBWB, slBWB, BWB);

        //TODO also for the others and for textfields
        tfBrightness.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER) && toolStatus.get(BRIGHTNESS).getSecond() && Integer.parseInt(tfBrightness.getText()) != toolStatus.get(BRIGHTNESS).getFirst().intValue()) {
                toolStatus.get(BRIGHTNESS).setFirst((int) slBrightness.getValue());
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
    }
    @Override
    public void resizeComponents(final int width, final int height) {
        //TODO
    }

    private void addComponentProperties(final JFXTextField tfield, final JFXSlider slider, final Tool tool) {
        tfield.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
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
            tfield.setText(nv.intValue() + "");
        });
        slider.focusedProperty().addListener((ev, ov, nv) -> {
            if (nv) {
                tfield.setText((int) slider.getValue() + "");
            }
        });
    }
}
