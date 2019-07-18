package net.nixill.dice.operations;

import java.util.function.BiFunction;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

public class ComparisonOperators<T extends DCValue> {
  private ComparisonFunction<T> coFunc;
  private String supersymbol;
  private int coPriority;

  public final ComparisonOperator GREATER = new ComparisonOperator(Comparison.GREATER);
  public final ComparisonOperator EQUAL = new ComparisonOperator(Comparison.EQUAL);
  public final ComparisonOperator LESS = new ComparisonOperator(Comparison.LESS);
  public final ComparisonOperator NOT_GREATER = new ComparisonOperator(Comparison.NOT_GREATER);
  public final ComparisonOperator NOT_EQUAL = new ComparisonOperator(Comparison.NOT_EQUAL);
  public final ComparisonOperator NOT_LESS = new ComparisonOperator(Comparison.NOT_LESS);
  public final ComparisonOperator MODULO = new ComparisonOperator(Comparison.MODULO);

  public ComparisonOperators(String symbol, int priority, ComparisonFunction<T> func) {
    this.supersymbol = symbol;
    this.coPriority = priority;
    this.coFunc = func;
  }

  public static boolean compares(double left, Comparison comp, double right) {
    if (comp == Comparison.MODULO) {
      return (left % right) == 0;
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
    MODULO("%", 0);
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