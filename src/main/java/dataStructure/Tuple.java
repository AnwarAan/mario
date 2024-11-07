package dataStructure;

import java.util.Objects;

public class Tuple<T> {
  public T x;
  public T y;
  public T z;

  public Tuple(T x, T y, T z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Tuple<T> copy() {
    return new Tuple<>(this.x, this.y, this.z);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj==null) return false;
    if (obj.getClass()!=Tuple.class) return false;

    Tuple other = (Tuple) obj;
    return other.equals(this.x) && other.y.equals(this.y) && other.z.equals(this.z);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  @Override
  public String toString() {
    return "Tuple<T>(" + x + ", " + y + ", " + z + ")";
  }
}
