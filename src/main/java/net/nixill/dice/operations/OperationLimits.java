package net.nixill.dice.operations;

public class OperationLimits {
  private static int perLineLimit;
  private static int perOperLimit;
  
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
}