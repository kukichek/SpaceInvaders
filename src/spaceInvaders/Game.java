package spaceInvaders;

import javafx.application.Application;
import javafx.stage.Stage;
import spaceInvaders.data.PersonalizedScore;
import spaceInvaders.data.XMLProcessing;
import spaceInvaders.scenes.GameScene;
import spaceInvaders.scenes.MenuScene;
import spaceInvaders.scenes.ScoreScene;

import java.util.List;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MenuScene menuScene = new MenuScene();
        ScoreScene scoreScene = new ScoreScene(XMLProcessing.readXMLFile(SCORE_TABLE_XML_PATH));
        GameScene gameScene = new GameScene();

        stage.setScene(menuScene);

        menuScene.addScoreButtonSupport(event -> {
            stage.setScene(scoreScene);
        });
        menuScene.addCloseButtonSupport(event -> {
            XMLProcessing.writeInXMLFile(SCORE_TABLE_XML_PATH, scoreScene.getScoreTable());
            stage.close();
        });
        menuScene.addPlayButtonSupport(event -> {
            stage.setScene(gameScene);
            List<PersonalizedScore> scoreTable = scoreScene.getScoreTable();
            gameScene.startGame(((scoreTable.isEmpty())? null: scoreTable.get(0).getName()), (scoreScene.getScoreTable().isEmpty())? 0 : scoreScene.getScoreTable().get(0).getScore());
        });

        gameScene.addSetMenuScenePropertySupport(scoreScene.getScoreTableListener());
        gameScene.addSetMenuScenePropertySupport(event -> {
            stage.setScene(menuScene);
        });

        scoreScene.addBackMenuSupport(event -> {
            stage.setScene(menuScene);
        });

        stage.setTitle("Space invaders");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static final String SCORE_TABLE_XML_PATH = "resources/xml/scoreTable.xml";
}
