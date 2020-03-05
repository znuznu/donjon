package donjon.Controller;

import donjon.Game.Game;
import donjon.Main.Main;
import donjon.Manager.MusicManager;
import donjon.Manager.SoundManager;
import donjon.Manager.SpriteManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EndController implements Initializable {
    @FXML
    Label idDeathCounterLabel;

    @FXML
    Label idTotalCoinsLabel;

    @FXML
    ImageView idDeathCounterImageView;

    @FXML
    ImageView idTotalCoinsImageView;

    @FXML
    private void clickSoundHover(MouseEvent event) {
        SoundManager.BUTTON_HOVER.play();
    }

    @FXML
    private void clickSound(MouseEvent event) throws Exception {
        SoundManager.BUTTON_CLICK.play();
    }

    @FXML
    public void clickReturn(MouseEvent actionEvent) {
        Main.getPrimaryStage().setScene(Main.sm.getScene(0));
        MusicManager.stop();
    }

    public void setData(Game game) {
        idTotalCoinsLabel.setText(" x " + game.getTotalCoins());
        idDeathCounterLabel.setText(" x " + game.getTotalDeath());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idDeathCounterImageView.setImage(SpriteManager.BLUE_DEATH[0]);
        idTotalCoinsImageView.setImage(SpriteManager.COIN[0][5]);
    }
}
