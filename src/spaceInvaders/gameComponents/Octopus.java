package spaceInvaders.gameComponents;

public class Octopus extends Enemy {
    public Octopus(int x, int y, int initialDelay) {
        super(x, y, 100, 72, initialDelay, new Collider(20, 15, 57, 40), 10);

        framesViewports[0] = new PositionVector(80, 5);
        framesViewports[1] = new PositionVector(80, 77);

        setCurrentFrame();
    }

    public Octopus(PositionVector position, int initialDelay) {
        this(position.x, position.y, initialDelay);
    }
}
