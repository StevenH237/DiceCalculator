package net.nixill.dice.parsing;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCExpression;
import net.nixill.dice.objects.DCOperation;
import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.Operator;
import net.nixill.dice.operations.PostfixOperator;
import net.nixill.dice.operations.PrefixOperator;

/**
 * A mutable object with which to build a {@link DCExpression}.
 */
public class ExpressionBuilder {
  private DCEntity          leftEnt;
  private DCEntity          rightEnt;
  private Operator          oper;
  private ExpressionBuilder leftExp;
  private ExpressionBuilder rightExp;
  
  /**
   * Create an empty ExpressionBuilder.
   */
  public ExpressionBuilder() {
  }
  
  /**
   * Create an ExpressionBuilder with the given operator.
   * 
   * @param oper
   *   The operator.
   */
  public ExpressionBuilder(Operator oper) {
    this.oper = oper;
  }
  
  /**
   * Adds either another {@link ExpressionBuilder} or a {@link DCEntity} to
   * the left side of this expression, replacing the previous value if
   * applicable.
   * 
   * @param left
   *   The ExpressionBuilder or DCEntity to add.
   * @throws IllegalArgumentException
   *   If the object is neither an ExpressionBuilder nor a DCEntity.
   */
  public void setLeft(Object left) {
    leftEnt = null;
    leftExp = null;
    if (left instanceof DCEntity) {
      leftEnt = (DCEntity) left;
    } else if (left instanceof ExpressionBuilder) {
      leftExp = (ExpressionBuilder) left;
    } else if (left != null) {
      throw new IllegalArgumentException(
          "ExpressionBuilder.setLeft() only accepts null, DCEntity, or ExpressionBuilder.");
    }
  }
  
  /**
   * Adds an {@link Operator} to the middle of this expresseion, replacing
   * the previous one if applicable.
   * 
   * @param oper
   *   The operator to use.
   */
  public void setOperator(Operator oper) {
    this.oper = oper;
  }
  
  /**
   * Adds either another {@link ExpressionBuilder} or a {@link DCEntity} to
   * the right side of this expression, replacing the previous value if
   * applicable.
   * 
   * @param right
   *   The ExpressionBuilder or DCEntity to add.
   * @throws IllegalArgumentException
   *   If the object is neither an ExpressionBuilder nor a DCEntity.
   */
  public void setRight(Object right) {
    rightEnt = null;
    rightExp = null;
    if (right instanceof DCEntity) {
      rightEnt = (DCEntity) right;
    } else if (right instanceof ExpressionBuilder) {
      rightExp = (ExpressionBuilder) right;
    } else if (right != null) {
      throw new IllegalArgumentException(
          "ExpressionBuilder.setRight() only accepts null, DCEntity, or ExpressionBuilder.");
    }
  }
  
  /**
   * Retrieves the left-side {@link DCEntity}. Returns null if the left
   * side has an {@link ExpressionBuilder} or nothing.
   * 
   * @return The left-side DCEntity, or null if there isn't one.
   */
  public DCEntity getLeftEnt() {
    return leftEnt;
  }
  
  /**
   * Retrieves the right-side {@link DCEntity}. Returns null if the right
   * side has an {@link ExpressionBuilder} or nothing.
   * 
   * @return The right-side DCEntity, or null if there isn't one.
   */
  public DCEntity getRightEnt() {
    return rightEnt;
  }
  
  /**
   * Retrieves the left-side {@link ExpressionBuilder}. Returns null if the
   * left side has a {@link DCEntity} or nothing.
   * 
   * @return The left-side ExpressionBuilder, or null if there isn't one.
   */
  public ExpressionBuilder getLeftExp() {
    return leftExp;
  }
  
  /**
   * Retrieves the right-side {@link ExpressionBuilder}. Returns null if
   * the right side has a {@link DCEntity} or nothing.
   * 
   * @return The right-side ExpressionBuilder, or null if there isn't one.
   */
  public ExpressionBuilder getRightExp() {
    return rightExp;
  }
  
  /**
   * Retrieves the current {@link Operator}, or null if one hasn't been
   * assigned yet.
   * 
   * @return The Operator, or null if there isn't one.
   */
  public Operator getOper() {
    return oper;
  }
  
  /**
   * Convenience method to return the left-side {@link ExpressionBuilder}
   * or {@link DCEntity}, no matter which it has. Returns null if it has
   * nothing.
   * 
   * @return The left-side ExpressionBuilder or DCEntity, or null if
   * nothing.
   */
  public Object getLeft() {
    if (leftEnt != null) {
      return leftEnt;
    } else {
      // even if it's null
      return leftExp;
    }
  }
  
  /**
   * Convenience method to return the right-side {@link ExpressionBuilder}
   * or {@link DCEntity}, no matter which it has. Returns null if it has
   * nothing.
   * 
   * @return The right-side ExpressionBuidler or DCEntity, or null if
   * nothing.
   */
  public Object getRight() {
    if (rightEnt != null) {
      return rightEnt;
    } else {
      // even if it's null
      return rightExp;
    }
  }
  
  /**
   * Returns the {@link DCOperation} currently being built by this
   * ExpressionBuilder.
   * <p>
   * A {@link BinaryOperator} requires both the left and right to have an
   * object. A {@link PrefixOperator} requires the right to have an object
   * and ignores the left. A {@link PostfixOperator} requires the left to
   * have an object and ignores the right.
   * 
   * @return The operation in question
   * @throws IllegalStateException
   *   If it has no operator or no object on the correct side(s).
   */
  public DCOperation build() {
    DCEntity leftSide = null;
    DCEntity rightSide = null;
    
    String operType = Operator.class.getSimpleName();
    
    if (oper == null) {
      throw new IllegalStateException(
          "DCOperations cannot be built without an operator.");
    }
    
    if (!(oper instanceof PrefixOperator)) {
      if (leftExp != null) {
        leftSide = leftExp.build();
      } else if (leftEnt != null) {
        leftSide = leftEnt;
      } else {
        throw new IllegalStateException(
            operType + " requires a left-side operand.");
      }
    }
    
    if (!(oper instanceof PostfixOperator)) {
      if (rightExp != null) {
        rightSide = rightExp.build();
      } else if (rightEnt != null) {
        rightSide = rightEnt;
      } else {
        throw new IllegalStateException(
            operType + " requires a right-side operand.");
      }
    }
    
    return new DCOperation(leftSide, oper, rightSide);
  }
}