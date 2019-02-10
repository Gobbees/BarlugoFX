package com.barlugofx.ui.welcome;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.Bloom;
//import javafx.scene.image.Image; decomment if you need to add the icons to buttons
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class manages the view events (i.e. button clicks, enter and exit) and effectively resizes the nodes.
 * Creating a WelcomeController object is useless and it probably will cause some sort of exception.
 */
public class WelcomeController {
    //private constant fields (nodes multipliers)
    private static final double IMG_MULTIPLIER = 0.66;
    private static final double BTN_WIDTH_MULTIPLIER = 0.33;
    private static final double BTN_HEIGHT_MULTIPLIER = 0.5;
    private static final double BPANE_RIGHT_MULTIPLIER = 0.33;
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
    private JFXButton btnNew;
    @FXML
    private JFXButton btnOpen;
    private Stage stage;
    /**
     * This method resizes all the view components.
     * @param width : the resized width
     * @param height : the resized height
     */
    public void resizeComponents(final int width, final int height) {
        btnNew.setPrefWidth(width * BTN_WIDTH_MULTIPLIER);
        btnNew.setPrefHeight(height * BTN_HEIGHT_MULTIPLIER);
        btnOpen.setPrefWidth(width * BTN_WIDTH_MULTIPLIER);
        btnOpen.setPrefHeight(height * BTN_HEIGHT_MULTIPLIER - height % 2);
        AnchorPane.setRightAnchor(bpaneLeft, width * BPANE_RIGHT_MULTIPLIER);
        separLabel.setVisible(false);
        separLabel.setPrefHeight(height / 10);
        bpaneLeft.setPrefSize(width, height);
        bpaneLeft.setPrefSize(width, height);
        iviewIcon.setFitWidth(bpaneLeft.getPrefWidth() * IMG_MULTIPLIER);
        iviewIcon.setFitHeight(bpaneLeft.getPrefHeight() * IMG_MULTIPLIER);
//        btnNew.setGraphic(new ImageView(new Image("file:res/img/file.png")));  //if ok transfer to fxml
//        btnOpen.setGraphic(new ImageView(new Image("file:res/img/folder.png")));
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
     */
    @FXML
    public void newProject() {
        System.out.println("Under construction");
    }
    /**
     * Called by view events, this method opens a filechooser window and allow to open a file.
     * @throws InterruptedException if something wrong happens with the filechooser.
     */
    @FXML
    public void openProject() throws InterruptedException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Project");
        fc.showOpenDialog(stage);
    }
    /**
     * Effects.
     */
    @FXML
    public void newEntered() {
        btnNew.setEffect(new Bloom());
    }
    /**
     * Effects.
     */
    @FXML
    public void newExited() {
        btnNew.setEffect(null);
    }
    /**
     * Effects.
     */
    @FXML
    public void openEntered() {
        btnOpen.setEffect(new Bloom());
    }
    /**
     * Effects.
     */
    @FXML
    public void openExited() {
        btnOpen.setEffect(null);
    }
}
