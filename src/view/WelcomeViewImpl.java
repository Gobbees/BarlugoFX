package view;


import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * 
 *
 */
public final class WelcomeViewImpl implements WelcomeView {
    //general variables
    private final Color darkGrey = Color.rgb(51, 49, 48);
    private final Image icon = new Image("file:res/img/a.png");
    private final String title = "Welcome to BarlugoFX";
    private final Dimension screenDimension;
    //components
    private final Stage stage;
    private final Scene scene;
    private final GridPane root;

    /**
     * Initialize all the components of the view.
     */
    public WelcomeViewImpl() {
        //initialize the stage
        stage = new Stage();
        stage.setTitle(title);
        stage.centerOnScreen();
        stage.getIcons().add(icon);
        //get the screen dimension
        screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        //initialize the root
        root = new GridPane();
        scene = new Scene(root, screenDimension.getWidth() / 2, screenDimension.getHeight() / 2);
        scene.setFill(darkGrey);
//        MenuBar menuBar = new MenuBar();
//        //menuBar.useSystemMenuBarProperty().set(true);
//        Menu menu = new Menu("Hello");
//        MenuItem item = new MenuItem("Test");
//        menu.getItems().add(item);
//        menuBar.getMenus().add(menu);
//        menuBar.getMenus().add(menu);
//        menuBar.getMenus().add(menu);
//        menuBar.getMenus().add(menu);
//        menuBar.getMenus().add(menu);
//        menuBar.getMenus().add(menu);
//
//
//        s.setOnDragDropped(e -> {
//            System.out.println("Drag n dropped");
//        });
//        File b = new File("res/a.png");
//        System.out.println(b.exists());
//        Image i = new Image("file:res/img/a.png", s.getHeight() / 2,s.getWidth() / 2,false,true,true);
//        root.addRow(1, new Button("Ciao"));
//        System.out.println(root.impl_getColumnCount() + " " + root.impl_getRowCount() );
        stage.setScene(scene);
    }

    @Override
    public void show() {
        stage.show();
    }
}
