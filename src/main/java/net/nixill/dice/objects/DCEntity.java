package net.nixill.dice.objects;

import java.text.DecimalFormat;

import net.nixill.dice.parsing.ExpressionSplitter;

/**
 * The base class of all objects in the calculator.
 * <p>
 * An entity has two (by default) subtypes: {@link DCExpression} and
 * {@link DCValue}. An expression can be coerced into a value by evaluating
 * the expression; therefore, the method {@link #getValue()} is available.
 */
public abstract class DCEntity {
  public abstract DCValue getValue();
  
  /**
   * A number formatter for decimals.
   */
  protected static DecimalFormat numFormat  = new DecimalFormat("#.###");
  /**
   * A number formatter for code.
   */
  protected static DecimalFormat codeFormat = new DecimalFormat(
      "#.#################;(-#.#################)");
  
  /**
   * Returns a string representation of the entity.
   * <p>
   * It defaults to a one-level medium representation followed by short
   * representations of children. See {@link #toString(int)} for more info.
   * 
   * @return A one-level medium string representation of the entity.
   */
  public final String toString() {
    return toString(1);
  }
  
  /**
   * Returns a string representation of the entity.
   * <p>
   * If the level parameter is non-zero (positive or negative), such a
   * representation is "medium", which means children should be shown at a
   * level one below the parameter of this one.
   * <p>
   * A level of zero should be shown as a "short" representation, with no
   * children.
   * 
   * @param level
   *   The level to show.
   * @return The string representation of the entity.
   */
  public abstract String toString(int level);
  
  /**
   * Returns a code representation of the entity.
   * <p>
   * The code representation should be usable to make an identical copy of
   * the entity when passed into {@link ExpressionSplitter#parse(String)}.
   * 
   * @return The code representation of the entity.
   */
  public abstract String toCode();
  
  /**
   * Prints to {@link System#out} a tree representation of the entity and
   * all its children.
   */
  public abstract void printTree(int level);
  
  /**
   * Prints a message with two spaces before it for each level.
   * 
   * @param level
   *   The level at which to print.
   * @param message
   *   The message to print.
   */
  protected void printSpaced(int level, String message) {
    System.out.println(
        new String(new char[level * 2]).replace("\0", " ") + message);
  }
}