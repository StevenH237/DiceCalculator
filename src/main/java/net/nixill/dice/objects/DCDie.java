package net.nixill.dice.objects;

public class DCDie extends DCSingle {
  public DCDie(double sides, double value) {
    sides = Math.floor(sides);
    potential = sides;
    
    this.value = value;
  }

  public DCDie(double sides) {
    sides = Math.floor(sides);
    potential = sides;
    if (sides >= 2) {
      value = (double) (Randomizer.nextInt((int) sides + 1));
    } else if (sides == 1) {
      value = Randomizer.nextDouble();
    } else {
      throw new IllegalArgumentException("Dice must have at least one side.");
    }
  }
}