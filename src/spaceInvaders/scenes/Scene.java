package spaceInvaders.scenes;

import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Scene extends javafx.scene.Scene {
    public Scene() throws FileNotFoundException {
        super(new AnchorPane(), SCENE_WIDTH, SCENE_HEIGHT, new ImagePattern(new Image(new FileInputStream(BACKGROUND_URL))));

        AnchorPane root = (AnchorPane) this.getRoot();

        root.setStyle("-fx-background-color: transparent;");
        root.setPrefWidth(SCENE_WIDTH);
        root.setPrefHeight(SCENE_HEIGHT);
    }

    protected static void setOnTheMiddle(AnchorPane pane, Control button, int y) {
        button.setLayoutX((pane.getPrefWidth() - button.getPrefWidth()) / 2);
        button.setLayoutY(y);
    }

    protected static final int SCENE_WIDTH = 640;
    protected static final int SCENE_HEIGHT = 720;

//    private static final String BACKGROUND_URL = "src/spaceInvaders/resources/background.jpg";
    private static final String BACKGROUND_URL = "resources/sprites/background.jpg";
}
