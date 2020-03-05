package donjon.Entities.Items;

import donjon.Entities.Engine.Animatable;
import donjon.Entities.Engine.Box;
import donjon.Entities.Characters.Player;
import donjon.Entities.Engine.Collision;
import donjon.Manager.AnimationManager;
import donjon.Manager.SoundManager;
import donjon.Manager.SpriteManager;

public class Soul extends Item implements Animatable, Collision {
    public Soul(double posX, double posY) {
        super(
                posX,
                posY,
                SpriteManager.SOUL[0][0],
                false,
                new Box(4, 0, 26, 32),
                AnimationManager.SOUL
        );
    }

    @Override
    public void updateAnimation() {
        this.updateAnimationTo(0);
    }

    @Override
    public void collide(Player player) {
        SoundManager.SOUL.play();
        player.addToInventory(this);
        this.isRemovable = true;
    }
}