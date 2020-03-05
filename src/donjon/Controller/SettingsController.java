package donjon.Controller;

import donjon.Main.Main;
import donjon.Manager.SettingsManager;
import donjon.Manager.SoundManager;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private Label infosLabel;

    @FXML
    private Label leftLabel;

    @FXML
    private Label rightLabel;

    @FXML
    private Label jumpLabel;

    @FXML
    private Label interactLabel;

    private String keySelected;
    private Scene previousScene;

    @FXML
    private void clickSoundHover(MouseEvent event) {
        SoundManager.BUTTON_HOVER.play();
    }

    @FXML
    private void clickSound(MouseEvent event) throws Exception {
        SoundManager.BUTTON_CLICK.play();
    }

    @FXML
    public void keyPressed(KeyEvent keyEvent) {
        Label[] keysLabels = new Label[]{rightLabel, leftLabel, jumpLabel, interactLabel};

        String key = keyEvent.getText().toUpperCase();
        System.out.println("key code = " + keyEvent.getCode());
        System.out.println("key text = " + keyEvent.getText());

        switch (keySelected) {
            case "right":
                rightLabel.setText(key);
                rightLabel.getStyleClass().remove("standard-select");
                removeDuplicate(keysLabels, 0);
                break;
            case "left":
                leftLabel.setText(key);
                leftLabel.getStyleClass().remove("standard-select");
                removeDuplicate(keysLabels, 1);
                break;
            case "jump":
                jumpLabel.setText(key);
                jumpLabel.getStyleClass().remove("standard-select");
                removeDuplicate(keysLabels, 2);
                break;
            case "interact":
                interactLabel.setText(key);
                interactLabel.getStyleClass().remove("standard-select");
                removeDuplicate(keysLabels, 3);
                break;
            case "none":
                break;
        }

        keySelected = "none";
    }

    @FXML
    public void clickDefault(MouseEvent event) {
        cleanKeys();
        keySelected = "none";
        rightLabel.setText("D");
        leftLabel.setText("Q");
        jumpLabel.setText("SPACE");
        interactLabel.setText("E");
    }

    @FXML
    public void clickKey(MouseEvent event) {
        cleanKeys();

        Label sourceLabel = (Label) event.getSource();
        if (sourceLabel == rightLabel) {
            if (keySelected.equals("right")) {
                keySelected = "none";
                return;
            }
            keySelected = "right";
            rightLabel.getStyleClass().add("standard-select");
        } else if (sourceLabel == leftLabel) {
            if (keySelected.equals("left")) {
                keySelected = "none";
                return;
            }
            keySelected = "left";
            leftLabel.getStyleClass().add("standard-select");
        } else if (sourceLabel == jumpLabel) {
            if (keySelected.equals("left")) {
                keySelected = "none";
                return;
            }
            keySelected = "jump";
            jumpLabel.getStyleClass().add("standard-select");
        } else {
            if (keySelected.equals("interact")) {
                keySelected = "none";
                return;
            }
            keySelected = "interact";
            interactLabel.getStyleClass().add("standard-select");
        }
    }

    @FXML
    public void clickSaveSettings(MouseEvent actionEvent) {
        try {
            FileWriter fw = new FileWriter("src/Resources/Misc/settings.cfg");
            fw.write("left " + leftLabel.getText() + "\n");
            fw.write("right " + rightLabel.getText() + "\n");
            fw.write("jump " + jumpLabel.getText() + "\n");
            fw.write("interact " + interactLabel.getText() + "\n");
            fw.close();

            SettingsManager.loadConfig();
            displayInfos("Success", true);
        } catch (FileNotFoundException e) {
            displayInfos("Can't find configuration file.", false);
            System.out.println("Error: Can't find configuration file");
        } catch (Exception e) {
            displayInfos("Bad format", false);
        }
    }

    @FXML
    public void clickReturn(MouseEvent actionEvent) {
        Main.getPrimaryStage().setScene(previousScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getKeysConfig();
        } catch (Exception e) {
            displayInfos("Error", false);
            e.printStackTrace();
        }

        keySelected = "none";
        borderPane.setFocusTraversable(true);
        infosLabel.setVisible(false);
    }

    /* Not really useful, but we can imagine accessible settings from the game scene. */
    public void setPreviousScene(Scene scene) {
        previousScene = scene;
    }

    /*
     *      0 left        1 right
     *      2 jump        3 interact
     */
    public void removeDuplicate(Label[] keysLabels, int pos) {
        for (int i = 0; i < keysLabels.length; i++) {
            if (i != pos && keysLabels[i].getText().toUpperCase().equals(keysLabels[pos].getText()))
                keysLabels[i].setText("None");
        }
    }

    public void displayInfos(String text, boolean success) {
        infosLabel.getStyleClass().clear();

        String style = "standard-success";
        if (!success) style = "standard-fail";

        infosLabel.getStyleClass().add(style);
        infosLabel.setText(text);
        infosLabel.setVisible(true);
        PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
        visiblePause.setOnFinished(event -> infosLabel.setVisible(false));
        visiblePause.play();
    }

    private void getKeysConfig() {
        leftLabel.setText(SettingsManager.LEFT);
        rightLabel.setText(SettingsManager.RIGHT);
        jumpLabel.setText(SettingsManager.JUMP);
        interactLabel.setText(SettingsManager.INTERACT);
    }

    private void cleanKeys() {
        leftLabel.getStyleClass().remove("standard-select");
        rightLabel.getStyleClass().remove("standard-select");
        jumpLabel.getStyleClass().remove("standard-select");
        interactLabel.getStyleClass().remove("standard-select");
    }
}