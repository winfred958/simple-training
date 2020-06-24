package com.winfred.training.designpattern.structure.bridge.impl;

import com.winfred.training.designpattern.structure.bridge.MessageSender;
import com.winfred.training.designpattern.structure.bridge.Notification;

import java.util.concurrent.Future;

public class UrgencyNotification extends Notification {
  
  public UrgencyNotification(MessageSender messageSender) {
    super(messageSender);
  }
  
  @Override
  public Future<String> notify(String message) {
    return messageSender.sendMessage(message);
  }
}
