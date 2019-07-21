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
 * A class that provides access to save and load {@link Function}s.
 */
public class Variables {
  private static VariableLoader loader;
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

  public static void setLoader(VariableLoader loader) {
    Variables.loader = loader;
  }

  private static ArrayList<ArrayList<DCEntity>> getStack() {
    return params.get();
  }

  public static void stackParams(ArrayList<DCEntity> pars) {
    getStack().add(0, pars);
  }

  public static void unstackParams() {
    ArrayList<ArrayList<DCEntity>> stack = getStack();
    if (stack.size() > 0) {
      stack.remove(0);
    }
  }

  public static ArrayList<DCEntity> getParams() {
    ArrayList<ArrayList<DCEntity>> stack = getStack();
    if (stack.size() > 0) {
      return stack.get(0);
    } else {
      return null;
    }
  }

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

  public static void save2(String name, DCEntity ent) {
    name = name.toLowerCase();

    if (name.startsWith("_")) {
      loader.saveEnv(name.substring(1), ent);
    } else {
      save(name, ent);
    }
  }

  public String getEnv(String name) {
    return env.get().get(name);
  }

  public String setEnv(String name, String value) {
    return env.get().put(name, value);
  }
}