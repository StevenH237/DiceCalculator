package net.nixill.dice.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import net.nixill.dice.defaults.functions.DieCoinFunctions;
import net.nixill.dice.exception.DiceCalcException;
import net.nixill.dice.exception.NoSuchFunctionException;
import net.nixill.dice.objects.DCCodeFunction;
import net.nixill.dice.objects.DCEntity;

/**
 * A class that provides access to save and load {@link Function}s and
 * Variables.
 */
public class Functions {
  private static FunctionLoader loader;
  private static ThreadLocal<ArrayList<ArrayList<DCEntity>>> params;
  private static ThreadLocal<HashMap<String, String>> env;
  private static HashMap<String, DCCodeFunction> builtins;

  static {
    params = new ThreadLocal<ArrayList<ArrayList<DCEntity>>>() {
      @Override
      protected ArrayList<ArrayList<DCEntity>> initialValue() {
        return new ArrayList<>();
      }
    };

    env = new ThreadLocal<HashMap<String, String>>() {
      @Override
      protected HashMap<String, String> initialValue() {
        return new HashMap<>();
      }
    };

    builtins = new HashMap<>();
    builtins.put("!d", DieCoinFunctions.DIE);
    builtins.put("!c", DieCoinFunctions.COIN);
  }

  /**
   * Sets the {@link FunctionLoader} to use for loading functions.
   * 
   * @param loader The loader to use.
   */
  public static void setLoader(FunctionLoader loader) {
    Functions.loader = loader;
  }

  private static ArrayList<ArrayList<DCEntity>> getStack() {
    return params.get();
  }

  /**
   * Sets numbered function parameters to a given list, hiding the current ones
   * under it.
   * 
   * @param pars The list to put down on the stack.
   */
  public static void stackParams(ArrayList<DCEntity> pars) {
    getStack().add(0, pars);
  }

  /**
   * Removes the most recent list of function parameters, expositing the list
   * beneath it.
   */
  public static void unstackParams() {
    ArrayList<ArrayList<DCEntity>> stack = getStack();
    if (stack.size() > 0) {
      stack.remove(0);
    }
  }

  /**
   * Gets the most recent list of function parameters, but leaves it atop the
   * stack.
   * 
   * @return The list from the top of the stack.
   */
  public static ArrayList<DCEntity> getParams() {
    ArrayList<ArrayList<DCEntity>> stack = getStack();
    if (stack.size() > 0) {
      return stack.get(0);
    } else {
      return null;
    }
  }

  /**
   * Get a variable with a given name.
   * 
   * @param name The name to get.
   * @return The value of that variable.
   */
  public static DCEntity get(String name) {
    // All function names must be lowercase
    name = name.toLowerCase();

    // Built-in functions
    if (name.startsWith("!")) {
      DCCodeFunction func = builtins.get(name);
      if (func != null) {
        return func;
      } else {
        throw new DiceCalcException(new NoSuchFunctionException("The function `" + name + "` does not exist."));
      }
    }

    // Function parameters
    else if (name.matches("\\d+")) {
      int i = Integer.parseInt(name) - 1;
      ArrayList<DCEntity> pars = params.get().get(0);
      if (pars.size() > i) {
        return pars.get(i);
      } else {
        throw new IndexOutOfBoundsException("The current function doesn't have " + (i + 1) + " parameters.");
      }
    }

    // Environment variables
    else if (name.startsWith("_")) {
      return loader.loadEnv(name.substring(1));
    }

    // Global variables
    else if (name.startsWith("$")) {
      return loader.loadGlobal(name.substring(1));
    }

    // Last results of variables
    else if (name.startsWith("^")) {
      return loader.lastResult(name.substring(1));
    }

    // User variables
    else {
      return loader.load(name);
    }
  }

  /**
   * Save a value to a variable.
   * 
   * @param name The name of the variable to save.
   * @param ent  The entity to save to that variable. Use <code>null</code> to
   *             remove.
   */
  public static void save(String name, DCEntity ent) {
    // All function names must be lowercase
    name = name.toLowerCase();

    // Built-in functions
    if (name.startsWith("!")) {
      throw new DiceCalcException(new IllegalArgumentException("Built-in functions can't be overwritten."));
    }

    // Function parameters
    else if (name.matches("\\d+")) {
      throw new DiceCalcException(new IllegalArgumentException("Function parameters can't be overwritten."));
    }

    // Environment variables
    else if (name.startsWith("_")) {
      throw new DiceCalcException(new IllegalArgumentException("Environment variables can't be overwritten."));
    }

    // Global variables
    else if (name.startsWith("$")) {
      loader.saveGlobal(name.substring(1), ent);
    }

    // Last results of variables
    else if (name.startsWith("^")) {
      throw new DiceCalcException(new IllegalArgumentException("Functions' last results can't be overwritten."));
    }

    // User variables
    else {
      loader.save(name, ent);
    }
  }

  /**
   * Saves a variable; this method allows saving environment variables.
   * 
   * @param name The name to save
   * @param ent  The entity to save
   */
  public static void save2(String name, DCEntity ent) {
    name = name.toLowerCase();

    if (name.startsWith("_")) {
      loader.saveEnv(name.substring(1), ent);
    } else {
      save(name, ent);
    }
  }

  /**
   * Gets the value of a thread variable.
   * 
   * @param name The name of the variable to retrieve.
   * @return The value of the named variable.
   */
  public String getThread(String name) {
    return env.get().get(name);
  }

  /**
   * Sets the value of a thread variable.
   * 
   * @param name  The name of the variable to set.
   * @param value The value to set.
   * @return The previous value, or <code>null</code> if none.
   */
  public String setThread(String name, String value) {
    return env.get().put(name, value);
  }
}