package net.nixill.dice.operations;

import java.util.function.Function;

import net.nixill.dice.objects.DCValue;

public abstract class UnaryOperator<R extends DCValue> extends Operator {
  protected Function<DCValue, R> func;
  protected boolean isPostfix;
  
  protected UnaryOperator(String symbol, int priority, boolean post, Function<DCValue, R> func) {
    super(symbol, priority);
    this.func = func;
    this.isPostfix = post;
  }

  public R run(DCValue oper) {
    return func.apply(oper);
  }

  public boolean isPostfix() {
    return isPostfix;
  }
}