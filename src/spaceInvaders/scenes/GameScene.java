package spaceInvaders.scenes;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import spaceInvaders.controls.ApproveNameSubScene;
import spaceInvaders.data.PersonalizedScore;
import spaceInvaders.gameComponents.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;

public class GameScene extends Scene {
    public GameScene() throws FileNotFoundException {
        super();
    }

    public void startGame(String playerName, int highScore) {
        root = (AnchorPane) getRoot();

        Levels levels = new Levels(root, SCENE_WIDTH, SCENE_HEIGHT);
        levels.startLevel(100, 0, Player.MAX_LIVES, highScore);

        setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) { levels.movePlayerLeft(); }
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) { levels.movePlayerRight(); }
            if (event.getCode() == KeyCode.SPACE) { levels.shootWithShell(); }
        });

        levels.addEndLevelPropertySupport(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                Levels.LevelResult levelResult = (Levels.LevelResult) propertyChangeEvent.getNewValue();
                levels.stop();

                if (levelResult.hasWon()) {
                    levels.startLevel(levelResult.getLevelDelay() - EnemyArmy.DELAY_STEP, levelResult.getPoints(), levelResult.getLives(), highScore);
                } else {
                    ApproveNameSubScene approveNameScene = new ApproveNameSubScene(SCENE_WIDTH, playerName);
                    root.getChildren().add(approveNameScene);
                    approveNameScene.showSubScene();
                    approveNameScene.addButtonPressedListener(event -> {
                                root.getChildren().clear();
                                setMenuScenePropertySupport.firePropertyChange("set menu scene", null, new PersonalizedScore(approveNameScene.getText(), levelResult.getPoints()));
                            }
                    );
                }
            }
        });
    }

    public void addSetMenuScenePropertySupport(PropertyChangeListener listener) {
        setMenuScenePropertySupport.addPropertyChangeListener(listener);
    }

    private AnchorPane root;

    private final PropertyChangeSupport setMenuScenePropertySupport = new PropertyChangeSupport(this);
}
