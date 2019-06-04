package com.winfred.training.concurrent.control;

import java.util.concurrent.CompletableFuture;

public class CompatableFutureTest {

    public static void main(String[] args) {
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            return "1";
        });



    }
}
