package jade;

import file.Serialize;

public abstract class Component extends Serialize {
  public GameObject gameObject;

  public void update(double dt) {
    return;
  }

  public void start() {
    return;
  }

  public void collision() {
    return;
  }

  public void trigger() {
    return;
  }

  public abstract Component copy();


}