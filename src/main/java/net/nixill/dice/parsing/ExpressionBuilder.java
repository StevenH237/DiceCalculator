package net.nixill.dice.parsing;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCExpression;
import net.nixill.dice.objects.DCOperation;
import net.nixill.dice.operations.Operator;

public class ExpressionBuilder {
  private DCEntity leftEnt;
  private DCEntity rightEnt;
  private Operator oper;
  private ExpressionBuilder leftExp;
  private ExpressionBuilder rightExp;

  public ExpressionBuilder() {}

  public ExpressionBuilder(Operator oper) {
    this.oper = oper;
  }

  public void setLeft(Object left) {
    leftEnt = null;
    leftExp = null;
    if (left instanceof DCEntity) {
      leftEnt = (DCEntity) left;
    } else if (left instanceof ExpressionBuilder) {
      leftExp = (ExpressionBuilder) left;
    } else if (left != null) {
      throw new IllegalArgumentException("ExpressionBuilder.setLeft() only accepts null, DCEntity, or ExpressionBuilder.");
    }
  }

  public void setOperator(Operator oper) {
    this.oper = oper;
  }

  public void setRight(Object right) {
    rightEnt = null;
    rightExp = null;
    if (right instanceof DCEntity) {
      rightEnt = (DCEntity) right;
    } else if (right instanceof ExpressionBuilder) {
      rightExp = (ExpressionBuilder) right;
    } else if (right != null) {
      throw new IllegalArgumentException("ExpressionBuilder.setRight() only accepts null, DCEntity, or ExpressionBuilder.");
    }
  }

  public DCEntity getLeftEnt() { return leftEnt; }
  public DCEntity getRightEnt() { return rightEnt; }
  public ExpressionBuilder getLeftExp() { return leftExp; }
  public ExpressionBuilder getRightExp() { return rightExp; }
  public Operator getOper() { return oper; }

  public Object getLeft() {
    if (leftEnt != null) {
      return leftEnt;
    } else {
      // even if it's null
      return leftExp;
    }
  }

  public Object getRight() {
    if (rightEnt != null) {
      return rightEnt;
    } else {
      // even if it's null
      return rightExp;
    }
  }

  public DCExpression build() {
    DCEntity leftSide;
    DCEntity rightSide;

    if (leftExp != null) {
      leftSide = leftExp.build();
    } else {
      leftSide = leftEnt;
    }

    if (rightExp != null) {
      rightSide = rightExp.build();
    } else {
      rightSide = rightEnt;
    }

    return new DCOperation(leftSide, oper, rightSide);
  }
}