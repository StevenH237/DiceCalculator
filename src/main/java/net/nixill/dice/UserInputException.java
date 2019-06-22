package net.nixill.dice;

public class UserInputException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  private final int position;
  
  public UserInputException(String message, int pos) {
    super(message);
    position = pos;
  }
  
  public int getPosition() {
    return position;
  }
}