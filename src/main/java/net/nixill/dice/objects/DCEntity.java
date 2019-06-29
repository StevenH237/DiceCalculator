package net.nixill.dice.objects;

import java.util.HashMap;

public abstract class DCEntity {
  public abstract DCValue getValue();
  public abstract DCValue getValue(HashMap<String, DCEntity> environment);
  public abstract String toString();
  public abstract String toShortString();
  public abstract String toLongString();
}