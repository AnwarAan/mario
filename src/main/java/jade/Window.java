package jade;

import org.lwjgl.Version;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import util.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private int width, height;
  private String title;
  private long glfwWindow = 0L;
  private long getGlfwWindow;
  public float r, g, b, a;
  private boolean fadeToBlack = false;

  private static Window window = null;
  private static Scene currentScene;


  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.title = "Mario";
    r = 1;
    g = 1;
    b = 1;
    a = 1;
  }

  public static void changeScene(int newScene) {
    switch (newScene) {
      case 0:
        currentScene = new LevelEditorScene();
//        currentScene.init()
        break;
      case 1:
        currentScene = new LevelScene();
        break;
      default:
        assert false:"Uknow Scene " + newScene;
        break;
    }
  }

  public static Window get() {
    if (Window.window==null) {
      Window.window = new Window();
    }
    return Window.window;
  }

  public void run() {
    System.out.println("Hello" + Version.getVersion());

    init();
    loop();
//
//    glfwFreeCallbacks(glfwWindow);
//    glfwDestroyWindow(glfwWindow);
//
//    glfwTerminate();
//    glfwSetErrorCallback(null).free();
  }

  public void init() {
    GLFWErrorCallback.createPrint(System.err).set();

    if (!glfwInit()) {
      throw new IllegalStateException("Unable Initialize");
    }

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
    glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

    if (glfwWindow==NULL) {
      throw new IllegalStateException("Failed create");
    }

    glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
    glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
    glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
    glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

    glfwMakeContextCurrent(glfwWindow);
    glfwSwapInterval(1);
    glfwShowWindow(glfwWindow);
    GL.createCapabilities();

    Window.changeScene(0);
  }

  public void loop() {
    float beginTime = Time.getTime();
    float endTime = Time.getTime();
    float dt = -1.0f;

    while (!glfwWindowShouldClose(glfwWindow)) {
      glfwPollEvents();

      GL11.glClearColor(r, g, b, a);
      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

      if (dt >= 0) {
        currentScene.update(dt);
      }
      currentScene.update(dt);

      glfwSwapBuffers(glfwWindow);

      endTime = Time.getTime();
      dt = endTime - beginTime;
      beginTime = endTime;

    }
  }

}
