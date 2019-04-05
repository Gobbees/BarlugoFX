package barlugofx.view.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import barlugofx.app.AppManager;
import barlugofx.app.AppManagerImpl;
import barlugofx.view.AbstractView;
import barlugofx.view.AnimationUtils;
import barlugofx.view.loading.LoadingView;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.stage.Stage;
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
        super("", "file:res/img/logo.png", stage, new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
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
                final Timeline stageTimeline = AnimationUtils.resizeToFullScreen(Duration.millis(ANIM_MILLIS), stage, ANIM_STEP, new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
                stageTimeline.setOnFinished(timelineEvent -> {
                    this.getStage().setMaximized(true);
                    this.getStage().setTitle(manager.getInputFileName());
                    this.getStage().setScene(this.getScene());
                    Platform.runLater(() -> {
                        //calls the controller setStage function after the scene set because I need the components sizes on the screen, 
                        //and they are initialized only with the new scene set
                        this.getController().setStage(this.getStage());
                        this.getController().setManager(manager);
                        this.getScene().widthProperty().addListener((obs, oldVal, newVal) -> this.getController().resizeComponents(newVal.doubleValue(), this.getScene().heightProperty().get()));
                        this.getScene().heightProperty().addListener((obs, oldVal, newVal) -> this.getController().resizeComponents(this.getScene().widthProperty().get(), newVal.doubleValue()));
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
