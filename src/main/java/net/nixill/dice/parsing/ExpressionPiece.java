package net.nixill.dice.parsing;

import net.nixill.dice.objects.DCExpression;

/**
 * Represents a single piece of a {@link DCExpression}.
 */
public class ExpressionPiece {
  /**
   * The text of that piece. Should include all things necessary to remake the
   * written expression from its pieces' <code>content</code>s.
   */
  public final String contents;
  /**
   * Which type the piece is. See {@link ExpressionPieceType} for more detail.
   */
  public final ExpressionPieceType type;
  /**
   * The index of the first character of the contents of the piece. Useful for
   * reporting errors.
   */
  public final int position;

  /**
   * Creates a new ExpressionPiece. See {@link #contents}, {@link #type}, and
   * {@link #position} for details on the parameters.
   */
  public ExpressionPiece(String contents, ExpressionPieceType type, int pos) {
    this.contents = contents;
    this.type = type;
    this.position = pos;
  }

  public String toString() {
    return type.type + ":" + contents + "(" + position + ")";
  }

  /**
   * Represents the types of pieces of an expression.
   */
  public enum ExpressionPieceType {
    /** A number, made from just digits and a decimal point. */
    NUMBER("num"),
    /** Any of the symbols }, (), [], or comma. */
    BRACKET("br"),
    /** Any prefix operator. */
    PREFIX_OPERATOR("pre"),
    /** Any binary operator. */
    BINARY_OPERATOR("bin"),
    /** Any postfix operator. */
    POSTFIX_OPERATOR("post"),
    /** A { followed by a name. */
    NAME("name");

    private final String type;

    private ExpressionPieceType(String t) {
      type = t;
    }
  }
}