package net.nixill.dice.operations;

import java.util.function.BiFunction;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

/**
 * A binary operator, i.e. one that has operands on both sides.
 */
public class BinaryOperator<R extends DCValue> extends Operator {
  protected BiFunction<DCEntity, DCEntity, R> func;

  /**
   * Creates a new BinaryOperator.
   * 
   * @param symbol   The symbol it uses.
   * @param priority The priority of the operator.
   * @param func     The function of the operator.
   */
  public BinaryOperator(String symbol, int priority, BiFunction<DCEntity, DCEntity, R> func) {
    super(symbol, priority);
    this.func = func;
  }

  /**
   * Run the given function with a left- and a right-hand operand.
   * 
   * @param left  The operand on the left.
   * @param right The operand on the right.
   * @return The result of the operation.
   */
  public R run(DCEntity left, DCEntity right) {
    return func.apply(left, right);
  }
}