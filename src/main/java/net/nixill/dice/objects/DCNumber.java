package net.nixill.dice.objects;

public class DCNumber extends DCSingle {
  public DCNumber(double value, double potential) {
    super(value, potential);
  }

  public DCNumber(double value) {
    this.value = value;
    if (value <= 6) {
      potential = 6;
    } else if (value <= 20) {
      potential = 20;
    } else {
      potential = Math.pow(10, Math.ceil(Math.log10(value)));
    }
  }

  @Override
  public String toLongString() {
    return toString();
  }
  
  public void printTree(int level) {
    printSpaced(level, "Number " + value);
  }
}