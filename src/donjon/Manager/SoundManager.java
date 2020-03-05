package donjon.Manager;

import javafx.scene.media.AudioClip;

public class SoundManager {
    /* Menus */
    public static final AudioClip BUTTON_CLICK = load("Menu/button_click.wav");
    public static final AudioClip BUTTON_HOVER = load("Menu/button_hover.wav");

    /* Game sounds */
    public static final AudioClip COIN      = load("Game/coin.wav");
    public static final AudioClip DEATH     = load("Game/death.wav");
    public static final AudioClip GROUNDED  = load("Game/grounded.wav");
    public static final AudioClip JUMP      = load("Game/jump.wav");
    public static final AudioClip KEY       = load("Game/key.wav");
    public static final AudioClip OPEN      = load("Game/open.wav");
    public static final AudioClip SOUL      = load("Game/soul.wav");

    public static AudioClip load(String stringFile) {
        return new AudioClip(SoundManager.class.getResource("/Resources/Sounds/" + stringFile).toString());
    }
}
