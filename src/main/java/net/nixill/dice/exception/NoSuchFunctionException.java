package net.nixill.dice.exception;

public class NoSuchFunctionException extends DiceCalcException {
  private static final long serialVersionUID = 7060054020946072161L;

  public NoSuchFunctionException(String msg) {
    super(msg);
  }
}