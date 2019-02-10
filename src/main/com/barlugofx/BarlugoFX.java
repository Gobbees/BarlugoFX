package com.barlugofx;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import com.barlugofx.ui.welcome.WelcomeView;

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
            new WelcomeView();
        } catch (Exception e) {
            //add log
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ops, an error occurred");
            alert.setContentText("I'm sorry, but there was a fatal error.\nSee log for information");
            alert.showAndWait();
        }
    }
}
