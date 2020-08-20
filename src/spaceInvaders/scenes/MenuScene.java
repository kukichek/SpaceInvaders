package spaceInvaders.scenes;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import spaceInvaders.controls.ClickableButton;
import spaceInvaders.controls.LabelLogo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;

public class MenuScene extends Scene {
    public MenuScene() throws FileNotFoundException {
        super();

        AnchorPane root = (AnchorPane) this.getRoot();

        LabelLogo logo = new LabelLogo("Space invaders", SCENE_WIDTH);
        logo.setLayoutY(100);

        ClickableButton playButton = new ClickableButton("Play!");
        Scene.setOnTheMiddle(root, playButton, 400);

        ClickableButton scoreButton = new ClickableButton("Score");
        Scene.setOnTheMiddle(root, scoreButton, 500);

        ClickableButton exitButton = new ClickableButton("Exit");
        Scene.setOnTheMiddle(root, exitButton, 600);

        playButton.setOnMouseClicked((MouseEvent event) -> {
            playButtonSupport.firePropertyChange("pressed play button", "free button", "pressed button");
        });

        scoreButton.setOnMouseClicked((MouseEvent event) -> {
            scoreButtonSupport.firePropertyChange("pressed score button", "free button", "pressed button");
        });

        exitButton.setOnMouseClicked((MouseEvent event) -> {
            closeButtonSupport.firePropertyChange("pressed exit button", "free button", "pressed button");
        });

        root.getChildren().addAll(playButton, scoreButton, exitButton, logo);
    }

    public void addPlayButtonSupport(PropertyChangeListener listener) {
        playButtonSupport.addPropertyChangeListener(listener);
    }

    public void addScoreButtonSupport(PropertyChangeListener listener) {
        scoreButtonSupport.addPropertyChangeListener(listener);
    }

    public void addCloseButtonSupport(PropertyChangeListener listener) {
        closeButtonSupport.addPropertyChangeListener(listener);
    }

    private final PropertyChangeSupport playButtonSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport scoreButtonSupport = new PropertyChangeSupport(this);
    private final PropertyChangeSupport closeButtonSupport = new PropertyChangeSupport(this);
}
