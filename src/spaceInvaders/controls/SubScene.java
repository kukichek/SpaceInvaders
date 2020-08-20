package spaceInvaders.controls;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class SubScene extends javafx.scene.SubScene {
    public SubScene(Parent root, double width, double height) {
        super(root, width, height);
        Pane rootPane = (Pane) root;

        BackgroundImage bgImage = new BackgroundImage(
                new Image(BACKGROUND_URL, width, height, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null);
        rootPane.setBackground(new Background(bgImage));
    }
    private static final String BACKGROUND_URL = "spaceInvaders/controls/resources/yellow_panel.png";
}
