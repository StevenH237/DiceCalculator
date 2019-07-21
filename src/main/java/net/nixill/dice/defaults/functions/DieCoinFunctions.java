package net.nixill.dice.defaults.functions;

import net.nixill.dice.exception.DiceCalcException;
import net.nixill.dice.objects.DCCodeFunction;
import net.nixill.dice.objects.DCCoin;
import net.nixill.dice.objects.DCDie;

/**
 * Provides built-in functions to regenerate a saved die or coin. Also
 * usable in theory to create new ones with arbitrary values.
 */
public class DieCoinFunctions {
  /**
   * (Re)creates a die.
   * <p>
   * The first parameter is the actual value of the die, and the second the
   * number of sides.
   */
  public static final DCCodeFunction DIE;
  /**
   * (Re)creates a flipped coin.
   * <p>
   * The first parameter is 0 if the coin is tails and 1 if heads. The
   * second parameter is the value of a heads.
   */
  public static final DCCodeFunction COIN;
  
  static {
    DIE = new DCCodeFunction((params) -> {
      if (params.size() < 2) {
        throw new DiceCalcException(new NullPointerException(
            "Must have at least two params for a !d"));
      }
      
      double value = params.get(0).getValue().getSingle().getAmount();
      double sides = params.get(1).getValue().getSingle().getAmount();
      sides = Math.floor(sides);
      
      if (sides < 1) {
        throw new DiceCalcException(new IllegalArgumentException(
            "Dice must have at least one side."));
      } else if (sides == 1 && (value < 0 || value >= 1)) {
        throw new DiceCalcException(new IllegalArgumentException(
            "Dice with one side must have a value between 0, inclusive, and 1, exclusive."));
      } else if (sides > 1 && (value < 1 || value >= (sides + 1))) {
        throw new DiceCalcException(new IllegalArgumentException(
            "Dice with two or more sides must have a value that, when floored, is between 1 and the number of sides, inclusive."));
      }
      
      return new DCDie(sides, value);
    });
    
    COIN = new DCCodeFunction((params) -> {
      if (params.size() < 2) {
        throw new DiceCalcException(new NullPointerException(
            "Must have at least two params for a !c"));
      }
      
      boolean isHeads = params.get(0).getValue().getSingle()
          .getAmount() >= 0.5;
      double value = params.get(1).getValue().getSingle().getAmount();
      
      return new DCCoin(value, isHeads);
    });
  }
}