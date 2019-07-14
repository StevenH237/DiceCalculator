package net.nixill.testing;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.operations.FunctionLoader;

public class Loader extends FunctionLoader {
  private final static FunctionState LEVEL =

  public DCEntity load(String name) {
    return null;
  }
  
  public DCEntity lastResult(String name) {
    return null;
  }
  
  public void save(String name, DCEntity ent) {
    
  }
  
  public void saveResult(String name, DCValue result) {
    
  }
  
  private static class FunctionState {
    private final DCEntity function;
    private DCValue        lastResult;
    
    private FunctionState(DCEntity func) {
      function = func;
    }
    
    private FunctionState(DCEntity func, DCValue last) {
      function = func;
      lastResult = last;
    }
  }
}