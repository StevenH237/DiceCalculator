package net.nixill.dice.objects;

import java.util.HashMap;

public abstract class DCExpression extends DCEntity {
  public DCValue getValue() {
    return run(null);
  }

  public DCValue getValue(HashMap<String, DCEntity> environment) {
    return run(environment);
  }

  public abstract DCValue run(HashMap<String, DCEntity> environment);
}