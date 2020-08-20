package spaceInvaders.gameComponents;

public class Crab extends Enemy {
    public Crab(int x, int y, int initialDelay) {
        super(x, y, 95, 72, initialDelay, new Collider(20, 15, 55, 40), 20);

        framesViewports[0] = new PositionVector(180, 5);
        framesViewports[1] = new PositionVector(180, 77);

        setCurrentFrame();
    }

    public Crab(PositionVector position, int initialDelay) {
        this(position.x, position.y, initialDelay);
    }
}
