package com.winfred.training.designpattern.creational.factory.method;

/**
 * 工厂方法模式
 * 创建同类对象, 需要大量重复的代码.
 * 客户端(应用层)不依赖于产品实例如何被创建. 实现等细节
 * 一个类通过其子类来指定创建哪个对象
 * <p>
 * 举例: {@link org.slf4j.Logger} {@link org.slf4j.LoggerFactory}
 */
public class FactoryMethodTest {
    public static void main(String[] args) {
        ICarFactory aCarFactory = new ACarFactory();
        ICarFactory bCarFactory = new BCarFactory();

        aCarFactory.create();
        bCarFactory.create();
    }
}
