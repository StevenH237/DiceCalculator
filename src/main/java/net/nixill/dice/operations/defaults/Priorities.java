package net.nixill.dice.operations.defaults;

import net.nixill.dice.operations.Operator;

/**
 * Contains the priorities of default operators as constants.
 * <p>
 * All constants are integers and are not required to be used. However, it is
 * recommended that custom operators have a priority relative to one of these
 * constants as they may change between versions.
 */
public final class Priorities {
  private Priorities() {
  }

  /**
   * The priority of "+" and binary "-", which is 0.
   */
  public static final int PLUS = 0;

  /**
   * The priority of unary "-", which is 5.
   */
  public static final int NEGATIVE = PLUS + 5;

  /**
   * The priority of "*", "/", and "%", which is 10.
   */
  public static final int TIMES = PLUS + 10;

  /**
   * The priority of "!", which is 15.
   */
  public static final int FACTORIAL = PLUS + 15;

  /**
   * The priority of "^", which is 20.
   * <p>
   * This level is right-associative.
   */
  public static final int POWER = PLUS + 20;

  static {
    Operator.setFromRight(POWER);
  }
}