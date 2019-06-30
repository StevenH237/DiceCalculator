package net.nixill.dice.operations;

public final class Priorities {
  private Priorities() {}

  public static final int PLUS = 0; //also minus
  public static final int NEGATIVE = PLUS + 1;
  public static final int TIMES = PLUS + 2; //also divide and modulo
  public static final int FACTORIAL = PLUS + 3;
  public static final int POWER = PLUS + 4; //also root

  static {
    Operator.setFromRight(POWER);
  }
}