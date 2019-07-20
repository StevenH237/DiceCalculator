package net.nixill.dice.operations;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;

public abstract class VariableLoader {
  public abstract DCEntity load(String name);
  
  public abstract DCEntity loadEnv(String name);
  
  public abstract DCEntity loadGlobal(String name);
  
  public abstract DCEntity lastResult(String name);
  
  public abstract void save(String name, DCEntity ent);
  
  public abstract void saveResult(String name, DCValue result);
  
  public abstract void saveGlobal(String name, DCEntity ent);
  
  public abstract void saveEnv(String name, DCEntity ent);
}