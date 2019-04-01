package barlugofx.view.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import barlugofx.app.AppManager;
import barlugofx.app.AppManagerImpl;
import barlugofx.view.AbstractView;
import barlugofx.view.AnimationUtils;
import barlugofx.view.loading.LoadingView;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * This class creates the main view. It must be called by its constructor method.
 */
public class MainView extends AbstractView<MainController> {
    //private constant fields
    private static final double ANIM_MILLIS = 400.0;
    private static final double ANIM_STEP = 50.0;

    /**
     * @param stage the input stage
     * @param file the file chosen by the user
     * @throws IOException
     */
    public MainView(final Stage stage, final File file) {
        super("BarlugoFX", "file:res/img/logo.png", stage, new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
                            (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight())));
        final LoadingView t = new LoadingView(stage);
        try {
            this.loadFXML("file:res/fxml/FXMLMain.fxml");
        } catch (IOException e) {
            //log!!!!!
            e.printStackTrace();
        }
        new Thread(() ->  {
            AppManager manager;
            try {
                manager = new AppManagerImpl(file);
            } catch (IOException e) {
                //TODO log
                //TODO error view
                e.printStackTrace();
                return;
            }
            final FadeTransition loadingOut = t.getFadeOutTransition();
            loadingOut.setOnFinished(fadeOutEvent -> {
                Platform.runLater(() -> {
                    this.getStage().setScene(null);
                    this.getStage().setResizable(true);
                });
                //this is performed after the animation finish because if not the view is closed too strongly.
                final Timeline stageTimeline = AnimationUtils.resizeToFullScreen(Duration.millis(ANIM_MILLIS), stage, ANIM_STEP, new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
                stageTimeline.setOnFinished(timelineEvent -> { 
                	this.getStage().setMaximized(true);
                    this.getStage().setScene(this.getScene());
                    Platform.runLater(() -> {
                        //calls the controller setStage function after the scene set because I need the components sizes on the screen, 
                        //and they are initialized only with the new scene set
                        this.getController().setStage(this.getStage());
                        this.getController().setManager(manager);
                        System.out.println("M " + stage.getWidth() + " " +stage.getHeight());
                    });
                });
                Platform.runLater(() -> {
                    stageTimeline.play();
                });
            });
            Platform.runLater(() -> loadingOut.play());
        }).start();
    }
}
