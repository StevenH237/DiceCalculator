package net.nixill.dice.objects;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A list of resolved, unchanging values.
 */
public class DCList extends DCValue {
  private ArrayList<DCValue> listItems;

  /**
   * Creates a new list of values.
   */
  public DCList(Collection<DCValue> items) {
    listItems = new ArrayList<>(items);
  }

  /**
   * Casts this DCList to a {@link DCSingle} by taking the sum of all of its
   * items.
   */
  @Override
  public DCSingle getSingle() {
    double sum = 0;
    for (DCValue val : listItems) {
      sum += val.getSingle().getAmount();
    }
    return new DCNumber(sum);
  }

  /**
   * Returns this DCList itself.
   */
  @Override
  public DCList getList() {
    return this;
  }

  /**
   * Returns a new {@link ArrayList} containing this list's items.
   */
  public ArrayList<DCValue> getItems() {
    return new ArrayList<>(listItems);
  }

  @Override
  public String toString() {
    String out = getSingle().getAmount() + " [";
    for (DCValue val : listItems) {
      out += val.toShortString() + ", ";
    }
    out = out.substring(0, out.length() - 2) + "]";
    return out;
  }

  @Override
  public String toShortString() {
    String out = getSingle().getAmount() + " [...]";
    return out;
  }

  @Override
  public String toLongString() {
    String out = getSingle().getAmount() + " [";
    for (DCValue val : listItems) {
      out += val.toLongString() + ", ";
    }
    out = out.substring(0, out.length() - 2) + "]";
    return out;
  }

  public void printTree(int level) {
    printSpaced(level, "List: " + listItems.size() + " item(s)");
    for (DCValue val : listItems) {
      val.printTree(level + 1);
    }
  }
}