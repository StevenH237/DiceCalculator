package net.nixill.dice.objects;

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
   * Returns a medium string representation of the entity.
   * <p>
   * Such a representation should include short-string representations of child
   * entities.
   * 
   * @return A medium string representation of the entity.
   */
  public abstract String toString();

  /**
   * Returns a short string representation of the entity.
   * <p>
   * Such a representation should not include representations of child entities.
   * 
   * @return A short string representation of the entity.
   */
  public abstract String toShortString();

  /**
   * Returns a long string representation of the entity.
   * <p>
   * Such a representation should include long-string representations of child
   * entities.
   * 
   * @return A long string representation of the entity.
   */
  public abstract String toLongString();

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