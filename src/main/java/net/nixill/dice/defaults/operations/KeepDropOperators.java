package net.nixill.dice.defaults.operations;

import java.util.ArrayList;
import java.util.Collections;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCList;
import net.nixill.dice.objects.DCOperation;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.ComparisonOperators;
import net.nixill.dice.operations.Functions;
import net.nixill.dice.operations.Operator;
import net.nixill.dice.operations.ComparisonOperators.Comparison;

public class KeepDropOperators {
  /**
   * The binary "k" operator, which trims a list to the specified size
   * (keep first <i>R</i> items).
   * <p>
   * If R is zero or negative, an empty list is returned without evaluating
   * L.
   * <p>
   * If L is an operation with a {@link DiceOperators#ROLL_UNTIL} operator,
   * the roll itself is limited to the given number of tries, then returned
   * directly.
   * <p>
   * In all other cases, L's value is converted to a list, which is then
   * trimmed to contain no more than the first R items.
   */
  public static BinaryOperator<DCList> KEEP_FIRST = new BinaryOperator<DCList>(
      "k", Priorities.KEEP_DROP, 2, (left, right) -> {
        int count = (int) (right.getValue().getSingle().getAmount());
        
        // If the count is zero, then it doesn't matter what the left is at
        // all. The result will always be an empty list. So don't even
        // bother evaluating the left.
        if (count <= 0) {
          return new DCList(new ArrayList<>());
        }
        
        // If the left is a "roll until" operator, limiting it here saves
        // processing time
        if (left instanceof DCOperation) {
          Operator oper = ((DCOperation) left).getOperator();
          
          if (DiceOperators.ROLL_UNTIL.isMember(oper)) {
            String prev = Functions.getThread("ulimit");
            Functions.setThread("ulimit", Integer.toString(count));
            
            DCList val = (DCList) (left.getValue());
            
            Functions.setThread("ulimit", prev);
            
            return val;
          }
        }
        
        DCList list = left.getValue().getList();
        ArrayList<DCValue> vals = list.getItems();
        for (int i = count; i < vals.size(); /* no increment */) {
          vals.remove(i);
        }
        return new DCList(vals);
      });
  
  public static BinaryOperator<DCList> KEEP_HIGHEST = new BinaryOperator<>(
      "kh", Priorities.KEEP_DROP, 2,
      (left, right) -> keepDropByRank(left, right, true, true));
  public static BinaryOperator<DCList> DROP_HIGHEST = new BinaryOperator<>(
      "dh", Priorities.KEEP_DROP, 2,
      (left, right) -> keepDropByRank(left, right, false, true));
  public static BinaryOperator<DCList> KEEP_LOWEST  = new BinaryOperator<>(
      "kl", Priorities.KEEP_DROP, 2,
      (left, right) -> keepDropByRank(left, right, true, false));
  public static BinaryOperator<DCList> DROP_LOWEST  = new BinaryOperator<>(
      "dl", Priorities.KEEP_DROP, 2,
      (left, right) -> keepDropByRank(left, right, false, false));
  
  public static DCList keepDropByRank(DCEntity left, DCEntity right,
      boolean keep, boolean highest) {
    int count = (int) (right.getValue().getSingle().getAmount());
    
    // Short circuit: If we're keeping zero, don't bother evaluating the
    // list on the left. The result would always be [] anyway.
    if (count <= 0 && keep) {
      return new DCList(new ArrayList<>());
    }
    
    DCList list = left.getValue().getList();
    
    // Short circuit: If we're dropping everything, the result is []
    // anyway.
    if (count >= list.size() && !keep) {
      return new DCList(new ArrayList<>());
    }
    
    // Now, if we're dropping, invert the count. The count is what's in the
    // "k" values list, but when we're dropping, we receive the "d" values
    // list.
    if (!keep) {
      count = list.size() - count;
    }
    
    // And if we're keeping everything or dropping nothing, the result is
    // the input.
    if (count >= list.size()) {
      return list;
    }
    
    ArrayList<DCValue> dVals = list.getItems();
    ArrayList<Boolean> keeps = new ArrayList<>(
        Collections.nCopies(dVals.size(), false));
    
    double now = Double.NaN;
    double next = Double.NaN;
    
    while (count > 0) {
      if (highest) {
        next = Double.NEGATIVE_INFINITY;
      } else {
        next = Double.POSITIVE_INFINITY;
      }
      
      for (int i = 0; i < dVals.size() && count > 0; i += 1) {
        DCValue val = dVals.get(i);
        double amt = val.getSingle().getAmount();
        
        if (!keeps.get(i)) {
          if (amt == now) {
            keeps.set(i, true);
            count -= 1;
          } else {
            if (highest) {
              next = Math.max(next, amt);
            } else {
              next = Math.min(next, amt);
            }
          }
        }
      }
      
      now = next;
    }
    
    ArrayList<DCValue> out = new ArrayList<>();
    
    for (int i = 0; i < dVals.size(); i++) {
      if (keeps.get(i) == keep) {
        out.add(dVals.get(i));
      }
    }
    
    return new DCList(out);
  }
  
  // comparison operators!
  public static ComparisonOperators<DCList> KEEP_COMPARISON = new ComparisonOperators<>(
      "k", Priorities.KEEP_DROP, 2, (left, comp,
          right) -> keepDropByComparison(left, comp, right, true));
  public static ComparisonOperators<DCList> DROP_COMPARISON = new ComparisonOperators<>(
      "d", Priorities.KEEP_DROP, 2, (left, comp,
          right) -> keepDropByComparison(left, comp, right, false));
  
  public static DCList keepDropByComparison(DCEntity left, Comparison comp,
      DCEntity right, boolean keep) {
    ArrayList<DCValue> list = left.getValue().getList().getItems();
    double value = right.getValue().getSingle().getAmount();
    
    for (int i = 0; i < list.size(); /* no autoincrement */) {
      double lValue = list.get(i).getSingle().getAmount();
      if ((comp.compares(lValue, value)) != keep) {
        list.remove(i);
      } else {
        i += 1;
      }
    }
    
    return new DCList(list);
  }
}