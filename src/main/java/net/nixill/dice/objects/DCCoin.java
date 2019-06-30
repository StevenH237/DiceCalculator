package net.nixill.dice.objects;

public class DCCoin extends DCSingle {
  public DCCoin(double potent, boolean heads) {
    super(heads?potent:0, potent);
  }

  public DCCoin(double potent) {
    this(potent, Randomizer.get().nextBoolean());
  }

  public boolean isHeads() {
    return value != 0;
  }

  public double headsValue() {
    return potential;
  }

  public String toString() {
    if (value != 0) {
      return "H";
    } else {
      return "T";
    }
  }

  public String toShortString() {
    return toString();
  }

  public String toLongString() {
    if (value != 0) {
      return "Heads (" + value + ")";
    } else {
      return "Tails (0)";
    }
  }

  public void printTree(int level) {
    printSpaced(level, "Coin: " + toLongString());
  }
}