package com.winfred.training.juc.concurrent.control;

import com.alibaba.fastjson.JSON;
import com.winfred.training.juc.concurrent.pool.ForkJoinUtils;
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

/**
 * CompletableFuture, 控制线程AND关系
 * <p>
 * CompletableFuture<V> thenCombine(CompletionStage<U>, fn<T, U>);
 * CompletableFuture<V> thenCombineAsync(CompletionStage<U>, fn<T, U>);
 * <p>
 * CompletionStage<Void> thenAcceptBoth(other, consumer);
 * CompletionStage<Void> thenAcceptBothAsync(other, consumer);
 * <p>
 * CompletionStage<Void> runAfterBoth(other, action);
 * CompletionStage<Void> runAfterBothAsync(other, action);
 */

@Slf4j
public class CompletableFutureAnd {


  @Test
  public void thenCombine() {

    CompletableFuture<SimpleResponse> completableFutureA = getCompletableFuture("A", 1000L);
    CompletableFuture<SimpleResponse> completableFutureB = getCompletableFuture("B", 3000L);
    CompletableFuture<SimpleResponse> completableFutureC = getCompletableFuture("C", 2000L);
    AllResponse allResponse = new AllResponse();
    CompletableFuture<AllResponse> allCombine = completableFutureA
        .thenCombineAsync(completableFutureB, new BiFunction<SimpleResponse, SimpleResponse, AllResponse>() {
          @Override
          public AllResponse apply(SimpleResponse simpleResponse, SimpleResponse simpleResponse2) {
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
      AllResponse response = allCombine.get();
      log.info("result = {}", JSON.toJSONString(response));
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
