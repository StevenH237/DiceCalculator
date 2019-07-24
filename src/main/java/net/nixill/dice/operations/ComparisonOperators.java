package net.nixill.dice.operations;

import java.util.function.BiFunction;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

/**
 * A set of eight {@link BinaryOperator}s based on a comparison.
 */
public class ComparisonOperators<T extends DCValue> {
  private ComparisonFunction<T> coFunc;
  private String                supersymbol;
  private int                   coPriority;
  
  /**
   * This ComparisonOperator made with a greater than sign.
   */
  public final ComparisonOperator GREATER;
  /**
   * This ComparisonOperator made with an equals sign.
   */
  public final ComparisonOperator EQUAL;
  /**
   * This ComparisonOperator made with a less than sign.
   */
  public final ComparisonOperator LESS;
  /**
   * This ComparisonOperator made with a less than or equal to sign.
   */
  public final ComparisonOperator NOT_GREATER;
  /**
   * This ComparisonOperator made with a not-equals sign.
   */
  public final ComparisonOperator NOT_EQUAL;
  /**
   * This ComparisonOperator made with a greater than or equal to sign.
   */
  public final ComparisonOperator NOT_LESS;
  /**
   * This ComparisonOperator made with a modulo sign.
   */
  public final ComparisonOperator MODULO;
  /**
   * This ComparisonOperator made with a not modulo sign.
   */
  public final ComparisonOperator NOT_MODULO;
  
  /**
   * Creates a new set of ComparisonOperators.
   * 
   * @param symbol
   *   The symbol to use as a base.
   * @param priority
   *   The priority of the resulting operators.
   * @param func
   *   The function to perform.
   */
  public ComparisonOperators(String symbol, int priority,
      ComparisonFunction<T> func) {
    this.supersymbol = symbol;
    this.coPriority = priority;
    this.coFunc = func;
    
    GREATER = new ComparisonOperator(Comparison.GREATER);
    EQUAL = new ComparisonOperator(Comparison.EQUAL);
    LESS = new ComparisonOperator(Comparison.LESS);
    NOT_GREATER = new ComparisonOperator(Comparison.NOT_GREATER);
    NOT_EQUAL = new ComparisonOperator(Comparison.NOT_EQUAL);
    NOT_LESS = new ComparisonOperator(Comparison.NOT_LESS);
    MODULO = new ComparisonOperator(Comparison.MODULO);
    NOT_MODULO = new ComparisonOperator(Comparison.NOT_MODULO);
  }
  
  /**
   * The function of a ComparisonOperator, which accepts two
   * {@link DCEntity}s and a {@link Comparison} to produce a result.
   * 
   * @param <T>
   *   The type of the result of the function.
   */
  @FunctionalInterface
  public static interface ComparisonFunction<T extends DCValue> {
    /**
     * Runs this function.
     */
    public T run(DCEntity left, Comparison comp, DCEntity right);
  }
  
  /**
   * A comparison that produces a boolean result when given two numbers.
   */
  public static enum Comparison {
    /**
     * The greater-than comparison (<code>left &gt; right</code>).
     */
    GREATER(">", (left, right) -> (left > right)),
    /**
     * The equal-to comparison (<code>left == right</code>).
     */
    EQUAL("=", (left, right) -> (left == right)),
    /**
     * The less-than comparison (<code>left &lt; right</code>).
     */
    LESS("<", (left, right) -> (left < right)),
    /**
     * The less-than-or-equal-to (or not-greater-than) comparison
     * (<code>left &lt;= right</code>).
     */
    NOT_GREATER("<=", (left, right) -> (left <= right)),
    /**
     * The not-equal-to (or less-than-or-greater-than) comparison
     * (<code>left != right</code>).
     */
    NOT_EQUAL("!=", (left, right) -> (left != right)),
    /**
     * The greater-than-or-equal-to (or not-less-than) comparison
     * (<code>left &gt;= right</code>).
     */
    NOT_LESS(">=", (left, right) -> (left >= right)),
    /**
     * The modulo comparison, which returns <code>true</code> iff
     * <code>left</code> is a multiple of <code>right</code>
     * (<code>left % right == 0</code>).
     */
    MODULO("%", (left, right) -> ((left % right) == 0)),
    /**
     * The not-modulo comparison, which is the opposite of {@link #MODULO}
     * (<code>left % right != 0</code>).
     */
    NOT_MODULO("!%", (left, right) -> ((left % right) != 0));
    
    private String                  subsymbol;
    private CompareFunction<Double> func;
    
    private Comparison(String sym, CompareFunction<Double> comp) {
      subsymbol = sym;
      func = comp;
    }
    
    /**
     * Returns the result of two numbers surrounding this
     * {@link Comparison}.
     */
    public boolean compares(double left, double right) {
      return func.compares(left, right);
    }
    
    /**
     * A function that returns true iff the two parameters satisfy the
     * given comparison.
     */
    public static interface CompareFunction<T> {
      public boolean compares(T left, T right);
    }
  }
  
  /**
   * An individual ComparisonOperator, made from the set of eight with a
   * specific {@link Comparison}.
   */
  public class ComparisonOperator extends BinaryOperator<T> {
    /**
     * Creates the ComparisonOperator.
     * 
     * @param comp
     *   The comparison to use.
     */
    public ComparisonOperator(Comparison comp) {
      super(supersymbol + comp.subsymbol, coPriority,
          new BiFunction<DCEntity, DCEntity, T>() {
            @Override
            public T apply(DCEntity left, DCEntity right) {
              return coFunc.run(left, comp, right);
            }
          });
    }
  }
}