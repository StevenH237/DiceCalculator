package net.nixill.dice;

public class UserInputException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  
  public UserInputException(String message) {
    super(message);
  }
}