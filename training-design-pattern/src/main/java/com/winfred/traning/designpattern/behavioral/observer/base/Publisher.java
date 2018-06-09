package com.winfred.traning.designpattern.behavioral.observer.base;

import java.util.Observable;

public class Publisher extends Observable {

	public void pub(Object object) {
		super.setChanged();
		notifyObservers(object);
	}
}
