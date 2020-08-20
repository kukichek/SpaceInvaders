package spaceInvaders.controls;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class LabelLogo extends Label {
    public LabelLogo(String text, int maxWidth) {
        super(text);
        setLabelFont(72);
        setTextFill(Color.WHITE);

        setMaxWidth(maxWidth);
        setWrapText(true);

        setTextAlignment(TextAlignment.CENTER);

        setShadow();
    }

    private void setShadow() {
        DropShadow downDropShadow = new DropShadow(0, 5.0, 5.0, Color.web("0x323232"));
        DropShadow upDropShadow = new DropShadow(0, -5.0, -5.0, Color.web("#ffd017"));

        upDropShadow.setInput(downDropShadow);
        setEffect(upDropShadow);
    }
}
