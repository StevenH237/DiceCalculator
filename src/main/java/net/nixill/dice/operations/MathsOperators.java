package net.nixill.dice.operations;

import net.nixill.dice.objects.DCList;
import net.nixill.dice.objects.DCNumber;

public class MathsOperators {
  //TODO add actual functions
  public static final BinaryOperator<DCList> PLUS =
    new BinaryOperator<>("+", Priorities.PLUS, null);
  public static final BinaryOperator<DCList> MINUS =
    new BinaryOperator<>("-", Priorities.PLUS, null);
  public static final BinaryOperator<DCNumber> TIMES =
    new BinaryOperator<>("*", Priorities.TIMES, null);
  public static final BinaryOperator<DCNumber> DIVIDE =
    new BinaryOperator<>("/", Priorities.TIMES, null);
  public static final BinaryOperator<DCNumber> POWER =
    new BinaryOperator<>("^", Priorities.POWER, null);
  
  public static final UnaryOperator<DCList> NEGATIVE =
    new UnaryOperator<>("-", Priorities.NEGATIVE, null);
  
  public static final UnaryOperator<DCList> FACTORIAL =
    new UnaryOperator<>("!", Priorities.FACTORIAL, null);
}