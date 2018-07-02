package com.winfred.training.designpattern.behavioral.observer;

import java.util.Observable;
import java.util.Observer;


/**
 * 观察者
 * 消息订阅
 *
 * @author winfred
 */
public class SubscriberOne implements Observer, ObserverExt {

    @Override
    public void update(Observable observable, Object arg) {
        doSomething(arg);
    }
}
