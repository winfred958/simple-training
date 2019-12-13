package com.winfred.training.designpattern.build.factory.simple;

import lombok.extern.slf4j.Slf4j;

/**
 * 简单工厂模式
 */
@Slf4j
public class BydCarFactory {

    public Car buildCar(Class<? extends Car> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            log.error("", e);
        } catch (IllegalAccessException e) {
            log.error("", e);
        }
        return null;
    }
}
