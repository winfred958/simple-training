package com.winfred.training.juc.concurrent.control;

import com.winfred.training.juc.concurrent.pool.ForkJoinUtils;
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

    CompletableFuture<SimpleResponse> completableFutureA = getCompletableFuture("A-1", 3000L);
    CompletableFuture<SimpleResponse> completableFutureB = getCompletableFuture("A-2", 1000L);
    CompletableFuture<SimpleResponse> completableFutureC = getCompletableFuture("A-3", 2000L);

    CompletableFuture<SimpleResponse> completableFuture = completableFutureA
            .applyToEitherAsync(completableFutureB, new Function<SimpleResponse, SimpleResponse>() {
              @Override
              public SimpleResponse apply(SimpleResponse simpleResponse) {
                return simpleResponse;
              }
            })
            .applyToEitherAsync(completableFutureC, new Function<SimpleResponse, SimpleResponse>() {
              @Override
              public SimpleResponse apply(SimpleResponse simpleResponse) {
                return simpleResponse;
              }
            });

    try {
      SimpleResponse simpleResponse = completableFuture.get();
      simpleResponse.getCode();
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

}
