package com.winfred.training.juc.concurrent.simple;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class ThreadTest {

  @Test
  public void threadTest() throws ExecutionException, InterruptedException {
    Runnable task = new Runnable() {
      @SneakyThrows
      @Override
      public void run() {
        Thread.sleep(1000);
        String name = Thread.currentThread().getName();
        log.info("thread name: {}", name);
      }
    };


    FutureTask futureTask = new FutureTask(task, null);
    futureTask.get();
  }


  @Test
  public void DaemonThreadTest() {
    Runnable task = new Runnable() {
      @SneakyThrows
      @Override
      public void run() {
        Thread.sleep(1000);
        String name = Thread.currentThread().getName();
        log.info("thread name: {}", name);
      }
    };

    Thread thread = new Thread(task);
    thread.setName("thread-01");
    thread.setDaemon(false);
    thread.start();
  }
}
