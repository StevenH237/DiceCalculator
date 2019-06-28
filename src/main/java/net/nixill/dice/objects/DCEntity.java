package net.nixill.dice.objects;

public abstract class DCEntity {
  public abstract DCValue getValue();
  public abstract String toString();
  public abstract String toShortString();
  public abstract String toLongString();
}