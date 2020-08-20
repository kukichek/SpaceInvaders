package spaceInvaders.controls;

import spaceInvaders.utils.Swap;

public class SlideButton extends javafx.scene.control.Button {
    public SlideButton(SlideDirection direction) {
        setPrefWidth(direction.getButtonWidth());
        setPrefHeight(direction.getButtonHeight());
        setStyle(String.format(BUTTON_STYLE_DUMMY, direction.getPath()));
    }

    public enum SlideDirection {
        UP("/spaceInvaders/controls/resources/yellow_sliderUp.png", Orientation.PORTRAIT),
        DOWN("/spaceInvaders/controls/resources/yellow_sliderDown.png", Orientation.PORTRAIT),
        LEFT("/spaceInvaders/controls/resources/yellow_sliderLeft.png", Orientation.LANDSCAPE);

        private enum Orientation {
            LANDSCAPE,
            PORTRAIT;
        }

        public String getPath() {
            return path;
        }
        public int getButtonWidth() {
            return buttonWidth;
        }
        public int getButtonHeight() {
            return buttonHeight;
        }

        SlideDirection(String path, Orientation orientation) {
            this.path = path;

            if (orientation == Orientation.LANDSCAPE) {
                buttonHeight = Swap.intsGetItself(buttonWidth, buttonWidth = buttonHeight);
            }
        }

        private String path;
        private int buttonWidth = 28;
        private int buttonHeight = 40;
    }

    private static final String BUTTON_STYLE_DUMMY = "-fx-background-color: transparent; " +
            "-fx-background-image: url(%s)";
}
