package barlugofx.view.export;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import barlugofx.controller.AppManager;
import barlugofx.view.View;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class shows the export view of barlugofx program. It must be called by
 * its constructor method.
 */
public class ExportView extends View<ExportController> {
    private static final double WIDTH_MULTIPLIER = 0.33;
    private static final double HEIGHT_MULTIPLIER = 0.25;

    /**
     * The class constructor. It sets all the parameters and display the export
     * stage.
     * 
     * @param manager the input manager
     */
    public ExportView(final AppManager manager) {
        super("Export", new Stage(),
                new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * WIDTH_MULTIPLIER),
                        (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * HEIGHT_MULTIPLIER)));
        this.getStage().getIcons().add(new Image(getClass().getResource("/img/logo.png").toExternalForm()));
        try {
            this.loadFXML(getClass().getResource("/fxml/FXMLExport.fxml"));
        } catch (IOException e) {
            View.showErrorAlert(e.getMessage());
            e.printStackTrace();
            // TODO log4j
            return;
        }
        this.getStage().setResizable(false);
        this.getStage().setScene(this.getScene());
        this.getController().setStage(this.getStage());
        this.getController().setManager(manager);
        this.getStage().centerOnScreen();
        this.getStage().initStyle(StageStyle.UTILITY);
        this.getStage().show();
    }

    /**
     * This function closes the opened stage.
     */
    public void closeStage() {
        this.getStage().close();
    }
}
