package net.nixill.dice.operations;

import java.util.function.Function;

import net.nixill.dice.objects.DCValue;

public class UnaryOperator<R extends DCValue> extends Operator {
  protected Function<DCValue, R> func;
  
  public UnaryOperator(String symbol, int priority, Function<DCValue, R> func) {
    super(symbol, priority);
    this.func = func;
  }

  public R run(DCValue oper) {
    return func.apply(oper);
  }
}