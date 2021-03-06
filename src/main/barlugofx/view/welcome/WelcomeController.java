package barlugofx.view.welcome;

import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import barlugofx.utils.Format;
import barlugofx.view.AbstractViewController;
import barlugofx.view.AnimationUtils;
import barlugofx.view.main.MainView;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

/**
 * This class manages the view events (i.e. button clicks, enter and exit) and effectively resizes the nodes.
 * Creating a WelcomeController object is useless and it probably will cause some sort of exception.
 */
public final class WelcomeController extends AbstractViewController {
    //private constant fields (nodes multipliers)
    private static final double IMG_MULTIPLIER = 0.66;
    private static final double BTN_WIDTH_MULTIPLIER = 0.33;
    private static final double BTN_HEIGHT_MULTIPLIER = 1;
    private static final double BPANE_RIGHT_MULTIPLIER = 0.33;
    private static final int ANIM_MILLIS = 300;
    @FXML
    private AnchorPane apaneMain;
    @FXML
    private BorderPane bpaneLeft;
    @FXML
    private ImageView iviewIcon;
    @FXML
    private Label lblDescription;
    @FXML
    private Separator separLabel;
    @FXML
    private JFXButton btnImage;
    /**
     * Resizes the components in relation to the new sizes.
     * @param width the new width
     * @param height the new height
     */
    public void resizeComponents(final double width, final double height) {
        btnImage.setPrefWidth(width * BTN_WIDTH_MULTIPLIER);
        btnImage.setPrefHeight(height * BTN_HEIGHT_MULTIPLIER);
        AnchorPane.setRightAnchor(bpaneLeft, width * BPANE_RIGHT_MULTIPLIER);
        separLabel.setVisible(false);
        separLabel.setPrefHeight(height / 10);
        bpaneLeft.setPrefSize(width, height);
        bpaneLeft.setPrefSize(width, height);
        iviewIcon.setFitWidth(bpaneLeft.getPrefWidth() * IMG_MULTIPLIER);
        iviewIcon.setFitHeight(bpaneLeft.getPrefHeight() * IMG_MULTIPLIER);
    }
    /**
     * Called by view events, this method initiates the main view.
     * @throws IOException 
     */
    @FXML
    public void openImage() throws IOException {
        final FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Select an image", Format.getAllPossibleInputs()));
        fc.setTitle(btnImage.getText());
        openMainView(fc.showOpenDialog(this.getStage()));
    }
    /**
     * Called by view events, this method opens a filechooser window and allow to open a file.
     * @throws IOException 
     */
    @FXML
    public void openProject() throws IOException {
        final FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Select a BarlugoFX bfx file", "*.bfx"));
        openMainView(fc.showOpenDialog(this.getStage()));
    }
    /**
     * Effects.
     */
    @FXML
    public void imgEntered() {
        btnImage.setEffect(new Bloom());
    }
    /**
     * Effects.
     */
    @FXML
    public void imgExited() {
        btnImage.setEffect(null);
    }
    //private functions
    private void openMainView(final File file) {
        if (file != null) {
            final FadeTransition ft = AnimationUtils.fadeOutTransition(Duration.millis(ANIM_MILLIS), this.getStage().getScene().getRoot());
            ft.setOnFinished(e -> {
                new MainView(this.getStage(), file);
            });
            ft.play();
        }
    }
}
