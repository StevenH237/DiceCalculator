package net.nixill.dice.operations;

import java.util.function.Function;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

/**
 * A unary operator, i.e. one that has operands only on one side.
 */
public abstract class UnaryOperator<R extends DCValue> extends Operator {
  protected Function<DCEntity, R> func;
  protected boolean               isPostfix;
  
  /**
   * Creates a new UnaryOperator.
   * 
   * @param symbol
   *   The symbol it uses.
   * @param priority
   *   The priority of the operator.
   * @param func
   *   The function of the operator.
   */
  protected UnaryOperator(String symbol, int priority, boolean post,
      int level, Function<DCEntity, R> func) {
    super(symbol, priority, level);
    this.func = func;
    this.isPostfix = post;
  }
  
  /**
   * Run the given function with a single operand.
   * 
   * @param operand
   *   The operand against the operator.
   * @return The result of the operation.
   */
  public R run(DCEntity operand) {
    return func.apply(operand);
  }
  
  /**
   * Whether or not the operator is a postfix operator.
   * 
   * @return true iff the operator is a postfix operator.
   */
  public boolean isPostfix() {
    return isPostfix;
  }
}