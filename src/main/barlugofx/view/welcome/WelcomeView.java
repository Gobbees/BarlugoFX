package barlugofx.view.welcome;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import barlugofx.view.AbstractView;
import javafx.stage.Stage;
/**
 *  This class shows the welcome view of barlugofx program. It must be called by its constructor method.
 */
public class WelcomeView extends AbstractView<WelcomeController> {
    private static final double MIN_DIM_MULTIPLIER = 0.2;
    /**
     * The constructor that initializes all the fields and show the stage.
     * @throws IOException if the loader file URL is invalid.
     */
    public WelcomeView() {
        super("Welcome to BarlugoFX", "file:res/img/logo.png", new Stage(), new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2,
                                                                                                              (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2));
        try {
            this.loadFXML("file:res/fxml/FXMLWelcome.fxml");
        } catch (IOException e) {
            //TODO log!!!!!!!!!!!!!!!!!!!!!
            e.printStackTrace();
        }
        this.getStage().setMinWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * MIN_DIM_MULTIPLIER);
        this.getStage().setMinHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight() * MIN_DIM_MULTIPLIER);
        this.getController().setStage(this.getStage());
        this.getController().resizeComponents((int) this.getScene().widthProperty().get(), (int) this.getScene().heightProperty().get());
        //add resize listeners
        this.getScene().widthProperty().addListener((obs, oldVal, newVal) -> this.getController().resizeComponents(newVal.doubleValue(), this.getScene().heightProperty().get()));
        this.getScene().heightProperty().addListener((obs, oldVal, newVal) -> this.getController().resizeComponents(this.getScene().widthProperty().get(), newVal.doubleValue()));
        //add scene to the stage and show
        this.getStage().centerOnScreen();
        this.getStage().setScene(this.getScene());
        this.getStage().show();
    }
}
