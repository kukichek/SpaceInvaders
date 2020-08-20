package spaceInvaders.gameComponents;

import javafx.scene.layout.AnchorPane;
import spaceInvaders.graphics.LevelGraphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Levels {
    public Levels(AnchorPane root, int sceneWidth, int sceneHeight) {
        this.root = root;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    public void startLevel(int initialDelay, int startGamePoints, int initialLives, int highScore) {
        time = 0;
        totalGamePoints = startGamePoints;

        player = new Player((sceneWidth - Player.getImageWidth()) / 2, 600, initialLives);

        LevelGraphics levelGraphics = new LevelGraphics(highScore, totalGamePoints, player.getLives(), sceneWidth, sceneHeight, root);

        PropertyChangeListener successfulShotListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                GameObject person = ((GameObject[]) propertyChangeEvent.getNewValue())[0];
                GameObject shell = ((GameObject[]) propertyChangeEvent.getNewValue())[1];

                root.getChildren().remove(person.getComponentView());
                root.getChildren().remove(shell.getComponentView());

                ((Shell) shell).stopTimer();

                if (person instanceof Player) {
                    Player shotPlayer = (Player) person;
                    shotPlayer.decreaseLives();
                    levelGraphics.decreaseLives();

                    if (!shotPlayer.isDead()) {
                        root.getChildren().add(shotPlayer.getComponentView());
                    } else {
                        endLevelPropertySupport.firePropertyChange("player loose",
                                null, new LevelResult(initialDelay, totalGamePoints, player.getLives(), false));
                    }
                } else {
                    totalGamePoints += ((Enemy) person).getPoints();

                    levelGraphics.setCurrentScoreLabel(totalGamePoints);
                }
            }
        };

        player.addSuccessfulShotPropertySupport(successfulShotListener);

        army = new EnemyArmy(sceneWidth, sceneHeight, initialDelay, root, player);
        army.addSuccessfulShotPropertySupport(successfulShotListener);
        army.addHasArmyDestroyedPropertySupport(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                player.increaseLives();
                endLevelPropertySupport.firePropertyChange("player wins",
                        null, new LevelResult(initialDelay, totalGamePoints, player.getLives(), true));
            }
        });
        army.addHasArmySeekedBottomBorderPropertySupport(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                endLevelPropertySupport.firePropertyChange("player loose", null, new LevelResult(initialDelay, totalGamePoints, player.getLives(), false));
            }
        });

        root.getChildren().addAll(army.getArmyView());
        root.getChildren().add(player.getComponentView());
    }

    public void stop() {
        root.getChildren().clear();
        army.stopTimer();
    }

    public void addEndLevelPropertySupport(PropertyChangeListener listener) {
        endLevelPropertySupport.addPropertyChangeListener(listener);
    }

    public void movePlayerLeft() {
        if (player.getX() > -20) {
            player.moveLeft();
        }
    }

    public void movePlayerRight() {
        if (player.getX() < sceneWidth - player.getImageWidth() + 20) {
            player.moveRight();
        }
    }

    public void shootWithShell() {
        shootWithShell(army);
    }

    private void shootWithShell(EnemyArmy army) {
        if (System.currentTimeMillis() - time > TIME_DIFFERENCE) {
            time = System.currentTimeMillis();

            Shell shell = new Shell(player.getX() + (Player.getImageWidth() - Shell.getImageWidth()) / 2, player.getY() - Shell.getImageHeight());

            root.getChildren().add(shell.getComponentView());

            shell.addOutOfBoundsPropertySupport(propertyChangeEvent ->
                    root.getChildren().remove(shell.getComponentView()));
            shell.addShotPropertySupport(army.getShotListener());
        }
    }

    public static class LevelResult {
        public LevelResult(int delay, int points, int lives, boolean hasWon) {
            this.levelDelay = delay;
            this.points = points;
            this.lives = lives;
            this.hasWon = hasWon;
        }

        public int getLevelDelay() {
            return levelDelay;
        }

        public int getPoints() {
            return points;
        }

        public int getLives() { return lives; }

        public boolean hasWon() {
            return hasWon;
        }

        private int levelDelay;
        private int points;
        private int lives;
        private boolean hasWon;
    }

    private int totalGamePoints;
    private long time;
    private Player player;
    private EnemyArmy army;

    private final AnchorPane root;
    private final int sceneWidth;
    private final int sceneHeight;

    private static final int TIME_DIFFERENCE = 1250;

    private final PropertyChangeSupport endLevelPropertySupport = new PropertyChangeSupport(this);
}
