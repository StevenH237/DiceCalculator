package net.nixill.dice.objects;

/**
 * Represents a coin, which is a DCSingle that can only have two states - 0 or
 * its potential value.
 */
public class DCCoin extends DCSingle {
  boolean heads;

  /**
   * Create a new coin with a predetermined state.
   * 
   * @param potent The potential value - the value for Heads.
   * @param heads  Whether or not the coin should be Heads.
   */
  public DCCoin(double potent, boolean heads) {
    this.heads = heads;
    potential = potent;
    if (heads) {
      value = potent;
    } else {
      value = 0;
    }
  }

  /**
   * Create a new coin with a randomly determined state.
   * 
   * @param potent The potential value - the value for heads.
   */
  public DCCoin(double potent) {
    this(potent, Randomizer.get().nextBoolean());
  }

  /**
   * Whether or not the coin landed Heads.
   * <p>
   * This function can be used to distinguish the two states on a coin with a
   * potential value of 0.
   */
  public boolean isHeads() {
    return heads;
  }

  /**
   * The value if the coin were to be heads.
   * <p>
   * This function is identical to {@link #getPotential()}.
   */
  public double headsValue() {
    return potential;
  }

  public String toString() {
    if (heads) {
      return "Heads";
    } else {
      return "Tails";
    }
  }

  public String toShortString() {
    if (heads) {
      return "H";
    } else {
      return "T";
    }
  }

  public String toLongString() {
    if (heads) {
      return "Heads (" + value + ")";
    } else {
      return "Tails (0)";
    }
  }

  public void printTree(int level) {
    printSpaced(level, "Coin: " + toLongString());
  }
}