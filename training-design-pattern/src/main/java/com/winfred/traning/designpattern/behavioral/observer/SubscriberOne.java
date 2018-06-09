package com.winfred.traning.designpattern.behavioral.observer;

import java.util.Observable;
import java.util.Observer;


public class SubscriberOne implements Observer, ObserverExt {

	@Override
	public void update(Observable observable, Object arg) {
		doSomething(arg);
	}
}
