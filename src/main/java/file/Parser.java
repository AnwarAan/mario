package file;

import jade.GameObject;
import util.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Parser {
  public static int offset = 0;
  public static int line = 1;
  public static byte[] bytes;

  public static void openLevelFile(String filename) {
    File tmp = new File("assets/levels" + filename + ".level");
    if (!tmp.exists()) {
      bytes = new byte[0];
      return;
    }
    offset = 0;
    line = 1;

    try {
      ZipFile zipFile = new ZipFile("assets/levels/" + filename + ".level");
      ZipEntry jsonFile = zipFile.getEntry(filename + ".json");
      InputStream stream = zipFile.getInputStream(jsonFile);

      byte[] finalByte = new byte[0];
      while (stream.available() != 0) {
        byte[] byteBuffer = new byte[stream.available()];
        stream.read(byteBuffer);
        finalByte = Util.combine(finalByte, byteBuffer);
      }
      Parser.bytes = finalByte;
      stream.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public static void openFile(String filename) {
    File tmp = new File(filename + ".layput");
    if (!tmp.exists()) {
      bytes = new byte[0];
      return;
    }
    offset = 0;
    line = 1;

    try {
      ZipFile zipFile = new ZipFile(filename + ".layout");
      ZipEntry jsonFile = zipFile.getEntry(filename + ".json");
      InputStream stream = zipFile.getInputStream(jsonFile);

      byte[] finalByte = new byte[0];
      while (stream.available() != 0) {
        byte[] byteBuffer = new byte[stream.available()];
        stream.read(byteBuffer);
        finalByte = Util.combine(finalByte, byteBuffer);
      }
      Parser.bytes = finalByte;
      stream.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public static GameObject parseGameObject() {
    if (bytes.length == 0 || atEnd()) return null;
    if (peek() == ',') Parser.consume(',');
    skipWhitespace();
    if (atEnd()) return null;
    return GameObject.deserilize();
  }

  public static void consume(char c) {
    char actual = peek();
    if (actual != c) {
      assert false : "Error: Expected '" + c + "' but instead got '" + actual + "' at line " + Parser.line;
    }
  }

  public static void skipWhitespace() {
    while (!atEnd() && (peek() == ' ' || peek() == '\n' || peek() == '\t' || peek() == '\r' || (byte) peek() == 0)) {
      if (peek() == '\n') Parser.line++;
      advance();
    }
  }

  public static char advance() {
    char c = (char) bytes[offset];
    offset++;
    return c;
  }

  public static boolean atEnd() {
    return offset >= bytes.length;
  }

  public static char peek() {
    return (char) bytes[offset];
  }
}
