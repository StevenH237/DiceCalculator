package net.nixill.dice.operations;

public class OperationLimits {
  private static int perLineLimit = Integer.MAX_VALUE;
  private static int perOperLimit = Integer.MAX_VALUE;
  
  private static ThreadLocal<Integer> perLineCount = ThreadLocal
      .withInitial(() -> {
        return 0;
      });
  
  public static void setLineLimit(int lim) {
    perLineLimit = lim;
  }
  
  public static void setOperLimit(int lim) {
    perOperLimit = lim;
  }
  
  public static int getLineLimit() {
    return perLineLimit;
  }
  
  public static int getOperLimit() {
    return perOperLimit;
  }
  
  public static void setLineCount(int count) {
    perLineCount.set(count);
  }
  
  public static int getLineCount() {
    return perLineCount.get();
  }
  
  public static int getLimit() {
    int limitByLine = perLineLimit - perLineCount.get();
    
    return Integer.min(limitByLine, perOperLimit);
  }
  
  public static String getLimitMessage() {
    int limitByLine = perLineLimit - perLineCount.get();
    
    if (limitByLine > perOperLimit) {
      return "You can only roll " + perLineLimit + " dice per command.";
    } else {
      return "You can only roll " + perOperLimit
          + " dice per single operator.";
    }
  }
  
  public static int addToLimit(int added) {
    int newVal = perLineCount.get() + added;
    perLineCount.set(newVal);
    return newVal;
  }
}