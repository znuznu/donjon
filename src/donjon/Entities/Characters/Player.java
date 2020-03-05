package donjon.Entities.Characters;

import donjon.Entities.Engine.Animation;
import donjon.Entities.Engine.Box;
import donjon.Entities.Engine.Direction;
import donjon.Entities.Items.Item;
import donjon.Entities.Items.Key;
import donjon.Game.Level;
import donjon.Manager.SoundManager;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Player extends Character {
    /* The variables below are linked to the level the player is in */
    private ArrayList<Item> inventory;
    private int doubleJumpCharge;
    private int coinsCount;

    /* Maximum jumps without touching the ground */
    private int maxJump;

    /* The current state the player is in */
    private State state;

    Player(double posX, double posY, Image sprite, boolean isRemovable, Box boundingbox,
           Direction direction, Animation[] animation, double velocityX, double velocityY, int maxJump) {
        super(posX, posY, sprite, isRemovable, boundingbox, direction, animation, velocityX, velocityY);
        this.state = State.FALLING;
        this.doubleJumpCharge = 0;
        this.coinsCount = 0;
        this.inventory = new ArrayList<>();
        this.maxJump = maxJump;
    }

    private enum State {
        JUMPING,
        GROUNDED,
        FALLING,
        INTERACTING
    }

    public void updateAnimation() {
        boolean left = this.getDirection().getLeft();
        boolean right = this.getDirection().getRight();

        switch (this.getStateToString()) {
            case "GROUNDED":
                if (left || right) {
                    if (this.getViewDirection().getLeft())
                        this.updateAnimationTo(2);
                    else
                        this.updateAnimationTo(3);
                } else {
                    if (this.getViewDirection().getLeft())
                        this.updateAnimationTo(0);
                    else
                        this.updateAnimationTo(1);
                }
                break;
            case "FALLING":
            case "JUMPING":
                if (this.getViewDirection().getLeft()) {
                    if (this.getDoubleJumpCharge() < this.maxJump)
                        this.updateAnimationTo(4);
                    else
                        this.updateAnimationTo(6);
                } else {
                    if (this.getDoubleJumpCharge() < this.maxJump)
                        this.updateAnimationTo(5);
                    else
                        this.updateAnimationTo(7);
                }
                break;
            case "INTERACTING":
                if (this.getViewDirection().getLeft())
                    this.updateAnimationTo(0);
                else
                    this.updateAnimationTo(1);
                break;
        }
    }

    /*
    * Finite State Machine of the player: moving him, double jumping..., everything is here
    * A lot of refactoring can be done.
    */
    public void handleActions(int doubleJumpFrameCounter, Level level, boolean right, boolean left, boolean jump, boolean interact) {
        if (this.getDoubleJumpCharge() == 1)
            doubleJumpFrameCounter += 1;
        else
            doubleJumpFrameCounter = 0;

        switch (this.getStateToString()) {
            case "FALLING":
                if (jump && this.getDoubleJumpCharge() < this.maxJump) {
                    this.setVelocityY(7);
                    level.moveCharacter(this, true, false, right, left);
                    this.increaseDoubleJumpCharge();
                    SoundManager.JUMP.play();
                    this.setStringState("JUMPING");
                } else if (jump && this.getDoubleJumpCharge() < this.maxJump && doubleJumpFrameCounter > 15) {
                    this.increaseDoubleJumpCharge();
                    this.setVelocityY(7);
                    level.moveCharacter(this, true, false, right, left);
                    SoundManager.JUMP.play();
                    this.setStringState("JUMPING");
                } else if (level.playerIsGrounded()) {
                    this.setDoubleJumpCharge(0);
                    this.setStringState("GROUNDED");
                    SoundManager.GROUNDED.play();
                } else {
                    double increase = level.getPlayer().getVelocityY() + level.getGravity();
                    this.setVelocityY(increase);
                    level.moveCharacter(this, false, true, right, left);
                }
                break;
            case "GROUNDED":
                if (interact) {
                    this.setStringState("INTERACTING");
                } else if (jump) {
                    this.setVelocityY(7);
                    level.moveCharacter(this, true, false, right, left);
                    this.increaseDoubleJumpCharge();
                    SoundManager.JUMP.play();
                    this.setStringState("JUMPING");
                } else if (!level.playerIsGrounded()) {
                    this.setStringState("FALLING");
                    this.setVelocityY(0);
                } else
                    level.moveCharacter(this, false, false, right, left);
                break;
            case "JUMPING":
                if (jump && this.doubleJumpCharge < this.maxJump && doubleJumpFrameCounter > 15) {
                    this.increaseDoubleJumpCharge();
                    this.setVelocityY(7);
                    level.moveCharacter(this, true, false, right, left);
                } else if (this.getVelocityY() < 0) {
                    this.setStringState("FALLING");
                    this.setVelocityY(0);
                } else {
                    double decrease = level.getPlayer().getVelocityY() - level.getGravity();
                    this.setVelocityY(decrease);
                    level.moveCharacter(this, true, false, right, left);
                }
                break;
            case "INTERACTING":
                if (!interact)
                    this.setStringState("GROUNDED");
                break;
        }
    }

    public void increaseCoins(int coins) {
        this.coinsCount += coins;
    }

    public int getCoinsCount() {
        return coinsCount;
    }

    public int getDoubleJumpCharge() {
        return doubleJumpCharge;
    }

    public void setDoubleJumpCharge(int doubleJumpCharge) {
        this.doubleJumpCharge = doubleJumpCharge;
    }

    public void increaseDoubleJumpCharge() {
        this.doubleJumpCharge += 1;
    }

    public void setStringState(String state) {
        this.state = State.valueOf(state);
    }

    public String getStateToString() {
        return state.toString();
    }

    public void removeUsedKeys() {
        Iterator<Item> itemIterator = inventory.iterator();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();

            if (item instanceof Key) {
                if (((Key) item).hasBeenUsed()) {
                    itemIterator.remove();
                }
            }
        }
    }

    public boolean canUseKey() {
        for (Item item : inventory) {
            if (item instanceof Key) {
                ((Key) item).setUsed(true);
                return true;
            }
        }

        return false;
    }

    public void addToInventory(Item item) {
        this.inventory.add(item);
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }
}
