package net.nixill.dice.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.nixill.dice.operations.Functions;
import net.nixill.dice.parsing.UserInputException;

public class DCFunction extends DCExpression {
  private ArrayList<DCEntity> params;
  private String name;

  public DCFunction(String name, Collection<DCEntity> params) {
    this.name = name;
    this.params = new ArrayList<>(params);
  }

  @Override
  public DCValue getValue() {
    HashMap<String, DCEntity> funcs = Functions.getAllMerged();
    
    DCEntity ent = funcs.get(name);

    if (ent == null) {
      try {
        Integer.parseInt(name);
        if (params.size() >= 1) {
          ent = params.get(0);
        } else {
          throw new UserInputException("Function doesn't have " + name + " param(s).", -1);
        }
      } catch (NumberFormatException ex) {
        throw new UserInputException("Unknown function or variable " + name, -1);
      }
    }

    HashMap<String, DCEntity> pars = new HashMap<>();
    for (int i = 0; i < params.size(); i++) {
      pars.put(i + 1 + "", params.get(i));
    }

    HashMap<String, DCEntity> preFuncs = Functions.setFunctions(pars);
    DCValue val = ent.getValue();
    Functions.setFunctions(preFuncs);

    return ent.getValue();
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
  
  public List<DCEntity> getParams() {
    return Collections.unmodifiableList(params);
  }

  public void printTree(int level) {
    printSpaced(level, "Function \"" + name + "\": " + params.size() + " param(s)");
    for (DCEntity ent : params) {
      ent.printTree(level + 1);
    }
  }
}