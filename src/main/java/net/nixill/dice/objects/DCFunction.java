package net.nixill.dice.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DCFunction extends DCExpression {
  private ArrayList<DCEntity> params;
  private String name;

  public DCFunction(String name, Collection<DCEntity> params) {
    this.name = name;
    this.params = new ArrayList<>(params);
  }

  @Override
  public DCValue run(HashMap<String, DCEntity> environment) {
    throw new UnsupportedOperationException("Functions are coming in a future version!");
  }

  @Override
  public String toString() {
    String out = "{" + name;
    for (DCEntity ent : params) {
      out += "," + ent.toShortString();
    }
    return out + "}";
  }

  @Override
  public String toShortString() {
    return "{" + name + "," + params.size() + " params}";
  }

  @Override
  public String toLongString() {
    String out = "{" + name;
    for (DCEntity ent : params) {
      out += "," + ent.toLongString();
    }
    return out + "}";
  }
  
  public List<DCEntity> getParams() {
    return Collections.unmodifiableList(params);
  }
}