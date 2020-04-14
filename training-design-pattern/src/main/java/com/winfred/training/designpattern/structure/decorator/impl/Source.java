package com.winfred.training.designpattern.structure.decorator.impl;

import com.winfred.training.designpattern.structure.decorator.ISource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Source implements ISource {

    private String msg = "煎饼果子";
    private Double price = 5.0;

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public Double getPrice() {
        return price;
    }
}
