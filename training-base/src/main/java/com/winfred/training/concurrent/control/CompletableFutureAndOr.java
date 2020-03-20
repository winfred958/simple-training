package com.winfred.training.concurrent.control;

import com.winfred.training.base.ForkJoinUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class CompletableFutureAndOr {

    /**
     * AND 有返回值
     */
    @Test
    public void thenCombine() {

        CompletableFuture<SimpleResponse> completableFutureA = getCompletableFuture("A", 1000L);
        CompletableFuture<SimpleResponse> completableFutureB = getCompletableFuture("B", 3000L);
        CompletableFuture<SimpleResponse> completableFutureC = getCompletableFuture("C", 2000L);

        CompletableFuture<AllResponse> allCombine = completableFutureA
                .thenCombineAsync(completableFutureB, new BiFunction<SimpleResponse, SimpleResponse, AllResponse>() {
                    @Override
                    public AllResponse apply(SimpleResponse simpleResponse, SimpleResponse simpleResponse2) {
                        AllResponse allResponse = new AllResponse();
                        allResponse.setResponseA(simpleResponse);
                        allResponse.setResponseB(simpleResponse2);
                        return allResponse;
                    }
                }, ForkJoinUtils.getInstance())
                .thenCombineAsync(completableFutureC, new BiFunction<AllResponse, SimpleResponse, AllResponse>() {
                    @Override
                    public AllResponse apply(AllResponse allResponse, SimpleResponse simpleResponse2) {
                        allResponse.setResponseC(simpleResponse2);
                        return allResponse;
                    }
                }, ForkJoinUtils.getInstance())
                .thenComposeAsync(new Function<AllResponse, CompletionStage<AllResponse>>() {
                    @Override
                    public CompletionStage<AllResponse> apply(AllResponse allResponse) {
                        log.info("组装数据");
                        return CompletableFuture.supplyAsync(new Supplier<AllResponse>() {
                            @Override
                            public AllResponse get() {
                                log.info("处理");
                                return allResponse;
                            }
                        }, ForkJoinUtils.getInstance());
                    }
                }, ForkJoinUtils.getInstance());


        try {
            AllResponse allResponse = allCombine.get();
            allResponse.getResponseA();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private CompletableFuture<SimpleResponse> getCompletableFuture(String apiName, Long took) {
        return CompletableFuture
                .supplyAsync(new Supplier<SimpleResponse>() {
                    @Override
                    public SimpleResponse get() {
                        log.info("request: {}", apiName);
                        try {
                            Thread.sleep(took);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return new SimpleResponse(apiName, 200);
                    }
                }, ForkJoinUtils.getInstance())
                .thenApplyAsync(new Function<SimpleResponse, SimpleResponse>() {
                    @Override
                    public SimpleResponse apply(SimpleResponse simpleResponse) {
                        log.info("\t \t response: {} response", apiName);
                        return simpleResponse;
                    }
                }, ForkJoinUtils.getInstance());
    }

    @Data
    @AllArgsConstructor
    static class SimpleResponse {
        private String context;
        private Integer code;
    }

    @Data
    static class AllResponse {
        private SimpleResponse responseA;
        private SimpleResponse responseB;
        private SimpleResponse responseC;
    }
}
