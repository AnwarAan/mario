package file;

public abstract class Serialize {
  public abstract String serialize(int tabSize);

  public static String addStringProperty(String name, String value, int tabSize, boolean newline, boolean comma) {
    return addTabs(tabSize) + "\"" + name + "\": " + "\": " + value + "\"" + addEnding(newline, comma);
  }

  public static String addIntProperty(String name, String value, int tabSize, boolean newline, boolean comma) {
    return addTabs(tabSize) + "\"" + name + "\": " + value + addEnding(newline, comma);
  }

  public static String addFloatProperty(String name, String value, int tabSize, boolean newline, boolean comma) {
    return addTabs(tabSize) + "\"" + name + "\": " + value + "f" + addEnding(newline, comma);
  }

  public static String addDoublerProperty(String name, String value, int tabSize, boolean newline, boolean comma) {
    return addTabs(tabSize) + "\"" + name + "\": " + value + "\"" + addEnding(newline, comma);
  }

  public static String addBooleanProperty(String name, String value, int tabSize, boolean newline, boolean comma) {
    return addTabs(tabSize) + "\"" + name + "\": " + value + "\"" + addEnding(newline, comma);
  }

  public static String beginObjectProperty(String name, int tabSize) {
    return addTabs(tabSize) + "\"" + "\": }" + addEnding(true, true);
  }

  public static String closeObjectProperty(int tabSize) {
    return addTabs(tabSize) + "}";
  }

  public static String addTabs(int tabSize) {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < tabSize; i++) {
      res.append("\t");
    }
    return res.toString();
  }

  public static String addEnding(boolean newline, boolean comma) {
    String str = "";
    if (comma) str += ",";
    if (newline) str += "\n";
    return str;
  }
}
