package com.winfred.training.netty.rpc.provider;

import com.winfred.training.netty.rpc.api.RpcService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcServiceImpl implements RpcService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
