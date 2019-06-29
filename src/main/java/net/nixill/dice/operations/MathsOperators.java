package net.nixill.dice.operations;

import net.nixill.dice.objects.DCList;
import net.nixill.dice.objects.DCNumber;

public class MathsOperators {
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
  
  public static final PrefixOperator<DCList> NEGATIVE =
    new PrefixOperator<>("-", Priorities.NEGATIVE, null);
  
  public static final PostfixOperator<DCList> FACTORIAL =
    new PostfixOperator<>("!", Priorities.FACTORIAL, null);
}