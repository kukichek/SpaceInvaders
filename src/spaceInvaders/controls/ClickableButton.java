package spaceInvaders.controls;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ClickableButton extends javafx.scene.control.Button {

    public ClickableButton(String text) {
        super(text);
        setButtonFont();
        setPrefWidth(BUTTON_WIDTH);
        setButtonExitedStyle();

        setOnMouseEntered((MouseEvent event) -> {
            setButtonEnteredStyle();
        });
        setOnMouseExited((MouseEvent event) -> {
            setButtonExitedStyle();
        });
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }

    private void setButtonEnteredStyle() {
        setStyle(BUTTON_ENTERED_PATH);
        setPrefHeight(ENTERED_BUTTON_HEIGHT);
        setLayoutY(getLayoutY() + HEIGHT_DIFFERENCE);
    }

    private void setButtonExitedStyle() {
        setStyle(BUTTON_EXITED_PATH);
        setPrefHeight(EXITED_BUTTON_HEIGHT);
        setLayoutY(getLayoutY() - HEIGHT_DIFFERENCE);
    }

    private static final int ENTERED_BUTTON_HEIGHT = 45;
    private static final int EXITED_BUTTON_HEIGHT = 49;
    private static final int HEIGHT_DIFFERENCE = EXITED_BUTTON_HEIGHT - ENTERED_BUTTON_HEIGHT;
    private static final int BUTTON_WIDTH = 190;

//    private static final String FONT_PATH = "src/spaceInvaders/resources/kenvector_future.ttf";
    private static final String FONT_PATH = "resources/font/kenvector_future.ttf";
    private static final String BUTTON_ENTERED_PATH = "-fx-background-color: transparent;" +
            " -fx-background-image: url('/spaceInvaders/controls/resources/yellow_button_pressed.png')";
    private static final String BUTTON_EXITED_PATH = "-fx-background-color: transparent;" +
            " -fx-background-image: url('/spaceInvaders/controls/resources/yellow_button_released.png')";
}
