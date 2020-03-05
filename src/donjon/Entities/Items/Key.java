package donjon.Entities.Items;

import donjon.Entities.Engine.Animatable;
import donjon.Entities.Engine.Box;
import donjon.Entities.Characters.Player;
import donjon.Entities.Engine.Collision;
import donjon.Manager.AnimationManager;
import donjon.Manager.SoundManager;
import donjon.Manager.SpriteManager;

public class Key extends Item implements Animatable, Collision {
    boolean used;

    public Key(double posX, double posY) {
        super(
                posX,
                posY,
                SpriteManager.KEY[0][0],
                false,
                new Box(6, 6, 23, 23),
                AnimationManager.KEY
        );

        used = false;
    }

    @Override
    public void updateAnimation() {
        this.updateAnimationTo(0);
    }

    @Override
    public void collide(Player player) {
        SoundManager.KEY.play();
        player.addToInventory(this);
        this.isRemovable = true;
    }

    public boolean hasBeenUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
