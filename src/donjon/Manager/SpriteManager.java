package donjon.Manager;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class SpriteManager {
    /* Blue */
    public static Image[][] BLUE = load("Characters/blue.png", 36, 36);
    public static Image[] BLUE_IDLE_RIGHT               = new Image[]{BLUE[0][0], BLUE[0][2]};
    public static Image[] BLUE_IDLE_LEFT                = new Image[]{BLUE[1][4], BLUE[1][2]};
    public static Image[] BLUE_WALK_RIGHT               = new Image[]{BLUE[0][0], BLUE[0][1], BLUE[0][0]};
    public static Image[] BLUE_WALK_LEFT                = new Image[]{BLUE[1][4], BLUE[1][3], BLUE[1][4]};
    public static Image[] BLUE_JUMP_RIGHT               = new Image[]{BLUE[0][1]};
    public static Image[] BLUE_JUMP_LEFT                = new Image[]{BLUE[1][3]};
    public static Image[] BLUE_JUMP_NO_CHARGE_RIGHT     = new Image[]{BLUE[2][3]};
    public static Image[] BLUE_JUMP_NO_CHARGE_LEFT      = new Image[]{BLUE[2][1]};
    public static Image[] BLUE_DEATH                    = new Image[]{BLUE[1][0]};

    /* Coin */
    public static Image[][] COIN = load("Items/coin.png", 36, 36);
    public static Image[] COIN_FLIP = new Image[]{COIN[0][0], COIN[0][1], COIN[0][2], COIN[0][3], COIN[0][4], COIN[0][5]};

    /* Key */
    public static Image[][] KEY = load("Items/key.png", 36, 36);
    public static Image[] KEY_LEVITATE = new Image[]{KEY[0][0], KEY[0][1]};

    /* Soul */
    public static Image[][] SOUL = load("Items/soul.png", 36, 36);
    public static Image[] SOUL_SOULING = new Image[]{SOUL[0][0], SOUL[0][1], SOUL[0][2]};

    /* Lever */
    public static Image[][] LEVER = load("Interactables/lever.png", 36, 36);
    public static Image[] LEVER_INTERACT = new Image[]{LEVER[0][0], LEVER[0][1]};

    /* Lock */
    public static Image[][] LOCK = load("Interactables/lock.png", 36, 36);
    public static Image[] LOCK_INTERACT = new Image[]{LOCK[0][0], LOCK[0][1]};

    /* Fire */
    public static Image[][] FIRE = load("Kill/fire.png", 36, 36);
    public static Image[] FIRE_BURNING = new Image[]{FIRE[0][0], FIRE[0][1], FIRE[0][2]};


    /* Return the Image based on imageFile */
    public static Image loadSimpleImage(String imageFile) {
        return new Image(SpriteManager.class.getResourceAsStream("/Resources/Sprites/" + imageFile));
    }

    public static Image[][] load(String spriteSheet, int spriteWidth, int spriteHeight) {
        Image[][] croppedSpriteSheet;

        try {
            Image spritesheet = new Image(SpriteManager.class.getResourceAsStream("/Resources/Sprites/" + spriteSheet));
            int c = (int) spritesheet.getWidth() / spriteWidth;
            int r = (int) spritesheet.getHeight() / spriteHeight;

            croppedSpriteSheet = new Image[r][c];
            PixelReader pixelReader = spritesheet.getPixelReader();

            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    croppedSpriteSheet[i][j] = new WritableImage(
                            pixelReader,
                            j * spriteWidth,
                            i * spriteHeight,
                            spriteWidth,
                            spriteHeight
                    );
                }
            }

            return croppedSpriteSheet;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[E] Error: can't load the spritesheet " + spriteSheet);
        }

        return null;
    }
}
