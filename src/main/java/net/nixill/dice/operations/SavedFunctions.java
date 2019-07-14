package net.nixill.dice.operations;

import java.util.ArrayList;
import java.util.function.Function;

import net.nixill.dice.exception.DiceCalcException;
import net.nixill.dice.objects.DCEntity;

/**
 * A class that provides access to save and load {@link Function}s.
 */
public class SavedFunctions {
  private static FunctionLoader                              loader;
  private static ThreadLocal<ArrayList<ArrayList<DCEntity>>> params;
  
  public static void setLoader(FunctionLoader loader) {
    SavedFunctions.loader = loader;
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
  
  public static DCEntity get(String name) {
    try {
      int i = Integer.parseInt(name) - 1;
      ArrayList<DCEntity> pars = params.get().get(0);
      if (pars.size() > i) {
        return pars.get(i);
      } else {
        throw new DiceCalcException(new IndexOutOfBoundsException(
            "The current function doesn't have " + (i + 1)
                + " parameters."));
      }
    } catch (NumberFormatException ex) {
      return loader.load(name);
    }
  }
}