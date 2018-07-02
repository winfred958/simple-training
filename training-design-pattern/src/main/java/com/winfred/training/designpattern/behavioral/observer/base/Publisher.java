package com.winfred.training.designpattern.behavioral.observer.base;

import java.util.Observable;

/**
 * 被观察者
 * 消息发布者
 *
 * @author winfred
 */
public class Publisher extends Observable {

    public void pub(Object object) {
        super.setChanged();
        notifyObservers(object);
    }
}
