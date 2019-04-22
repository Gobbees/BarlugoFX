package barlugofx.view.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import barlugofx.controller.AppManager;
import barlugofx.controller.AppManagerImpl;
import barlugofx.view.View;
import barlugofx.view.AnimationUtils;
import barlugofx.view.loading.LoadingView;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class creates the main view. It must be called by its constructor method.
 */
public class MainView extends View<MainController> {
    // private constant fields
    private static final double ANIM_MILLIS = 1500.0;
    private static final double MIN_DIM_MULTIPLIER = 0.5;

    /**
     * @param stage the input stage
     * @param file  the file chosen by the user
     * @throws IOException
     */
    public MainView(final Stage stage, final File file) {
        super("", stage, new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight())));
        this.getStage().getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));
        final LoadingView t = new LoadingView(stage);
        try {
            this.loadFXML(getClass().getResource("/fxml/FXMLMain.fxml"));
        } catch (IOException e) {
            View.showErrorAlert(e.getMessage());
            e.printStackTrace();
        }
        new Thread(() -> {
            AppManager manager;
            try {
                manager = new AppManagerImpl(file);
            } catch (IOException e) {
                View.showErrorAlert(e.getMessage());
                e.printStackTrace();
                return;
            }
            final FadeTransition loadingOut = t.getFadeOutTransition();
            loadingOut.setOnFinished(fadeOutEvent -> {
                Platform.runLater(() -> {
                    this.getStage().setScene(null);
                    this.getStage().setResizable(true);
                    this.getStage().setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * MIN_DIM_MULTIPLIER);
                    this.getStage().setMinHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * MIN_DIM_MULTIPLIER);
                    this.getStage().setTitle(manager.getInputFileName());
                    AnimationUtils.fadeInTransition(Duration.millis(ANIM_MILLIS), this.getScene().getRoot()).play();
                    this.getStage().setScene(this.getScene());
                    this.getController().setStage(this.getStage());
                    this.getController().setManager(manager);
                    this.getScene().widthProperty().addListener(
                            (obs, oldVal, newVal) -> this.getController().resizeComponents(newVal.doubleValue(), this.getScene().heightProperty().get()));
                    this.getScene().heightProperty().addListener(
                            (obs, oldVal, newVal) -> this.getController().resizeComponents(this.getScene().widthProperty().get(), newVal.doubleValue()));
                });
            });
            loadingOut.play();
            Platform.runLater(() -> this.getStage().setMaximized(true));
        }, "Loading image...").start();
    }
}
