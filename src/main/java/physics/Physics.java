package physics;

import dataStructure.Tuple;
import jade.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Physics {
  List<GameObject> staticObject;
  List<GameObject> dynamicObjects;

  private float tickSpeed = 1 / 60f;
  private float tickSpeedLef = 0f;

  private Tuple<Integer> tuple;

  public Physics() {
    this.staticObject = new ArrayList<>();
    this.dynamicObjects = new ArrayList<>();
    this.tuple = new Tuple<>(0, 0, 0);
  }

  public void reset() {
    this.dynamicObjects.clear();
    this.staticObject.clear();
  }

  public void addGameObject(GameObject go) {
    Bounds bounds = go.getComponent(Bounds.class);
    if (bounds != null) {
      if (bounds.isStatic) {
        this.staticObject.add(go);
      } else {
        this.dynamicObjects.add(go);
      }
    }
  }

  public void update(double dt) {
    for (; tickSpeedLef < dt; tickSpeedLef += tickSpeed) {
    for (GameObject go:dynamicObjects){
      Rigi
    }
    }
  }
}
