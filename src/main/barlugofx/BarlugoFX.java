package barlugofx;

import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import barlugofx.view.AbstractView;
import barlugofx.view.welcome.WelcomeView;

/**
 * The main class that starts the whole application.
 */
public final class BarlugoFX extends Application {
    /**
     * Launches the start function, which will initialize the whole application.
     * 
     * @param args input parameters
     */
    public static void main(final String... args) {
        try {
            launch(args);
        } catch (final Error heapThrowable) {
            AbstractView.showErrorAlert(heapThrowable.getMessage());
            heapThrowable.printStackTrace();
        }
    }

    @Override
    public void start(final Stage stage) {
        // load fonts from here because it is not possible with css files
        Font.loadFont(getClass().getResourceAsStream("/font/Inconsolata-Regular.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("/font/Inconsolata-Bold.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/font/OstrichSans-Medium.otf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/font/OstrichSans-Heavy.otf"), 16);
        try {
            new WelcomeView();
        } catch (Exception e) {
            AbstractView.showErrorAlert(e.getMessage());
            e.printStackTrace();
        }
    }
}
