package com.winfred.training.designpattern.behavioral.chain.auth;

import java.util.LinkedList;
import java.util.List;

public interface HandlerPipeline {

  void doHandler(MemberEntity member);

  class Builder {
    private List<Handler> handlers;

    public Builder addLast(Handler handler) {
      if (null == handlers) {
        handlers = new LinkedList<>();
      }
      handlers.add(handler);
      return this;
    }

    HandlerPipeline create() {

      return new HandlerPipeline() {

        @Override
        public void doHandler(MemberEntity member) {
          if (null == handlers || handlers.size() < 1) {
            return;
          }
          for (Handler handler : handlers) {
            handler.doHandler(member);
          }
        }
      };
    }
  }
}
