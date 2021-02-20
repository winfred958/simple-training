package com.winfred.training.juc.concurrent.control;

import com.winfred.training.juc.concurrent.pool.ForkJoinUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class CompletableFutureSerial {

  String actionOne = "穿衣服";
  String actionTwo = "穿袜子";
  String actionThree = "穿鞋子";


  /**
   * 串行, 相当于 map
   * CompletableFuture<T> 转化为 CompletableFuture<U>
   */
  @Test
  public void thenApply() {

    CompletableFuture<Message> completableFuture = getApplyCompletableFuture(actionOne, 2000L);
    Object join = completableFuture.join();
  }

  static class PostRecode<T, R> implements Function<T, R> {
    @Override
    public R apply(T str) {
      String value = String.valueOf(str);
      log.info("{} - 完毕!", value);
      return (R) new Message(value, true);
    }
  }

  /**
   * then compose 连接两个 CompletableFuture, 构成串行依赖
   */
  @Test
  public void thenComposeAsync() {
    CompletableFuture<Message> completableFutureFirst = getApplyCompletableFuture(actionOne, 2000L);

    CompletableFuture<Message> messageCompletableFuture = completableFutureFirst
        .thenComposeAsync(new Function<Message, CompletionStage<Message>>() {
          @Override
          public CompletionStage<Message> apply(Message message) {
            return getApplyCompletableFuture(actionTwo, 3000L);
          }
        }, ForkJoinUtils.getInstance())
        .thenComposeAsync(new Function<Message, CompletionStage<Message>>() {
          @Override
          public CompletionStage<Message> apply(Message message) {
            return getApplyCompletableFuture(actionThree, 1000L);
          }
        });

    try {
      Message message = messageCompletableFuture.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Data
  @AllArgsConstructor
  static class Message {
    private String action;
    private Boolean isSuccess;
  }

  private CompletableFuture<Message> getApplyCompletableFuture(String actionName, Long took) {
    CompletableFuture<Message> completableFuture = CompletableFuture
        .supplyAsync(new Supplier<String>() {
          @Override
          public String get() {

            log.info("{} 开始 ...", actionName);
            try {
              Thread.sleep(took);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            return actionName;
          }
        }, ForkJoinUtils.getInstance())

        .thenApplyAsync(new PostRecode<String, Message>(), ForkJoinUtils.getInstance())
        .exceptionally(new Function<Throwable, Message>() {
          @Override
          public Message apply(Throwable throwable) {
            log.error("", throwable);
            return new Message("", false);
          }
        });
    return completableFuture;
  }


}
