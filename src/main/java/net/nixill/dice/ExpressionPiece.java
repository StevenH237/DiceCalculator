package net.nixill.dice;

public class ExpressionPiece {
  public final String contents;
  public final ExpressionPieceType type;
  public final int position;

  public ExpressionPiece(String contents, ExpressionPieceType type, int pos) {
    this.contents = contents;
    this.type = type;
    this.position = pos;
  }

  public enum ExpressionPieceType {
    NUMBER, // a literal number
    BRACKET, // ()[],
    PREFIX_OPERATOR, // a prefix operator
    BINARY_OPERATOR, // a binary operator
    POSTFIX_OPERATOR, // a postfix operator
    NAME; // a saved name, including the brackets
  }
}