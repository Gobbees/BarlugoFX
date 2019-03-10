package barlugofx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.awt.Toolkit;
import java.io.File;

import barlugofx.view.main.MainView;
import barlugofx.view.welcome.WelcomeView;

/**
 * The main class that starts the whole application.
 */
public final class BarlugoFX extends Application {
    /**
     * Launches the start function, which will initialize the whole application.
     * @param args : input parameters
     */
    public static void main(final String... args) {
        launch(args);
    }
    @Override
    public void start(final Stage stage) {
        //initialize....
        try {
            //new WelcomeView();
            //NOT USEFUL. I use it cause the main opening is faster.
            final Stage s = new Stage();
            s.setScene(new Scene(new Group(), 100, 100));
            s.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
            s.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
            new MainView(s, new File("/Users/gg_mbpro/Desktop/London 2019/IMG_5532.png"));
        } catch (Exception e) {
            //add log
            e.printStackTrace();
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ops, an error occurred");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
