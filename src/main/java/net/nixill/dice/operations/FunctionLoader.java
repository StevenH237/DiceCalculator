package net.nixill.dice.operations;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

/**
 * A loader of variables and functions within a single program.
 */
public abstract class FunctionLoader {
  /**
   * Load a normal variable (no prefix).
   * 
   * @param name The name of the variable.
   */
  public abstract DCEntity load(String name);

  /**
   * Load an environment variable (<code>_</code> prefix).
   * 
   * @param name The name of the variable, with its <code>_</code> prefix removed.
   */
  public abstract DCEntity loadEnv(String name);

  /**
   * Load a global variable (had <code>$</code> prefix).
   * 
   * @param name The name of the variable, with its <code>$</code> prefix removed.
   */
  public abstract DCEntity loadGlobal(String name);

  /**
   * Load the last result of a normal variable (had <code>^</code> prefix).
   * 
   * @param name The name of the variable, with its <code>^</code> prefix removed.
   */
  public abstract DCEntity lastResult(String name);

  /**
   * Save a normal variable (no prefix).
   * 
   * @param name The name of the variable.
   * @param ent  The entity to store in the variable.
   */
  public abstract void save(String name, DCEntity ent);

  /**
   * Save an environment variable (had <code>_</code> prefix).
   * 
   * @param name The name of the variable, with its <code>_</code> prefix removed.
   * @param ent  The entity to store in the variable.
   */
  public abstract void saveEnv(String name, DCEntity ent);

  /**
   * Save a global variable (<code>$</code> prefix).
   * 
   * @param name The name of the variable, with its <code>$</code> prefix removed.
   * @param ent  The entity to store in the variable.
   */
  public abstract void saveGlobal(String name, DCEntity ent);

  /**
   * Save the result of a function variable.
   * 
   * @param name   The name of the variable.
   * @param result The result to store in that variable.
   */
  public abstract void saveResult(String name, DCValue result);
}