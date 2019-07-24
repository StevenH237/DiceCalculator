package net.nixill.testing;

import java.util.ArrayList;

import org.junit.Test;

import net.nixill.dice.objects.DCEntity;
import net.nixill.dice.objects.DCValue;
import net.nixill.dice.operations.Functions;
import net.nixill.dice.parsing.ExpressionPiece;
import net.nixill.dice.parsing.ExpressionSplitter;

public class AppTest {
  // @Test
  // public void mathTest() {
  // System.out.println("-- BEGIN MATH TEST --");
  // Random rand = new Random();
  
  // for (int i = 0; i < 10; i++) {
  // int a = rand.nextInt(100);
  // int b = rand.nextInt(100);
  // System.out.println("Fraction: " + a + "/" + b);
  // double frac = ((double) a) / ((double) b);
  // System.out.println("Decimal: " + frac);
  // a = NixMath.float2num(frac);
  // b = NixMath.float2den(frac);
  // System.out.println("Simplified: " + a + "/" + b);
  // System.out.println("\u200b");
  // }
  // }
  
  @Test
  public void tests() {
    System.out.println("-- BEGIN NEW TEST --");
    Functions.setLoader(new Loader());
    
    // v0.1 tests
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
    testLine("{_last}+3");
    testLine("{level,10}");
    testLine("{_ans}+2");
    
    // v0.3 tests
    testLine("\"Hello!\"");
    testLine("[1, 2, 3]?");
    testLine("$[1, 2, 3]");
    testLine("#[1, 2, 3]");
    testLine("\"Hello\" + [32] + \"World\"");
    testLine("\"Hello\" + 32 + \"World\"");
    testLine("[\"red\", \"green\", \"blue\"]?s1");
    testLine("1 + 2");
    testLine("1 + [3]");
    testLine("1 + \"4\"");
    testLine("[2] + [5]");
    testLine("[50] + \"6\"");
    
    // v0.4 tests
    testLine("2d5");
    testLine("d20");
    testLine("d1");
    testLine("6u>=5");
    testLine("6u<=5+{_u}");
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
    System.out.println("Input: " + input);
    
    try {
      DCEntity firstParse = ExpressionSplitter.parse(input);
      String firstString = firstParse.toCode();
      System.out.println("First parse: " + firstString);
      
      DCEntity secondParse = ExpressionSplitter.parse(firstString);
      String secondString = secondParse.toCode();
      System.out.println("Second parse: " + secondString);
      
      if (!firstString.equals(secondString)) {
        throw new AssertionError("Expression strings aren't equal!");
      }
      
      DCValue value = secondParse.getValue();
      String thirdString = value.toCode();
      System.out.println("Value: " + thirdString);
      
      DCEntity fourthParse = ExpressionSplitter.parse(thirdString);
      String fourthString = fourthParse.toCode();
      System.out.println("Fourth parse: " + fourthString);
      
      if (!thirdString.equals(fourthString)) {
        throw new AssertionError("Value strings aren't equal!");
      }
      
      System.out.println("\u200b");
      
      Functions.save2("_ans", value);
      if (!input.contains("{_last")) {
        Functions.save2("_last", firstParse);
      }
    } catch (Exception ex) {
      System.out.println("\u200b");
      ex.printStackTrace();
      throw ex;
    }
  }
  
  /*
   * public void testLine(String input) { testSaveLoad(input); }
   * 
   * public void testSaveLoad(String input) { System.out.println("Input: "
   * + input);
   * 
   * DCEntity firstParse = ExpressionSplitter.parse(input); String
   * firstString = firstParse.toCode(); System.out.println("First parse: "
   * + firstString);
   * 
   * DCEntity secondParse = ExpressionSplitter.parse(firstString); String
   * secondString = secondParse.toCode();
   * System.out.println("Second parse: " + secondString);
   * 
   * if (!firstString.equals(secondString)) { throw new
   * AssertionError("Strings aren't equal!"); }
   * 
   * System.out.println("\u200b"); }
   * 
   * public void testIO(String input) { try { // Split it
   * System.out.println("For input: " + input); ArrayList<ExpressionPiece>
   * split = ExpressionSplitter.split(input); printExpList(split);
   * 
   * // Tree it DCEntity exp = ExpressionParser.parseLine(split);
   * System.out.println("printTree:"); exp.printTree(1);
   * 
   * // Run it DCValue val = exp.getValue(); System.out.println("Value:");
   * val.printTree(1);
   * 
   * // Save local variables SavedFunctions.save("_last", exp);
   * SavedFunctions.save("_ans", val);
   * 
   * System.out.println("\u200b"); } catch (UserInputException ex) {
   * System.err.println(ex.getMessage());
   * System.err.println("At position: " + ex.getPosition()); throw ex; } }
   */
}
