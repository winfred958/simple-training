package com.winfred.training.concurrent.control;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class CompatableFutureTest {

    public static void main(String[] args) {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(1);
            return "1";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(2);
            return "2";
        });

        CompletableFuture<String> cf3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(3);
            return "3";
        });


        CompletableFuture<String> cf4 = CompletableFuture.supplyAsync(() -> {
            System.out.println(4);
            return "4";
        });


        CompletableFuture<String> cf5 = CompletableFuture.supplyAsync(() -> {
            System.out.println(5);
            return "5";
        });

        CompletableFuture<Void> allOf = CompletableFuture
                .allOf(
                        cf1,
                        cf2,
                        cf3,
                        cf4,
                        cf5
                );


        System.out.println("================xxx=1");
        allOf.join();
        System.out.println("================xxx=2");

        allOf
                .thenApplyAsync(new Function<Void, Object>() {
                    @Override
                    public Object apply(Void aVoid) {
                        try {
                            String str1 = cf1.get();
                            String str2 = cf2.get();
                            System.out.println(str1 + str2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });

    }
}
