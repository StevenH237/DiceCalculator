package net.nixill.dice.objects;

/**
 * Represents a coin, which is a DCSingle that can only have two states - 0
 * or its potential value.
 */
public class DCCoin extends DCSingle {
  boolean heads;
  
  /**
   * Create a new coin with a predetermined state.
   * 
   * @param potent
   *   The potential value - the value for Heads.
   * @param heads
   *   Whether or not the coin should be Heads.
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
   * @param potent
   *   The potential value - the value for heads.
   */
  public DCCoin(double potent) {
    this(potent, Randomizer.get().nextBoolean());
  }
  
  /**
   * Whether or not the coin landed Heads.
   * <p>
   * This function can be used to distinguish the two states on a coin with
   * a potential value of 0.
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
  
  @Override
  public String toString(int level) {
    String out;
    
    if (heads) {
      out = "Heads";
    } else {
      out = "Tails";
    }
    
    if (level == 0) {
      out = out.substring(0, 1);
    }
    
    return out;
  }
  
  @Override
  public String toCode() {
    String out = "{!c,";
    if (heads) {
      out += "1";
    } else {
      out += "0";
    }
    out += "," + codeFormat.format(value) + "}";
    return out;
  }
  
  @Override
  public void printTree(int level) {
    printSpaced(level,
        "Coin: " + toString() + " (" + numFormat.format(value) + ")");
  }
}