package spaceInvaders.graphics;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import spaceInvaders.controls.Label;

import java.util.ArrayList;
import java.util.List;

public class LevelGraphics {
    public LevelGraphics(int highScore, int currentScore, int lives, int sceneWidth, int sceneHeight, AnchorPane root) {
        this.lives = lives;
//        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.root = root;

//        leftBorder = 20;
//        rightBorder = sceneWidth - leftBorder;

        livesImageViews = new ArrayList<>();
        for (int i = 0; i < lives - 1; i++) {
            livesImageViews.add(getIndexedImageView(i));
        }

        currentScoreLabel = new Label(String.format(DUMMY_SCORE_STRING, "score", currentScore));
        highScoreLabel = new Label(String.format(DUMMY_SCORE_STRING, "high score", highScore));
        currentLivesLabel = new Label(Integer.toString(lives - 1));

        currentScoreLabel.setLayoutX(20);
        currentScoreLabel.setLayoutY(10);
        currentScoreLabel.setTextFill(Color.WHITE);
        highScoreLabel.setLayoutX(sceneWidth / 2);
        highScoreLabel.setLayoutY(10);
        highScoreLabel.setTextFill(Color.WHITE);
        currentLivesLabel.setLayoutX(20);
        currentLivesLabel.setLayoutY(sceneHeight - 40);
        currentLivesLabel.setTextFill(Color.WHITE);

        Line line = new Line(20, sceneHeight - 50, sceneWidth - 20, sceneHeight - 50);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(3);

        root.getChildren().addAll(currentScoreLabel, highScoreLabel, currentLivesLabel, line);
    }

    public void setCurrentScoreLabel(int currentScore) {
        currentScoreLabel.setText(String.format(DUMMY_SCORE_STRING, "score", currentScore));
    }

//    public void increaseLives() {
//        if (lives > 0) {
//            setCurrentLivesLabel(lives - 1);
//
//            livesImageViews.add(getIndexedImageView(livesImageViews.size()));
//        }
//    }

    public void decreaseLives() {
        lives--;
        setCurrentLivesLabel(lives);

        if (!livesImageViews.isEmpty()) {
            root.getChildren().remove(livesImageViews.get(livesImageViews.size() - 1));
            livesImageViews.remove(livesImageViews.size() - 1);
        }
    }

    private ImageView getIndexedImageView(int index) {
        ImageView liveView = new ImageView(PLAYER_URL);

        liveView.setViewport(new Rectangle2D(0, 145, 140, 80));
        liveView.setScaleX(0.3);
        liveView.setScaleY(0.3);

        liveView.setLayoutX(40 * index);
        liveView.setLayoutY(sceneHeight - 67);

        root.getChildren().add(liveView);

        return liveView;
    }

    private void setCurrentLivesLabel(int lives) {
        currentLivesLabel.setText(Integer.toString(lives - 1));
    }

    private int lives;
    private AnchorPane root;
    private Label currentScoreLabel;
    private Label highScoreLabel;
    private Label currentLivesLabel;
    private List<ImageView> livesImageViews;

    private static int sceneHeight;
//    private static int sceneWidth;
//    private static int leftBorder;
//    private static int rightBorder;

    private static final String DUMMY_SCORE_STRING = "%s: %d";
    private static final String PLAYER_URL = "spaceInvaders/resources/componentsSprite.png";
}
