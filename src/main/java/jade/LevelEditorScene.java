package jade;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWNativeWGL;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.opengl.WGLARBCreateContext;
import renderer.Shader;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class LevelEditorScene extends Scene {

  private String vertexShaderSrc = "#version 330 core\n" +
    "layout(location=0) in vec3 aPos;\n" +
    "layout(location=1) in vec4 aColor;\n" +
    "\n" +
    "out vec4 fColor;\n" +
    "\n" +
    "void main(){\n" +
    "    fColor = aColor;\n" +
    "    gl_Position = vec4(aPos, 1.0);\n" +
    "}";

  private String fragmentShaderSrc = "#version 330 core\n" +
    "\n" +
    "in vec4 fColor;\n" +
    "\n" +
    "out vec4 color;\n" +
    "\n" +
    "void main(){\n" +
    "    color=fColor;\n" +
    "}\n" +
    "\n";

  private int vertexID, fragmentID, shaderProgram;
  private float[] vertexArray = {
    0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
    -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
    0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
    -0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
  };
  private int[] elementArray = {
    2, 1, 0,
    0, 1, 3,

  };

  private int vaoID, vboID, eboID;
  private Shader defaultShader;

  public LevelEditorScene() {
  }

  @Override
  public void init() {
    defaultShader=new Shader("assets/shaders/default.glsl");
    defaultShader.compile();





    //////
    vaoID = glGenVertexArrays();
    glBindVertexArray(vaoID);

    FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
    vertexBuffer.put(vertexArray).flip();

    vboID = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, vboID);
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

    IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
    elementBuffer.put(elementBuffer).flip();

    eboID = glGenBuffers();
    glBindBuffer(GL_ARRAY_BUFFER, eboID);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

    int positionSize = 3;
    int colorSize = 4;
    int floatSizeByte = 4;
    int vertexSizeByte = (positionSize + colorSize) * floatSizeByte;
    glVertexAttribPointer(0, positionSize, GL_FLAT, false, vertexSizeByte, 0);
    glEnableVertexAttribArray(0);

    glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeByte, positionSize * floatSizeByte);
    glEnableVertexAttribArray(1);
  }

  @Override
  public void update(float dt) {
    defaultShader.use();

    glBindVertexArray(vaoID);

    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);

    defaultShader.detach();
  }
}
