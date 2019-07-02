package net.nixill.dice.objects;

/**
 * A resolved, unchanging value.
 */
public abstract class DCValue extends DCEntity {
  /**
   * Returns this DCValue.
   * 
   * @return This DCValue.
   */
  public DCValue getValue() {
    return this;
  }

  /**
   * Casts the value to a {@link DCSingle}.
   * 
   * @return This value as a {@link DCSingle}.
   */
  public abstract DCSingle getSingle();

  /**
   * Casts the value to a {@link DCList}.
   * 
   * @return This value as a {@link DCSingle}.
   */
  public abstract DCList getList();
}