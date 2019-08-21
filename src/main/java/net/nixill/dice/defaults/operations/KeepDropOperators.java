package net.nixill.dice.defaults.operations;

import java.util.ArrayList;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCList;
import net.nixill.dice.objects.DCOperation;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.Functions;
import net.nixill.dice.operations.Operator;

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
    
    // And if we're keeping everything or dropping nothing, the result is the input.
    else if (count >= list.size() && keep) {
      return list;
    } else if (count <= 0 && !keep) {
      return list;
    }
    
    ArrayList<DCValue> dVals = list.getItems();
    ArrayList<DCValue> kVals = new ArrayList<>();
    
    double now = Double.NaN;
    boolean cont = true;
    double next = Double.NaN;
    
    if (highest) {
      next = Double.NEGATIVE_INFINITY;
    } else {
      next = Double.POSITIVE_INFINITY;
    }
    
    while (count > 0) {
      for (int i = 0; i < dVals.size() && count > 0; /* no autoincrement */) {
        double val = dVals.get(i);
        
        if (val == now) {
          
        }
      }
    }
  }
}