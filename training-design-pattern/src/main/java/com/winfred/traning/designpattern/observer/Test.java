package com.winfred.traning.designpattern.observer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observer;


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
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		int total = 60;
		for (int i = 0; i < total; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			System.out.println(
					"spark-submit       --class com.yamibuy.datamining.etl.ec.branch.SearchModel       --master yarn           --deploy-mode cluster           --driver-memory 6g       --num-executors 40       --executor-memory 2g       --executor-cores 4       --conf spark.sql.warehouse.dir=hdfs://yamicdh5/user/yamipro/spark/warehouse       --files /etc/hive/conf/hive-site.xml       --jars /opt/cm/share/cmf/lib/mysql-connector-java.jar     /home/yamipro/git_dir/bi-engine/data-etl/target/data-etl.jar "
							+ simpleDateFormat.format(calendar.getTime()));
		}
		
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
