package jade;

import dataStructure.Tuple;
import file.Parser;
import physics.Collision;
import physics.Trigger;

import javax.xml.crypto.dsig.Transform;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class GameObject extends Object {
  private List<Component> components;
  private float lastZIndex;
  private String name;
  private boolean serializeable = true;
  private boolean isUi = false;
  private boolean isStarted = false;
  private Tuple<Integer> gridCoords = new Tuple<>(0, 0, 0);

  public GameObject(String name, Transform transform, int zIndex) {
    this.name = name;
//    this.transform=transform
    this.components = new ArrayList<>();
//    this.zIndex=zIndex;
//    this.lastZIndex=this.zIndex;
  }

  public String getName() {
    return this.name;
  }

  public void cpllision(Collision collision) {
    for (Component c : components) {
      c.collision(collision);
    }
  }

  public void trigger(Trigger trigger) {
    for (Component c : components) {

    }
  }

  public <T extends Component> T getComponent(Class<T> componentClass) {
    for (Component c : components) {
      if (componentClass.isAssignableFrom(c.getClass())) {
        try {
          return componentClass.cast(c);
        } catch (ClassCastException e) {
          e.printStackTrace();
          System.exit(-1);
        }
      }

    }
    return null;
  }

  public static GameObject deserilize(){

  }


}
