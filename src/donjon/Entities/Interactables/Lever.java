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

public class Lever extends Interactable implements Animatable, Collision {

    public Lever(double posX, double posY, Tile[] linkedTiles) {
        super(
                posX,
                posY,
                SpriteManager.LEVER[0][1],
                false,
                new Box(6, 12, 25, 20),
                AnimationManager.LEVER,
                linkedTiles
        );
    }

    @Override
    public void updateAnimation() {
        this.updateAnimationTo(0);
    }

    @Override
    public void collide(Player player) {
        if (player.getStateToString().equals("INTERACTING") && !on) {
            setOn(TileManager.TILES_LIST[23]);
            SoundManager.OPEN.play();
        }
    }
}
