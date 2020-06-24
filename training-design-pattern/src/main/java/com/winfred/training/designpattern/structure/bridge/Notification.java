package com.winfred.training.designpattern.structure.bridge;

import java.util.concurrent.Future;

/**
 * @author kevin
 */
public abstract class Notification {
  
  protected MessageSender messageSender;
  
  public Notification(MessageSender messageSender) {
    this.messageSender = messageSender;
  }
  
  public abstract Future<String> notify(String message);
  
}
