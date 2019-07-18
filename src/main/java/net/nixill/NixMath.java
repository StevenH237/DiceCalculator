package net.nixill;

public class NixMath {
  // http://jonisalonen.com/2012/converting-decimal-numbers-to-ratios/
  public double[] float2fracPriv(double x) {
    double tolerance = 1.0E-6;
    double h1 = 1;
    double h2 = 0;
    double k1 = 0;
    double k2 = 1;
    double b = x;
    do {
      double a = Math.floor(b);
      double aux = h1;
      h1 = a * h1 + h2;
      h2 = aux;
      aux = k1;
      k1 = a * k1 + k2;
      k2 = aux;
      b = 1 / (b - a);
    } while (Math.abs(x - h1 / k1) > x * tolerance);

    return new double[] { h1, k1 };
  }

  public double[] float2frac(double x) {
    return float2fracPriv(Math.signum(x) * Math.abs(x));
  }

  public double float2num(double x) {
    return float2frac(x)[0];
  }

  public double float2den(double x) {
    return float2frac(x)[1];
  }
}