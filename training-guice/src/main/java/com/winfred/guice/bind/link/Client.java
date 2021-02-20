package com.winfred.guice.bind.link;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Client {

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new InjectorBind());
    Token token = injector.getInstance(Token.class);
    String tokenStr = token.getToken("xxxx");

    System.out.println(tokenStr);

  }
}
