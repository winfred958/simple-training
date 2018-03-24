package com.winfred.traning.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

import com.alibaba.fastjson.JSON;

public class ObserverTwo implements Observer {

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(o.countObservers() + " | Observer-Two:" + JSON.toJSONString(arg));
	}

}
