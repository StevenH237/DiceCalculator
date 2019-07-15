package net.nixill.dice.operations.functions;

import net.nixill.dice.objects.DCCodeFunction;
import net.nixill.dice.objects.DCCoin;
import net.nixill.dice.objects.DCEntity;

public class CoinFunction extends DCCodeFunction {
  private DCEntity heads;
  private DCEntity value;

  public CoinFunction(DCEntity heads, DCEntity value) {
    super("!c", heads, value);
    this.heads = heads;
    this.value = value;
  }

  @Override
  public DCCoin getValue() {
    boolean isHeads = heads.getValue().getSingle().getAmount() >= 0.5;
    double val = value.getValue().getSingle().getAmount();

    return new DCCoin(val, isHeads);
  }
}