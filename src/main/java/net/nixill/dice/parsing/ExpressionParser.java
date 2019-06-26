package net.nixill.dice.parsing;

import java.util.ArrayList;

import net.nixill.dice.objects.DCExpression;

public class ExpressionParser {
  public static DCExpression parseEntireExpression(ArrayList<ExpressionPiece> pieces) {
    if (pieces.isEmpty()) {
      throw new UserInputException("Empty list received.", 0);
    }

    while (!pieces.isEmpty()) {
      ExpressionPiece piece = pieces.remove(0);
    }
  }
}