package net.nixill.testing;

import java.util.ArrayList;

import org.junit.Test;

import net.nixill.dice.ExpressionPiece;
import net.nixill.dice.Interpreter;
import net.nixill.dice.UserInputException;

public class AppTest {
  @Test
  public void splitTests() {
    testLine("3");
    testLine("4+2");
    testLine("3/-2");
    testLine("4!/3");
    testLine("2*(-1+5)");
    testLine("2**5");
    testLine("2+[1,2,3]");
  }

  public void printExpList(ArrayList<ExpressionPiece> list) {
    for (ExpressionPiece exp : list) {
      System.out.print(exp);
      System.out.print(" ");
    }
    System.out.println();
  }

  public void testLine(String input) {
    try {
      System.out.println("For input: " + input);
      printExpList(Interpreter.split(input));
    } catch (UserInputException ex) {
      System.err.println(ex.getMessage());
      System.err.println("At position: " + ex.getPosition());
      throw ex;
    }
  }
}
