package net.nixill.dice.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.nixill.dice.operations.SavedFunctions;

/**
 * A named function, with or without parameters.
 * <p>
 * The object also refers to saved variables (i.e. a value stored under the
 * given name) or function parameters (i.e. a function whose name is just a
 * number).
 */
public class DCFunction extends DCExpression {
  private ArrayList<DCEntity> params;
  private String              name;
  
  /**
   * Create a new DCFunction with its parameter entities.
   * 
   * @param name
   *   The function's name
   * @param params
   *   The function's params.
   */
  public DCFunction(String name, List<DCEntity> params) {
    this.name = name;
    this.params = new ArrayList<>(params);
  }
  
  /**
   * Runs the function and returns its final value.
   * <p>
   * This operation gets the entity named by this function, then gets the
   * value of that entity.
   * <p>
   * To simply retrieve the named entity, use {@link #getSaved()}.
   * 
   * @return The value of the named entity
   * @see #getSaved()
   */
  @Override
  public DCValue getValue() {
    DCEntity ent = getSaved();
    
    if (ent instanceof DCExpression) {
      SavedFunctions.stackParams(params);
      DCValue val = ent.getValue();
      SavedFunctions.unstackParams();
      
      return val;
    } else {
      return ent.getValue();
    }
  }
  
  /**
   * Gets the entity named by this function.
   * 
   * @return The named entity
   */
  public DCEntity getSaved() {
    DCEntity ent = SavedFunctions.get(name);
    
    return ent;
  }
  
  /**
   * Returns the parameters passed into the function.
   * 
   * @return The parameters passed into the function.
   */
  public List<DCEntity> getParams() {
    return Collections.unmodifiableList(params);
  }
  
  @Override
  public String toString() {
    String out = "{" + name;
    for (DCEntity ent : params) {
      out += "," + ent.toShortString();
    }
    return out + "}";
  }
  
  @Override
  public String toShortString() {
    return "{" + name + "," + params.size() + " params}";
  }
  
  @Override
  public String toLongString() {
    String out = "{" + name;
    for (DCEntity ent : params) {
      out += "," + ent.toLongString();
    }
    return out + "}";
  }
  
  public void printTree(int level) {
    printSpaced(level,
        "Function \"" + name + "\": " + params.size() + " param(s)");
    for (DCEntity ent : params) {
      ent.printTree(level + 1);
    }
  }
}