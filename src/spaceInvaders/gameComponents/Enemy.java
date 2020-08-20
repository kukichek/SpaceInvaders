package spaceInvaders.gameComponents;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;

public class Enemy extends GameObject {
    public enum Direction {
        LEFT(-STEP_WIDTH, 0),
        RIGHT(STEP_WIDTH, 0),
        DOWN(0, STEP_WIDTH);

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private int x, y;
    }

    public Enemy(int x, int y, int imageWidth, int imageHeight, int initialDelay, Collider collider, int points) {
        super(x, y, collider);

        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.points = points;

        delay = initialDelay;
        setTimer();
    }

    public boolean decreaseDelay(int delayDifference) {
        if (delay > delayDifference) {
            delay -= delayDifference;
            return true;
        }

        return false;
    }

    public void move(Direction direction) {
        position.x += direction.getX();
        position.y += direction.getY();

        setComponentPos();
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getPoints() {
        return points;
    }

    public void shoot(AnchorPane root, Player player) {
        Shell shell = new Shell(
                getX() + (getImageWidth() - Shell.getImageWidth()) / 2,
                getY() + Shell.getImageHeight(), Shell.Direction.DOWN, 5);
        root.getChildren().add(shell.getComponentView());
        shell.addOutOfBoundsPropertySupport(propertyChangeEvent ->
                root.getChildren().remove(shell.getComponentView()));
        shell.addShotPropertySupport(player.getShotListener());
    }

    private void setTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
            if (timerUnit >= delay) {
                currentFrame = (currentFrame + 1) % 2;
                setCurrentFrame();
                timerUnit = 0;
            } else {
                timerUnit++;
            }
            }
        };
        timer.start();
    }

    @Override
    protected void setComponentView() {
        super.setComponentView();
        componentView.setScaleX(IMAGE_SCALE_INDEX);
        componentView.setScaleY(IMAGE_SCALE_INDEX);

        setComponentPos();
    }

    protected void setCurrentFrame() {
        componentView.setViewport(new Rectangle2D(framesViewports[currentFrame].x, framesViewports[currentFrame].y, imageWidth, imageHeight));
    }

    private void setComponentPos() {
        componentView.setLayoutX(position.x);
        componentView.setLayoutY(position.y);
    }

    protected int imageWidth;
    protected int imageHeight;
    private static int delay;
    private static final int STEP_WIDTH = 7;

    private int currentFrame;
    private int timerUnit;
    private int points;

    PositionVector[] framesViewports = new PositionVector[2];
}
