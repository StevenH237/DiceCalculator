package net.nixill.dice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.nixill.dice.ExpressionPiece.ExpressionPieceType;

public class Operators {
  private static HashMap<String, Operation> prefixOperators;
  private static HashMap<String, Operation> postfixOperators;
  private static HashMap<String, Operation> binaryOperators;
  
  public final static String prefixRegex;
  public final static String postfixRegex;
  public final static String binaryRegex;
  public final static String combinedRegex;

  public final static Pattern ptnPrefix;
  public final static Pattern ptnPostfix;
  public final static Pattern ptnCombined;

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

    prefixRegex = keysToPattern(prefixOperators);
    postfixRegex = keysToPattern(postfixOperators);
    binaryRegex = keysToPattern(binaryOperators);
    combinedRegex = "(" + prefixRegex + "*)" + binaryRegex + "(" + postfixRegex + "*)";

    ptnPrefix = Pattern.compile(prefixRegex);
    ptnPostfix = Pattern.compile(postfixRegex);
    ptnCombined = Pattern.compile(combinedRegex);
  }

  private static String keysToPattern(HashMap<String, ?> map) {
    String out = "";
    for (String str : map.keySet()) {
      out += "|" + str.replaceAll("[^a-zA-Z0-9]", Matcher.quoteReplacement("\\$1"));
    }

    out = "(" + out.substring(1) + ")";
    return out;
  }
  
  public static List<ExpressionPiece> getOpers(String opers, boolean prefix, boolean postfix, int startPos) {
    ArrayList<ExpressionPiece> out = new ArrayList<>();
    
    Matcher mtcPrefix = null;
    Matcher mtcPostfix = null;
    String midString = null;
    
    if (!(prefix || postfix)) {
      Matcher mtcCombined = ptnCombined.matcher(opers);
      if (mtcCombined.matches()) {
        mtcPrefix = ptnPrefix.matcher(mtcCombined.group(1));
        mtcPostfix = ptnPostfix.matcher(mtcCombined.group(4));
        midString = mtcCombined.group(3);
      } else {
        throw new UserInputException("Operator " + opers + " isn't recognized.", startPos);
      }
    } else {
      if (prefix) {
        mtcPrefix = ptnPrefix.matcher(opers);
      } else {
        mtcPostfix = ptnPostfix.matcher(opers);
      }
    }

    return out;
  }
}