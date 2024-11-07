package jade;

import dataStructure.Tuple;

import javax.xml.crypto.dsig.Transform;
import java.util.ArrayList;
import java.util.List;

public class GameObject extends Object {
  private List<Component> components;
  private float lastZIndex;
  private String name;
  private boolean serializeable = true;
  private boolean isUi = false;
  private boolean isStarted = false;
  private Tuple<Integer> gridCoords = new Tuple;

  public GameObject(String name, Transform transform, int zIndex) {
    this.name = name;
//    this.transform=transform
    this.components = new ArrayList<>();
//    this.zIndex=zIndex;
//    this.lastZIndex=this.zIndex;
  }

  public String getName(){
    return  this.name;
  }

  public void cpllision(Collis){}
  public

}
