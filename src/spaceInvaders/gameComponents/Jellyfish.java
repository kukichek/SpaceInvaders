package spaceInvaders.gameComponents;

public class Jellyfish extends Enemy {
    public Jellyfish(int x, int y, int initialDelay) {
        super(x, y, 70, 72, initialDelay, new Collider(15, 15, 40, 40), 30);

        framesViewports[0] = new PositionVector(5, 5);
        framesViewports[1] = new PositionVector(5, 77);

        setCurrentFrame();
    }

    public Jellyfish(PositionVector position, int initialDelay) {
        this(position.x, position.y, initialDelay);
    }
}
