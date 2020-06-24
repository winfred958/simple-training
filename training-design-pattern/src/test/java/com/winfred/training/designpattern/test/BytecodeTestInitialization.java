package com.winfred.training.designpattern.test;

public class BytecodeTestInitialization {
  
  public static void main(String[] args) {
    Point2D point2DA = new Point2D(2, 2);
    Point2D point2DB = new Point2D(5, 6);
    
    double euclideanDistance = point2DA.euclideanDistance(point2DB);
    double manhattanDistance = point2DA.manhattanDistance(point2DB);
    
    ClassLoader classLoader = point2DA.getClass().getClassLoader();
    
  }
}

class Point2D {
  
  private double x;
  private double y;
  
  public double getX() {
    return x;
  }
  
  public double getY() {
    return y;
  }
  
  public Point2D(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public double euclideanDistance(Point2D pointB) {
    double distancePow = Math.pow(this.x - pointB.getX(), 2) + Math.pow(this.y - pointB.getY(), 2);
    return Math.sqrt(distancePow);
  }
  
  public double manhattanDistance(Point2D pointB) {
    return Math.abs(this.x - pointB.getX()) + Math.abs(this.y - pointB.getY());
  }
}