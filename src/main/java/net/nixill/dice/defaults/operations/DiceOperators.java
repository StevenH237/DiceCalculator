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
import net.nixill.dice.operations.Variables;

/**
 * Implementation of the default operators for rolling dice.
 */
public class DiceOperators {
  /**
   * The binary "d" operator, which rolls a set number of dice.
   * <ul>
   * <li>left operand - number: The number of dice to roll.</li>
   * <li>right operand - number: The number of sides for each die.</li>
   * <li>Returns - list: The list of rolled dice.</li>
   * </ul>
   */
  public static final BinaryOperator<DCList> DICE = new BinaryOperator<>("d", Priorities.DICE, (left, right) -> {
    double count = Math.floor(left.getValue().getSingle().getAmount());
    double sides = Math.floor(right.getValue().getSingle().getAmount());

    if (count < 1) {
      throw new DiceCalcException(new IllegalArgumentException("You must roll at least one die."));
    }

    if (sides < 1) {
      throw new DiceCalcException(new IllegalArgumentException("Dice must have at least one side."));
    }

    ArrayList<DCValue> out = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      out.add(new DCDie(sides));
    }

    return new DCList(out);
  });

  /**
   * The prefix "d" operator, which rolls a single die.
   * <ul>
   * <li>operand - number: The number of sides for the die.</li>
   * <li>Returns - list: The list of rolled dice.</li>
   * </ul>
   */
  public static final PrefixOperator<DCDie> ONE_DIE = new PrefixOperator<>("d", Priorities.DICE, (ent) -> {
    double sides = Math.floor(ent.getValue().getSingle().getAmount());

    if (sides < 1) {
      throw new DiceCalcException(new IllegalArgumentException("Dice must have at least one side."));
    }

    return new DCDie(sides);
  });

  /**
   * The set of comparison-based "u" operators, which rolls dice until one matches
   * the comparison.
   * <p>
   * The die that ends the streak will not be included in the list, but saved to
   * the variable <code>{_u}</code>.
   * <ul>
   * <li>left operand - number: The number of sides for each die.</li>
   * <li>right operand - comparison: The comparison which, when a die is rolled
   * that satisfies it, ends the rolling streak.</li>
   * <li>Returns - list: The list of rolled dice.</li>
   * </ul>
   */
  public static final ComparisonOperators<DCList> ROLL_UNTIL = new ComparisonOperators<>("u", Priorities.DICE,
      (left, comp, right) -> {
        double sides = Math.floor(left.getValue().getSingle().getAmount());
        double cutoff = right.getValue().getSingle().getAmount();

        Variables.save2("_u", null);

        boolean error = false;
        if (sides < 1) {
          throw new DiceCalcException(new IllegalArgumentException("Dice must have at least one side."));
        } else if (sides == 1) {
          if (comp == Comparison.MODULO || comp == Comparison.NOT_MODULO) {
            // Don't allow modulo for decimal dice; modulo requires a
            // precision too exact to be practical
            throw new DiceCalcException(
                new IllegalArgumentException("Can't roll-until an exact multiple of a decimal."));
          } else if (cutoff < 0 || cutoff >= 1) {
            // The cutoff range is entirely outside the range of possible
            // values
            // It's always an error because
            // - >=low, >low, !=low, <=high, <high, !=high - match
            // everything
            // - <=low, <low, =low, >=high, >high, =high - match nothing
            error = true;
          } else if (cutoff == 0) {
            // The cutoff range is exactly the edge of a possible value
            // Only an error if the comparison is
            // - (value) >= 1 (matches everything)
            // - (value) < 1 (matches nothing)
            // - (value) <= max (matches everything)
            // - (value) > max (matches nothing)
            error = (comp.compares(0, cutoff) == comp.compares(Math.nextDown(1), cutoff));
          }
        } else {

          // The conditions under which this operator shouldn't work are as
          // follows:
          if (cutoff < 1 || cutoff > sides) {
            // The cutoff range is entirely outside the range of possible
            // values
            // It's always an error because
            // - >=low, >low, !=low, <=high, <high, !=high - match
            // everything
            // - <=low, <low, =low, >=high, >high, =high - match nothing
            error = true;
          } else if (cutoff == 1 || cutoff == sides) {
            // The cutoff range is exactly the edge of a possible value
            // Only an error if the comparison is
            // - (value) >= 1 (matches everything)
            // - (value) < 1 (matches nothing)
            // - (value) <= max (matches everything)
            // - (value) > max (matches nothing)
            error = (comp.compares(1, cutoff) == comp.compares(sides, cutoff));
          } else if (cutoff == Math.floor(cutoff)) {
            // If the cutoff value is an int within the range of possible
            // values, then do NOT error.
          } else if (comp == Comparison.MODULO || comp == Comparison.NOT_MODULO) {
            // For modulo-based comparisons, if the cutoff isn't an int,
            // multiply it until it is since we only make ints anyway.
            // For example, the multiples of 1.5 (3/2) that a d6 can make
            // are
            // just 3 and 6 - the multiples of 3.
            cutoff = NixMath.float2num(cutoff);
            if (cutoff > sides) {
              error = true;
            }
          }
          // For a not-modulo-based comparison, a decimal is fine; the only
          // thing about it is that there's no difference between >= and >.
        }

        if (error) {
          throw new DiceCalcException(
              new IllegalArgumentException("The range you have selected would result in a pointless roll."));
        }

        ArrayList<DCValue> out = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
          DCDie die = new DCDie(sides);
          if (comp.compares(die.getAmount(), cutoff)) {
            Variables.save2("_u", die);
            break;
          } else {
            out.add(die);
          }
        }

        return new DCList(out);
      });
}