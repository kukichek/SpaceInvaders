package spaceInvaders.gameComponents;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Shell extends GameObject {
    public enum Direction {
        UP(-1),
        DOWN(1);

        Direction(int index) {
            direction_index = index;
        }

        public int getIndex() {
            return direction_index;
        }

        private int direction_index;
    }

    public Shell(int x, int y, Direction direction, int stepWidth) {
        super(x, y, new Collider(17, 17, 10, 27));

        this.direction = direction;
        this.stepWidth = stepWidth;
        setTimer();
    }

    public Shell(int x, int y) { this(x, y, Direction.UP, DEFAULT_STEP_WIDTH); }

    public static int getImageWidth() {
        return IMAGE_WIDTH;
    }

    public static int getImageHeight() {
        return IMAGE_HEIGHT;
    }

    public void addOutOfBoundsPropertySupport(PropertyChangeListener listener) {
        outOfBoundsPropertySupport.addPropertyChangeListener(listener);
    }

    public void addShotPropertySupport(PropertyChangeListener listener) {
        checkShotPropertySupport.addPropertyChangeListener(listener);
    }

    public void stopTimer() {
        timer.stop();
    }

    private void fly() {
        position.y += direction.getIndex() * stepWidth;
        setComponentPos();
    }

    private void setTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (position.y < -IMAGE_HEIGHT) {
                    stop();
                    outOfBoundsPropertySupport.firePropertyChange("shell is out of bounds", "in", "out");
                } else {
                    fly();
                    fireHelper();
                }
            }
        };
        timer.start();
    }

    @Override
    protected void setComponentView() {
        super.setComponentView();
        componentView.setViewport(new Rectangle2D(290, 10, IMAGE_WIDTH, IMAGE_HEIGHT));
        componentView.setScaleX(IMAGE_SCALE_INDEX);
        componentView.setScaleY(IMAGE_SCALE_INDEX);

        setComponentPos();
    }

    private void setComponentPos() {
        componentView.setLayoutX(position.x);
        componentView.setLayoutY(position.y);
    }

    private void fireHelper() {
        checkShotPropertySupport.firePropertyChange("check is successful shot", null, this);
    }

    private AnimationTimer timer;
    private int stepWidth = 7;

    private static final int DEFAULT_STEP_WIDTH = 7;
    private static final int IMAGE_WIDTH = 45;
    private static final int IMAGE_HEIGHT = 60;

    private Direction direction;
    private final PropertyChangeSupport outOfBoundsPropertySupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport checkShotPropertySupport = new PropertyChangeSupport(this);
}
