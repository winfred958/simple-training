package com.winfred.training.designpattern.creational.singleton;

/**
 * 单例: 饿汉模式
 * <p>
 * 类初始化时创建, 利用了类的初始化锁, 线程安全, (类初始化时创建,在用户线程创建前已经初始化完毕, 所以线程安全);
 * <p>
 * 优点: 线程安全, 无锁, 效率比较高
 * 缺点: 不管用不用都会创建
 */
public class HungrySingletonV2 {
    private static final HungrySingletonV2 HUNGRY_SINGLETON;

    static {
        HUNGRY_SINGLETON = new HungrySingletonV2();
    }

    private HungrySingletonV2() {
    }

    public static HungrySingletonV2 getInstance() {
        return HUNGRY_SINGLETON;
    }

    public static int getHashcode() {
        return getInstance().hashCode();
    }
}
