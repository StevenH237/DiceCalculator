package net.nixill.dice.objects;

import java.util.HashMap;

public abstract class DCValue extends DCEntity {
  public DCValue getValue() {
    return this;
  }

  public DCValue getValue(HashMap<String, DCEntity> environment) {
    return this;
  }

  public abstract DCSingle getSingle();
  public abstract DCList getList();
}