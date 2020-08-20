package spaceInvaders.gameComponents;

import javafx.geometry.Rectangle2D;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Player extends GameObject {
    public Player(int x, int y, int initialLives) {
        super(x, y, new Collider(37, 25, 63, 34));

        lives = initialLives;

        shotListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                Rectangle2D shootColliderRectangle = ((Shell) propertyChangeEvent.getNewValue()).getColliderRectangle();

                if (shootColliderRectangle.intersects(getColliderRectangle())) {
                    fireHelper((Shell) propertyChangeEvent.getNewValue());
                }
            }
        };
    }

    public void moveRight() {
        position.x += STEP_WIDTH;
        setComponentPos();
    }

    public void moveLeft() {
        position.x -= STEP_WIDTH;
        setComponentPos();
    }

    public static int getImageWidth() {
        return IMAGE_WIDTH;
    }

    public static int getImageHeight() {
        return IMAGE_HEIGHT;
    }

    public int getLives() {
        return lives;
    }

    public void addSuccessfulShotPropertySupport(PropertyChangeListener listener) {
        successfulShotPropertySupport.addPropertyChangeListener(listener);
    }

    public PropertyChangeListener getShotListener() {
        return shotListener;
    }

    public void fireHelper(Shell shell) {
        GameObject[] deleteObjects = {this, shell};
        successfulShotPropertySupport.firePropertyChange("successful shot", null, deleteObjects);
    }

    public void decreaseLives() {
        lives--;
    }

    public void increaseLives() {
        if (lives < MAX_LIVES) {
            lives++;
        }
    }

    public boolean isDead() {
        return (lives <= 0);
    }

    @Override
    protected void setComponentView() {
        super.setComponentView();
        componentView.setViewport(new Rectangle2D(0, 145, IMAGE_WIDTH, IMAGE_HEIGHT));
        componentView.setScaleX(IMAGE_SCALE_INDEX);
        componentView.setScaleY(IMAGE_SCALE_INDEX);

        setComponentPos();
    }

    private void setComponentPos() {
        componentView.setLayoutX(position.x);
        componentView.setLayoutY(position.y);
    }

    private static final int STEP_WIDTH = 5;
    private static final int IMAGE_WIDTH = 140;
    private static final int IMAGE_HEIGHT = 80;
    public static final int MAX_LIVES = 3;

    private int lives;
    private PropertyChangeListener shotListener;
    private final PropertyChangeSupport successfulShotPropertySupport = new PropertyChangeSupport(this);
}
