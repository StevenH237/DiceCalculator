package net.nixill.dice.objects;

public abstract class DCValue extends DCEntity {
  public DCValue getValue() {
    return this;
  }

  public abstract DCSingle getSingle();
  public abstract DCList getList();
}