package net.nixill.dice.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DCListExpression extends DCExpression {
  ArrayList<DCEntity> listItems;

  public DCListExpression(Collection<DCEntity> items) {
    listItems = new ArrayList<>(items);
  }

  @Override
  public DCList run(HashMap<String, DCEntity> environment) {
    ArrayList<DCValue> vals = new ArrayList<>();
    for (DCEntity ent : listItems) {
      if (ent instanceof DCExpression) {
        DCExpression exp = (DCExpression) ent;
        vals.add(exp.run(environment));
      } else if (ent instanceof DCValue) {
        vals.add((DCValue) ent);
      }
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
}