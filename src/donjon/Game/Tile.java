package donjon.Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile {
    public static final int NORMAL      = 0;
    public static final int BLOCKED     = 1;
    public static final int SPECIAL     = 2;
    public static final int ATTACHABLE  = 3;
    public static final int KILLING     = 4;

    private ImageView imageView;
    private int type;
    private int id;


    public Tile(Image image, int type, int id) {
        this.type = type;
        this.id = id;
        this.imageView = new ImageView(image);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageView getImageView() {
        return imageView;
    }
}