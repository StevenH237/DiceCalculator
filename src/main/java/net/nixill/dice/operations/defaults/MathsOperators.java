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
   * The binary "+" operator, which adds two numbers or joins two lists.
   * <p>
   * If both operands are {@link DCSingle}s (numbers), this operator returns a
   * {@link DCNumber} which is the sum of both of the operands. In any other case,
   * it returns a single {@link DCList} consisting of both operands (as lists)
   * joined together.
   * <p>
   * For the operator's function:
   * 
   * @param left  A number or list to start with
   * @param right The number or list to be added to left
   * @return The DCNumber or DCList summing both, as described above.
   */
  public static final BinaryOperator<DCList> PLUS = new BinaryOperator<>("+", Priorities.PLUS, MathsOperators::plusOp);
  /**
   * The bianry "-" operator, which subtracts two numbers or joins a list to a
   * negative list.
   * <p>
   * This works the same way as {@link #PLUS}, except that the right-hand operand
   * is made {@link #NEGATIVE} first.
   * <p>
   * For the operator's function:
   * 
   * @param left  A number or list to start with
   * @param right The number or list to subtract from the left
   * @return The DCNumber or DCList as described in {@link #PLUS}.
   */
  public static final BinaryOperator<DCList> MINUS = new BinaryOperator<>("-", Priorities.PLUS,
      MathsOperators::minusOp);
  /**
   * The binary "*" operator, which multiplies two numbers or sums of lists.
   * <p>
   * For the operator's function:
   * 
   * @param left  A number to start with.
   * @param right The number by which to multiply left.
   * @return left times right, as a DCNumber.
   */
  public static final BinaryOperator<DCNumber> TIMES = new BinaryOperator<>("*", Priorities.TIMES,
      MathsOperators::timesOp);
  /**
   * The binary "/" operator, which divides two numbers or sums of lists.
   * <p>
   * For the operator's function:
   * 
   * @param left  A number to start with.
   * @param right The number by which to divide left.
   * @return left divided by right, as a DCNumber.
   */
  public static final BinaryOperator<DCNumber> DIVIDE = new BinaryOperator<>("/", Priorities.TIMES,
      MathsOperators::divideOp);
  /**
   * The binary "^" operator, which raises one number (or sum of list) to the
   * power of another.
   * <p>
   * For the operator's function:
   * 
   * @param left  A number to start with.
   * @param right The power to which left should be raised.
   * @return left to the power of right, as a DCNumber.
   */
  public static final BinaryOperator<DCNumber> POWER = new BinaryOperator<>("^", Priorities.POWER,
      MathsOperators::powerOp);

  /**
   * The prefix "-" operator, which makes its operand negative.
   * <p>
   * Lists are made negative by making every item they contain negative.
   * <p>
   * For the operator's function:
   * 
   * @param operand A number or list to make negative.
   * @return The same number or list, with a negative value.
   */
  public static final PrefixOperator<DCList> NEGATIVE = new PrefixOperator<>("-", Priorities.NEGATIVE,
      MathsOperators::negativeOp);

  /**
   * The postfix "!" operator, which takes the factorial of a given number.
   * <p>
   * For the operator's function:
   * 
   * @param operand An integer between 0 and 15, inclusive.
   * @return The factorial of that integer, as an operand.
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