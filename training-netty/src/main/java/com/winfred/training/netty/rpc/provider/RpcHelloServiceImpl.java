package com.winfred.training.netty.rpc.provider;

import com.winfred.training.netty.rpc.api.RpcHelloService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcHelloServiceImpl implements RpcHelloService {
  
  @Override
  public String sayHello(String name) {
    return "Hello " + name;
  }
}
