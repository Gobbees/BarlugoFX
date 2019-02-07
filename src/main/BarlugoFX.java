package main;

import javafx.application.Application;
import javafx.stage.Stage;

import view.WelcomeViewImpl;

/**
 * The main class that starts the application.
 *
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
    public void start(final Stage stage) throws Exception {
        //initialize....
        new WelcomeViewImpl().show();
    }
}
