package donjon.Controller;

import donjon.Game.Game;
import donjon.Main.Main;
import donjon.Manager.MusicManager;
import donjon.Manager.SceneManager;
import donjon.Manager.SettingsManager;
import donjon.Manager.SoundManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static donjon.Manager.MusicManager.THEME;

public class MainMenuController implements Initializable {
    @FXML
    private void clickStart(MouseEvent event) throws Exception {
        Main.sm.loadScene(1);
        Game game = new Game(1, 0);
        SceneManager.gameController.initGame(game);
        SceneManager.gameController.startGame();
        MusicManager.play(THEME);
        Main.getPrimaryStage().setScene(Main.sm.getScene(1));
    }

    @FXML
    private void clickSoundHover(MouseEvent event) {
        SoundManager.BUTTON_HOVER.play();
    }

    @FXML
    private void clickSound(MouseEvent event) throws Exception {
        SoundManager.BUTTON_CLICK.play();
    }

    @FXML
    private void clickSettings(MouseEvent event) throws Exception {
        Main.sm.loadScene(2);
        SceneManager.settingsController.setPreviousScene(Main.sm.getScene(0));
        Main.getPrimaryStage().setScene(Main.sm.getScene(2));
    }

    @FXML
    private void clickQuit(MouseEvent event) throws Exception {
        Main.getPrimaryStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SettingsManager.loadConfig();
    }
}