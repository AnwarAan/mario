package jade;

import org.lwjgl.Version;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
  private int width, height;
  private String title;
  private long glfwWindow = 0L;
  private long getGlfwWindow;
  private float r, g, b, a;
  private boolean fadeToBlack = false;

  private static Window window = null;

  private Window() {
    this.width = 1920;
    this.height = 1080;
    this.title = "Mario";
    r = 1;
    g = 1;
    b = 1;
    a = 1;
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
  }

  public void loop() {
    while (!glfwWindowShouldClose(glfwWindow)) {
      glfwPollEvents();
      GL11.glClearColor(r, g, b, a);
      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

      if (fadeToBlack) {
        r = Math.max(r - 0.01f, 0);
        g = Math.max(g - 0.01f, 0);
        b = Math.max(b - 0.01f, 0);
      }

      if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
        fadeToBlack = true;
      }

      if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
        System.out.println("Space key is pressed");
      }

      glfwSwapBuffers(glfwWindow);
    }
  }

}
