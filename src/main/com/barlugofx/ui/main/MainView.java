package com.barlugofx.ui.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.barlugofx.model.imageTools.Image;
import com.barlugofx.model.imageTools.ImageImpl;
import com.barlugofx.model.imageTools.ImageUtilities;
import com.barlugofx.model.tools.Brightness;
import com.barlugofx.model.tools.common.ImageFilter;
import com.barlugofx.model.tools.common.ParameterImpl;
import com.barlugofx.model.tools.common.ParametersName;
import com.barlugofx.ui.Animations;
import com.barlugofx.ui.loading.LoadingView;

import apple.laf.JRSUIUtils.ScrollBar;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class creates the main view. It must be called by its constructor method.
 */
public class MainView {
    //private constant fields
    private static final String STAGE_TITLE = "BarlugoFX";
    private static final double ANIM_MILLIS = 1000.0;
    private static final double ANIM_STEP = 10.0;

    private Stage stage;
    private Parent root;
    private Scene scene;
    private final Dimension screenDimension;
    /**
     * @param stage
     * @param file
     * @throws IOException 
     */
    public MainView(final Stage stage, final File file) throws IOException {
        //init stage
        LoadingView t = new LoadingView(stage);
        this.stage = stage;
        stage.setTitle(STAGE_TITLE);
        //temp
        final BufferedImage image = ImageIO.read(file);
        final Image toWorkWith = ImageImpl.buildFromBufferedImage(image);
        //
        screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL("file:res/fxml/FXMLMain.fxml"));
        root = loader.load();
        scene = new Scene(root, screenDimension.getWidth(), screenDimension.getHeight());
        MainController mc = loader.getController();
        mc.setImage(toWorkWith);
        mc.setStage(stage);
        
        
        //load file, do initial operations (history init, ecc) by controller (to do)
        t.stop();
        
        Timeline tl = Animations.resizeToFullScreen(Duration.millis(ANIM_MILLIS), stage, ANIM_STEP, screenDimension);
        tl.setOnFinished(e -> {
            stage.setScene(scene);
        });
        tl.play();
        
        stage.show();
        
        
    }
}
