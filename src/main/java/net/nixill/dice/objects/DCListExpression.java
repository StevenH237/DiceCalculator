package net.nixill.dice.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * A type of {@link DCExpression} that consists of a list of many entities.
 * <p>
 * The value of a DCList is the same list but with all entities resolved
 * into values.
 */
public class DCListExpression extends DCExpression {
  ArrayList<DCEntity> listItems;
  
  /**
   * Creates a new DCListExpression with the given items.
   */
  public DCListExpression(List<DCEntity> items) {
    listItems = new ArrayList<>(items);
  }
  
  /**
   * Returns the number of items in this list.
   * 
   * @return The number of items
   */
  public int size() {
    return listItems.size();
  }
  
  /**
   * Gets a particular item from the list.
   * 
   * @param i
   *   The index of the item.
   * @return The item in question.
   */
  public DCEntity get(int i) {
    return listItems.get(i);
  }
  
  @Override
  public DCList getValue() {
    ArrayList<DCValue> vals = new ArrayList<>();
    for (DCEntity ent : listItems) {
      vals.add(ent.getValue());
    }
    return new DCList(vals);
  }
  
  @Override
  public String toString() {
    String out = "[";
    for (DCEntity ent : listItems) {
      out += ent.toShortString() + ", ";
    }
    out = out.substring(0, out.length() - 2) + "]";
    return out;
  }
  
  @Override
  public String toShortString() {
    String out = "[...]";
    return out;
  }
  
  @Override
  public String toLongString() {
    String out = "[";
    for (DCEntity ent : listItems) {
      out += ent.toLongString() + ", ";
    }
    out = out.substring(0, out.length() - 2) + "]";
    return out;
  }
  
  public void printTree(int level) {
    printSpaced(level, "List: " + listItems.size() + " item(s)");
    for (DCEntity ent : listItems) {
      ent.printTree(level + 1);
    }
  }
}