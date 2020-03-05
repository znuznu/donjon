package donjon.Manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class MusicManager {
    private static MediaPlayer currentMediaPlayer;
    public static final Media THEME = load("theme.mp3");

    public static Media load(String stringFile) {
        return new Media(MusicManager.class.getResource("/Resources/Music/" + stringFile).toString());
    }

    public static void play(Media media) {
        currentMediaPlayer = new MediaPlayer(media);
        currentMediaPlayer.setCycleCount(INDEFINITE);
        currentMediaPlayer.setVolume(0.4);
        currentMediaPlayer.play();
    }

    public static void stop() {
        currentMediaPlayer.stop();
    }
}
