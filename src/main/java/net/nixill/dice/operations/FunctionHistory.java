package net.nixill.dice.operations;

import java.util.ArrayList;

public class FunctionHistory {
  private static ThreadLocal<ArrayList<HistoryEntry>> histories = new ThreadLocal<ArrayList<HistoryEntry>>() {
    @Override
    protected ArrayList<HistoryEntry> initialValue() {
      return new ArrayList<>();
    }
  };
  
  public static void add(HistoryEntry ent) {
    histories.get().add(ent);
  }
  
  public static ArrayList<HistoryEntry> getList() {
    return new ArrayList<>(histories.get());
  }
  
  public static void clear() {
    histories.remove();
  }
  
  public static class HistoryEntry {
    public final int    level;
    public final String text;
    
    public HistoryEntry(int level, String text) {
      this.level = level;
      this.text = text;
    }
  }
}