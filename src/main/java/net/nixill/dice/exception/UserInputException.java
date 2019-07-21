package net.nixill.dice.exception;

/**
 * Thrown by the parser when encountering an error caused by malformed
 * input.
 */
public class UserInputException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  private final int position;
  
  /**
   * Creates a new UserInputException.
   * 
   * @param pos
   *   The position at which the malformed input occurred.
   */
  public UserInputException(String message, int pos) {
    super(message);
    position = pos;
  }
  
  /**
   * Returns the position at which the malformed input occurred.
   * 
   * @return The position
   */
  public int getPosition() {
    return position;
  }
}