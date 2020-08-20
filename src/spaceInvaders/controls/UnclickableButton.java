package spaceInvaders.controls;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UnclickableButton extends Button {
    public UnclickableButton(String text) {
        super(text);
        setButtonFont();
        setPrefWidth(BUTTON_WIDTH);
        setPrefHeight(BUTTON_HEIGHT);
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }

    public void setEnabledStyle() {
        setStyle(BUTTON_STYLE);
    }

    public void setDisabledStyle() {
        setStyle(BUTTON_DISABLED_STYLE);
    }

    private static final int BUTTON_HEIGHT = 45;
    private static final int BUTTON_WIDTH = 190;

//    private static final String FONT_PATH = "src/spaceInvaders/resources/kenvector_future.ttf";
    private static final String FONT_PATH = "resources/font/kenvector_future.ttf";
    private static final String BUTTON_STYLE = "-fx-background-color: transparent;" +
            " -fx-background-image: url('/spaceInvaders/controls/resources/blue_button_white.png')";
    private static final String BUTTON_DISABLED_STYLE = "-fx-text-fill: grey; -fx-opacity: 1.0;" +
            " -fx-background-image: url('/spaceInvaders/controls/resources/grey_button_white.png');";
}
