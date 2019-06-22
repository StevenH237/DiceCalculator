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
  public static ArrayList<ExpressionPiece> split(String input) {
    ArrayList<ExpressionPiece> out = new ArrayList<>();
    String last = "";
    int pos = 0;

    // These regexes give individual pieces of the expression
    Matcher mtcNumber = Pattern.compile("(0|1-9\\d*)(\\.\\d+)?").matcher("");
    Matcher mtcOperator = Pattern.compile("[a-z\\<\\>\\/\\?\\|\\~\\!\\#\\$\\%\\^\\&\\*\\-\\=\\+]+").matcher("");
    Matcher mtcSeparator = Pattern.compile("[\\(\\)\\[\\]\\,]").matcher("");
    Matcher mtcName = Pattern.compile("\\{\\$?[a-z][a-z\\_\\-0-9]*[a-z0-9]\\}").matcher("");

    while (!input.isEmpty()) {
      int add = 0;
      
      // See if it's a number first
      mtcNumber.reset(input);
      if (mtcNumber.lookingAt()) {
        last = mtcNumber.group();
        out.add(new ExpressionPiece(last, ExpressionPieceType.NUMBER, pos));
        add = mtcNumber.end();
      }

      // A separator second
      mtcSeparator.reset(input);
      if (mtcSeparator.lookingAt()) {
        last = mtcSeparator.group();
        out.add(new ExpressionPiece(mtcSeparator.group(), ExpressionPieceType.BRACKET, pos));
        add = mtcSeparator.end();
      }

      // A name third
      mtcName.reset(input);
      if (mtcName.lookingAt()) {
        last = mtcName.group();
        out.add(new ExpressionPiece(mtcName.group(), ExpressionPieceType.NAME, pos));
        add = mtcName.end();
      }

      // And lastly, an operator (or combination thereof)
      mtcOperator.reset(input);
      if (mtcOperator.lookingAt()) {
        last = mtcOperator.group();
        String opers = mtcOperator.group();
        add = mtcName.end();

        String next;
        if (input.length() == add) {
          next = "";
        } else {
          next = input.substring(add, 1);
        }

        boolean prefix = (last.equals("(") || last.equals("[") || last.equals(",") || last.equals(""));
        boolean postfix = (next.equals(")") || next.equals("]") || next.equals(",") || next.equals(""));

        if (prefix && postfix) {
          throw new UserInputError("A number was expected here.", pos);
        }
        
        List<ExpressionPiece> pcs = Operators.getPieces(opers, prefix, postfix, pos);
      }
    
      input = input.substring(add);
      pos += add;
        
    }

    return out;
  }
}