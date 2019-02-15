package com.barlugofx.ui.main;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageUtilities;
import com.barlugofx.model.tools.Brightness;
import com.barlugofx.model.tools.common.ImageFilter;
import com.barlugofx.model.tools.common.ParameterImpl;
import com.barlugofx.model.tools.common.ParametersName;
import com.barlugofx.ui.ViewController;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 *
 */
public class MainController implements ViewController {
    //private constant fields
    private static final int TOOLBOX_HEIGHT = 70;

    @FXML
    private BorderPane bpaneMain;
    @FXML
    private AnchorPane apaneMenuBar;
    @FXML
    private MenuBar menuBar;
    @FXML
    private ImageView iviewLogo;
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
    //temp
    private Image image;
    
    
    /* (non-Javadoc)
     * @see com.barlugofx.ui.ViewController#setStage(javafx.stage.Stage)
     */
    @Override
    public void setStage(final Stage stage) {
        this.stage = stage;
        initComponentSize();
        addListeners();
        //temp
        iviewLogo.setFitHeight(menuBar.getHeight() - 10);
        //iviewLogo.setFitHeight(menuBar.getHeight());
        System.out.println(System.getProperty("os.name"));
//        IF (SYSTEM.GETPROPERTY("OS.NAME").TOLOWERCASE().EQUALS("MAC OS X")) {
//            MENUBAR.SETUSESYSTEMMENUBAR(TRUE);
//        }
        //slider related
//        slider.setValue(0);
//        slider.setMin(-100);
//        slider.setMax(100);
//        slider.valueProperty().addListener((ev, ov, nv) -> {
//            tf.setText(nv.intValue() + "");
//            tf.setStyle("-fx-text-fill: white");
//            //temp
//            //apply(nv.intValue());
//            //
//        });
        
        //IMPORTANT !!!
        
        //scrollpaneadjs related
        scpaneAdjs.setFitToWidth(true);
        scpaneAdjs.setMinWidth(255);
//        apane.setPrefHeight(TOOLBOX_HEIGHT);
        
        //spRightColumn.widthProperty().addListener((a, b, c) -> System.out.println(c));
        
//        tf.setText(slider.valueProperty().intValue() + "");
        
//        tf.textProperty().addListener((ev, ov, nv) -> {
//            try {
//                slider.setValue(Integer.parseInt(nv));
//                tf.setStyle("-fx-text-fill:#333130");
//                //temp
//                //apply(Integer.parseInt(nv));
//                //
//            } catch (NumberFormatException e) {
//                tf.setStyle("-fx-text-fill:red");
//            }
//        });
        
        KeyCombination kc = new KeyCharacterCombination("+", KeyCombination.CONTROL_DOWN);
        Runnable rn = () -> System.out.println("CIAO");
        
        stage.getScene().getAccelerators().put(kc,  rn);
 
        //iviewLogo.setFitWidth(stage.getScene().getWidth() / 12);
        AnchorPane.setRightAnchor(iviewLogo, stage.getScene().getWidth() / 60);
        
    }
    //this function initializes all the components sizes in relation to the screen size.
    private void initComponentSize() {
        
    }
    
    private void addListeners() {
        
    }
    
    
    //temp
    private void apply(int value) {
        //imageView.setImage(null);
//        final ImageFilter contrast = Brightness.createBrightnees();
//        contrast.addParameter(ParametersName.BRIGHTNESS, new ParameterImpl<>(value));
//        imageView.setImage(SwingFXUtils.toFXImage(ImageUtilities.convertImageToBufferedImageWithAlpha(contrast.applyFilter(image)), null));
        
    }
    //temp
    public void setImage(Image im) {
        image = im;
    }
    //
    @FXML
    public void print() {
        System.out.println("New");
    }
    

    
  
    
    

}
