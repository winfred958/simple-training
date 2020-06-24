package com.winfred.spring.service.imp;

import com.winfred.spring.service.DistributedLock;
import org.springframework.stereotype.Service;

@Service
public class DistributedLockImpl implements DistributedLock {
  
  @Override
  public String lock(String key) {
    return null;
  }
  
  @Override
  public void unLock(String key) {
  
  }
}
