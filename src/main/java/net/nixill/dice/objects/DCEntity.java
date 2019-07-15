package net.nixill.dice.objects;

import net.nixill.dice.parsing.ExpressionSplitter;

/**
 * The base class of all objects in the calculator.
 * <p>
 * An entity has two (by default) subtypes: {@link DCExpression} and
 * {@link DCValue}. An expression can be coerced into a value by evaluating the
 * expression; therefore, the method {@link #getValue()} is available.
 */
public abstract class DCEntity {
  public abstract DCValue getValue();

  /**
   * Returns a string representation of the entity.
   * <p>
   * It defaults to a one-level medium representation followed by short
   * representations of children. See {@link #toString(int)} for more info.
   * 
   * @return A one-level medium string representation of the entity.
   */
  // TODO make this not final
  public final String toString() {
    return toString(1);
  }

  /**
   * Returns a string representation of the entity.
   * <p>
   * If the level parameter is non-zero (positive or negative), such a
   * representation is "medium", which means children should be shown at a level
   * one below the parameter of this one.
   * <p>
   * A level of zero should be shown as a "short" representation, with no
   * children.
   * 
   * @param level The level to show.
   * @return The string representation of the entity.
   */
  public abstract String toString(int level);

  /**
   * Returns a code representation of the entity.
   * <p>
   * The code representation should be usable to make an identical copy of the
   * entity when passed into {@link ExpressionSplitter#parse()}.
   * 
   * @return The code representation of the entity.
   */
  public abstract String toCode();

  protected static String numToString(double number) {
    if (number == Math.floor(number)) {
      return String.format("%0f", number);
    } else {
      return String.format("%2f", number);
    }
  }

  /**
   * Prints to {@link System#out} a tree representation of the entity and all its
   * children.
   */
  public abstract void printTree(int level);

  /**
   * Prints a message with two spaces before it for each level.
   * 
   * @param level   The level at which to print.
   * @param message The message to print.
   */
  protected void printSpaced(int level, String message) {
    System.out.println(new String(new char[level * 2]).replace("\0", " ") + message);
  }
}