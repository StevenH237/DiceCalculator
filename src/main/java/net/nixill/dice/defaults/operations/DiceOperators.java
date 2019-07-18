package net.nixill.dice.defaults.operations;

import java.util.ArrayList;

import net.nixill.NixMath;
import net.nixill.dice.exception.DiceCalcException;
import net.nixill.dice.objects.DCDie;
import net.nixill.dice.objects.DCList;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.ComparisonOperators;
import net.nixill.dice.operations.ComparisonOperators.Comparison;
import net.nixill.dice.operations.PrefixOperator;

public class DiceOperators {
  public static final BinaryOperator<DCList> DICE = new BinaryOperator<>(
      "d", Priorities.DICE, (left, right) -> {
        double count = Math.floor(left.getValue().getSingle().getAmount());
        double sides = Math
            .floor(right.getValue().getSingle().getAmount());
        
        if (count < 1) {
          throw new DiceCalcException(new IllegalArgumentException(
              "You must roll at least one die."));
        }
        
        if (sides < 1) {
          throw new DiceCalcException(new IllegalArgumentException(
              "Dice must have at least one side."));
        }
        
        ArrayList<DCValue> out = new ArrayList<>();
        for (int i = 0; i < count; i++) {
          out.add(new DCDie(sides));
        }
        
        return new DCList(out);
      });
  
  public static final PrefixOperator<DCDie> ONE_DIE = new PrefixOperator<>(
      "d", Priorities.DICE, (ent) -> {
        double sides = Math.floor(ent.getValue().getSingle().getAmount());
        
        if (sides < 1) {
          throw new DiceCalcException(new IllegalArgumentException(
              "Dice must have at least one side."));
        }
        
        return new DCDie(sides);
      });
  
  public static final ComparisonOperators<DCList> ROLL_UNTIL = new ComparisonOperators<>(
      "u", Priorities.DICE, (left, comp, right) -> {
        double sides = left.getValue().getSingle().getAmount();
        double cutoff = right.getValue().getSingle().getAmount();
        
        // The conditions under which this operator shouldn't work are as
        // follows:
        boolean error = false;
        if (cutoff < 1 || cutoff > sides) {
          // The cutoff range is entirely outside the range of possible
          // values
          // It's always an error because
          // - >=low, >low, !=low, <=high, <high, !=high - match everything
          // - <=low, <low, =low, >=high, >high, =high - match nothing
          error = true;
        } else if (cutoff == 1 || cutoff == sides) {
          // The cutoff range is exactly the edge of a possible value
          // Only an error if the comparison is
          // - (value) >= 1 (matches everything)
          // - (value) < 1 (matches nothing)
          // - (value) <= max (matches everything)
          // - (value) > max (matches nothing)
          error = (comp.compares(1, cutoff) == comp.compares(sides,
              cutoff));
        } else if (cutoff == Math.floor(cutoff)) {
          // If the cutoff value is an int within the range of possible
          // values, then do NOT error.
        } else if (comp == Comparison.MODULO
            || comp == Comparison.NOT_MODULO) {
          // For modulo-based comparisons, if the cutoff isn't an int,
          // multiply it until it is since we only make ints anyway.
          // For example, the multiples of 1.5 (3/2) that a d6 can make are
          // just 3 and 6 - the multiples of 3.
          cutoff = NixMath.float2num(cutoff);
          if (cutoff > sides) {
            error = true;
          }
        }
        // For a not-modulo-based comparison, a decimal is fine; the only
        // thing about it is that there's no difference between >= and >.
        
        return null;
      });
}