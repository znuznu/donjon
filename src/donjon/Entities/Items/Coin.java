package donjon.Entities.Items;

import donjon.Entities.Engine.Animatable;
import donjon.Entities.Engine.Box;
import donjon.Entities.Characters.Player;
import donjon.Entities.Engine.Collision;
import donjon.Manager.AnimationManager;
import donjon.Manager.SoundManager;
import donjon.Manager.SpriteManager;

public class Coin extends Item implements Animatable, Collision {
    public Coin(double posX, double posY) {
        super(
                posX,
                posY,
                SpriteManager.COIN[0][5],
                false,
                new Box(7, 7, 21, 21),
                AnimationManager.COIN
        );
    }

    @Override
    public void updateAnimation() {
        this.updateAnimationTo(0);
    }

    @Override
    public void collide(Player player) {
        SoundManager.COIN.play();
        player.increaseCoins(1);
        this.isRemovable = true;
    }
}
