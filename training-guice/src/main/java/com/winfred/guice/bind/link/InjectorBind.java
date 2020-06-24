package com.winfred.guice.bind.link;

import com.google.inject.AbstractModule;
import com.winfred.guice.bind.link.impl.TokenImpl;
import com.winfred.guice.bind.link.impl.UserServiceImpl;


public class InjectorBind extends AbstractModule {
  
  /**
   * 配置类之间关系
   */
  @Override
  protected void configure() {
    bind(UserService.class).to(UserServiceImpl.class);
    bind(Token.class).to(TokenImpl.class);
  }
}
