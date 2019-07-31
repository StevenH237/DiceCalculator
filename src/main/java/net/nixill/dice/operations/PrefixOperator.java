package net.nixill.dice.operations;

import java.util.function.Function;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

/**
 * Represents a prefix operator, i.e. one that precedes its sole operand. A
 * prefix operator always takes priority over any operator to its left.
 */
public class PrefixOperator<R extends DCValue> extends UnaryOperator<R> {
  /**
   * Create a new PrefixOperator.
   * 
   * @param symbol
   *   The symbol the operator uses.
   * @param priority
   *   The priority of the operator.
   * @param func
   *   The function the operator uses.
   */
  public PrefixOperator(String symbol, int priority, int level,
      Function<DCEntity, R> func) {
    super(symbol, priority, false, level, func);
  }
  
  public String toString() {
    return "pre:" + symbol;
  }
}