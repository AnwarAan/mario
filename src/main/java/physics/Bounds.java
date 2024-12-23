package physics;

import jade.Component;
import org.joml.Vector2f;

enum BoundsType {
  Box,
}

public abstract class Bounds extends Component {
  public BoundsType type;
  public boolean isStatic;

  protected boolean isTrigger;

  abstract public float getWidth();

  abstract public float getHeight();

  abstract public boolean raycast(Vector2f position);

  public static boolean checkCollision(Bounds b1, Bounds b2) {
    if (b1.type == b2.type && b1.type == BoundsType.Box) {
      return BoxBounds.checkCollision((BoxBounds) b1, (BoxBounds) b2);
    }
    return false;
  }

  public static Collision resolveCollision(Bounds b1, Bounds b2) {
    if (b1.type == BoundsType.Box && b2.type == BoundsType.Box) {
      BoxBounds b1Bounds = (BoxBounds) b1;
      return b1Bounds
    }
    return null;
  }

  public boolean isTrigger() {
    return this.isTrigger;
  }
}
