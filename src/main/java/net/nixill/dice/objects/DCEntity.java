package net.nixill.dice.objects;

public abstract class DCEntity {
  public abstract DCValue getValue();
  public abstract String toString();
  public abstract String toShortString();
  public abstract String toLongString();
  public abstract void printTree(int level);
  protected void printSpaced(int level, String message) {
    System.out.println(new String(new char[level*2]).replace("\0", " ") + message);
  }
}