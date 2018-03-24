package com.winfred.traning.designpattern.observer;

import java.util.Observable;


public class ObservableleImp extends Observable {

	public void doAction(Object object) {
		super.setChanged();
		notifyObservers(object);
		System.out.println("=================");
	}

}
