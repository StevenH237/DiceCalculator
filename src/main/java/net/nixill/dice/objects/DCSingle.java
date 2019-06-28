package net.nixill.dice.objects;

import java.util.ArrayList;

public abstract class DCSingle extends DCValue {
  protected double value;
  protected double potential;

  protected DCSingle(double value, double potential) {
    this.value = value;
    this.potential = potential;
  }

  protected DCSingle() {}

  public DCSingle getSingle() {
    return this;
  }

  public DCList getList() {
    ArrayList<DCValue> items = new ArrayList<>();
    items.add(this);
    return new DCList(items);
  }

  public double getNumber() {
    return value;
  }

  public double getPotential() {
    return potential;
  }

  public String toString() {
    return Double.toString(value);
  }

  public String toShortString() {
    return toString();
  }
}