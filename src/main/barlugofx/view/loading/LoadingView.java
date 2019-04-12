package barlugofx.view.loading;

import java.awt.Dimension;
import java.io.IOException;

import barlugofx.view.View;
import barlugofx.view.AnimationUtils;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *  This class switches from the past scene to a new loading scene. It must be called by its constructor method.
 *  The view is not resizable.
 */
public class LoadingView extends View<LoadingController> {
    //private constant fields
    private static final int ANIM_MILLIS = 600;
    /**
     * The constructor that initializes the scene and updates the stage.
     * @param stage the input stage where the new scene will be updated
     */
    public LoadingView(final Stage stage) {
        super("BarlugoFX", stage, new Dimension((int) stage.getScene().getWidth(), (int) stage.getScene().getHeight()));
        this.getStage().getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));
        try {
            this.loadFXML(getClass().getResource("/fxml/FXMLLoading.fxml"));
        } catch (IOException e) {
            View.showErrorAlert(e.getMessage());
            e.printStackTrace();
            return;
        }
        this.getController().setStage(this.getStage());
        final FadeTransition ft = AnimationUtils.fadeInTransition(Duration.millis(ANIM_MILLIS), this.getScene().getRoot());
        ft.play();
        this.getStage().setScene(this.getScene());
    }
    /**
     * This animation, when played, performs a fadeout on the loading scene. If you want to effectively stop the view, add on animation finish stage.setScene(null).
     * @return the fadeout animation on the current scene.
     */
    public FadeTransition getFadeOutTransition() {
        return AnimationUtils.fadeOutTransition(Duration.millis(ANIM_MILLIS), this.getScene().getRoot());
    }
}
