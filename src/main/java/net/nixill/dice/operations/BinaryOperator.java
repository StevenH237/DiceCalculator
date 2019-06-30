package net.nixill.dice.operations;

import java.util.function.BiFunction;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

public class BinaryOperator<R extends DCValue> extends Operator {
  protected BiFunction<DCEntity, DCEntity, R> func;
  
  public BinaryOperator(String symbol, int priority, BiFunction<DCEntity, DCEntity, R> func) {
    super(symbol, priority);
    this.func = func;
  }

  public R run(DCEntity left, DCEntity right) {
    return func.apply(left, right);
  }
}