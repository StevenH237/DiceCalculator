package net.nixill.dice.defaults.operations;

import java.util.ArrayList;

import net.nixill.dice.objects.DCList;
import net.nixill.dice.objects.DCNumber;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.PostfixOperator;

/**
 * This class contains the default Maths operators.
 */
public class MathsOperators {
  /**
   * The binary "*" operator, which multiplies two numbers.
   * <p>
   * <ul>
   * <li><code>left</code> operand - number: A starting number</li>
   * <li><code>right</code> operand - number: The number by which to
   * multiply <code>left</code>.</li>
   * <li>Returns - number: The product of the other two numbers.</li>
   * </ul>
   */
  public static final BinaryOperator<DCNumber> TIMES = new BinaryOperator<>(
      "*", Priorities.TIMES, (left, right) -> {
        return new DCNumber(left.getValue().getSingle().getAmount()
            * right.getValue().getSingle().getAmount());
      });
  
  /**
   * The binary "/" operator, which divides two numbers.
   * <p>
   * <ul>
   * <li><code>left</code> operand - number: A starting number</li>
   * <li><code>right</code> operand - number: The number by which to divide
   * <code>left</code>.</li>
   * <li>Returns - number: The product of the other two numbers.</li>
   * </ul>
   */
  public static final BinaryOperator<DCNumber> DIVIDE = new BinaryOperator<>(
      "/", Priorities.TIMES, (left, right) -> {
        return new DCNumber(left.getValue().getSingle().getAmount()
            / right.getValue().getSingle().getAmount());
      });
  
  /**
   * The binary "^" operator, which raises one number to the power of
   * another.
   * <p>
   * <ul>
   * <li><code>left</code> operand - number: A starting number</li>
   * <li><code>right</code> operand - number: The power by which to raise
   * <code>left</code>.</li>
   * <li>Returns - number: The <code>right</code>h power of
   * <code>left</code>.</li>
   * </ul>
   */
  public static final BinaryOperator<DCNumber> POWER = new BinaryOperator<>(
      "^", Priorities.POWER, (left, right) -> {
        return new DCNumber(
            Math.pow(left.getValue().getSingle().getAmount(),
                right.getValue().getSingle().getAmount()));
      });
  
  /**
   * The postfix "!" operator, which takes the factorial of a given number.
   * <ul>
   * <li>operand - number: The number of which to get the factorial, which
   * must be an integer between 1 and 15.</li>
   * <li>Returns - number: That number's factorial.</li>
   * </ul>
   */
  public static final PostfixOperator<DCNumber> FACTORIAL = new PostfixOperator<>(
      "!", Priorities.FACTORIAL, (num) -> {
        return new DCNumber(
            factorial(num.getValue().getSingle().getAmount()));
      });
  
  private static double factorial(double number) {
    number = Math.floor(number);
    if (number < 1) {
      return 0;
    } else if (number == 1) {
      return 1;
    } else if (number > 15) {
      throw new ArithmeticException(
          "Only factorials of 1 through 15 are supported.");
    } else {
      return factorial(number - 1) * number;
    }
  }
  
  /**
   * The binary "//" operator, which does division but only returns an
   * integer quotient.
   * <ul>
   * <li><code>left</code> operand - number: A starting number</li>
   * <li><code>right</code> operand - number: The number by which to divide
   * <code>left</code>.</li>
   * <li>Returns - number: The quotient of the other two numbers.</li>
   * </ul>
   */
  public static final BinaryOperator<DCNumber> INT_DIVIDE = new BinaryOperator<>(
      "//", Priorities.TIMES, (left, right) -> {
        return new DCNumber(
            Math.floor(left.getValue().getSingle().getAmount()
                / right.getValue().getSingle().getAmount()));
      });
  
  /**
   * The binary "%" operator, which does division but only returns the
   * remainder.
   * <ul>
   * <li><code>left</code> operand - number: A starting number</li>
   * <li><code>right</code> operand - number: The number by which to divide
   * <code>left</code>.</li>
   * <li>Returns - number: The quotient of the other two numbers.</li>
   * </ul>
   */
  public static final BinaryOperator<DCNumber> MODULO = new BinaryOperator<>(
      "%", Priorities.TIMES, (left, right) -> {
        return new DCNumber(left.getValue().getSingle().getAmount()
            % right.getValue().getSingle().getAmount());
      });
  
  /**
   * The binary "/%" operator, which does division and returns the quotient
   * and remainder separately.
   * <ul>
   * <li><code>left</code> operand - number: A starting number</li>
   * <li><code>right</code> operand - number: The number by which to divide
   * <code>left</code>.</li>
   * <li>Returns - list: The quotient and remainder of the other two
   * numbers.</li>
   * </ul>
   */
  public static final BinaryOperator<DCList> DUO_DIVIDE = new BinaryOperator<>(
      "/%", Priorities.TIMES, (left, right) -> {
        double lAmt = left.getValue().getSingle().getAmount();
        double rAmt = right.getValue().getSingle().getAmount();
        
        ArrayList<DCValue> out = new ArrayList<>();
        
        out.add(new DCNumber(Math.floor(lAmt / rAmt)));
        out.add(new DCNumber(lAmt % rAmt));
        
        return new DCList(out);
      });
}