package donjon.Entities.Kill;

import donjon.Entities.Characters.Player;
import donjon.Entities.Engine.Animation;
import donjon.Entities.Engine.Box;
import donjon.Entities.Engine.Entity;
import donjon.Manager.SoundManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Kill extends Entity {
    public Kill(double posX, double posY, Image sprite,
                boolean isRemovable, Box boundingbox, Animation[] animation) {
        super(posX, posY, new ImageView(sprite), isRemovable, boundingbox, animation);
    }

    @Override
    public void collide(Player player) {
        SoundManager.DEATH.play();
        player.setRemovable(true);
        player.kill();
    }
}
