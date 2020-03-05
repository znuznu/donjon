package donjon.Entities.Characters;

import donjon.Entities.Engine.Box;
import donjon.Entities.Engine.Collision;
import donjon.Entities.Engine.Direction;
import donjon.Manager.AnimationManager;
import donjon.Manager.SpriteManager;

public class Blue extends Player implements Collision {
    public Blue(double posX, double posY) {
        super(
                posX,
                posY,
                SpriteManager.BLUE[0][0],
                false,
                new Box(9, 5, 21, 23),
                new Direction(false, false, true, false),
                AnimationManager.BLUE,
                4,
                0,
                2
        );
    }

    @Override
    public void collide(Player player) {
        System.out.println("Ootch!");
    }
}
