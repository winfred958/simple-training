package com.winfred.training.designpattern.creational.factory.method;

public class NOPLoggerFactory implements ILoggerFactory {
    @Override
    public Logger getLogger(String name) {
        return null;
    }
}
