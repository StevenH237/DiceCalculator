package net.nixill.dice.objects;

import java.util.ArrayList;
import java.util.Collection;

public class DCList extends DCValue {
  private ArrayList<DCValue> listItems;
  
  public DCList(Collection<DCValue> items) {
    listItems = new ArrayList<>(items);
  }

  @Override
  public DCSingle getSingle() {
    double sum = 0;
    for (DCValue val : listItems) {
      sum += val.getSingle().getAmount();
    }
    return new DCNumber(sum);
  }

  @Override
  public DCList getList() {
    return this;
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

  public ArrayList<DCValue> getItems() {
    return new ArrayList<>(listItems);
  }

  public void printTree(int level) {
    printSpaced(level, "List: " + listItems.size() + " item(s)");
    for (DCValue val : listItems) {
      val.printTree(level + 1);
    }
  }
}