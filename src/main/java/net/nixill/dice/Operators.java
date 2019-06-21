package net.nixill.dice;

import java.util.HashMap;
import java.util.regex.Matcher;

public class Operators {
  private static HashMap<String, Operation> prefixOperators;
  private static HashMap<String, Operation> postfixOperators;
  private static HashMap<String, Operation> binaryOperators;

  static {
    prefixOperators = new HashMap<>();
    postfixOperators = new HashMap<>();
    binaryOperators = new HashMap<>();

    // for debug purposes, we'll just use null values
    // we're just trying to build the expressions right now, not run them
    prefixOperators.put("-", null);

    postfixOperators.put("!", null);

    binaryOperators.put("+", null);
    binaryOperators.put("-", null);
    binaryOperators.put("*", null);
    binaryOperators.put("/", null);


  }

  private static String keysToPattern(HashMap<String, ?> map) {
    String out = "";
    for (String str : map.keySet()) {
      out += "|" + str.replaceAll("[^a-zA-Z0-9]", Matcher.quoteReplacement("\\$1"));
    }

    out = "(" + out.substring(1) + ")";
    return out;
  }
}