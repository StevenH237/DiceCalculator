package net.nixill.dice.operations;

import java.util.function.BiFunction;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

/**
 * A set of eight {@link BinaryOperator}s based on a comparison.
 */
public class ComparisonOperators<T extends DCValue> {
  private ComparisonFunction<T> coFunc;
  private String supersymbol;
  private int coPriority;

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
   * @param symbol   The symbol to use as a base.
   * @param priority The priority of the resulting operators.
   * @param func     The function to perform.
   */
  public ComparisonOperators(String symbol, int priority, ComparisonFunction<T> func) {
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
   * Returns the result of comparing two numbers with the given comparison.
   */
  public static boolean compares(double left, Comparison comp, double right) {
    if (comp == Comparison.MODULO) {
      return (left % right) == 0;
    } else if (comp == Comparison.NOT_MODULO) {
      return (left % right) != 0;
    }

    if (left < right) {
      return (comp.factor % 5) == 0;
    } else if (left == right) {
      return (comp.factor % 3) == 0;
    } else if (left > right) {
      return (comp.factor % 2) == 0;
    }

    return false;
  }

  @FunctionalInterface
  public static interface ComparisonFunction<T extends DCValue> {
    public T run(DCEntity left, Comparison comp, DCEntity right);
  }

  public static enum Comparison {
    GREATER(">", 2), EQUAL("=", 3), LESS("<", 5), NOT_GREATER("<=", 15), NOT_EQUAL("!=", 10), NOT_LESS(">=", 6),
    MODULO("%", 0), NOT_MODULO("!%", 0);
    private String subsymbol;
    private int factor;

    private Comparison(String sym, int fact) {
      subsymbol = sym;
      factor = fact;
    }

    public Boolean compares(double left, double right) {
      return ComparisonOperators.compares(left, this, right);
    }
  }

  public class ComparisonOperator extends BinaryOperator<T> {
    public ComparisonOperator(Comparison comp) {
      super(supersymbol + comp.subsymbol, coPriority, new BiFunction<DCEntity, DCEntity, T>() {
        @Override
        public T apply(DCEntity left, DCEntity right) {
          return coFunc.run(left, comp, right);
        }
      });
    }
  }
}