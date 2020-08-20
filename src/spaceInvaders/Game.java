package spaceInvaders;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import spaceInvaders.data.PersonalizedScore;
import spaceInvaders.data.XMLProcessing;
import spaceInvaders.scenes.GameScene;
import spaceInvaders.scenes.MenuScene;
import spaceInvaders.scenes.ScoreScene;

import java.net.URISyntaxException;
import java.util.List;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MediaPlayer scorePlayer = getPlayerOnResource("/spaceInvaders/resources/score_music.mp4");
        MediaPlayer menuPlayer = getPlayerOnResource("/spaceInvaders/resources/menu_music.mp4");
        MediaPlayer gamePlayer = getPlayerOnResource("/spaceInvaders/resources/game_music.mp4");

        MenuScene menuScene = new MenuScene();
        ScoreScene scoreScene = new ScoreScene(XMLProcessing.readXMLFile(SCORE_TABLE_XML_PATH));
        GameScene gameScene = new GameScene();

        stage.setScene(menuScene);
        mainPlayer = menuPlayer;
        mainPlayer.play();

        menuScene.addScoreButtonSupport(event -> {
            setPlayer(scorePlayer);
            stage.setScene(scoreScene);
        });
        menuScene.addCloseButtonSupport(event -> {
            XMLProcessing.writeInXMLFile(SCORE_TABLE_XML_PATH, scoreScene.getScoreTable());
            stage.close();
        });
        menuScene.addPlayButtonSupport(event -> {
            setPlayer(gamePlayer);
            stage.setScene(gameScene);
            List<PersonalizedScore> scoreTable = scoreScene.getScoreTable();
            gameScene.startGame(((scoreTable.isEmpty())? null: scoreTable.get(0).getName()), (scoreScene.getScoreTable().isEmpty())? 0 : scoreScene.getScoreTable().get(0).getScore());
        });

        gameScene.addSetMenuScenePropertySupport(scoreScene.getScoreTableListener());
        gameScene.addSetMenuScenePropertySupport(event -> {
            setPlayer(menuPlayer);
            stage.setScene(menuScene);
        });

        scoreScene.addBackMenuSupport(event -> {
            setPlayer(menuPlayer);
            stage.setScene(menuScene);
        });

        stage.setTitle("Space invaders");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setPlayer(MediaPlayer player) {
        mainPlayer.stop();
        mainPlayer = player;
        mainPlayer.play();
    }

    private MediaPlayer getPlayerOnResource(String uri) throws URISyntaxException {
        MediaPlayer player = new MediaPlayer(new Media(getClass().getResource(uri).toURI().toString()));
        player.setCycleCount(AudioClip.INDEFINITE);
        return player;
    }

    private MediaPlayer mainPlayer;

//    public static final String SCORE_TABLE_XML_PATH = "src/spaceInvaders/resources/scoreTable.xml";
    public static final String SCORE_TABLE_XML_PATH = "resources/xml/scoreTable.xml";
}
