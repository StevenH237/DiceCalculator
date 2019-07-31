package net.nixill.dice.operations;

import java.util.HashSet;

/**
 * An Operator provides the logic for binary and unary operations.
 * <p>
 * This class stores the actual function, the symbol, and the operator
 * itself. Note that functions are actually stored on the subclasses, as
 * the type of operator determines how many parameters the function needs.
 * <p>
 * Most users of this API will not want to directly subclass this, but
 * rather use its existing subclasses - {@link BinaryOperator},
 * {@link PrefixOperator}, and {@link PostfixOperator} - as some parts of
 * the code are looking only for those classes.
 */
public abstract class Operator {
  // -- STATICS --//
  private static HashSet<Integer> fromRight = new HashSet<>();
  
  /**
   * Sets a specific priority level to be right-associative instead of
   * left-associative.
   * 
   * @param level
   *   The level to set
   */
  public static void setFromRight(int level) {
    fromRight.add(level);
  }
  
  /**
   * Sets a specific priority level to be left-associative instead of
   * right-associative.
   * 
   * @param level
   *   The level to set
   */
  public static void clearFromRight(int level) {
    fromRight.remove(level);
  }
  
  /**
   * Checks whether a specific priority level is right-associative.
   * 
   * @param level
   *   The level to check
   * @return true iff that priority level is right-associative.
   */
  public static boolean isFromRight(int level) {
    return fromRight.contains(level);
  }
  
  // -- NOT STATICS --//
  protected int    priority;
  protected String symbol;
  protected int    level;
  
  /**
   * Creates a new Operator.
   * 
   * @param symbol
   *   The symbol used by the operator.
   * @param priority
   *   The priority the operator has, compared to other operations with
   *   which it is in line.
   * @param level
   *   The level that must be used for the expression to show up in
   *   history.
   */
  public Operator(String symbol, int priority, int level) {
    this.priority = priority;
    this.symbol = symbol;
    this.level = level;
  }
  
  /**
   * Returns the priority of this operator. Operators with a higher
   * priority take numbers before those that are lower. Regardless of the
   * setting of <code>priority</code>, statements in brackets or
   * parentheses always go before statements outside them.
   * 
   * @return The priority
   */
  public int getPriority() {
    return priority;
  }
  
  /**
   * Returns the symbol used by the operator.
   * 
   * @return The symbol
   */
  public String getSymbol() {
    return symbol;
  }
  
  /**
   * Returns the level used by the operator.
   * 
   * @return The level
   */
  public int getLevel() {
    return level;
  }
  
  public abstract String toString();
}