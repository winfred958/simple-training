package com.winfred.traning.designpattern.behavioral.observer;

import com.alibaba.fastjson.JSON;

public interface ObserverExt {

    default void doSomething(Object object) {
        System.out.println(this.getClass().getName() + ": " + JSON.toJSONString(object));
    }
}
