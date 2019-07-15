package net.nixill.dice.objects;

import java.util.Arrays;

public abstract class DCCodeFunction extends DCFunction {
  public DCCodeFunction(String name, DCEntity... params) {
    super(name, Arrays.asList(params));
  }

  @Override
  public abstract DCValue getValue();

  @Override
  public DCEntity getSaved() {
    return this;
  }
}