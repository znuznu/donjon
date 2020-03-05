package donjon.Entities.Engine;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animation extends Transition {
    private final ImageView imageView;
    private final int count;
    private int lastIndex;
    private Image[] sequence;

    public Animation(Image[] sequence, double durationMs, int cycleCount) {
        this.imageView = new ImageView(sequence[0]);
        this.sequence = sequence;
        this.count = sequence.length;

        setCycleCount(cycleCount);
        setCycleDuration(Duration.millis(durationMs));
        setInterpolator(Interpolator.LINEAR);
    }

    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            imageView.setImage(sequence[index]);
            lastIndex = index;
        }
    }

    public ImageView getView() {
        return imageView;
    }
}

