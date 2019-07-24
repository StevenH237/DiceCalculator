package net.nixill.dice.objects;

import java.util.Random;

/**
 * Provides a way to access a thead-static {@link Random}.
 */
public class Randomizer {
  private static final ThreadLocal<Random> randoms = ThreadLocal
      .withInitial(() -> {
        return new Random();
      });
  
  /**
   * Retrieves the Random.
   */
  public static Random get() {
    return randoms.get();
  }
  
  /**
   * Creates a new Random with a set seed.
   * 
   * @param seed
   *   The seed to use
   */
  public static void setSeed(long seed) {
    randoms.set(new Random(seed));
  }
}