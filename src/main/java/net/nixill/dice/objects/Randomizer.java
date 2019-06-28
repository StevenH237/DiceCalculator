package net.nixill.dice.objects;

import java.util.HashMap;
import java.util.Random;

public class Randomizer {
  private static HashMap<Thread, Random> map;

  static {
    map = new HashMap<>();
  }

  private static Random getRandom() {
    Thread thread = Thread.currentThread();
    if (map.containsKey(thread)) {
      return map.get(thread);
    } else {
      Random rand = new Random();
      map.put(thread, rand);
      return rand;
    }
  }

  public static void setSeed(long seed) {
    getRandom().setSeed(seed);
  }

  public static long nextLong() {
    return getRandom().nextLong();
  }

  public static double nextDouble() {
    return getRandom().nextDouble();
  }

  public static boolean nextBoolean() {
    return getRandom().nextBoolean();
  }

  public static int nextInt(int max) {
    return getRandom().nextInt(max);
  }
}