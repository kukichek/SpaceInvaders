package spaceInvaders.gameComponents;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EnemyArmy {
    public EnemyArmy(int screenWidth, int screenHeight, int initialDelay, AnchorPane root, Player player) {
        EnemyArmy.screenWidth = screenWidth;
        EnemyArmy.screenHeight = screenHeight;
        x = 50;
        y = 80;
        delay = initialDelay;
        direction = Enemy.Direction.LEFT;

        random = new Random();
        army = new LinkedList<>();
        setArmy();
        setTimer(root, player);

        shotListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                Rectangle2D shootColliderRectangle = ((Shell) propertyChangeEvent.getNewValue()).getColliderRectangle();

                GameObject[] deleteObjects = null;

                for (Enemy enemy : army) {
                    if (enemy.getColliderRectangle().intersects(shootColliderRectangle)) {
                        deleteObjects = new GameObject[2];
                        deleteObjects[0] = enemy;
                        deleteObjects[1] = (Shell) propertyChangeEvent.getNewValue();

                        break;
                    }
                }

                if (deleteObjects != null) {
                    decreaseDelay(DELAY_STEP);
                    maxRandomBound -= RANDOM_BOUND_STEP;

                    army.remove(deleteObjects[0]);
                    if (army.isEmpty()) {
                        hasArmyDestroyedPropertySupport.firePropertyChange("army has been destroyed", false, true);
                    }

                    ((Shell) deleteObjects[1]).stopTimer();
                    successfulShotPropertySupport.firePropertyChange("successful shot", null, deleteObjects);
                }
            }
        };
    }

    public List<ImageView> getArmyView() {
        List<ImageView> armyView = new LinkedList<>();

        for (Enemy enemy : army) {
            armyView.add(enemy.getComponentView());
        }

        return armyView;
    }

    public boolean decreaseDelay(int delayDifference) {
        if (delay > delayDifference) {
            delay -= delayDifference;

            if (!army.isEmpty()) {
                army.get(0).decreaseDelay(delayDifference);
            }

            return true;
        }

        return false;
    }

    public void addSuccessfulShotPropertySupport(PropertyChangeListener listener) {
        successfulShotPropertySupport.addPropertyChangeListener(listener);
    }

    public void addHasArmyDestroyedPropertySupport(PropertyChangeListener listener) {
        hasArmyDestroyedPropertySupport.addPropertyChangeListener(listener);
    }

    public void addHasArmySeekedBottomBorderPropertySupport(PropertyChangeListener listener) {
        hasArmySeekedBottomBorderPropertySupport.addPropertyChangeListener(listener);
    }

    public PropertyChangeListener getShotListener() {
        return shotListener;
    }

    private void setTimer(AnchorPane root, Player player) {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
            if (i >= delay) {
                if (direction == Enemy.Direction.LEFT) {
                    for (Enemy enemy : army) {
                        if (enemy.getX() <= 0) {
                            direction = Enemy.Direction.RIGHT;
                            break;
                        }
                    }

                    moveArmy(direction);
                } else {
                    for (Enemy enemy : army) {
                        if (enemy.getX() + enemy.getImageWidth() >= screenWidth) {
                            direction = Enemy.Direction.DOWN;
                            break;
                        }
                    }

                    if (direction == Enemy.Direction.DOWN) {
                        moveArmy(direction);

                        for (Enemy enemy : army) {
                            if (enemy.getColliderRectangle().getMaxY() >= screenHeight - 50) {
                                hasArmySeekedBottomBorderPropertySupport.firePropertyChange("enemy army has seeked bottom border", false, true);
                            }
                        }

                        direction = Enemy.Direction.LEFT;
                    }

                    moveArmy(direction);
                }
                i = 0;
            } else {
                i++;
            }

            shootArmy(root, player);
            }
        };
        timer.start();
    }

    public void stopTimer(){
        timer.stop();
    }

    private void setArmy() {
        for (int i = 0; i < 7; ++i) {
            army.add(new Octopus(x + i * 60, y + 20 + 200, delay));
            army.add(new Octopus(x + i * 60, y + 20 + 150, delay));

            army.add(new Crab(x + i * 60, y + 10 + 100, delay));
            army.add(new Crab(x + i * 60, y + 10 + 50, delay));
        }

        for (int i = 0; i < 9; ++i) {
            Enemy enemy = new Jellyfish(x + 10 + i * 45, y, delay);
            army.add(enemy);
        }
    }

    private void moveArmy(Enemy.Direction direction) {
        x += direction.getX();
        y += direction.getY();

        for (Enemy enemy : army) {
            enemy.move(direction);
        }
    }

    private void shootArmy(AnchorPane root, Player player) {
        for (Enemy enemy : army) {
            if (random.nextInt(maxRandomBound) == 0) {
                enemy.shoot(root, player);
            }
        }
    }

    private int delay;
    private int x, y, i;
    private static int screenWidth;
    private static int screenHeight;
    private List<Enemy> army;
    private AnimationTimer timer;
    private Enemy.Direction direction;
    private PropertyChangeListener shotListener;

    private int maxRandomBound = (ARMY_COUNT + 1) * RANDOM_BOUND_STEP;
    public static final int DELAY_STEP = 3;
    private static final int RANDOM_BOUND_STEP = 200;
    private static final int ARMY_COUNT = 37;

    private final Random random;
    private final PropertyChangeSupport successfulShotPropertySupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport hasArmyDestroyedPropertySupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport hasArmySeekedBottomBorderPropertySupport = new PropertyChangeSupport(this);
}
