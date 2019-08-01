package net.nixill.dice.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.nixill.dice.exception.DiceCalcException;
import net.nixill.dice.exception.NoSuchFunctionException;
import net.nixill.dice.operations.FunctionHistory;
import net.nixill.dice.operations.Functions;
import net.nixill.dice.operations.FunctionHistory.HistoryEntry;

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
    
    if (!(ent instanceof DCCodeFunction)) {
      FunctionHistory
          .add(new HistoryEntry(1, "{" + name + "} => " + ent.toCode()));
    }
    
    if (ent instanceof DCExpression) {
      Functions.stackParams(params);
      DCValue val = ent.getValue();
      Functions.unstackParams();
      
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
    DCEntity ent = null;
    
    try {
      ent = Functions.get(name);
    } catch (IndexOutOfBoundsException ex) {
      if (params.size() >= 1) {
        ent = params.get(0);
      } else {
        throw new DiceCalcException(ex);
      }
    }
    
    if (ent == null) {
      throw new NoSuchFunctionException(
          "The function `" + name + "` does not exist.");
    }
    
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
  
  /**
   * Returns the name of the function.
   * 
   * @return The name of the function.
   */
  public String getName() {
    return name;
  }
  
  @Override
  public String toString(int lvl) {
    String out = "{" + name;
    for (DCEntity ent : params) {
      out += ", " + ent.toString(lvl - 1);
    }
    return out + "}";
  }
  
  @Override
  public String toCode() {
    String out = "{" + name;
    for (DCEntity ent : params) {
      out += "," + ent.toCode();
    }
    return out + "}";
  }
  
  @Override
  public void printTree(int level) {
    printSpaced(level,
        "Function \"" + name + "\": " + params.size() + " param(s)");
    for (DCEntity ent : params) {
      ent.printTree(level + 1);
    }
  }
}