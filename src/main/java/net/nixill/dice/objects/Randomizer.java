package net.nixill.dice.objects;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Randomizer {
  private static HashMap<Thread, Random> map;

  static {
    map = new HashMap<>();
    // Remove randomizers for dead threads every ten minutes.
    new Timer().schedule(new CleanupTask(), 600000, 600000);
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

  private static class CleanupTask extends TimerTask {
    @Override
    public void run() {
      for (Thread th : map.keySet()) {
        if (!th.isAlive()) {
          map.remove(th);
        }
      }
    }
  }
}