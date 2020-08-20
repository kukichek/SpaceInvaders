package spaceInvaders.controls;

import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Label extends javafx.scene.control.Label {
    public Label(String text) {
        super(text);
        setLabelFont(23);
    }

    protected void setLabelFont(int size) {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), size));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", size));
        }
    }

//    protected static final String FONT_PATH = "src/spaceInvaders/resources/kenvector_future.ttf";
    protected static final String FONT_PATH = "resources/font/kenvector_future.ttf";
}
