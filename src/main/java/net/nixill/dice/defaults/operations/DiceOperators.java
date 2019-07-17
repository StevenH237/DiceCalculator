package net.nixill.dice.defaults.operations;

import java.util.ArrayList;

import net.nixill.dice.exception.DiceCalcException;
import net.nixill.dice.objects.DCDie;
import net.nixill.dice.objects.DCList;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.ComparisonOperators;
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
  
  public static final ComparisonOperators<DCList> ROLL_UNTIL = new ComparisonOperators<>("u", Priorities.DICE, (left, comp, right) -> {
    double sides = 
  });
}