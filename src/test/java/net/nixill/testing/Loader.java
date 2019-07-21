package net.nixill.testing;

import net.nixill.dice.exception.NoSuchFunctionException;
import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCNumber;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.operations.VariableLoader;
import net.nixill.dice.parsing.ExpressionSplitter;

public class Loader extends VariableLoader {
  private static FunctionState LEVEL = new FunctionState(ExpressionSplitter.parse("({1,5}^2+{1,5})/2"));
  private static FunctionState _LAST = new FunctionState(new DCNumber(0));
  private static FunctionState _ANS = new FunctionState(new DCNumber(0));
  private static FunctionState _U = null;

  private FunctionState loadState(String name) {
    if (name.equals("level")) {
      return LEVEL;
    } else if (name.equals("_last")) {
      return _LAST;
    } else if (name.equals("_ans")) {
      return _ANS;
    } else if (name.equals("_u")) {
      return _U;
    }

    throw new NoSuchFunctionException("There's no function named " + name + ".");
  }

  public DCEntity load(String name) {
    return loadState(name).function;
  }

  public DCEntity lastResult(String name) {
    return loadState(name).lastResult;
  }

  public void save(String name, DCEntity ent) {
    if (name.equals("level")) {
      LEVEL = new FunctionState(ent);
      return;
    } else if (name.equals("_last")) {
      _LAST = new FunctionState(ent);
      return;
    } else if (name.equals("_ans")) {
      _ANS = new FunctionState(ent);
      return;
    } else if (name.equals("_u")) {
      _U = new FunctionState(ent);
      return;
    }

    throw new NoSuchFunctionException("This loader can't save functions of your own choosing.");
  }

  public void saveResult(String name, DCValue result) {
    loadState(name).lastResult = result;
  }

  private static class FunctionState {
    private final DCEntity function;
    private DCValue lastResult;

    private FunctionState(DCEntity func) {
      function = func;
    }

    private FunctionState(DCEntity func, DCValue last) {
      function = func;
      lastResult = last;
    }
  }

  @Override
  public DCEntity loadEnv(String name) {
    return loadState("_" + name).function;
  }

  @Override
  public DCEntity loadGlobal(String name) {
    return loadState("$" + name).function;
  }

  @Override
  public void saveGlobal(String name, DCEntity ent) {
    save("$" + name, ent);
  }

  @Override
  public void saveEnv(String name, DCEntity ent) {
    save("_" + name, ent);
  }
}