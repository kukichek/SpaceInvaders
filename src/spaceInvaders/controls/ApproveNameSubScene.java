package spaceInvaders.controls;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class ApproveNameSubScene extends SubScene {
    public ApproveNameSubScene(int parentSceneWidth, String initialPlayerName) {
        super(new AnchorPane(), 460, 240);

        Pattern playerNameRegex = Pattern.compile("[\\w]{1,12}");

        AnchorPane subScenePane = (AnchorPane) getRoot();

        setLayoutX((parentSceneWidth - getWidth()) / 2);
        setLayoutY(-getHeight());

        Label enterLabel = new Label("Enter your name:");
        enterLabel.setPrefWidth(300);
        enterLabel.setPrefHeight(40);
        enterLabel.setLayoutX((getWidth() - enterLabel.getPrefWidth()) / 2);
        enterLabel.setLayoutY(30);

        approveButton = new UnclickableButton("Approve");
        approveButton.setLayoutX((getWidth() - approveButton.getPrefWidth()) / 2);
        approveButton.setLayoutY(165);

        textField = new TextField();
        if (initialPlayerName != null) {
            textField.setText(initialPlayerName);
            approveButton.setEnabledStyle();
        } else {
            approveButton.setDisabledStyle();
        }
        try {
            textField.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            textField.setFont(Font.font("Verdana", 23));
        }
        textField.setPrefColumnCount(14);
        textField.setLayoutX(40);
        textField.setLayoutY((getHeight() - 40) / 2);
        textField.textProperty().addListener(event ->
                setButtonState(playerNameRegex, textField.getText())
        );

        subScenePane.getChildren().addAll(enterLabel, textField, approveButton);
    }

    public void showSubScene() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (getLayoutY() < 180) {
                    setLayoutY(getLayoutY() + 10);
                } else {
                    stop();
                }
            }
        };
        timer.start();
    }

    public void addButtonPressedListener(EventHandler handler) {
        approveButton.setOnAction(handler);
    }

    public String getText() {
        return textField.getText();
    }

    private void setButtonState(Pattern playerNameRegex, String text) {
        if (playerNameRegex.matcher(text).matches()) {
            approveButton.setDisable(false);
            approveButton.setEnabledStyle();
        } else {
            approveButton.setDisable(true);
            approveButton.setDisabledStyle();
        }
    }

    private UnclickableButton approveButton;
    private TextField textField;
//    private static final String FONT_PATH = "src/spaceInvaders/resources/kenvector_future.ttf";
    private static final String FONT_PATH = "resources/font/kenvector_future.ttf";
}
