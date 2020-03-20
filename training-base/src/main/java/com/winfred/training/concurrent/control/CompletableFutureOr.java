package com.winfred.training.concurrent.control;

import com.winfred.training.base.ForkJoinUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CompletableFuture, 控制线程OR关系
 * <p>
 * CompletionStage<U> applyToEither(CompletionStage<T>, fn<T, U>);
 * CompletionStage<U> applyToEitherAsync(CompletionStage<T>, fn<T, U>);
 * <p>
 * CompletionStage<Void> acceptEither(other, consumer);
 * CompletionStage<Void> acceptEitherAsync(other, consumer);
 * <p>
 * CompletionStage<Void> runAfterEither(other, action);
 * CompletionStage<Void> runAfterEitherAsync(other, action);
 */
@Slf4j
public class CompletableFutureOr {

    @Test
    public void applyToEitherAsync() {

        CompletableFuture<SimpleResponse> completableFutureA = getCompletableFuture("A-1", 1000L);
        CompletableFuture<SimpleResponse> completableFutureB = getCompletableFuture("A-2", 3000L);
        CompletableFuture<SimpleResponse> completableFutureC = getCompletableFuture("A-3", 2000L);

        AllResponse allResponse = new AllResponse();
        CompletableFuture<AllResponse> completableFuture = completableFutureA
                .applyToEitherAsync(completableFutureB, new Function<SimpleResponse, AllResponse>() {
                    @Override
                    public AllResponse apply(SimpleResponse simpleResponse) {
                        allResponse.setResponseA(simpleResponse);
                        return allResponse;
                    }
                });
        try {
            AllResponse allResponse1 = completableFuture.get();
            allResponse1.getResponseA();
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


    public


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
