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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 *
 */
public class MainController implements ViewController {
    @FXML
    private ScrollPane spTools;
    
    @FXML
    private SplitPane spRightColumn;
    
    @FXML
    private AnchorPane apane;
    
    @FXML
    private JFXSlider slider;
    
    @FXML
    private JFXTextField tf;
    
    @FXML
    private ImageView imageView;
    //temp
    private Image image;


    /* (non-Javadoc)
     * @see com.barlugofx.ui.ViewController#setStage(javafx.stage.Stage)
     */
    @Override
    public void setStage(final Stage stage) {
        slider.setValue(0);
        slider.setMin(-100);
        slider.setMax(100);
        spTools.setFitToWidth(true);
        apane.setPrefHeight(100);
        spRightColumn.widthProperty().addListener((a, b, c) -> System.out.println(c));
        spTools.setMinWidth(200);
        tf.setText(slider.valueProperty().intValue() + "");
        slider.valueProperty().addListener((ev, ov, nv) -> {
            tf.setText(nv.intValue() + "");
            //temp
            apply(nv.intValue());
            //
        });
        tf.textProperty().addListener((ev, ov, nv) -> {
            try {
                slider.setValue(Integer.parseInt(nv));
                tf.setStyle("-fx-text-fill:#333130");
                //temp
                apply(Integer.parseInt(nv));
                //
            } catch (NumberFormatException e) {
                tf.setStyle("-fx-text-fill:red");
            }
        });
 
        
    }
    //temp
    private void apply(int value) {
        //imageView.setImage(null);
        final ImageFilter contrast = Brightness.createBrightnees();
        contrast.addParameter(ParametersName.BRIGHTNESS, new ParameterImpl<>(value));
        imageView.setImage(SwingFXUtils.toFXImage(ImageUtilities.convertImageToBufferedImageWithAlpha(contrast.applyFilter(image)), null));
        
    }
    //temp
    public void setImage(Image im) {
        image = im;
    }
    //
    
  
    
    

}
