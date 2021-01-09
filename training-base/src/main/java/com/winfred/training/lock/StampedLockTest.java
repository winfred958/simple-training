package com.winfred.training.lock;

import com.winfred.training.concurrent.pool.ThreadPoolUtil;

import java.util.concurrent.locks.StampedLock;

/**
 * @author kevin
 */
public class StampedLockTest {

  private StampedLock stampedLock = new StampedLock();

  private CacheTest cache = null;


  public static void main(String[] args) {
    StampedLockTest stampedLockTest = new StampedLockTest();

    int max = 100;

//        for (int i = 0; i < max; i++) {
//            ThreadPoolUtil.doExecutor(() -> {
//                CacheTest test = stampedLockTest.getOrCreateCache();
//                if (null != test) {
//                    System.out.println("缓存获取成功: " + test.getKey());
//                }
//            });
//        }


    for (int i = 0; i < max; i++) {
      ThreadPoolUtil.doExecutor(() -> {
        CacheTest test = stampedLockTest.getOrCreateCacheWithLockV1();
        if (null != test) {
          System.out.println("缓存获取成功: " + test.getKey());
        }
      });
    }


//        for (int i = 0; i < max; i++) {
//            ThreadPoolUtil.doExecutor(() -> {
//                CacheTest test = stampedLockTest.getOrCreateCacheWithLockV2();
//                if (null != test) {
//                    System.out.println("缓存获取成功: " + test.getKey());
//                }
//            });
//        }

    ThreadPoolUtil.destroy();

  }


  public CacheTest getCache() {
    return cache;
  }

  public CacheTest createCache() {

    CacheTest cacheTest = new CacheTest();
    try {
      System.out.println("------------> 开始创建缓存");
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if (cache != null) {
      System.out.println("缓存已经存在");
      throw new RuntimeException("缓存已经存在");
    }
    cacheTest.setKey("ttt");
    cacheTest.setField("fff");
    cacheTest.setValue("vvv");
    cache = cacheTest;
    return cache;
  }

  public CacheTest getOrCreateCache() {
    CacheTest cache = getCache();
    if (cache != null) {
      return cache;
    }
    return createCache();
  }

  public CacheTest getOrCreateCacheWithLockV1() {
    // 乐观读
    long lockKey = stampedLock.tryOptimisticRead();
    CacheTest cache;
    try {
      cache = getCache();
      if (cache != null) {
        return cache;
      }
      if (lockKey == 0) {
        // 无锁, 加写锁
        lockKey = stampedLock.writeLock();
      } else {
        // 有读锁, 读锁升级写锁
        long writeLockKey = stampedLock.tryConvertToWriteLock(lockKey);
        if (0 == writeLockKey) {
          // 读锁升级失败
          stampedLock.unlockRead(lockKey);
          lockKey = stampedLock.writeLock();
        } else {
          // 读锁升级写锁成功
          lockKey = writeLockKey;
        }
      }
      cache = getCache();
      if (cache != null) {
        return cache;
      }
      cache = createCache();
    } finally {
      if (0 != lockKey) {
        stampedLock.unlock(lockKey);
      }
    }
    return cache;
  }

  public CacheTest getOrCreateCacheWithLockV2() {

    CacheTest cache;

    cache = getCache();
    if (cache != null) {
      return cache;
    }
    long lockKey = stampedLock.tryWriteLock();
    try {
      if (0L == lockKey) {
        lockKey = stampedLock.writeLock();
      }
      cache = getCache();
      if (cache != null) {
        return cache;
      }
      cache = createCache();
    } finally {
      stampedLock.unlockWrite(lockKey);
    }
    return cache;
  }

  class CacheTest {
    private String key;
    private String field;
    private String value;

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public String getField() {
      return field;
    }

    public void setField(String field) {
      this.field = field;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }
}
