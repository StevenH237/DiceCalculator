package net.nixill.dice.operations;

import java.util.function.BiFunction;

import net.nixill.dice.objects.DCValue;

public class BinaryOperator<R extends DCValue> extends Operator {
  protected BiFunction<DCValue, DCValue, R> func;
  
  public BinaryOperator(String symbol, int priority, BiFunction<DCValue, DCValue, R> func) {
    super(symbol, priority);
    this.func = func;
  }

  public R run(DCValue left, DCValue right) {
    return func.apply(left, right);
  }
}