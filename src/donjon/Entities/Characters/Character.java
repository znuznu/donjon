package donjon.Entities.Characters;

import donjon.Entities.Engine.Animation;
import donjon.Entities.Engine.Box;
import donjon.Entities.Engine.Direction;
import donjon.Entities.Engine.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Character extends Entity {
    private Direction direction;
    private Direction viewDirection;
    private double velocityX, velocityY;
    private boolean alive;

    Character(double posX, double posY, Image sprite, boolean isRemovable, Box boundingbox,
              Direction direction, Animation[] animation, double velocityX, double velocityY) {
        super(posX, posY, new ImageView(sprite), isRemovable, boundingbox, animation);
        this.direction = direction;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.viewDirection = new Direction(false, false, true, false);
        this.alive = true;
    }

    public void moveBy(double dx, double dy) {
        if (dx == 0 && dy == 0) return;
        posX += dx;
        posY += dy;
    }

    public boolean isCollidingWith(Entity entity) {
        double x = this.getPosX() + this.getBoundingbox().getX();
        double y = this.getPosY() + this.getBoundingbox().getY();
        double w = this.getBoundingbox().getWidth();
        double h = this.getBoundingbox().getHeight();

        double xEntity = entity.getPosX() + entity.getBoundingbox().getX();
        double yEntity = entity.getPosY() + entity.getBoundingbox().getY();
        double wEntity = entity.getBoundingbox().getWidth();
        double hEntity = entity.getBoundingbox().getHeight();

        return (x < xEntity + wEntity && x + w > xEntity && y < yEntity + hEntity && h + y > yEntity);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getViewDirection() {
        return viewDirection;
    }

    public void updateViewDirection(boolean left, boolean right) {
        if (left)
            this.viewDirection = new Direction(false, false, false, true);
        else if (right)
            this.viewDirection = new Direction(false, false, true, false);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        this.alive = false;
    }
}
