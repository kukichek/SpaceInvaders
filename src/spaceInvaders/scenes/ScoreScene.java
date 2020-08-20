package spaceInvaders.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import spaceInvaders.controls.Label;
import spaceInvaders.controls.SlideButton;
import spaceInvaders.controls.SubScene;
import spaceInvaders.data.PersonalizedScore;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;

public class ScoreScene extends Scene {
    private class TableLabel extends Label {
        public TableLabel(String text) {
            super(text);
            setPadding(new Insets(10));

            setPrefHeight(LABEL_HEIGHT);
            setPrefWidth(LABEL_WIDTH);

            BackgroundImage bgImage = new BackgroundImage(
                    new Image(LABEL_URL, LABEL_WIDTH, LABEL_HEIGHT, false, true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, null);
            setBackground(new Background(bgImage));
        }

        private static final String LABEL_URL = "spaceInvaders/controls/resources/yellow_button_white.png";
        private static final int LABEL_HEIGHT = 49;
        private static final int LABEL_WIDTH = 220;
    }

    public ScoreScene(List<PersonalizedScore> scores) throws FileNotFoundException {
        super();

        scoreTableListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                updateScoreList((PersonalizedScore) propertyChangeEvent.getNewValue());
                setBestScores();
            }
        };

        AnchorPane root = (AnchorPane) this.getRoot();

        Label tableLabel = new TableLabel("Best scores");
        Scene.setOnTheMiddle(root, tableLabel, 60);
        SlideButton backMenuButton = new SlideButton(SlideButton.SlideDirection.LEFT);
        backMenuButton.setLayoutX(20);
        backMenuButton.setLayoutY(SCENE_HEIGHT - 20 - 28);

        backMenuButton.setOnMouseClicked((MouseEvent event) -> {
            backMenuSupport.firePropertyChange("pressed back menu button", "free button", "pressed button");
        });

        subSceneRoot = new BorderPane();
        subSceneRoot.setPadding(new Insets(40));
        SubScene scoreTable = new SubScene(subSceneRoot, 460, 400);
        scoreTable.setLayoutX((SCENE_WIDTH - scoreTable.getWidth()) / 2);
        scoreTable.setLayoutY(220);

        this.scoreTable = scores;
        setBestScores();

        root.getChildren().addAll(tableLabel, backMenuButton, scoreTable);
    }

    public List<PersonalizedScore> getScoreTable() {
        return scoreTable;
    }

    public void addBackMenuSupport(PropertyChangeListener listener) {
        backMenuSupport.addPropertyChangeListener(listener);
    }

    public PropertyChangeListener getScoreTableListener() {
        return scoreTableListener;
    }

    private void updateScoreList(PersonalizedScore personalizedScore) {
        if (scoreTable.isEmpty() || scoreTable.get(scoreTable.size() - 1).getScore() <= personalizedScore.getScore()) {
            scoreTable.add(personalizedScore);
        }

        scoreTable.sort(Comparator.reverseOrder());

        if (scoreTable.size() > SCORE_COUNT) {
            scoreTable.remove(scoreTable.size() - 1);
        }
    }

    private void setBestScores() {
        subSceneRoot.getChildren().clear();

        VBox namesBox = new VBox();
        namesBox.alignmentProperty().setValue(Pos.CENTER_LEFT);
        namesBox.setSpacing(15);
        VBox scoresBox = new VBox();
        scoresBox.alignmentProperty().setValue(Pos.CENTER_RIGHT);
        scoresBox.setSpacing(15);

        for (PersonalizedScore score : scoreTable) {
            Label label = new Label(score.getName());
            namesBox.getChildren().add(label);
            label = new Label(Integer.toString(score.getScore()));
            scoresBox.getChildren().add(label);
        }
        subSceneRoot.setLeft(namesBox);
        subSceneRoot.setRight(scoresBox);
    }

    private BorderPane subSceneRoot;

    private final PropertyChangeSupport backMenuSupport = new PropertyChangeSupport(this);
    private final PropertyChangeListener scoreTableListener;
    private List<PersonalizedScore> scoreTable;

    public static final int SCORE_COUNT = 7;
}
