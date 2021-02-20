package com.winfred.training.designpattern.behavioral.chain.auth;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;


/**
 * {@link }
 */
@Slf4j
public class ChainClient {

  @Test
  public void chainTest() {
    HandlerPipeline.Builder builder = new HandlerPipeline.Builder()
        .addLast(new ValidateHandler())
        .addLast(new LoginHandler())
        .addLast(new AuthHandler());

    HandlerPipeline handlerPipeline = builder.create();

    handlerPipeline.doHandler(new MemberEntity());
  }
}
