package com.winfred.training.base;

public class SynchronizedTest1 {
  
  
  public static void main(String[] args) {
    String lockStr = "123";
    lockStr.hashCode();
    
    synchronized (lockStr) {
      System.out.println();
    }
    
  }
}
