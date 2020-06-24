package com.winfred.spring.service;

/**
 * @doc https://github.com/winfred958/advanced-java/blob/master/docs/distributed-system/distributed-lock-redis-vs-zookeeper.md
 */
public interface DistributedLock {
  
  String lock(String key);
  
  void unLock(String key);
}
