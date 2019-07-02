package net.nixill.dice.operations.defaults;

import java.util.ArrayList;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCList;
import net.nixill.dice.objects.DCNumber;
import net.nixill.dice.objects.DCSingle;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.PostfixOperator;
import net.nixill.dice.operations.PrefixOperator;

/**
 * This class contains the default Maths operators.
 */
public class MathsOperators {
  /**
   * The binary "+" operator, which joins two lists.
   * <ul>
   * <li><code>left</code> operand - list: A starting list</li>
   * <li><code>right</code> operand - list: The list to join to
   * <code>left</code>.</li>
   * <li>Returns - list: A list formed by joining <code>left</code> and
   * <code>right</code>.</li>
   * </ul>
   */
  public static final BinaryOperator<DCList> PLUS = new BinaryOperator<>("+", Priorities.PLUS, MathsOperators::plusOp);

  /**
   * The bianry "-" operator, which subtracts two numbers or joins a list to a
   * negative list.
   * <p>
   * This works the same way as {@link #PLUS}, except that the right-hand operand
   * is made {@link #NEGATIVE} first.
   * <ul>
   * <li><code>left</code> operand - list: A starting list</li>
   * <li><code>right</code> operand - list: The list to subtract from
   * <code>left</code>.</li>
   * <li>Returns - list: A list formed by joining <code>left</code> to negative
   * <code>right</code>.</li>
   * </ul>
   */
  public static final BinaryOperator<DCList> MINUS = new BinaryOperator<>("-", Priorities.PLUS,
      MathsOperators::minusOp);

  /**
   * The binary "*" operator, which multiplies two numbers.
   * <p>
   * <ul>
   * <li><code>left</code> operand - number: A starting number</li>
   * <li><code>right</code> operand - number: The number by which to multiply
   * <code>left</code>.</li>
   * <li>Returns - number: The product of the other two numbers.</li>
   * </ul>
   */
  public static final BinaryOperator<DCNumber> TIMES = new BinaryOperator<>("*", Priorities.TIMES,
      MathsOperators::timesOp);

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
  public static final BinaryOperator<DCNumber> DIVIDE = new BinaryOperator<>("/", Priorities.TIMES,
      MathsOperators::divideOp);

  /**
   * The binary "^" operator, which raises one number to the power of another.
   * <p>
   * <ul>
   * <li><code>left</code> operand - number: A starting number</li>
   * <li><code>right</code> operand - number: The power by which to raise
   * <code>left</code>.</li>
   * <li>Returns - number: The product of the other two numbers.</li>
   * </ul>
   */
  public static final BinaryOperator<DCNumber> POWER = new BinaryOperator<>("^", Priorities.POWER,
      MathsOperators::powerOp);

  /**
   * The prefix "-" operator, which makes its operand negative.
   * <p>
   * Lists are made negative by making every item they contain negative.
   * <ul>
   * <li>operand - list: The list to make negative.</li>
   * <li>Returns - list: That list, negative.</li>
   * </ul>
   */
  public static final PrefixOperator<DCList> NEGATIVE = new PrefixOperator<>("-", Priorities.NEGATIVE,
      MathsOperators::negativeOp);

  /**
   * The postfix "!" operator, which takes the factorial of a given number.
   * <ul>
   * <li>operand - number: The number of which to get the factorial, which must be
   * an integer between 1 and 15.</li>
   * <li>Returns - number: That number's factorial.</li>
   * </ul>
   */
  public static final PostfixOperator<DCNumber> FACTORIAL = new PostfixOperator<>("!", Priorities.FACTORIAL,
      MathsOperators::factorialOp);

  private static DCList plusOp(DCEntity leftEnt, DCEntity rightEnt) {
    ArrayList<DCValue> combined;
    DCValue left = leftEnt.getValue();
    DCValue right = rightEnt.getValue();

    if (left instanceof DCSingle) {
      combined = new ArrayList<>();
      combined.add(left);
    } else {
      combined = left.getList().getItems();
    }

    if (right instanceof DCSingle) {
      combined.add(right);
    } else {
      combined.addAll(right.getList().getItems());
    }

    return new DCList(combined);
  }

  private static DCList negativeOp(DCEntity ent) {
    ArrayList<DCValue> in;
    DCValue val = ent.getValue();

    if (val instanceof DCSingle) {
      in = new ArrayList<>();
      in.add(val);
    } else {
      in = val.getList().getItems();
    }

    ArrayList<DCValue> out = new ArrayList<>();

    for (DCValue inVal : in) {
      if (inVal instanceof DCSingle) {
        DCSingle inSingle = (DCSingle) inVal;
        out.add(new DCNumber(-inSingle.getAmount(), inSingle.getPotential()));
      } else {
        out.add(negativeOp(inVal));
      }
    }

    return new DCList(out);
  }

  private static DCList minusOp(DCEntity left, DCEntity right) {
    return plusOp(left, negativeOp(right));
  }

  private static DCNumber timesOp(DCEntity left, DCEntity right) {
    return new DCNumber(left.getValue().getSingle().getAmount() * right.getValue().getSingle().getAmount());
  }

  private static DCNumber divideOp(DCEntity left, DCEntity right) {
    return new DCNumber(left.getValue().getSingle().getAmount() / right.getValue().getSingle().getAmount());
  }

  private static DCNumber powerOp(DCEntity left, DCEntity right) {
    return new DCNumber(Math.pow(left.getValue().getSingle().getAmount(), right.getValue().getSingle().getAmount()));
  }

  private static DCNumber factorialOp(DCEntity num) {
    return new DCNumber(factorial(num.getValue().getSingle().getAmount()));
  }

  private static double factorial(double number) {
    number = Math.floor(number);
    if (number < 1) {
      return 0;
    } else if (number == 1) {
      return 1;
    } else if (number > 15) {
      throw new ArithmeticException("Only factorials of 1 through 15 are supported.");
    } else {
      return factorial(number - 1) * number;
    }
  }
}