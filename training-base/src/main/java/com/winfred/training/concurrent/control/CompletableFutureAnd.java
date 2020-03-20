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
public class CompletableFutureAnd {

    @Test
    public void thenCombine() {

        CompletableFuture<SimpleResponse> completableFutureA = getCompletableFuture("A");
        CompletableFuture<SimpleResponse> completableFutureB = getCompletableFuture("B");
        CompletableFuture<SimpleResponse> completableFutureC = getCompletableFuture("C");

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

    private CompletableFuture<SimpleResponse> getCompletableFuture(String apiName) {
        return CompletableFuture
                .supplyAsync(new Supplier<SimpleResponse>() {
                    @Override
                    public SimpleResponse get() {
                        log.info("访问接口: {}", apiName);
                        return new SimpleResponse(apiName, 200);
                    }
                });
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
