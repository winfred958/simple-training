package com.winfred.training.designpattern.structure.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK代理是不需要依赖第三方的库，只要要JDK环境就可以进行使用，它有几个要求
 * * 实现InvocationHandler
 * * 使用Proxy.newProxyInstance产生代理对象(采用字节重组, 重写生成代理对象)
 * * 被代理的对象必须要实现接口
 * <p>
 * <p>
 * 原理 - 字节码重组 - JDK 动态代理生成对象的步骤:
 * 1. 获取被代理对象的引用, 并反射获取它的所有接口.
 * 2. JDK动态代理重新生成一个新的类, 同时新的类要实现被代理对象的所有接口.
 * 3. 动态生成Java代码, 新加的业务逻辑方法由一定的逻辑代码调用.
 * 4. 变异生成新的Java .class文件
 * 5. 重新加载到JVM运行
 * <p>
 * 源码: {@link }
 *
 * @author z
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    public Object getProxy() {
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        if (null != method.getAnnotation(TestBefore.class)) {
            before(args);
            result = method.invoke(target, args);
        } else if (null != method.getAnnotation(TestAfter.class)) {
            result = method.invoke(target, args);
            after(args);
        } else if (null != method.getAnnotation(TestAround.class)) {
            before(args);
            result = method.invoke(target, args);
            after(args);
        } else {
            result = method.invoke(target, args);
        }
        return result;
    }

    private void before(Object[] args) {
        System.out.println(String.format("%s : %s", this.getClass().getName(), " --- before ---"));
    }

    private void after(Object[] args) {
        System.out.println(String.format("%s : %s", this.getClass().getName(), " --- after ---"));
    }

}
