package donjon.Entities.Kill;

import donjon.Entities.Engine.Box;
import donjon.Manager.AnimationManager;
import donjon.Manager.SpriteManager;

public class Fire extends Kill {
    public Fire(double posX, double posY) {
        super(
                posX,
                posY,
                SpriteManager.FIRE[0][0],
                false,
                new Box(0, 0, 36, 36),
                AnimationManager.FIRE
        );
    }

    @Override
    public void updateAnimation() {
        updateAnimationTo(0);
    }
}
