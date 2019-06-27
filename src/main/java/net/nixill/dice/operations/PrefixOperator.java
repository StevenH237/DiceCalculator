package net.nixill.dice.operations;

import java.util.function.Function;

import net.nixill.dice.objects.DCValue;

public class PrefixOperator<R extends DCValue> extends UnaryOperator<R> {
  public PrefixOperator(String symbol, int priority, Function<DCValue, R> func) {
    super(symbol, priority, false, func);
  }
}