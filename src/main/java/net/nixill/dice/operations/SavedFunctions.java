package net.nixill.dice.operations;

import java.util.ArrayList;
import java.util.function.Function;

import net.nixill.dice.objects.DCEntity;

/**
 * A class that stores saved {@link Function}s per thread and allows them
 * to be accessed statically.
 */
public class SavedFunctions {
  private static FunctionLoader                              loader;
  private static ThreadLocal<ArrayList<ArrayList<DCEntity>>> params;
  
  public static void setLoader(FunctionLoader loader) {
    SavedFunctions.loader = loader;
  }
  
}