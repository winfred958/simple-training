package com.winfred.training.socket.netty;

public class Test {
    public void asd() {
        System.out.println("==========aaaaaaaaaa===========");
    }

    public static void main(String[] args) {
        try {
            Test test = (Test) Thread.currentThread().getContextClassLoader().loadClass(Test.class.getName()).newInstance();
            test.asd();

            System.out.println(System.nanoTime());
            System.out.println(System.nanoTime());


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
