package net.nixill.testing;

import java.util.ArrayList;

import org.junit.Test;

<<<<<<< HEAD
import net.nixill.dice.ExpressionPiece;
import net.nixill.dice.ExpressionSplitter;
import net.nixill.dice.UserInputException;
=======
import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.parsing.ExpressionParser;
import net.nixill.dice.parsing.ExpressionPiece;
import net.nixill.dice.parsing.ExpressionSplitter;
import net.nixill.dice.parsing.UserInputException;
>>>>>>> 9e166283a19240e916029897003bfac06e646532

public class AppTest {
  @Test
  public void tests() {
    System.out.println("-- BEGIN NEW SPLITTER TEST --");
    testLine("3");
    testLine("4+2");
    testLine("3/-2");
    testLine("4!/3");
    testLine("2*(-1+5)");
    testLine("2**5");
    testLine("2+[1,2,3]");
    testLine("[1,(1+1),(1-1)]");
    testLine("{stats}*6");
    testLine("{level,5}");
    testLine("({1}**2+{1})/2");
    testLine("{$stats,6}");
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
<<<<<<< HEAD
      printExpList(ExpressionSplitter.split(input));
=======
      ArrayList<ExpressionPiece> split = ExpressionSplitter.split(input);
      printExpList(split);

      // Tree it
      DCEntity exp = ExpressionParser.parseLine(split);
      System.out.println("Tree toString: " + exp.toString());

>>>>>>> 9e166283a19240e916029897003bfac06e646532
      System.out.println("\u200b");
    } catch (UserInputException ex) {
      System.err.println(ex.getMessage());
      System.err.println("At position: " + ex.getPosition());
      throw ex;
    }
  }
}
