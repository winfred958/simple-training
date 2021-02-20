package com.winfred.training.designpattern.structure.bridge.impl;

import com.winfred.training.designpattern.structure.bridge.MessageSender;

import java.util.concurrent.Future;

/**
 * @author kevin
 */
public class EmailMessageSender implements MessageSender {

  @Override
  public Future<String> sendMessage(String message) {
    return null;
  }
}
