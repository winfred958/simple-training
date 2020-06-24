package com.winfred.training.netty.rpc.consumer;

import com.winfred.training.netty.rpc.api.RpcHelloService;
import com.winfred.training.netty.rpc.api.RpcService;
import com.winfred.training.netty.rpc.consumer.proxy.RpcProxy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcConsumer {
  
  public static void main(String[] args) {
    RpcHelloService rpcHelloService = RpcProxy.create(RpcHelloService.class);
    
    String sayHello = rpcHelloService.sayHello("kevin");
    log.info("{}", sayHello);
    
    RpcService rpcService = RpcProxy.create(RpcService.class);
    
    int add = rpcService.add(1, 1);
    log.info("{}", add);
    
  }
}
