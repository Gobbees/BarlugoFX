package barlugofx.view.export;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import barlugofx.app.AppManager;
import barlugofx.view.AbstractView;
import javafx.stage.Stage;

/**
 *  This class shows the export view of barlugofx program. It must be called by its constructor method.
 */
public class ExportView extends AbstractView<ExportController> {
    private static final double WIDTH_MULTIPLIER = 0.33;
    private static final double HEIGHT_MULTIPLIER = 0.25;
    /**
     * The class constructor. It sets all the parameters and display the export stage.
     * @param manager the input manager
     */
    public ExportView(final AppManager manager) {
        super("Export", "file:res/img/logo.png", new Stage(), new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * WIDTH_MULTIPLIER),
              (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * HEIGHT_MULTIPLIER)));
        try {
            this.loadFXML("file:res/fxml/FXMLExport.fxml");
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
