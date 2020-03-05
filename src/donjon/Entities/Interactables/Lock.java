package donjon.Entities.Interactables;

import donjon.Game.Tile;
import donjon.Entities.Engine.Animatable;
import donjon.Entities.Engine.Box;
import donjon.Entities.Characters.Player;
import donjon.Entities.Engine.Collision;
import donjon.Manager.AnimationManager;
import donjon.Manager.SoundManager;
import donjon.Manager.SpriteManager;
import donjon.Manager.TileManager;

public class Lock extends Interactable implements Animatable, Collision {

    public Lock(double posX, double posY, Tile[] linkedTiles) {
        super(
                posX,
                posY,
                SpriteManager.LOCK[0][0],
                false,
                new Box(4, 0, 26, 32),
                AnimationManager.LOCK,
                linkedTiles
        );
    }

    @Override
    public void updateAnimation() {
        this.updateAnimationTo(0);
    }

    @Override
    public void collide(Player player) {
        if (player.getStateToString().equals("INTERACTING") && !on && player.canUseKey()) {
            setOn(TileManager.TILES_LIST[5]);
        }
    }
}
