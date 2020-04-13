package com.winfred.training.designpattern.creational.factory.method;

public class ACarFactory implements ICarFactory {
    @Override
    public ICar create() {
        return new ACar();
    }
}
