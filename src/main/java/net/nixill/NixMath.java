package net.nixill;

public class NixMath {
  // http://jonisalonen.com/2012/converting-decimal-numbers-to-ratios/
  public static int[] float2fracPriv(double x) {
    double tolerance = 1.0E-6;
    int h1 = 1;
    int h2 = 0;
    int k1 = 0;
    int k2 = 1;
    double b = x;
    do {
      int a = (int) Math.floor(b);
      int aux = h1;
      h1 = a * h1 + h2;
      h2 = aux;
      aux = k1;
      k1 = a * k1 + k2;
      k2 = aux;
      b = 1 / (b - a);
    } while (Math.abs(x - ((double) h1) / ((double) k1)) > x * tolerance);
    
    return new int[] { h1, k1 };
  }
  
  public static int[] float2frac(double x) {
    return float2fracPriv(Math.signum(x) * Math.abs(x));
  }
  
  public static int float2num(double x) {
    return float2frac(x)[0];
  }
  
  public static int float2den(double x) {
    return float2frac(x)[1];
  }
}