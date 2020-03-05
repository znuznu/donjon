package donjon.Manager;

import donjon.Entities.Engine.Animation;

public class AnimationManager {
    private static final double IDLE_DURATION       = 1000;
    private static final double WALK_DURATION       = 300;
    private static final double JUMP_DURATION       = 300;
    private static final double FLIPPING_DURATION   = 700;
    private static final double LEVITATE_DURATION   = 1500;
    private static final double SOUL_DURATION       = 800;
    private static final double LEVER_DURATION      = 1000;
    private static final double LOCK_DURATION       = 1000;
    private static final double FIRE_DURATION       = 700;

    public static Animation[] BLUE = new Animation[] {
            new Animation(SpriteManager.BLUE_IDLE_LEFT, IDLE_DURATION, -1),
            new Animation(SpriteManager.BLUE_IDLE_RIGHT, IDLE_DURATION, -1),
            new Animation(SpriteManager.BLUE_WALK_LEFT, WALK_DURATION, -1),
            new Animation(SpriteManager.BLUE_WALK_RIGHT, WALK_DURATION, -1),
            new Animation(SpriteManager.BLUE_JUMP_LEFT, JUMP_DURATION, -1),
            new Animation(SpriteManager.BLUE_JUMP_RIGHT, JUMP_DURATION, -1),
            new Animation(SpriteManager.BLUE_JUMP_NO_CHARGE_LEFT, JUMP_DURATION, -1),
            new Animation(SpriteManager.BLUE_JUMP_NO_CHARGE_RIGHT, JUMP_DURATION, -1)
    };

    public static Animation[] COIN = new Animation[] {
            new Animation(SpriteManager.COIN_FLIP, FLIPPING_DURATION, -1)
    };

    public static Animation[] KEY = new Animation[] {
            new Animation(SpriteManager.KEY_LEVITATE, LEVITATE_DURATION, -1)
    };

    public static Animation[] SOUL = new Animation[] {
            new Animation(SpriteManager.SOUL_SOULING, SOUL_DURATION, -1)
    };

    public static Animation[] LEVER = new Animation[] {
            new Animation(SpriteManager.LEVER_INTERACT, LEVER_DURATION, 1)
    };

    public static Animation[] LOCK = new Animation[] {
            new Animation(SpriteManager.LOCK_INTERACT, LOCK_DURATION, 1)
    };

    public static Animation[] FIRE = new Animation[] {
            new Animation(SpriteManager.FIRE_BURNING, FIRE_DURATION, 1)
    };
}
