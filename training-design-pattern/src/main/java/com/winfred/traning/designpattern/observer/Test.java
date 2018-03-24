package com.winfred.traning.designpattern.observer;

import java.util.Observer;

import lombok.Data;

public class Test {

	public static void main(String[] args) {
		Observer observer1 = new ObserverOne();
		Observer observer2 = new ObserverTwo();

		ObservableleImp observable = new ObservableleImp();


		observable.addObserver(observer1);
		observable.addObserver(observer2);

		observable.doAction(new TestEntity("1"));
		observable.doAction(new TestEntity("2"));
		observable.doAction(new TestEntity("3"));
		observable.doAction(new TestEntity("4"));
		observable.doAction(new TestEntity("5"));
	}

	@Data
	static class TestEntity {

		public TestEntity(String attr) {
			this.attr = attr;
		}

		private String attr;
	}
}
