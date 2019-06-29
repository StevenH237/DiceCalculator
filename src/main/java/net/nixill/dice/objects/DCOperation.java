package net.nixill.dice.objects;

import java.util.HashMap;

import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.Operator;
import net.nixill.dice.operations.PostfixOperator;
import net.nixill.dice.operations.PrefixOperator;

public class DCOperation extends DCExpression {
  private DCEntity left;
  private DCEntity right;
  private Operator oper;

  public DCOperation(DCEntity left, Operator oper, DCEntity right) {
    this.left = left;
    this.oper = oper;
    this.right = right;
  }

  @Override
  public DCValue run(HashMap<String, DCEntity> env) {
    if (oper instanceof BinaryOperator) {
      return ((BinaryOperator<?>) oper).run(left.getValue(env), right.getValue(env));
    } else if (oper instanceof PrefixOperator) {
      return ((PrefixOperator<?>) oper).run(right.getValue(env));
    } else if (oper instanceof PostfixOperator) {
      return ((PostfixOperator<?>) oper).run(left.getValue(env));
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    return null;
  }

  @Override
  public String toShortString() {
    return null;
  }

  @Override
  public String toLongString() {
    return null;
  }
  
}