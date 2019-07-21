package net.nixill.dice.objects;

/**
 * Represents a die, which is a random number between 1 and its potential.
 */
public class DCDie extends DCSingle {
  /**
   * Creates a new die with a predetermined state.
   */
  public DCDie(double sides, double value) {
    sides = Math.floor(sides);
    potential = sides;

    if (sides < 1) {
      throw new IllegalArgumentException("Dice must have at least one side.");
    }

    if (value > sides) {
      throw new IllegalArgumentException("Dice must have at least as many sides as their value.");
    }

    this.value = value;
  }

  /**
   * Creates a new die with a randomized state.
   * <p>
   * A die with at least two sides will have an integer value between 1 and the
   * number of sides. A die with one side will have a random decimal value between
   * 0 (inclusive) and 1 (exclusive).
   */
  public DCDie(double sides) {
    sides = Math.floor(sides);
    potential = sides;
    if (sides >= 2) {
      value = (double) (Randomizer.get().nextInt((int) sides) + 1);
    } else if (sides == 1) {
      value = Randomizer.get().nextDouble();
    } else {
      throw new IllegalArgumentException("Dice must have at least one side.");
    }
  }

  /**
   * Gets the number of sides on the die.
   * <p>
   * This function is identical to {@link #getPotential()}.
   */
  public double getSides() {
    return potential;
  }

  @Override
  public String toString(int level) {
    return numFormat.format(value);
  }

  @Override
  public String toCode() {
    return "{!d," + codeFormat.format(value) + "," + codeFormat.format(potential) + "}";
  }

  @Override
  public void printTree(int level) {
    printSpaced(level, "Die: " + numFormat.format(value) + " (d" + numFormat.format(potential) + ")");
  }
}