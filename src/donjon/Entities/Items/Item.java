package donjon.Entities.Items;

import donjon.Entities.Engine.Animation;
import donjon.Entities.Engine.Box;
import donjon.Entities.Engine.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Item extends Entity {
    public Item(double posX, double posY, Image sprite, boolean isRemovable, Box boundingbox, Animation[] animation) {
        super(posX, posY, new ImageView(sprite), isRemovable, boundingbox, animation);
    }
}
