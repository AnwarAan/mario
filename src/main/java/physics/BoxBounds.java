package physics;

import org.joml.Vector2f;

public class BoxBounds {
  private float width, height;
  private float halfWidth, halfHeight;
  private Vector2f center = new Vector2f();
  private float xBuffer = 0.0f;
  private float yBuffer = 0.0f;
  private boolean shouldCheckTop = true;
  private boolean shouldCheckBottom = true;
  private boolean shouldCheckLeft = true;
  private boolean shouldCheckRight = true;

  public void calculateCenter() {
  }

  public static boolean checkCollision(BoxBounds b1, BoxBounds b2) {
    b1.calculateCenter();
    b2.calculateCenter();

    float dx = b2.center.x - b1.center.x;
    float dy = b2.center.y - b1.center.y;

    float combinedHalfWidth = b1.halfWidth + b2.halfWidth;
    float combinedHalfHeight = b1.halfHeight + b2.halfHeight;

    if (Math.abs(dx) <= combinedHalfWidth) {
      return Math.abs(dy) <= combinedHalfHeight;
    }

    return false;
  }
}
