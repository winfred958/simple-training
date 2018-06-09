package com.winfred.traning.designpattern.observer;

import java.util.Observable;


public class ObservableImpl extends Observable {

	public void doAction(Object object) {
		super.setChanged();
		notifyObservers(object);
		System.out.println("=================");
	}

}
