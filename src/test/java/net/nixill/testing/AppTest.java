package net.nixill.testing;

import java.util.ArrayList;

import org.junit.Test;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCNumber;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.operations.Functions;
import net.nixill.dice.parsing.ExpressionParser;
import net.nixill.dice.parsing.ExpressionPiece;
import net.nixill.dice.parsing.ExpressionSplitter;
import net.nixill.dice.parsing.UserInputException;

public class AppTest {
  @Test
  public void tests() {
    System.out.println("-- BEGIN NEW SPLITTER TEST --");
    Functions.putLocal("_last", new DCNumber(0));
    Functions.putLocal("_ans", new DCNumber(0));
    Functions.putLocal("level", ExpressionSplitter.parse("({1,3}^2+{1,3})/2"));

    testLine("3");
    testLine("4+2");
    testLine("3/-2");
    testLine("4!/3");
    testLine("2*(-1+5)");
    testLine("2^5");
    testLine("2+[1,2,3]");
    testLine("[1,(1+1),(1-1)]");
    testLine("{level}");
    testLine("{level,5}");
    testLine("{level,10}");
    testLine("{_ans}+2");
    testLine("{10}");
  }

  public void printExpList(ArrayList<ExpressionPiece> list) {
    System.out.println("Pieces: ");
    for (ExpressionPiece exp : list) {
      System.out.print(exp);
      System.out.print(" ");
    }
    System.out.println();
  }

  public void testLine(String input) {
    try {
      // Split it
      System.out.println("For input: " + input);
      ArrayList<ExpressionPiece> split = ExpressionSplitter.split(input);
      printExpList(split);

      // Tree it
      DCEntity exp = ExpressionParser.parseLine(split);
      System.out.println("printTree:");
      exp.printTree(1);

      // Run it
      DCValue val = exp.getValue();
      System.out.println("Value:");
      val.printTree(1);

      System.out.println("\u200b");
    } catch (UserInputException ex) {
      System.err.println(ex.getMessage());
      System.err.println("At position: " + ex.getPosition());
      throw ex;
    }
  }
}
