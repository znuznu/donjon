package donjon.Main;

import donjon.Manager.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static SceneManager sm;
    private static Stage ps;

    public static Stage getPrimaryStage() {
        return ps;
    }

    public static void main(String[] args) {
        /*
         * Could help to resolve a problem with Ubuntu based distributions
         * that implies more than ~60 fps with the AnimationTimer handle() loop.
         */
        System.setProperty("quantum.multithreaded", "false");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        sm = new SceneManager();
        sm.loadScene(0);
        primaryStage.setTitle("Donjon");
        primaryStage.setScene(sm.getScene(0));
        primaryStage.setResizable(false);
        primaryStage.show();
        ps = primaryStage;
    }
}
