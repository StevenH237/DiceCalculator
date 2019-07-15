package net.nixill.dice.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.nixill.dice.exception.UserInputException;
import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.parsing.ExpressionPiece.ExpressionPieceType;

/**
 * A class that takes an input string and splits it into a list of
 * {@link ExpressionPiece}s.
 */
public class ExpressionSplitter {

  /**
   * Runs {@link #split} and {@link ExpressionParser#parseLine} consecutively.
   */
  public static DCEntity parse(String input) {
    input = input.toLowerCase();
    input = input.replaceAll("[ `]", "");

    ArrayList<ExpressionPiece> stack = split(input);
    DCEntity ent = ExpressionParser.parseLine(stack);

    return ent;
  }

  /**
   * Separates the string into a list of strings such that each string is one of:
   * <ul>
   * <li>A single parenthesis or bracket</li>
   * <li>A name in braces</li>
   * <li>A number</li>
   * <li>An operator</li>
   * </ul>
   * <p>
   * &nbsp;
   * 
   * @param input The string to split
   * @return The split string
   */
  public static ArrayList<ExpressionPiece> split(String input) {
    ArrayList<ExpressionPiece> out = new ArrayList<>();
    String last = "";
    int pos = 0;

    // These regexes give individual pieces of the expression
    Matcher mtcNumber = Pattern.compile("(0|[1-9]\\d*)(\\.\\d+)?").matcher("");
    Matcher mtcOperator = Pattern.compile("[a-z\\<\\>\\/\\?\\|\\~\\!\\#\\$\\%\\^\\&\\*\\-\\=\\+ `\\t\\n]+").matcher("");
    Matcher mtcSeparator = Pattern.compile("[\\(\\)\\[\\]\\,\\}]").matcher("");
    Matcher mtcName = Pattern.compile("\\{([\\$\\_\\^]?[a-zA-Z][a-zA-Z\\_\\-0-9]*[a-zA-Z0-9]|\\d+|![dcDC])")
        .matcher("");
    Matcher mtcString = Pattern.compile("\\\"([^\\\\\\\"]|\\\\[^A-Za-z0-9])*\\\"").matcher("");
    Matcher mtcWhitespace = Pattern.compile("[ `\\t\\n]").matcher("");

    while (!input.isEmpty()) {
      int add = 0;

      // this feels like bad practice but fuck it
      while (true) {
        // See if it's whitespace before any tokens
        mtcWhitespace.reset(input);
        if (mtcWhitespace.lookingAt()) {
          // don't change last
          // don't add to out
          add = mtcWhitespace.end();

          break;
        }

        // See if it's a number first
        mtcNumber.reset(input);
        if (mtcNumber.lookingAt()) {
          last = mtcNumber.group();
          out.add(new ExpressionPiece(last, ExpressionPieceType.NUMBER, pos));
          add = mtcNumber.end();

          break;
        }

        // A separator second
        mtcSeparator.reset(input);
        if (mtcSeparator.lookingAt()) {
          last = mtcSeparator.group();
          out.add(new ExpressionPiece(mtcSeparator.group(), ExpressionPieceType.BRACKET, pos));
          add = mtcSeparator.end();

          break;
        }

        // A name third
        mtcName.reset(input);
        if (mtcName.lookingAt()) {
          last = mtcName.group();
          out.add(new ExpressionPiece(mtcName.group(), ExpressionPieceType.NAME, pos));
          add = mtcName.end();

          break;
        }

        // A string fourth
        mtcString.reset(input);
        if (mtcString.lookingAt()) {
          last = mtcString.group();
          out.add(new ExpressionPiece(mtcString.group(), ExpressionPieceType.STRING, pos));
          add = mtcString.end();

          break;
        }

        // And lastly, an operator (or combination thereof)
        mtcOperator.reset(input);
        if (mtcOperator.lookingAt()) {
          String opers = mtcOperator.group().replaceAll("[ \\t\\n`]", "");
          add = mtcOperator.end();

          String next;
          if (input.length() == add) {
            next = "";
          } else {
            next = input.substring(add, add + 1);
          }

          // If the operator immediately follows an opening bracket, it can
          // only be a prefix operator.
          boolean prefix = (last.equals("(") || last.equals("[") || last.equals(",") || last.equals(""));
          // If the operator immediately precedes a closing bracket, it can
          // only be a postfix operator.
          boolean postfix = (next.equals(")") || next.equals("]") || next.equals(",") || next.equals(""));

          // An operator can't be both (i.e. the only thing between two
          // brackets).
          if (prefix && postfix) {
            throw new UserInputException("A number was expected here.", pos);
          }

          // Multiple operators might be in a row, so get them all.
          List<ExpressionPiece> pcs = Operators.getOpers(opers, prefix, postfix, pos);
          out.addAll(pcs);

          last = mtcOperator.group();

          break;
        }

        // If no match and it reaches this point:
        throw new UserInputException("I don't know what this means.", pos);
      }

      input = input.substring(add);
      pos += add;
    }

    return out;
  }
}