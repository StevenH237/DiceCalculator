package net.nixill.dice.defaults.operations;

import net.nixill.dice.operations.Operator;

/**
 * Contains the priorities of default operators as constants.
 * <p>
 * All constants are integers and are not required to be used. However, it
 * is recommended that custom operators have a priority relative to one of
 * these constants as they may change between versions.
 */
public final class Priorities {
  private Priorities() {
  }
  
  /**
   * The priority of "+" and binary "-", which is 0.
   */
  public static final int JOIN = 0;
  
  /**
   * The priority of "d", which is 50.
   */
  public static final int DICE = JOIN + 50;
  
  /**
   * The priority of "p", which is 45. This level is right-associative.
   */
  public static final int PICK = DICE - 5;
  
  /**
   * The priority of prefix "#" and "$", which is -5.
   */
  public static final int LIST = JOIN - 5;
  
  /**
   * The priority of unary "-", which is 5.
   */
  public static final int NEGATIVE = JOIN + 5;
  
  /**
   * The priority of "*", "/", and "%", which is 10.
   */
  public static final int TIMES = JOIN + 10;
  
  /**
   * The priority of "!", which is 15.
   */
  public static final int FACTORIAL = JOIN + 15;
  
  /**
   * The priority of "^", which is 20.
   * <p>
   * This level is right-associative.
   */
  public static final int POWER = JOIN + 20;
  
  static {
    Operator.setFromRight(POWER);
    Operator.setFromRight(PICK);
  }
}