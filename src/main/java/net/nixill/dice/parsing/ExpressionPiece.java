package net.nixill.dice.parsing;

public class ExpressionPiece {
  public final String contents;
  public final ExpressionPieceType type;
  public final int position;

  public ExpressionPiece(String contents, ExpressionPieceType type, int pos) {
    this.contents = contents;
    this.type = type;
    this.position = pos;
  }

  public String toString() {
    return type.type + ":" + contents + "(" + position + ")";
  }

  public enum ExpressionPieceType {
    NUMBER("num"), // a literal number
    BRACKET("br"), // ()[],
    PREFIX_OPERATOR("pre"), // a prefix operator
    BINARY_OPERATOR("bin"), // a binary operator
    POSTFIX_OPERATOR("post"), // a postfix operator
    NAME("name"); // a saved name, including the brackets
    private final String type;
    private ExpressionPieceType(String t) {
      type = t;
    }
  }
}