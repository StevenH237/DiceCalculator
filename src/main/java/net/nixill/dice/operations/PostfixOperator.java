package net.nixill.dice.operations;

import java.util.function.Function;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

/**
 * Represents a postfix operator, i.e. one that follows its sole operand.
 * 
 * A postfix operator always takes priority over any operator to its right.
 */
public class PostfixOperator<R extends DCValue> extends UnaryOperator<R> {
  /**
   * Create a new PostfixOperator.
   * 
   * @param symbol   The symbol the operator uses.
   * @param priority The priority of the operator.
   * @param func     The function the operator uses.
   */
  public PostfixOperator(String symbol, int priority, Function<DCEntity, R> func) {
    super(symbol, priority, true, func);
  }

  public String toString() {
    return "post:" + symbol;
  }
}