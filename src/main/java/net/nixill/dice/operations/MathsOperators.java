package net.nixill.dice.operations;

import java.util.ArrayList;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCList;
import net.nixill.dice.objects.DCNumber;
import net.nixill.dice.objects.DCSingle;
import net.nixill.dice.objects.DCValue;

public class MathsOperators {
  public static final BinaryOperator<DCList> PLUS =
    new BinaryOperator<>("+", Priorities.PLUS, MathsOperators::plusOp);
  public static final BinaryOperator<DCList> MINUS =
    new BinaryOperator<>("-", Priorities.PLUS, MathsOperators::minusOp);
  public static final BinaryOperator<DCNumber> TIMES =
    new BinaryOperator<>("*", Priorities.TIMES, MathsOperators::timesOp);
  public static final BinaryOperator<DCNumber> DIVIDE =
    new BinaryOperator<>("/", Priorities.TIMES, MathsOperators::divideOp);
  public static final BinaryOperator<DCNumber> POWER =
    new BinaryOperator<>("^", Priorities.POWER, MathsOperators::powerOp);
  
  public static final PrefixOperator<DCList> NEGATIVE =
    new PrefixOperator<>("-", Priorities.NEGATIVE, MathsOperators::negativeOp);
  
  public static final PostfixOperator<DCNumber> FACTORIAL =
    new PostfixOperator<>("!", Priorities.FACTORIAL, MathsOperators::factorialOp);

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