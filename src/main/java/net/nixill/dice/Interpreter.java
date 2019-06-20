package net.nixill.dice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.nixill.dice.ExpressionPiece.ExpressionPieceType;

public class Interpreter {

  public static Expression parse(String input) {
    input = input.toLowerCase();
    input = input.replaceAll("[ `]", "");

    ArrayList<ExpressionPiece> stack = split(input);
    
    return null; //TODO fix stub
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
   * @param input The string to split
   * @return The split string
   */
  private static ArrayList<ExpressionPiece> split(String input) {
    ArrayList<ExpressionPiece> out = new ArrayList<>();
    String last = "";

    // These regexes give individual pieces of the expression
    Matcher mtcNumber = Pattern.compile("(0|1-9\\d*)(\\.\\d+)?").matcher("");
    Matcher mtcOperator = Pattern.compile("[a-z\\<\\>\\/\\?\\|\\~\\!\\#\\$\\%\\^\\&\\*\\-\\=\\+]+").matcher("");
    Matcher mtcSeparator = Pattern.compile("[\\(\\)\\[\\]\\,]").matcher("");
    Matcher mtcEnder = Pattern.compile("[\\@\\:\\']").matcher("");
    Matcher mtcName = Pattern.compile("\\{\\$?[a-z][a-z\\_\\-0-9]*[a-z0-9]\\}").matcher("");

    while (!input.isEmpty()) {
      // See if it's a number first
      mtcNumber.reset(input);
      if (mtcNumber.lookingAt()) {
        out.add(new ExpressionPiece(mtcNumber.group(), ExpressionPieceType.NUMBER));
        input = input.substring(mtcNumber.end());
      }

      // A separator second
      mtcSeparator.reset(input);
      if (mtcSeparator.lookingAt()) {
        out.add(new ExpressionPiece(mtcNumber.group(), ExpressionPieceType.BRACKET));
        input = input.substring(mtcSeparator.end());
      }


    }

    return out;
  }
}