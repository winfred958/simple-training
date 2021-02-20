package com.winfred.training.netty.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

@Data
public class InvokeProtocol implements Serializable {

  private String className;
  private String methodName;
  private Class<?>[] parameterTypes;
  private Object[] parameterValues;
}
