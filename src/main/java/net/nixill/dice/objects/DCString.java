package net.nixill.dice.objects;

import java.util.ArrayList;

public class DCString extends DCValue {
  private String value;
  
  public DCString(String val) {
    value = val;
  }
  
  @Override
  public DCSingle getSingle() {
    return getList().getSingle();
  }
  
  @Override
  public DCList getList() {
    char[] chars = value.toCharArray();
    
    ArrayList<DCValue> out = new ArrayList<>();
    
    for (char chr : chars) {
      out.add(new DCNumber((double) chr));
    }
    
    return new DCList(out);
  }
  
  @Override
  public DCString getString() {
    return this;
  }
  
  @Override
  public String toString() {
    return value;
  }
  
  @Override
  public String toShortString() {
    return value;
  }
  
  @Override
  public String toLongString() {
    return value;
  }
  
  @Override
  public void printTree(int level) {
    printSpaced(level, "String: " + value);
  }
  
}