package donjon.Manager;

import donjon.Controller.EndController;
import donjon.Controller.GameController;
import donjon.Controller.MainMenuController;
import donjon.Controller.SettingsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class SceneManager {
    public static final double WINDOW_HEIGHT = 684;
    public static final double WINDOW_WIDTH = 720;

    public static final double GAME_HEIGHT = 576;
    public static final double GAME_WIDTH = 720;

    private static final String MAIN_MENU_FILE = "mainmenu.fxml";
    private static final String GAME_FILE = "game.fxml";
    private static final String SETTINGS_FILE = "settings.fxml";
    private static final String END_FILE = "end.fxml";

    public static GameController gameController;
    public static SettingsController settingsController;
    public static EndController endController;

    private Scene[] scenes;

    public SceneManager() {
        scenes = new Scene[4];
    }

    public static Scene load(String file) {
        Group root = new Group();

        /* Burp, ugly but necessary, kind of an error scene */
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.BLACK);

        try {
            System.out.println("[+] New scene: " + file);
            FXMLLoader loader = new FXMLLoader(MainMenuController.class.getResource(file));
            Parent parent = loader.load();

            switch (file) {
                case GAME_FILE:
                    gameController = loader.getController();
                    break;
                case SETTINGS_FILE:
                    settingsController = loader.getController();
                    break;
                case END_FILE:
                    endController = loader.getController();
                    break;
            }

            scene = new Scene(parent, WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scene;
    }

    public void loadScene(int i) {
        switch (i) {
            case 0:
                scenes[0] = load(MAIN_MENU_FILE);
                break;
            case 1:
                scenes[1] = load(GAME_FILE);
                break;
            case 2:
                scenes[2] = load(SETTINGS_FILE);
                break;
            case 3:
                scenes[3] = load(END_FILE);
                break;
        }
    }

    public Scene getScene(int i) {
        return scenes[i];
    }
}
