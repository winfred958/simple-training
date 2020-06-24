package com.winfred.training.designpattern.structure.decorator.logger;

import org.slf4j.Logger;

public class Client {
  private static Logger logger = JsonLoggerFactory.getLogger(Client.class);
  
  public static void main(String[] args) {
    logger.info("tttttttttttttttttttt");
  }
}
