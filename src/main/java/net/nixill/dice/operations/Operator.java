package net.nixill.dice.operations;

import java.util.HashSet;

public abstract class Operator {
  //-- STATICS --//
  private static HashSet<Integer> fromRight = new HashSet<>();

  public static void setFromRight(int level) {
    fromRight.add(level);
  }

  public static void clearFromRight(int level) {
    fromRight.remove(level);
  }

  public static boolean isFromRight(int level) {
    return fromRight.contains(level);
  }

  //-- NOT STATICS --//
  protected int priority;
  protected String symbol;

  public Operator(String symbol, int priority) {
    this.priority = priority;
    this.symbol = symbol;
  }

  public int getPriority() {
    return priority;
  }

  public String getSymbol() {
    return symbol;
  }

}