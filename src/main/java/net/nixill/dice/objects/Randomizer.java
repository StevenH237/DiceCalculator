package net.nixill.dice.objects;

import java.util.Random;

public class Randomizer {
  private static final ThreadLocal<Random> randoms = ThreadLocal.withInitial(() -> {return new Random();});

  public static Random get() {
    return randoms.get();
  }
}