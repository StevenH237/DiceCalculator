package net.nixill.dice.parsing;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.operations.Operator;

public class ExpressionBuilder {
  private DCEntity leftEnt;
  private DCEntity rightEnt;
  private Operator oper;
  private ExpressionBuilder leftExp;
  private ExpressionBuilder rightExp;

  public ExpressionBuilder() {}
  public ExpressionBuilder(DCEntity left) {
    leftEnt = left;
  }
  public ExpressionBuilder(ExpressionBuilder left) {
    leftExp = left;
  }
  public ExpressionBuilder(Operator oper) {
    this.oper = oper;
  }

  public void setLeft(DCEntity left) {
    leftEnt = left;
    leftExp = null;
  }

  public void setLeft(ExpressionBuilder left) {
    leftExp = left;
    leftEnt = null;
  }

  public void setOperator(Operator oper) {
    this.oper = oper;
  }

  public void setRight(DCEntity right) {
    rightEnt = right;
    rightExp = null;
  }

  public void setRight(ExpressionBuilder right) {
    rightExp = right;
    rightEnt = null;
  }

  public DCEntity getLeftEnt() { return leftEnt; }
  public DCEntity getRightEnt() { return rightEnt; }
  public ExpressionBuilder getLeftExp() { return leftExp; }
  public ExpressionBuilder getRightExp() { return rightExp; }
  public Operator getOper() { return oper; }

  public DCEntity build() {
    //TODO build this method
  }
}