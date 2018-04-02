package com.winfred.traning.designpattern.observer;

import java.util.Observer;

import lombok.Data;
import lombok.Getter;

public class Test {

	public static void main(String[] args) {
		
		// 观察者
		Observer observer1 = new ObserverOne();
		Observer observer2 = new ObserverTwo();
		Observer observer3 = new ObserverThr();

		// 被观察者
		ObservableImp observable = new ObservableImp();


		observable.addObserver(observer1);
		observable.addObserver(observer2);
		observable.addObserver(observer3);

		observable.doAction(new TestEntity("1"));
		observable.doAction(new TestEntity("2"));
		observable.doAction(new TestEntity("3"));
		observable.doAction(new TestEntity("4"));
		observable.doAction(new TestEntity("5"));
	}

	static class TestEntity {

		public TestEntity(String attr) {
			this.attr = attr;
		}

		private String attr;

		public String getAttr() {
			return attr;
		}

		public void setAttr(String attr) {
			this.attr = attr;
		}
	}
}
