package jade;

import org.joml.Matrix4d;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
  private boolean isPrespective = false;
  private Matrix4f projectionMatrix, viewMatrix, inverseProjection;
  private float fov = 45;
  private float aspect = 0.0f;
  private Vector2f position;
  private Vector3f vec3Position;

  public Camera(Vector2f position) {
    this.position = position;
    this.vec3Position = new Vector3f(position.x, position.y, 10.f);
    this.projectionMatrix = new Matrix4f();
    this.inverseProjection = new Matrix4f();
//    this.caluclateAspect();
//    this.adjustPerspective();
  }

  public Vector2f position() {
    return this.position;
  }

  public void calculateAspect() {
    this.aspect = (float) Window.getWindow().getWidth() / (float) Window.getWindow().getHeight();
  }

  public void adjustPerspective() {
    if (isPrespective) {
      projectionMatrix = projectionMatrix.perspective(fov, (float) Window
        .getWindow().getWidth() / (float) Window.getWindow().getHeight(), 0.1f, 100.0f);
    } else {
      projectionMatrix.identity();
      projectionMatrix.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f);
      inverseProjection.identity();
      projectionMatrix.invert(inverseProjection);
    }
  }

  public Matrix4f getInverseProjection() {
    return this.inverseProjection;
  }

  public Vector2f worldToScreen(Vector2f wordCoords) {
    wordCoords.x -= position.x;
    wordCoords.y -= position.y;
    return wordCoords;
  }

  public Vector3f getVec3Position() {
    this.vec3Position.x = position.x;
    this.vec3Position.y = position.y;
    return this.vec3Position;
  }

  public Matrix4f getViewMatrix() {
    Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
    this.viewMatrix.identity();
    this.viewMatrix = viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f), cameraFront.add(position.x, position.y, 0.0f), cameraUp);
    return this.viewMatrix;
  }

  public Matrix4f getFixedMatrix() {
    Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
    this.viewMatrix.identity();
    this.viewMatrix = viewMatrix.lookAt(new Vector3f(0.0f, 0.0f, 20.0f), cameraFront, cameraUp);
    return this.viewMatrix;
  }

  public Matrix4f getProjectionMatrix() {
    return projectionMatrix;
  }
}
