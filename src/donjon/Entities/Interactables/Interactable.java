package donjon.Entities.Interactables;

import donjon.Entities.Engine.Animation;
import donjon.Entities.Engine.Box;
import donjon.Entities.Engine.Entity;
import donjon.Game.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Interactable extends Entity {
    protected Tile[] linkedTiles;
    protected boolean on;

    public Interactable(double posX, double posY, Image sprite, boolean isRemovable,
                        Box boundingbox, Animation[] animation, Tile[] linkedTiles) {
        super(posX, posY, new ImageView(sprite), isRemovable, boundingbox, animation);
        this.linkedTiles = linkedTiles;
        this.on = false;
    }

    protected void setOn(Tile tile) {
        on = true;
        updateAnimation();

        for (Tile t : linkedTiles) {
            t.setType(tile.getType());
            t.setId(tile.getId());
            t.getImageView().setImage(tile.getImageView().getImage());
        }
    }
}
