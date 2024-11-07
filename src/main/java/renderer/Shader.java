package renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
  private int shaderProgram;
  private int vertexID;
  private int fragmentID;
  private boolean beingUsed;

  private String fragmentSource;
  private String vertexSource;
  private String filepath;

  public Shader(String filepath) {
    this.filepath = filepath;
    try {
      String source = new String(Files.readAllBytes(Paths.get(filepath)));
      String[] splitString = source.split("(#type)( )+[a-zA-Z]+");
      int index = source.indexOf("#type") + 6;
      int eol = source.indexOf("\r\n", index);
      String firstPattern = source.substring(index, eol).trim();
      index = source.indexOf("#type", eol) + 6;
      eol = source.indexOf("\r\n", index);
      String secondPattern = source.substring(index, eol).trim();

      if (firstPattern.equals("vertex")) {
        vertexSource = splitString[1];
      } else if (firstPattern.equals("fragment")) {
        fragmentSource = splitString[1];
      } else {
        throw new IOException("Unexpected token '" + firstPattern + "' while compiling shader.");
      }

      if (secondPattern.equals("vertex")) {
        vertexSource = splitString[2];
      } else if (secondPattern.equals("fragment")) {
        fragmentSource = splitString[2];
      } else {
        throw new IOException("Unexpected token '" + secondPattern + "' while compiling shader.");
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public void compile() {
    // Load vertex shader and compile
    vertexID = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vertexID, vertexSource);
    glCompileShader(vertexID);

    // Check for errors in compilation
    int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
    if (success==0) {
      int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
      System.out.println("ERROR: '" + filepath + "'\n\tVertex shader compilation failed.");
      System.out.println(glGetShaderInfoLog(vertexID, len));
      System.exit(-1);
    }

    // Load fragment shader and compile
    fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fragmentID, fragmentSource);
    glCompileShader(fragmentID);

    // Check for errors in compilation
    success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
    if (success==0) {
      int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
      System.out.println("ERROR: '" + filepath + "'\n\tFragment shader compilation failed.");
      System.out.println(glGetShaderInfoLog(fragmentID, len));
      System.exit(-1);
    }

    // Link the shaders and create their shader program
    shaderProgram = glCreateProgram();
    glAttachShader(shaderProgram, vertexID);
    glAttachShader(shaderProgram, fragmentID);
    glLinkProgram(shaderProgram);

    // Check for linking errors
    success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
    if (success==GL_FALSE) {
      int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
      System.out.println("ERROR: '" + filepath + "'\n\tShader linking failed.");
      System.out.println(glGetProgramInfoLog(shaderProgram, len));
      System.exit(-1);
    }
  }

  public void use() {
    glUseProgram(shaderProgram);
    beingUsed = true;
  }

  public void detach() {
    glUseProgram(0);
    beingUsed = false;
  }

  public void delete() {
    glDeleteShader(vertexID);
    glDeleteShader(fragmentID);
    glDeleteProgram(shaderProgram);
  }

  public void uploadVec4f(String varName, Vector4f vec4) {
    int varLocation = glGetUniformLocation(shaderProgram, varName);
    if (!beingUsed) this.use();
    glUniform4f(varLocation, vec4.x, vec4.y, vec4.z, vec4.w);
  }

  public void uploadVec3f(String varName, Vector3f vec3) {
    int varLocation = glGetUniformLocation(shaderProgram, varName);
    if (!beingUsed) this.use();
    glUniform3f(varLocation, vec3.x, vec3.y, vec3.z);
  }

  public void uploadVec2f(String varName, Vector2f vec2) {
    int varLocation = glGetUniformLocation(shaderProgram, varName);
    if (!beingUsed) this.use();
    glUniform2f(varLocation, vec2.x, vec2.y);
  }

  public void uploadFloat(String varName, float value) {
    int varLocation = glGetUniformLocation(shaderProgram, varName);
    if (!beingUsed) this.use();
    glUniform1f(varLocation, value);
  }

  public void uploadInt(String varName, int value) {
    int varLocation = glGetUniformLocation(shaderProgram, varName);
    if (!beingUsed) this.use();
    glUniform1i(varLocation, value);
  }

  public void uploadMat4f(String varName, Matrix4f mat4) {
    int varLocation = glGetUniformLocation(shaderProgram, varName);
    if (!beingUsed) this.use();
    FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
    mat4.get(matBuffer);
    glUniformMatrix4fv(varLocation, false, matBuffer);
  }

  public void uploadMat3f(String varName, Matrix3f mat3) {
    int varLocation = glGetUniformLocation(shaderProgram, varName);
    if (!beingUsed) this.use();
    FloatBuffer matBuffer = BufferUtils.createFloatBuffer(0);
    mat3.get(matBuffer);
    glUniformMatrix3fv(varLocation, false, matBuffer);
  }

  public void uploadTexture(String varName, int slot) {
    int varLocation = glGetUniformLocation(shaderProgram, varName);
    if (!beingUsed) this.use();
    glUniform1i(varLocation, slot);
  }
}
