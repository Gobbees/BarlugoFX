package com.barlugofx.ui.welcome;

import java.io.File;
import java.io.IOException;

import com.barlugofx.ui.Animations;
import com.barlugofx.ui.ViewController;
import com.barlugofx.ui.loading.LoadingView;
import com.jfoenix.controls.JFXButton;

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
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class manages the view events (i.e. button clicks, enter and exit) and effectively resizes the nodes.
 * Creating a WelcomeController object is useless and it probably will cause some sort of exception.
 */
public class WelcomeController implements ViewController {
    //private constant fields (nodes multipliers)
    private static final double IMG_MULTIPLIER = 0.66;
    private static final double BTN_WIDTH_MULTIPLIER = 0.33;
    private static final double BTN_HEIGHT_MULTIPLIER = 0.5;
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
    @FXML
    private JFXButton btnProject;
    private Stage stage;
    /**
     * This method resizes all the view components.
     * @param width : the resized width
     * @param height : the resized height
     */
    public void resizeComponents(final int width, final int height) {
        btnImage.setPrefWidth(width * BTN_WIDTH_MULTIPLIER);
        btnImage.setPrefHeight(height * BTN_HEIGHT_MULTIPLIER);
        btnProject.setPrefWidth(width * BTN_WIDTH_MULTIPLIER);
        btnProject.setPrefHeight(height * BTN_HEIGHT_MULTIPLIER - height % 2);
        AnchorPane.setRightAnchor(bpaneLeft, width * BPANE_RIGHT_MULTIPLIER);
        separLabel.setVisible(false);
        separLabel.setPrefHeight(height / 10);
        bpaneLeft.setPrefSize(width, height);
        bpaneLeft.setPrefSize(width, height);
        iviewIcon.setFitWidth(bpaneLeft.getPrefWidth() * IMG_MULTIPLIER);
        iviewIcon.setFitHeight(bpaneLeft.getPrefHeight() * IMG_MULTIPLIER);
    }

    /**
     * Set the stage in order to permit the correct display of the filechooser window.
     * @param s the input stage
     */
    public void setStage(final Stage s) {
        stage = s;
    }
    /**
     * Called by view events, this method initiates the main view.
     * @throws IOException 
     */
    @FXML
    public void openImage() throws IOException {
        final FileChooser fc = new FileChooser();
        fc.setTitle(btnImage.getText());
        openMainView(fc.showOpenDialog(stage));
    }
    /**
     * Called by view events, this method opens a filechooser window and allow to open a file.
     * @throws IOException 
     */
    @FXML
    public void openProject() throws IOException {
        final FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Select a BarlugoFX bfx file", "*.bfx"));
        fc.setTitle(btnProject.getText());
        openMainView(fc.showOpenDialog(stage));
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
    /**
     * Effects.
     */
    @FXML
    public void prjEntered() {
        btnProject.setEffect(new Bloom());
    }
    /**
     * Effects.
     */
    @FXML
    public void prjExited() {
        btnProject.setEffect(null);
    }
    //private functions
    private void openMainView(final File project) {
        if (project != null) {
            FadeTransition ft = Animations.playFadeOutTransition(Duration.millis(ANIM_MILLIS), stage.getScene().getRoot());
            ft.setOnFinished(e -> {
                try {
                    new LoadingView(stage); //temp: there will be mainview
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }
}