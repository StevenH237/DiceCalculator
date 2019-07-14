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
  
  public DiceCalcException(Throwable t) {
    super(t);
  }
}