package com.barlugofx.view.main;

import com.barlugofx.app.AppManager;
import com.barlugofx.model.imageTools.ImageUtilities;
import com.barlugofx.view.InputOutOfBoundException;
import com.barlugofx.view.ViewController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
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

    /* (non-Javadoc)
     * @see com.barlugofx.ui.ViewController#setStage(javafx.stage.Stage)
     */
    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;
        this.scene = this.stage.getScene();
        initComponentSize();
        addListeners();
//KEYCOMBINATION
//        KeyCombination kc = new KeyCharacterCombination("+", KeyCombination.CONTROL_DOWN);
//        Runnable rn = () -> System.out.println("CIAO");
//        stage.getScene().getAccelerators().put(kc,  rn);
    }

    private void setImage(final Image image) {
        iviewImage.setImage(image);
    }
    /**TODO
     * @param manager
     */
    public void setManager(final AppManager manager) {
        this.manager = manager;
        setImage(SwingFXUtils.toFXImage(ImageUtilities.convertImageToBufferedImageWithAlpha(manager.getImage()), null));
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
    //this function adds all the components listeners
    private void addListeners() {  //TODO REFACTORING
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
        tfSCR.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slSCR.getMin() && newValue <= slSCR.getMax()) {
                    slSCR.setValue(newValue);
                    tfSCR.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfSCR.setStyle("-jfx-focus-color: red");
            }
        });
        tfSCG.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slSCG.getMin() && newValue <= slSCG.getMax()) {
                    slSCG.setValue(newValue);
                    tfSCG.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfSCG.setStyle("-jfx-focus-color: red");
            }
        });
        tfSCB.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slSCB.getMin() && newValue <= slSCB.getMax()) {
                    slSCB.setValue(newValue);
                    tfSCB.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfSCB.setStyle("-jfx-focus-color: red");
            }
        });
        tfBWR.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slBWR.getMin() && newValue <= slBWR.getMax()) {
                    slBWR.setValue(newValue);
                    tfBWR.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfBWR.setStyle("-jfx-focus-color: red");
            }
        });
        tfBWG.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slBWG.getMin() && newValue <= slBWG.getMax()) {
                    slBWG.setValue(newValue);
                    tfBWG.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfBWG.setStyle("-jfx-focus-color: red");
            }
        });
        tfBWB.textProperty().addListener((ev, ov, nv) -> {
            try {
                int newValue = Integer.parseInt(nv);
                if (newValue >= slBWB.getMin() && newValue <= slBWB.getMax()) {
                    slBWB.setValue(newValue);
                    tfBWB.setStyle("-jfx-focus-color: #5affd0");
                } else {
                    throw new InputOutOfBoundException();
                }
            } catch (NumberFormatException | InputOutOfBoundException ex) {
                tfBWB.setStyle("-jfx-focus-color: red");
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
        slSCR.valueProperty().addListener((ev, ov, nv) -> {
            tfSCR.setText(nv.intValue() + "");
        });
        slSCG.valueProperty().addListener((ev, ov, nv) -> {
            tfSCG.setText(nv.intValue() + "");
        });
        slSCB.valueProperty().addListener((ev, ov, nv) -> {
            tfSCB.setText(nv.intValue() + "");
        });
        slBWR.valueProperty().addListener((ev, ov, nv) -> {
            tfBWR.setText(nv.intValue() + "");
        });
        slBWG.valueProperty().addListener((ev, ov, nv) -> {
            tfBWG.setText(nv.intValue() + "");
        });
        slBWB.valueProperty().addListener((ev, ov, nv) -> {
            tfBWB.setText(nv.intValue() + "");
        });
    }
    /**
     * 
     */
    @FXML
    public void apply() {
        manager.setBW(slBWR.getValue(), slBWG.getValue(), slBWB.getValue());
        manager.setBrightness((int) slBrightness.getValue());
        setImage(SwingFXUtils.toFXImage(ImageUtilities.convertImageToBufferedImageWithAlpha(manager.getImage()), null));
    }
    @Override
    public void resizeComponents(final int width, final int height) {
        //TODO
    }

}
