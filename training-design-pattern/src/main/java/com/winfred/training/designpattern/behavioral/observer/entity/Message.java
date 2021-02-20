package com.winfred.training.designpattern.behavioral.observer.entity;

import lombok.Data;

@Data
public class Message {

  public Message(String body) {
    super();
    this.body = body;
  }

  private String body;
}
