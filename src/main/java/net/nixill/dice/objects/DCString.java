package net.nixill.dice.objects;

import java.util.ArrayList;

/**
 * A string of text.
 */
public class DCString extends DCValue {
  private String value;

  /**
   * Creates a new string from the given text.
   */
  public DCString(String val) {
    value = val;
  }

  /**
   * Returns this String as a number by taking its length.
   */
  @Override
  public DCSingle getSingle() {
    return new DCNumber(value.length());
  }

  /**
   * Returns this String as a list by taking all of its character codes.
   */
  @Override
  public DCList getList() {
    char[] chars = value.toCharArray();

    ArrayList<DCValue> out = new ArrayList<>();

    for (char chr : chars) {
      out.add(new DCNumber((double) chr));
    }

    return new DCList(out);
  }

  /**
   * Returns this String itself.
   */
  @Override
  public DCString getString() {
    return this;
  }

  @Override
  public String toString(int lvl) {
    return value;
  }

  @Override
  public String toCode() {
    return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\"";
  }

  @Override
  public void printTree(int level) {
    printSpaced(level, "String: " + value);
  }

}