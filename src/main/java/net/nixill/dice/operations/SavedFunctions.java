package net.nixill.dice.operations;

import java.util.ArrayList;
import java.util.function.Function;

import net.nixill.dice.objects.DCEntity;

/**
 * A class that provides access to save and load {@link Function}s.
 */
public class SavedFunctions {
  private static FunctionLoader loader;
  private static ThreadLocal<ArrayList<ArrayList<DCEntity>>> params;

  public static void setLoader(FunctionLoader loader) {
    SavedFunctions.loader = loader;
  }

  private static ArrayList<ArrayList<DCEntity>> getStack() {
    return params.get();
  }

  public static void stackParams(ArrayList<DCEntity> pars) {
    getStack().add(pars);
  }

  public static void unstackParams() {
    ArrayList<ArrayList<DCEntity>> stack = getStack();
    if (stack.size() > 0) {
      stack.remove(stack.size() - 1);
    }
  }
}