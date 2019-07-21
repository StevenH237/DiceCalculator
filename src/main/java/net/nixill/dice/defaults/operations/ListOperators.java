package net.nixill.dice.defaults.operations;

import java.util.ArrayList;
import java.util.Random;

import net.nixill.dice.objects.DCCoin;
import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCList;
import net.nixill.dice.objects.DCListExpression;
import net.nixill.dice.objects.DCNumber;
import net.nixill.dice.objects.DCSingle;
import net.nixill.dice.objects.DCString;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.objects.Randomizer;
import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.PostfixOperator;
import net.nixill.dice.operations.PrefixOperator;

/**
 * This class contains the default List operators.
 */
public class ListOperators {
  /**
   * The binary "+" operator, which adds two numbers, joins two lists, or
   * concatenates two strings.
   * <ul>
   * <li><code>left</code> operand - value: A starting value</li>
   * <li><code>right</code> operand - value: The value to add to
   * <code>left</code>.</li>
   * <li>Returns - list: A list formed by joining <code>left</code> and
   * <code>right</code>.</li>
   * </ul>
   */
  public static final BinaryOperator<DCValue> JOIN = new BinaryOperator<>(
      "+", Priorities.JOIN, ListOperators::joinOp);
  
  public static DCValue joinOp(DCEntity leftEnt, DCEntity rightEnt) {
    DCValue left = leftEnt.getValue();
    DCValue right = rightEnt.getValue();
    
    if (left instanceof DCString || right instanceof DCString) {
      return new DCString(
          left.getString().toString() + right.getString().toString());
    } else if (left instanceof DCList || right instanceof DCList) {
      ArrayList<DCValue> out = left.getList().getItems();
      out.addAll(right.getList().getItems());
      return new DCList(out);
    } else {
      return new DCNumber(
          left.getSingle().getAmount() + right.getSingle().getAmount());
    }
  }
  
  /**
   * The prefix "-" operator, which makes its operand negative.
   * <p>
   * Lists are made negative by making every item they contain negative.
   * <ul>
   * <li>operand - list: The list to make negative.</li>
   * <li>Returns - list: That list, negative.</li>
   * </ul>
   */
  public static final PrefixOperator<DCValue> NEGATIVE = new PrefixOperator<>(
      "-", Priorities.NEGATIVE, ListOperators::negativeOp);
  
  public static DCValue negativeOp(DCEntity ent) {
    DCValue val = ent.getValue();
    
    if (val instanceof DCSingle) {
      return new DCNumber(-val.getSingle().getAmount());
    }
    
    ArrayList<DCValue> in = val.getList().getItems();
    ArrayList<DCValue> out = new ArrayList<>();
    
    for (DCValue inVal : in) {
      if (inVal instanceof DCSingle) {
        DCSingle inSingle = (DCSingle) inVal;
        out.add(
            new DCNumber(-inSingle.getAmount(), inSingle.getPotential()));
      } else {
        out.add(negativeOp(inVal));
      }
    }
    
    return new DCList(out);
  }
  
  /**
   * The bianry "-" operator, which subtracts two numbers or joins a list
   * to a negative list.
   * <p>
   * This works the same way as {@link #JOIN}, except that the right-hand
   * operand is made {@link #NEGATIVE} first.
   * <ul>
   * <li><code>left</code> operand - list: A starting list</li>
   * <li><code>right</code> operand - list: The list to subtract from
   * <code>left</code>.</li>
   * <li>Returns - list: A list formed by joining <code>left</code> to
   * negative <code>right</code>.</li>
   * </ul>
   */
  public static final BinaryOperator<DCValue> NEG_JOIN = new BinaryOperator<>(
      "-", Priorities.JOIN, (left, right) -> {
        return joinOp(left, negativeOp(right));
      });
  
  /**
   * The prefix "#" operator, which returns the length of the list.
   * <p>
   * Sub-lists are only counted as a single item in this list.
   * <ul>
   * <li>Operand - list: A list</li>
   * <li>Returns - number: The length of the list</li>
   * </ul>
   */
  public static final PrefixOperator<DCNumber> SIZE = new PrefixOperator<>(
      "#", Priorities.LIST, (ent) -> {
        DCValue val = ent.getValue();
        if (val instanceof DCSingle) {
          return new DCNumber(1);
        } else {
          return new DCNumber(val.getList().getItems().size());
        }
      });
  
  /**
   * The prefix "$" operator, which returns the sum of the list.
   * <ul>
   * <li>Operand - list: A list</li>
   * <li>Returns - number: The sum of all the items in the list</li>
   * </ul>
   */
  public static final PrefixOperator<DCSingle> SUM = new PrefixOperator<>(
      "$", Priorities.LIST, (ent) -> {
        return ent.getValue().getSingle();
      });
  
  /**
   * The postfix "?" operator, which shuffles the list.
   * <ul>
   * <li>Operand - list: A list</li>
   * <li>Returns - list: The same list with its items in random order</li>
   * </ul>
   */
  public static final PostfixOperator<DCList> SHUFFLE = new PostfixOperator<>(
      "?", Priorities.LIST, (ent) -> {
        DCList list = ent.getValue().getList();
        
        if (list.size() < 2) {
          return list;
        } else {
          ArrayList<DCValue> in = list.getItems();
          ArrayList<DCValue> out = new ArrayList<>();
          
          Random rand = Randomizer.get();
          
          while (!in.isEmpty()) {
            DCValue val = in.remove(rand.nextInt(in.size()));
            out.add(val);
          }
          return new DCList(out);
        }
      });
  
  /**
   * The binary "s" operator, which selects a random item from the list.
   * <p>
   * Only the selected item is evaluated, which can be useful for recursive
   * functions.
   * <p>
   * If the right operand is a coin, Heads selects the second item, and
   * Tails selects the first. In all other cases, items are numbered
   * starting from 1.
   * <ul>
   * <li><code>left</code> operand - expression list: A list</li>
   * <li><code>right</code> operand - number: The item to select from the
   * list</li>
   */
  public static final BinaryOperator<DCValue> SELECT = new BinaryOperator<DCValue>(
      "s", Priorities.LIST, (left, right) -> {
        DCValue val = right.getValue();
        int selection = 0;
        
        if (val instanceof DCCoin) {
          if (((DCCoin) val).isHeads()) {
            selection = 1;
          }
        } else {
          selection = (int) Math.floor(val.getSingle().getAmount()) - 1;
        }
        
        selection = Math.max(0, selection);
        
        if (left instanceof DCListExpression) {
          DCListExpression exp = (DCListExpression) left;
          selection = Math.min(selection, exp.size() - 1);
          return exp.get(selection).getValue();
        } else {
          DCList list = left.getValue().getList();
          selection = Math.min(selection, list.size() - 1);
          return list.get(selection);
        }
      });
}
