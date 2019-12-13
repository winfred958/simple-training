package com.winfred.training.designpattern.build.factory;

import com.winfred.training.designpattern.build.factory.simple.BydCarFactory;
import com.winfred.training.designpattern.build.factory.simple.BydQin;
import com.winfred.training.designpattern.build.factory.simple.BydTang;
import com.winfred.training.designpattern.build.factory.simple.Car;
import org.junit.Test;

public class SimpleFactoryTest {

    @Test
    public void simpleFactoryTest() {
        BydCarFactory carFactory = new BydCarFactory();
        Car bydQin = carFactory.buildCar(BydQin.class);
        bydQin.goAhead(100);

        Car bydTang = carFactory.buildCar(BydTang.class);
        bydTang.goAhead(80);

    }
}
