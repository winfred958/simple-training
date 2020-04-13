package com.winfred.training.designpattern.creational.factory.method.log;

public class NOPLoggerFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String name) {
        return null;
    }
}
