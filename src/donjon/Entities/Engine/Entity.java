package donjon.Entities.Engine;

import javafx.scene.image.ImageView;

public abstract class Entity implements Animatable, Collision {
    protected double posX, posY;
    protected ImageView sprite;
    protected boolean isRemovable;
    protected Box boundingbox;
    protected Animation[] animation;
    protected Animation currentAnimation;

    public Entity(double posX, double posY, ImageView sprite, boolean isRemovable, Box boundingbox, Animation[] animation) {
        this.posX = posX;
        this.posY = posY;
        this.sprite = sprite;
        this.isRemovable = isRemovable;
        this.boundingbox = boundingbox;
        this.animation = animation;
        this.currentAnimation = animation[0];
    }

    public void updateAnimationTo(int animationId) {
        this.getCurrentAnimation().pause();
        this.setCurrentAnimation(this.getAnimation()[animationId]);
        this.getSprite().setImage(this.getCurrentAnimation().getView().getImage());
        this.getCurrentAnimation().play();
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public Box getBoundingbox() {
        return boundingbox;
    }

    public Animation[] getAnimation() {
        return animation;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public boolean isRemovable() {
        return isRemovable;
    }

    public void setRemovable(boolean removable) {
        isRemovable = removable;
    }
}
