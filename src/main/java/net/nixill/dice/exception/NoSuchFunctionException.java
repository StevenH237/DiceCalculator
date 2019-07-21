package net.nixill.dice.exception;

/**
 * Thrown when a user tries to load a function that doesn't exist.
 */
public class NoSuchFunctionException extends DiceCalcException {
  private static final long serialVersionUID = 7060054020946072161L;

  /**
   * Creates a new NoSuchFunctionException with an underlying message.
   */
  public NoSuchFunctionException(String msg) {
    super(msg);
  }
}