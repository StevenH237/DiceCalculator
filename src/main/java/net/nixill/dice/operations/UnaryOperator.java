package net.nixill.dice.operations;

import java.util.function.Function;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

public abstract class UnaryOperator<R extends DCValue> extends Operator {
  protected Function<DCEntity, R> func;
  protected boolean isPostfix;
  
  protected UnaryOperator(String symbol, int priority, boolean post, Function<DCEntity, R> func) {
    super(symbol, priority);
    this.func = func;
    this.isPostfix = post;
  }

  public R run(DCEntity operand) {
    return func.apply(operand);
  }

  public boolean isPostfix() {
    return isPostfix;
  }
}