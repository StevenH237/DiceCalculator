package net.nixill.dice.objects;

import java.util.HashMap;

public abstract class DCExpression extends DCEntity {
  public DCValue getValue() {
    return run(null);
  }

  public abstract DCValue run(HashMap<String, DCExpression> environment);
}