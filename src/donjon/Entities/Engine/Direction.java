package donjon.Entities.Engine;

public class Direction {
    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    public Direction(boolean up, boolean down, boolean right, boolean left) {
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
    }

    public boolean isFalse() {
        return !(up && down && right && left);
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean getLeft() {
        return left;
    }

    public boolean getRight() {
        return right;
    }

    public String toString() {
        return "up " + up + " " + down + " left " + left + " right " + right;
    }
}
