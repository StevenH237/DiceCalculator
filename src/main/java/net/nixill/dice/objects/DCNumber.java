package net.nixill.dice.objects;

/**
 * An arbitrary number.
 */
public class DCNumber extends DCSingle {
  /**
   * Creates an arbitrary number with your own choice of potential.
   */
  public DCNumber(double value, double potential) {
    this.value = value;
    this.potential = potential;
  }

  /**
   * Creates an arbitrary number with an arbitrarily selected potential.
   * <p>
   * The potential is determined by which of 6, 20, or the next-higher power of 10
   * is lowest without being below the value.
   */
  public DCNumber(double value) {
    this.value = value;
    if (value <= 6) {
      potential = 6;
    } else if (value <= 20) {
      potential = 20;
    } else {
      potential = Math.pow(10, Math.ceil(Math.log10(value)));
    }
  }

  @Override
  public String toString(int lvl) {
    return numToString(value);
  }

  @Override
  public String toCode() {
    return "" + value;
  }

  @Override
  public void printTree(int level) {
    printSpaced(level, "Number " + value);
  }
}