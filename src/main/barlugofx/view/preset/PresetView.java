package barlugofx.view.preset;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import barlugofx.app.AppManager;
import barlugofx.view.AbstractView;
import javafx.stage.Stage;

/**
 *  This class shows the preset view of barlugofx program. It must be called by its constructor method.
 */
public class PresetView extends AbstractView<PresetController> {
    private static final double WIDTH_MULTIPLIER = 0.23;
    private static final double HEIGHT_MULTIPLIER = 0.56;
    /**
     * The class constructor. It sets all the parameters and display the preset stage.
     * @param manager the input manager
     */
    public PresetView(final AppManager manager) {
        super("Preset", "file:res/img/logo.png", new Stage(), new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * WIDTH_MULTIPLIER),
              (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * HEIGHT_MULTIPLIER)));
        try {
            this.loadFXML("file:res/fxml/FXMLPreset.fxml");
        } catch (IOException e) {
            //TODO log!!!!!!!!!!!!!!!!!!!!!
            e.printStackTrace();
        }
        this.getStage().setResizable(false);
        this.getStage().setScene(this.getScene());
        this.getController().setStage(this.getStage());
        this.getController().setManager(manager);
        this.getStage().centerOnScreen();
        this.getStage().show();
    }
}