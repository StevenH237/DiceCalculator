package net.nixill.dice.parsing;

import java.util.ArrayDeque;
import java.util.ArrayList;

import net.nixill.dice.exception.UserInputException;
import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCExpression;
import net.nixill.dice.objects.DCFunction;
import net.nixill.dice.objects.DCListExpression;
import net.nixill.dice.objects.DCNumber;
import net.nixill.dice.objects.DCString;
import net.nixill.dice.operations.Operator;
import net.nixill.dice.operations.PostfixOperator;
import net.nixill.dice.operations.PrefixOperator;
import net.nixill.dice.parsing.ExpressionPiece.ExpressionPieceType;

/**
 * A class that takes a list of {@link ExpressionPiece}s and builds a tree of
 * {@link DCExpression}s from them.
 */
public class ExpressionParser {
  /**
   * Builds a {@link DCExpression} tree from a list of {@link ExpressionPiece}s
   * 
   * @param pieces The list of pieces from which to build
   * @return The root of the DCExpression tree
   * @throws UserInputException If there are two values not separated by an
   *                            operator, or unmatched brackets.
   */
  public static DCEntity parseLine(ArrayList<ExpressionPiece> pieces) {
    DCEntity ent = parseChain(pieces);
    if (!pieces.isEmpty()) {
      ExpressionPiece piece = pieces.get(0);
      throw new UserInputException("Unmatched " + piece.contents, piece.position);
    }
    return ent;
  }

  private static DCEntity parseParentheses(ArrayList<ExpressionPiece> pieces) {
    ExpressionPiece lpar = pieces.remove(0);
    DCEntity ent = parseChain(pieces);
    if (pieces.isEmpty()) {
      throw new UserInputException("Unmatched (", lpar.position);
    } else {
      ExpressionPiece rpar = pieces.remove(0);
      if (!rpar.contents.equals(")")) {
        throw new UserInputException("Unmatched ( and " + rpar.contents, lpar.position);
      }
    }

    return ent;
  }

  private static DCListExpression parseList(ArrayList<ExpressionPiece> pieces) {
    ExpressionPiece lbracket = pieces.remove(0);
    ArrayList<DCEntity> listItems = new ArrayList<>();

    // Allow empty lists
    ExpressionPiece next = pieces.get(0);
    if (next.contents.equals("]")) {
      pieces.remove(0);
      return new DCListExpression(listItems);
    }

    // Add items to non-empty lists
    while (!pieces.isEmpty()) {
      // First get the value
      DCEntity ent = parseChain(pieces);
      if (ent == null) {
        throw new UserInputException("List with empty value", pieces.get(0).position);
      }

      // Add it to the list of values
      listItems.add(ent);

      // Make sure we're not already at the end of the line
      if (pieces.isEmpty()) {
        throw new UserInputException("Unmatched [", lbracket.position);
      }

      // Then get the next "bracket"
      next = pieces.remove(0);

      // If it's the wrong kind of closing bracket, error.
      if (next.contents.equals(")") || next.contents.equals("}")) {
        throw new UserInputException("Unmatched [ and " + next.contents, lbracket.position);
      }

      // If it's a list closer, close the list.
      if (next.contents.equals("]")) {
        return new DCListExpression(listItems);
      }
    }

    throw new UserInputException("Unmatched [", lbracket.position);
  }

  private static DCFunction parseFunction(ArrayList<ExpressionPiece> pieces) {
    ExpressionPiece lbracket = pieces.remove(0);
    String name = lbracket.contents.substring(1).toLowerCase();
    ArrayList<DCEntity> params = new ArrayList<>();

    // Allow no-param functions
    ExpressionPiece next = pieces.get(0);
    if (next.contents.equals("}")) {
      pieces.remove(0);
      return new DCFunction(name, params);
    }

    if (next.contents.equals(",")) {
      pieces.remove(0);
    }

    // Add items to with-param functions
    while (!pieces.isEmpty()) {
      // First get the value
      DCEntity ent = parseChain(pieces);
      if (ent == null) {
        throw new UserInputException("Function with empty param", pieces.get(0).position);
      }

      // Add it to the list of params
      params.add(ent);

      // Make sure we're not already at the end of the line
      if (pieces.isEmpty()) {
        throw new UserInputException("Unmatched {", lbracket.position);
      }

      // Then get the next "bracket"
      next = pieces.remove(0);

      // If it's the wrong kind of closing bracket, error.
      if (next.contents.equals(")") || next.contents.equals("]")) {
        throw new UserInputException("Unmatched { and " + next.contents, lbracket.position);
      }

      // If it's a function closer, close the function.
      if (next.contents.equals("}")) {
        return new DCFunction(name, params);
      }
    }

    throw new UserInputException("Unmatched {", lbracket.position);
  }

  private static DCEntity parseChain(ArrayList<ExpressionPiece> pieces) {
    if (pieces.isEmpty()) {
      throw new UserInputException("Empty list received.", 0);
    }

    ArrayDeque<ExpressionBuilder> exps = new ArrayDeque<>();
    DCEntity hold = null;
    boolean valueLast = false;

    while (!pieces.isEmpty()) {
      ExpressionPiece piece = pieces.get(0);

      // Get the next value, if there is one
      DCEntity ent = null;
      boolean err = false;
      if (piece.type == ExpressionPieceType.BRACKET) {
        if (piece.contents.equals("(")) {
          if (!valueLast) {
            ent = parseParentheses(pieces);
          } else {
            err = true;
          }
        } else if (piece.contents.equals("[")) {
          if (!valueLast) {
            ent = parseList(pieces);
          } else {
            err = true;
          }
        } else /* ], ), }, , */ {
          break;
        }
      } else if (piece.type == ExpressionPieceType.NAME) {
        if (!valueLast) {
          ent = parseFunction(pieces);
        } else {
          err = true;
        }
      } else if (piece.type == ExpressionPieceType.NUMBER) {
        if (!valueLast) {
          ent = new DCNumber(Double.parseDouble(piece.contents));
          pieces.remove(0);
        } else {
          err = true;
        }
      } else if (piece.type == ExpressionPieceType.STRING) {
        if (!valueLast) {
          String check = piece.contents.substring(1, piece.contents.length() - 1);

          check = check.replace("\\\\", "\uF000").replace("\\", "").replace("\uF000", "\\");

          ent = new DCString(check);
          pieces.remove(0);
        } else {
          err = true;
        }
      }

      if (err) {
        throw new UserInputException("Two consecutive values", piece.position);
      }

      // If there is a value, put it in the most recent expression
      if (ent != null) {
        valueLast = true;

        if (exps.isEmpty()) {
          // If there's no expression, just hold the value
          hold = ent;
        } else {
          ExpressionBuilder exp = exps.getLast();
          if (exp.getRightEnt() == null && exp.getRightExp() == null) {
            exp.setRight(ent);
          } else {
            throw new UserInputException("Two consecutive values.", piece.position);
          }
        }
        continue;
      }

      // If there's no value, the piece must be an operator
      ExpressionBuilder expNew = null;
      Operator op = null;
      valueLast = false;

      if (piece.type == ExpressionPieceType.BINARY_OPERATOR) {
        op = Operators.getBinaryOperator(piece.contents);
      } else if (piece.type == ExpressionPieceType.POSTFIX_OPERATOR) {
        op = Operators.getPostfixOperator(piece.contents);
      } else if (piece.type == ExpressionPieceType.PREFIX_OPERATOR) {
        op = Operators.getPrefixOperator(piece.contents);
      }

      expNew = new ExpressionBuilder(op);

      // If it's the first operator, then just use the held value
      if (exps.isEmpty()) {
        if (hold != null) {
          expNew.setLeft(hold);
        }
        exps.addLast(expNew);
      } else {
        // For prefix operators, don't check priorities to the left
        // Just stack it in.
        if (op instanceof PrefixOperator) {
          exps.getLast().setRight(expNew);
          exps.addLast(expNew);
        } else {
          ExpressionBuilder expOld = null;
          ExpressionBuilder expNext = null;
          // Removes expressions from the stack that are a higher priority
          // than the one being inserted - or that are postfix.
          while (!exps.isEmpty()) {
            expNext = exps.getLast();
            if (expNext.getOper().getPriority() > op.getPriority()
                || (expNext.getOper().getPriority() == op.getPriority() && !Operator.isFromRight(op.getPriority()))
                || expNext.getOper() instanceof PostfixOperator) {
              expOld = exps.removeLast();
              expNext = null;
            } else {
              break;
            }
          }

          // The last removed expression becomes the left of this one
          // (if no such expression, then the right of the first
          // expression)
          // and this expression becomes the right of the next one
          if (expOld == null) {
            expNew.setLeft(expNext.getRight());
          } else {
            expNew.setLeft(expOld);
          }

          if (expNext != null) {
            expNext.setRight(expNew);
          }

          exps.addLast(expNew);
        }
      }

      pieces.remove(0);
    }

    if (exps.isEmpty()) {
      return hold;
    } else {
      return exps.getFirst().build();
    }
  }

}