package spaceInvaders.gameComponents;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public abstract class GameObject {
    static protected class PositionVector {
        int x, y;

        public PositionVector(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public PositionVector() {
            this(0, 0);
        }
    }

    static protected class Collider {
        private PositionVector colliderPosition = new PositionVector();
        private PositionVector dimension = new PositionVector();

        public Collider(int x, int y, int width, int height) {
            colliderPosition.x = x;
            colliderPosition.y = y;
            dimension.x = width;
            dimension.y = height;
        }

        public Collider(Collider collider) {
            this(collider.colliderPosition.x, collider.colliderPosition.y, collider.dimension.x, collider.dimension.y);
        }

        public Rectangle2D getRectangle(int offsetX, int offsetY) {
            return new Rectangle2D(offsetX + colliderPosition.x, offsetY + colliderPosition.y, dimension.x, dimension.y);
        }
    }

    public GameObject(int x, int y, Collider collider) {
        position.x = x;
        position.y = y;

        this.collider = new Collider(collider);

        setComponentView();
    }

    public GameObject(PositionVector position, Collider collider) {
        this(position.x, position.y, collider);
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Rectangle2D getColliderRectangle() {
        return collider.getRectangle(position.x, position.y);
    }

    public ImageView getComponentView() {
        return componentView;
    }

    protected void setComponentView() {
        componentView = new ImageView(PLAYER_URL);
    }

    protected ImageView componentView;
    protected Collider collider;

    protected PositionVector position = new PositionVector();

    protected static final double IMAGE_SCALE_INDEX = 0.6;
    private static final String PLAYER_URL = "spaceInvaders/resources/componentsSprite.png";
}
