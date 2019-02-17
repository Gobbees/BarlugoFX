package com.barlugofx.ui.main;

import com.barlugofx.ui.InputOutOfBoundException;
import com.barlugofx.ui.ViewController;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCharacterCombination;
//import javafx.scene.input.KeyCombination;
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
    private Stage stage;
    private Scene scene;

    /* (non-Javadoc)
     * @see com.barlugofx.ui.ViewController#setStage(javafx.stage.Stage)
     */
    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;
        this.scene = this.stage.getScene();
        initComponentSize();
        addListeners();

//        KeyCombination kc = new KeyCharacterCombination("+", KeyCombination.CONTROL_DOWN);
//        Runnable rn = () -> System.out.println("CIAO");
//        stage.getScene().getAccelerators().put(kc,  rn);
    }

    //this function initializes all the components sizes in relation to the screen size.
    private void initComponentSize() {
        System.out.println(menuBar.getHeight());
        tflowLogo.setStyle("-fx-font-size: " + menuBar.getHeight());
        scpaneAdjs.setFitToWidth(true);
        spaneRightColumn.setMinWidth(scene.getWidth() * RIGHT_COLUMN_MIN_MULTIPLIER);
        spaneRightColumn.setMaxWidth(scene.getWidth() * RIGHT_COLUMN_MAX_MULTIPLIER);
    }
    //this function adds all the components listeners
    private void addListeners() {
        tfExposure.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slExposure.getMin() && newValue <= slExposure.getMax()) {
                    slExposure.setValue(newValue);
                    tfExposure.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfExposure.setStyle("-jfx-focus-color: red");
            }
        });
        tfContrast.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slContrast.getMin() && newValue <= slContrast.getMax()) {
                    slContrast.setValue(newValue);
                    tfContrast.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfContrast.setStyle("-jfx-focus-color: red");
            }
        });
        tfBrightness.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slBrightness.getMin() && newValue <= slBrightness.getMax()) {
                    slBrightness.setValue(newValue);
                    tfBrightness.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfBrightness.setStyle("-jfx-focus-color: red");
            }
        }); 
        tfWhitebalance.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slWhitebalance.getMin() && newValue <= slWhitebalance.getMax()) {
                    slWhitebalance.setValue(newValue);
                    tfWhitebalance.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfWhitebalance.setStyle("-jfx-focus-color: red");
            }
        });
        tfSaturation.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slSaturation.getMin() && newValue <= slSaturation.getMax()) {
                    slSaturation.setValue(newValue);
                    tfSaturation.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfSaturation.setStyle("-jfx-focus-color: red");
            }
        });
        tfHue.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slHue.getMin() && newValue <= slHue.getMax()) {
                    slHue.setValue(newValue);
                    tfHue.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfHue.setStyle("-jfx-focus-color: red");
            }
        });

        slExposure.valueProperty().addListener((ev, ov, nv) -> {
            tfExposure.setText(nv.intValue() + "");
        });
        slContrast.valueProperty().addListener((ev, ov, nv) -> {
            tfContrast.setText(nv.intValue() + "");
        });
        slBrightness.valueProperty().addListener((ev, ov, nv) -> {
            tfBrightness.setText(nv.intValue() + "");
        });
        slWhitebalance.valueProperty().addListener((ev, ov, nv) -> {
            tfWhitebalance.setText(nv.intValue() + "");
        });
        slSaturation.valueProperty().addListener((ev, ov, nv) -> {
            tfSaturation.setText(nv.intValue() + "");
        });
        slHue.valueProperty().addListener((ev, ov, nv) -> {
            tfHue.setText(nv.intValue() + "");
        });
    }
}
