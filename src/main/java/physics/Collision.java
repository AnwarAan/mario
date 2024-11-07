package physics;

import jade.GameObject;
import org.joml.Vector2f;

public class Collision {
  public  enum CollisionSide{
    TOP,LEFT,RIGHT,BOTTOM,TRIGGER;
  }
  public GameObject gameObject;
  public CollisionSide collisionSide;
  public Vector2f contactPoint
  public BoxBounds bounds;
}
