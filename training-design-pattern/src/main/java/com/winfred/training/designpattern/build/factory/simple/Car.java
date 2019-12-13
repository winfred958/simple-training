package com.winfred.training.designpattern.build.factory.simple;

public interface Car {
    void goAhead(Integer speed);

    void turnTo(Integer speed, Double angle);

    void backOff(Integer speed, Double angle);
}
