package net.nixill.dice.objects;

import java.util.ArrayList;
import java.util.function.Function;

import net.nixill.dice.operations.Variables;

public class DCCodeFunction extends DCExpression {
  private Function<ArrayList<DCEntity>, DCValue> code;

  public DCCodeFunction(Function<ArrayList<DCEntity>, DCValue> func) {
    code = func;
  }

  @Override
  public DCValue getValue() {
    return getValue(Variables.getParams());
  }

  public DCValue getValue(ArrayList<DCEntity> params) {
    return code.apply(params);
  }

  @Override
  public String toString(int level) {
    return "{!x}";
  }

  @Override
  public String toCode() {
    return "{!x}";
  }

  @Override
  public void printTree(int level) {
    printSpaced(level, "Arbitrary code function");
  }
}