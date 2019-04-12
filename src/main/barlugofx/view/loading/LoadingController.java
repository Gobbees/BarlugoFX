package barlugofx.view.loading;

import com.jfoenix.controls.JFXSpinner;

import barlugofx.view.AbstractViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *  This class sets the view sizes and manages its components.
 */
public class LoadingController extends AbstractViewController {
    //private constant fields (nodes multipliers)
    private static final double IMG_MULTIPLIER = 0.40;
    private static final double SEP_MULTIPLIER = 0.33;
    @FXML
    private BorderPane bpaneMain;
    @FXML
    private VBox vboxCenter;
    @FXML
    private ImageView iviewLogo;
    @FXML
    private Separator bottomSeparator;
    @FXML
    private JFXSpinner spinner;
    /**
     * Sets the private field stage (and sets the general sizes of components).
     * @param stage the input stage(needed to take scene sizes)
     */
    public void setStage(final Stage stage) {
        super.setStage(stage);
        iviewLogo.setFitWidth(this.getStage().getScene().widthProperty().get() * IMG_MULTIPLIER);
        iviewLogo.setFitHeight(this.getStage().getScene().heightProperty().get() * IMG_MULTIPLIER);
        bottomSeparator.setVisible(false);
        bottomSeparator.setPrefHeight(this.getStage().getScene().heightProperty().get() * SEP_MULTIPLIER);
    }
}
