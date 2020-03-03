package com.winfred.training.designpattern.structure.bridge;

import java.util.concurrent.Future;

/**
 * @author kevin
 */
public interface MessageSender {
    /**
     * 发送消息
     *
     * @return
     */
    Future<String> sendMessage(String message);
}
