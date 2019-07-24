package net.nixill.dice.exception;

/**
 * A DiceCalcException is a wrapper around non-bug errors in the code.
 * <p>
 * For example, it wraps around a DivideByZeroException because that's not
 * something wrong with the code itself, but a user just trying to divide a
 * number by zero.
 */
public class DiceCalcException extends RuntimeException {
  private static final long serialVersionUID = 7206711603686419099L;
  
  /**
   * Create a new DiceCalcException with an underlying cause.
   * 
   * @param t
   *   The cause.
   */
  public DiceCalcException(Throwable t) {
    super(t);
  }
  
  /**
   * Create a new DiceCalcException with an underlying message.
   * 
   * @param msg
   *   The message.
   */
  public DiceCalcException(String msg) {
    super(msg);
  }
  
  /**
   * Create a new DiceCalcException with an underlying message <i>and</i>
   * cause.
   * 
   * @param msg
   *   The message.
   * @param t
   *   The cause.
   */
  public DiceCalcException(String msg, Throwable t) {
    super(msg, t);
  }
}