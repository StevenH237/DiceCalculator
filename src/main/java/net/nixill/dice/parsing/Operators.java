package net.nixill.dice.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.MathsOperators;
import net.nixill.dice.operations.PostfixOperator;
import net.nixill.dice.operations.PrefixOperator;
import net.nixill.dice.parsing.ExpressionPiece.ExpressionPieceType;

public class Operators {
  private static HashMap<String, PrefixOperator<?>> prefixOperators;
  private static HashMap<String, PostfixOperator<?>> postfixOperators;
  private static HashMap<String, BinaryOperator<?>> binaryOperators;
  
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

    prefixOperators.put("-", MathsOperators.NEGATIVE);

    postfixOperators.put("!", MathsOperators.FACTORIAL);

    binaryOperators.put("+", MathsOperators.PLUS);
    binaryOperators.put("-", MathsOperators.MINUS);
    binaryOperators.put("*", MathsOperators.TIMES);
    binaryOperators.put("/", MathsOperators.DIVIDE);
    binaryOperators.put("^", MathsOperators.POWER);

    prefixRegex = keysToPattern(prefixOperators);
    postfixRegex = keysToPattern(postfixOperators);
    binaryRegex = keysToPattern(binaryOperators);
    combinedRegex = "(" + postfixRegex + "*)" + binaryRegex + "(" + prefixRegex + "*)";

    ptnPrefix = Pattern.compile(prefixRegex);
    ptnPostfix = Pattern.compile(postfixRegex);
    ptnCombined = Pattern.compile(combinedRegex);
  }

  private static String keysToPattern(HashMap<String, ?> map) {
    String out = "";
    for (String str : map.keySet()) {
      out += "|" + str.replaceAll("([^a-zA-Z0-9])", Matcher.quoteReplacement("\\") + "$1");
    }

    out = "(" + out.substring(1) + ")";
    return out;
  }
  
  public static List<ExpressionPiece> getOpers(String opers, boolean prefix, boolean postfix, int startPos) {
    ArrayList<ExpressionPiece> out = new ArrayList<>();

    int pos = startPos;
    
    Matcher mtcPrefix = null;
    Matcher mtcPostfix = null;
    String midString = null;
    
    // Split the operator string into postfixes, in-between, and prefixes.
    if (!(prefix || postfix)) {
      Matcher mtcCombined = ptnCombined.matcher(opers);
      if (mtcCombined.matches()) {
        mtcPostfix = ptnPostfix.matcher(mtcCombined.group(1));
        mtcPrefix = ptnPrefix.matcher(mtcCombined.group(4));
        midString = mtcCombined.group(3);
      } else {
        throw new UserInputException("Operator " + opers + " isn't recognized.", startPos);
      }
    } else {
      if (postfix) {
        mtcPostfix = ptnPostfix.matcher(opers);
      } else {
        mtcPrefix = ptnPrefix.matcher(opers);
      }
    }

    // Get the postfixes first
    if (postfix || !prefix) {
      pos = getMultiOpers(out, mtcPostfix, pos, true);
    }

    if (!postfix && !prefix) {
      out.add(new ExpressionPiece(midString, ExpressionPieceType.BINARY_OPERATOR, pos));
      pos += midString.length();
    }

    if (prefix || !postfix) {
      getMultiOpers(out, mtcPrefix, pos, false);
    }

    return out;
  }

  private static int getMultiOpers(List<ExpressionPiece> list, Matcher matcher, int pos, boolean post) {
    while (matcher.lookingAt()) {
      list.add(new ExpressionPiece(matcher.group(),
        (post)?ExpressionPieceType.POSTFIX_OPERATOR:
          ExpressionPieceType.PREFIX_OPERATOR,
        pos));
      pos += matcher.end();
      matcher.region(matcher.end(), matcher.regionEnd());
    }
    return pos;
  }

  public static BinaryOperator<?> getBinaryOperator(String oper) {
    return binaryOperators.get(oper);
  }

  public static PrefixOperator<?> getPrefixOperator(String oper) {
    return prefixOperators.get(oper);
  }

  public static PostfixOperator<?> getPostfixOperator(String oper) {
    return postfixOperators.get(oper);
  }
}