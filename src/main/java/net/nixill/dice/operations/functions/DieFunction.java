package net.nixill.dice.operations.functions;

import net.nixill.dice.objects.DCCodeFunction;
import net.nixill.dice.objects.DCDie;
import net.nixill.dice.objects.DCEntity;

public class DieFunction extends DCCodeFunction {
  private DCEntity value;
  private DCEntity sides;

  public DieFunction(DCEntity value, DCEntity sides) {
    super("!d", value, sides);

    this.value = value;
    this.sides = sides;
  }

  @Override
  public DCDie getValue() {
    double val = value.getValue().getSingle().getAmount();
    val = Math.floor(val);
    double sid = sides.getValue().getSingle().getAmount();

    if (sid < 1) {
      // TODO throw exception
    } else if (sid > 1 && (val < 1 || val > sid)) {
      // TODO throw different exception
    } else if (sid == 1 && (val < 0 || val >= 1)) {
      // TODO throw third exception
    }

    return new DCDie(sid, val);
  }
}