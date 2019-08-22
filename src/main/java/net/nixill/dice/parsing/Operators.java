package net.nixill.dice.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.nixill.dice.defaults.operations.DiceOperators;
import net.nixill.dice.defaults.operations.KeepDropOperators;
import net.nixill.dice.defaults.operations.ListOperators;
import net.nixill.dice.defaults.operations.MathsOperators;
import net.nixill.dice.exception.UserInputException;
import net.nixill.dice.operations.BinaryOperator;
import net.nixill.dice.operations.ComparisonOperators;
import net.nixill.dice.operations.Operator;
import net.nixill.dice.operations.PostfixOperator;
import net.nixill.dice.operations.PrefixOperator;
import net.nixill.dice.parsing.ExpressionPiece.ExpressionPieceType;

/**
 * This class is used during parsing to get {@link Operator}s from their
 * symbols and separate multiple consecutive operators.
 */
public class Operators {
  private static HashMap<String, PrefixOperator<?>>  prefixOperators;
  private static HashMap<String, PostfixOperator<?>> postfixOperators;
  private static HashMap<String, BinaryOperator<?>>  binaryOperators;
  
  private static String prefixRegex;
  private static String postfixRegex;
  private static String binaryRegex;
  private static String combinedRegex;
  
  private static Pattern ptnPrefix;
  private static Pattern ptnPostfix;
  private static Pattern ptnCombined;
  
  static {
    prefixOperators = new HashMap<>();
    postfixOperators = new HashMap<>();
    binaryOperators = new HashMap<>();
    
    putOperator(ListOperators.NEGATIVE);
    putOperator(ListOperators.JOIN);
    putOperator(ListOperators.NEG_JOIN);
    putOperator(ListOperators.SIZE);
    putOperator(ListOperators.SELECT);
    putOperator(ListOperators.SHUFFLE);
    putOperator(ListOperators.SUM);
    
    putOperator(MathsOperators.FACTORIAL);
    putOperator(MathsOperators.TIMES);
    putOperator(MathsOperators.DIVIDE);
    putOperator(MathsOperators.POWER);
    putOperator(MathsOperators.INT_DIVIDE);
    putOperator(MathsOperators.MODULO);
    putOperator(MathsOperators.DUO_DIVIDE);
    
    putOperator(DiceOperators.DICE);
    putOperator(DiceOperators.ONE_DIE);
    putOperator(DiceOperators.ROLL_UNTIL);
    putOperator(DiceOperators.PICK);
    putOperator(DiceOperators.PICK_ONE);
    putOperator(DiceOperators.PICK_REPLACE);
    
    putOperator(KeepDropOperators.DROP_HIGHEST);
    putOperator(KeepDropOperators.DROP_LOWEST);
    putOperator(KeepDropOperators.KEEP_FIRST);
    putOperator(KeepDropOperators.KEEP_HIGHEST);
    putOperator(KeepDropOperators.KEEP_LOWEST);
    
    initRegexes();
  }
  
  private static String keysToPattern(HashMap<String, ?> map) {
    String out = "";
    for (String str : map.keySet()) {
      out += "|" + str.replaceAll("([^a-zA-Z0-9])",
          Matcher.quoteReplacement("\\") + "$1");
    }
    
    out = "(" + out.substring(1) + ")";
    return out;
  }
  
  /**
   * Gets the consecutive operators from a single string.
   * 
   * @param opers
   *   The string of consecutive operators.
   * @param prefix
   *   Whether the operators can only be prefixes.
   * @param postfix
   *   Whether the operators can only be postfixes.
   * @param startPos
   *   The index of the first character, in case of
   *   {@link UserInputException}s.
   * @return The list of operators, as {@link ExpressionPiece}s, found in
   * the string.
   */
  public static List<ExpressionPiece> getOpers(String opers,
      boolean prefix, boolean postfix, int startPos) {
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
        throw new UserInputException(
            "Operator " + opers + " isn't recognized.", startPos);
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
      out.add(new ExpressionPiece(midString,
          ExpressionPieceType.BINARY_OPERATOR, pos));
      pos += midString.length();
    }
    
    if (prefix || !postfix) {
      getMultiOpers(out, mtcPrefix, pos, false);
    }
    
    return out;
  }
  
  private static int getMultiOpers(List<ExpressionPiece> list,
      Matcher matcher, int pos, boolean post) {
    while (matcher.lookingAt()) {
      list.add(new ExpressionPiece(matcher.group(),
          (post) ? ExpressionPieceType.POSTFIX_OPERATOR
              : ExpressionPieceType.PREFIX_OPERATOR,
          pos));
      pos += matcher.end();
      matcher.region(matcher.end(), matcher.regionEnd());
    }
    return pos;
  }
  
  /**
   * Get a BinaryOperator by its symbol.
   * 
   * @param oper
   *   The symbol
   * @return The operator
   */
  public static BinaryOperator<?> getBinaryOperator(String oper) {
    return binaryOperators.get(oper);
  }
  
  /**
   * Get a PrefixOperator by its symbol.
   * 
   * @param oper
   *   The symbol
   * @return The operator
   */
  public static PrefixOperator<?> getPrefixOperator(String oper) {
    return prefixOperators.get(oper);
  }
  
  /**
   * Get a PostfixOperator by its symbol.
   * 
   * @param oper
   *   The symbol
   * @return The operator
   */
  public static PostfixOperator<?> getPostfixOperator(String oper) {
    return postfixOperators.get(oper);
  }
  
  /**
   * Add a BinaryOperator to the list of recognized operators.
   * 
   * @param oper
   *   The operator to add
   */
  public static void putOperator(BinaryOperator<?> oper) {
    binaryOperators.put(oper.getSymbol(), oper);
  }
  
  /**
   * Add all of a {@link ComparisonOperators}' set to the list of
   * recognized operators.
   * 
   * @param opers
   *   The operator set to add
   */
  public static void putOperator(ComparisonOperators<?> opers) {
    putOperator(opers.EQUAL);
    putOperator(opers.NOT_LESS);
    putOperator(opers.GREATER);
    putOperator(opers.NOT_EQUAL);
    putOperator(opers.LESS);
    putOperator(opers.NOT_GREATER);
    putOperator(opers.MODULO);
    putOperator(opers.NOT_MODULO);
  }
  
  /**
   * Add a PrefixOperator to the list of recognized operators.
   * 
   * @param oper
   *   The operator to add
   */
  public static void putOperator(PrefixOperator<?> oper) {
    prefixOperators.put(oper.getSymbol(), oper);
  }
  
  /**
   * Add a PostfixOperator to the list of recognized operators.
   * 
   * @param oper
   *   The operator to add
   */
  public static void putOperator(PostfixOperator<?> oper) {
    postfixOperators.put(oper.getSymbol(), oper);
  }
  
  /**
   * Re-initialize the internal regular expressions. Use this after adding
   * a new set of operators.
   */
  public static void initRegexes() {
    prefixRegex = keysToPattern(prefixOperators);
    postfixRegex = keysToPattern(postfixOperators);
    binaryRegex = keysToPattern(binaryOperators);
    combinedRegex = "(" + postfixRegex + "*)" + binaryRegex + "("
        + prefixRegex + "*)";
    
    ptnPrefix = Pattern.compile(prefixRegex);
    ptnPostfix = Pattern.compile(postfixRegex);
    ptnCombined = Pattern.compile(combinedRegex);
  }
}